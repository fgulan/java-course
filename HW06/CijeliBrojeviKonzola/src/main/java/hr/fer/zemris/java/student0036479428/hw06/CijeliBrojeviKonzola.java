package hr.fer.zemris.java.student0036479428.hw06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Program koji čita cijeli broj iz komandne linije te provjerava i ispisuje
 * njegova svojstva (parnost, je li prost). Navedena svojstva provjerava
 * uporabom metoda iz razreda CijeliBrojevi.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CijeliBrojeviKonzola {

	/**
	 * Početna točka programa CijeliBrojeviKonzola. Parametri komandne linije se ne koriste.
	 * @param args Parametri komandne linije.
	 * @throws IOException Problem s čitanjem s konzole.
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader ulaz = new BufferedReader(new InputStreamReader(System.in,
				StandardCharsets.UTF_8));

		while (true) {
			System.out.print("Unesite broj> "); 
			String brojString = ulaz.readLine();
			if(brojString.trim().equals("quit")) {
				System.exit(0);
			}
			int broj = 0;
			try {
				broj = Integer.parseInt(brojString);
			} catch (NumberFormatException e) {
				System.out.println("Neispravan unos!");
				continue;
			}
			System.out.print("Paran: " + (CijeliBrojevi.jeParan(broj) ? "DA" : "NE") + ", ");
			System.out.print("neparan: " + (CijeliBrojevi.jeNeparan(broj) ? "DA" : "NE") + ", ");
			System.out.println("prim: " + (CijeliBrojevi.jeProst(broj) ? "DA" : "NE"));
		}
	}

}
