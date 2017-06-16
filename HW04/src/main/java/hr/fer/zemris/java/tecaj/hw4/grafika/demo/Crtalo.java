package hr.fer.zemris.java.tecaj.hw4.grafika.demo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.Elipsa;
import hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kruznica;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kvadrat;
import hr.fer.zemris.java.tecaj.hw4.grafika.Linija;
import hr.fer.zemris.java.tecaj.hw4.grafika.Pravokutnik;
import hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Program Crtalo koristi dobivenu vanjsku biblioteku "prikaz.jar" te
 * pomoću nje iscrtava geometrijske likove dobivene iz ulazne datoteke.
 * 
 * <p>Kao argument program prihvaća path do ulazne datoteke koja sadrži
 * geometrijske likove koje treba iscrtati i dimenziju slike, 
 * npr "dat.txt 400 400". Datoteka mora sadržavati
 * naziv geometrijskog lika, položaj na slici i dimenzije, npr.
 * "PRAVOKUTNIK 10 15 100 150". Ukoliko se želi iscrtati više likova
 * odjednom tada svaki lik treba odvojiti novim retkom.
 * 
 * @author Filip Gulan
 * @version 1.0
 * 
 */
public class Crtalo {

	/**
	 * Početna točka programa Crtalo.
	 * @param args Argumenti komandne linije.
	 * @throws IOException Ukoliko postoji problem s čitanjem ulazne datoteke.
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 3) {
			System.out.println("Neispravan broj ulaznih argumenata! "
					+ "Unesite putanju do datoteke i dimenziju slike!");
			System.exit(-1);
		}
		
		String[] definicije = null;
		try {
			definicije = Files.readAllLines(
					Paths.get(args[0]), StandardCharsets.UTF_8).toArray(new String[0]);
		} catch (NoSuchFileException e) {
			System.out.println("Program ne može pronaći putanju: " + e.getMessage());
			System.exit(-1);
		}
		
		SimpleHashtable stvaratelji = null;
		try {
			stvaratelji = podesi(Linija.class, Pravokutnik.class, 
					 Elipsa.class, Kruznica.class, Kvadrat.class);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		GeometrijskiLik[] likovi = izradiLikove(definicije, stvaratelji);
		
		Slika slika = null;
		try {
			slika = new Slika(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		} catch (NumberFormatException e) {
			System.out.println("Neispravni ulazni argumenti visine i širine!");
			System.exit(-1);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		for (GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		
		PrikaznikSlike.prikaziSliku(slika);
	}
	
	/**
	 * Stvara polje geometrijskih likova na temelju ulazne definicije likova
	 * i podržanih stvaratelja.
	 * @param definicije Ulazna definicija likova.
	 * @param stvaratelji Hash tablica s razredima podržanih geometrijskih likova.
	 * @return Polje stvorenih geometrijskih likova.
	 */
	private static GeometrijskiLik[] izradiLikove(String[] definicije, SimpleHashtable stvaratelji) {
		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		
		for (int index = 0; index < definicije.length; index++) {			
			try {
				String lik = definicije[index].substring(0, definicije[index].indexOf(" "));
				String parametri = definicije[index].substring(definicije[index].indexOf(" ")+1, definicije[index].length());
				StvarateljLika stvaratelj = (StvarateljLika) stvaratelji.get(lik);
				likovi[index] = stvaratelj.stvoriIzStringa(parametri);
			} catch (IllegalArgumentException e) {
				System.out.println("Greška u ulaznoj datoteci! Linija: \"" + (definicije[index]) + "\". " + e.getMessage());
				System.exit(-1);
			} catch (NullPointerException e) {
				System.out.println("Greška u ulaznoj datoteci! Linija: \"" + (definicije[index]) + "\". " + "Ne postoji lik s navedenim imenom!");
				System.exit(-1);
			} catch (Exception e) {
				System.out.println("Neočekivana greška!");
				System.exit(-1);
			}
		}
		
		return likovi;
	}
	
	/**
	 * Stvara hash tablicu stvaratelja razreda dobivenih kao ulazni argument.
	 * @param razredi Niz razreda koje želimo dodati u hash tablicu.
	 * @return Hash tablica popunjena stvarateljima danih razreda.
	 * @throws RuntimeException Ukoliko dani razred nije implementirao i definirao
	 * sučelje StvarateljLika.
	 */
	private static SimpleHashtable podesi(Class<?> ... razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		for(Class<?> razred : razredi) {
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika)field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch(Exception ex) {
				throw new IllegalArgumentException(
					"Nije moguće doći do stvaratelja za razred "+
					razred.getName()+".", ex);
			}
		}
		return stvaratelji;
	}
}
