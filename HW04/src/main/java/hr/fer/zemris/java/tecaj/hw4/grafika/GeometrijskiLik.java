package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred roditelj za sve tipove geometrijskih likova. Svako dijete razred
 * mora implementirati metodu sadrziTocku() koja ce vraćati da li dobivena 
 * točka pripada tom liku. Razred također implementira metodu popuniLik() 
 * koja zadani lik iscrtava na dobivenu sliku. Metoda se kreće piksel po
 * piksel po cijeloj slici i provjerava da li je navedeni piksel sadržan u 
 * geometrijskom liku. Ukoliko postoji bolji način za iscrtavanje lika, korisnika
 * se savjetuje da metodu popuniLik() pregazi svojom implementacijom.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public abstract class GeometrijskiLik {

	/**
	 * Provjerava je li točka sa danim koordinatama sadržana u geometrijskom liku.
	 * @param x Koordinata na x-osi(horizontalna).
	 * @param y Koordinata na y-osi(vertikalna).
	 * @return <code>true</code> ako je dana točka sadržana u geometrijskom liku,
	 * <code>false</code> inače.
	 */
	public abstract boolean sadrziTocku(int x, int y);
	
	/**
	 * Iscrtava navedeni lik na dobivenu sliku.
	 * @param slika Slika na koju se iscrtava.
	 */
	public void popuniLik(Slika slika) {
		for (int y = 0, visina = slika.getVisina(), sirina = slika.getSirina(); y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (this.sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}
