package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred Elipsa je dijete razred razreda GeometrijskiLik.
 * Svaka elipsa je definiran s koordinatom središta elipse
 * te polumjerom velike i male poluosi.
 * 
 * <p>Razred Elipsa implementira svoju metodu popuniLik() koja je
 * učinkovitija od one dane u razredu GeometrijskiLik.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Elipsa extends GeometrijskiLik {
	
	/** Polumjer velike poluosi */
	private int a;
	/** Polumjer male poluosi */
	private int b;
	/** Koordinata x središta elipse */
	private int centarX;
	/** Koordinata y središta elipse */
	private int centarY;
	
	/**
	 * Konstruktor razreda Elipsa. Stvara lik elipse s danim dimenzijama.
	 * @param sredisteX Koordinata x središta elipse
	 * @param sredisteY Koordinata y središta elipse
	 * @param a Polumjer velike poluosi
	 * @param b Polumjer male poluosi
	 */
	public Elipsa(int sredisteX, int sredisteY, int a, int b) {
		this.a = a;
		this.b = b;
		this.centarX = sredisteX;
		this.centarY = sredisteY; 
	}
	
	/**
	 * Provjerava da li trenutn elipsa sadržava danu koordinatnu točku.
	 * @param x Kooridinatna točka na x-osi.
	 * @param y Kooridinatna točka na y-osi.
	 * @return Vraća <code>true</code> ukoliko trenutna elipsa sadržava danu
	 * točku, <code>false</code> inače.
	 */
	@Override
	public boolean sadrziTocku(int x, int y) {
		if(jednadzbaElipse(x, y) <= 1 || Math.abs(jednadzbaElipse(x, y)-1) < 1e-3) {
			return true;
		}
		return false;
	}

	/**
	 * Izračunava vrijednost jednadže elipse za dane vrijednosti x i y.
	 * @param x Kooridinatna točka na x-osi.
	 * @param y Kooridinatna točka na y-osi.
	 * @return Vrijednost jednadžbe u točkama x i y.
	 */
	private double jednadzbaElipse(int x, int y) {
		return (Math.pow((x-centarX), 2)/(a*a)) + (Math.pow((y-centarY), 2)/(b*b));
	}
	
	/**
	 * Popopunjava danu sliku s trenutnom elipsom. Metoda nadjačava 
	 * metodu popuniLik() iz razreda geometrijski lik s učinkovitijim
	 * i bržim algoritmom za iscrtavanje elipse.
	 * @param slika Slika na koju iscrtavamo trenutnu elipsu.
	 */
	@Override
	public void popuniLik(Slika slika) {		
		int yStart = Math.max(0, centarY-b);
		int yKraj = Math.min(slika.getVisina(), centarY+b);
		int xStart = Math.max(0, centarX-a);
		int xKraj = Math.min(slika.getSirina(), centarX+a);
		
		for (int x = xStart; x < xKraj; x++) {
			for (int y = yStart; y < yKraj; y++) {
				if(sadrziTocku(x, y)) {
					slika.upaliTocku(x,y);
				}
			}
		}
	}
	
	/** Konstanta stvaratelja lika */
	public static final StvarateljLika STVARATELJ = new ElipsaStvaratelj();
	
	/**
	 * Privatni razred koji implementira sučelje StvarateljLika.
	 * Dani razred stvara lik elipse s imenom "ELIPSA"
	 * i parametrima danim u metodi stvoriIzStringa();
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 * 
	 */
	private static class ElipsaStvaratelj implements StvarateljLika {
		
		/**
		 * Vraća naziv geometrijskog lika pravokutnika
		 * kao "PRAVOKUTNIK".
		 * @return Niz znakova "PRAVOKUTNIK".
		 */
		@Override
		public String nazivLika() {
			return "ELIPSA";
		}
		
		/**
		 * Stvara lik elipse s danim arugumentima. Metoda kao argument prima
		 * string koji sadrži koordinatnu točku središta elipse, te polumjer 
		 * velike i male poluosi, npr. u obliku "100 150 100 65". Ukoliko dimenzije i točka
		 * prelaze okvire slike iscrtat će se samo onaj dio koji bi bio vidljiv na dijelu slike od
		 * točke (0, 0) do točke (sirina-1, visina -1).
		 * @param parametri Točka središta elipse, te polumjer velike i male poluosi.
		 * @return Stvoreni geometrijski lik razreda Elipsa.
		 * @throws IllegalArgumentException Ukoliko su ulazni parametri neispravni.
		 */
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			ParserArgumenata parser = new ParserArgumenata(parametri);
			if(parser.brojArgumenata() != 4) {
				throw new IllegalArgumentException("Neispravan broj argumenata za lik elipse!");
			}
			int[] arg = parser.getPoljeArgumenata();
			if(arg[2] <= 0 || arg[3] <= 0) {
				throw new IllegalArgumentException("Polumjeri poluosi elipse moraju biti pozitivni cijeli brojevi!");
			}
			return new Elipsa(arg[0], arg[1], arg[2], arg[3]);
		}
	}

}
