package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Parsira ulazni niz znakova na razdvojene brojeve tipa integer.
 * Konstruktor kao arugment prima niz znakova u obliku npr.
 * "10 28 98 35" te rastavlja niz znakova dan u primjeru na brojeve
 * 10, 28, 98 i 35.
 * 
 * <p>Argument mora sadržavati niz znakova u kojem se nalaze cijeli brojevi 
 * razdvojeni jednim ili više whitespace-a. Ukoliko niz sadrži slova ili
 * racionalne brojeve objekt baca iznimku.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ParserArgumenata {
	
	/** Niz znakova koji sadrži brojeve */
	String argumenti;
	/** Niz brojeva dobivenih iz niza znakova */
	int[] poljeArgumenata;
	
	/**
	 * Konstruktor razreda ParserArgumenata.
	 * @param argumenti Znakovni niz koji se treba parsirati.
	 */
	public ParserArgumenata(String argumenti) {
		this.argumenti = argumenti;
		parsiraj();
	}
	
	/**
	 * Parsira dani ulazni znakovni niz.
	 * @throws IllegalArgument Exception Ukoliko ulazni niz nije ispravan, tj. ne sadrži 
	 * samo cijele brojeve
	 */
	private void parsiraj() {
		String[] temp = argumenti.split("\\s+");
		poljeArgumenata = new int[temp.length];
		
		for(int i = 0; i < temp.length; i++) {
			try {
				poljeArgumenata[i] = Integer.parseInt(temp[i]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Neispravan ulazni argument! "
						+ "Problem pri parisanju niza " + "\"" + temp[i] + "\"");
			}
		}
	}
	
	/**
	 * Vraća količinu brojeva dobivenih parsiranjem.
	 * @return Količinu brojeva dobivenih parsiranjem.
	 */
	public int brojArgumenata() {
		return poljeArgumenata.length;
	}
	
	/**
	 * Vraća polje parsiranih brojeva.
	 * @return Polje parsiranih brojeva tipa int.
	 */
	public int[] getPoljeArgumenata() {
		return poljeArgumenata;
	}
}
