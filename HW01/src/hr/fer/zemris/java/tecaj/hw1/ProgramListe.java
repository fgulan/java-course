package hr.fer.zemris.java.tecaj.hw1;
/**
 * Implementation of singly linked list with methods to add data, count the number of elements in the list, sort and print.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class ProgramListe {
	/**
	 * Linked list node with data type of String and reference to the next node.
	 * @author Filip Gulan
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	/**
	 * Start point of ProgramListe
	 * @param args Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: "+vel);
	}
	/**
	 * Count the number of elements in list-
	 * @param cvor Reference to the first node in the list.
	 * @return Number of elements in list.
	 */
	static int velicinaListe(CvorListe cvor) {
		int lenght=0;
		for(CvorListe tmp = cvor; tmp != null; tmp = tmp.sljedeci) lenght++;
		return lenght;
	}
	/**
	 * Adds data to the end of the list. 
	 * @param prvi Reference to the first node in the list.
	 * @param podatak Data to add to the list.
	 * @return Reference to the first node in the list.
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
		 CvorListe cvorListe = new CvorListe();
		 
		 cvorListe.podatak = podatak;
		 cvorListe.sljedeci = null;
		 if(prvi == null) prvi = cvorListe;
		 else {
			 CvorListe p = prvi;
			 while(p.sljedeci != null) p = p.sljedeci;
			 p.sljedeci = cvorListe;
		 }
		 return prvi;
	}
	/**
	 * Prints all elements from the list.
	 * @param cvor Reference to the first node in the list.
	 */
	static void ispisiListu(CvorListe cvor) {
		for(CvorListe tmp = cvor; tmp != null; tmp = tmp.sljedeci) System.out.println(tmp.podatak);
	}
	/**
	 * Sorts the list.
	 * Method sorts data in the list using Bubble sort algorithm.
	 * @param cvor Reference to the first node in the list.
	 * @return Reference to the first node in the list.
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		CvorListe temp = cvor;
		String podatak;
		boolean swaped = true;
		while(swaped) {
			swaped = false;
			while(temp.sljedeci != null) {
				if(temp.podatak.compareToIgnoreCase(temp.sljedeci.podatak) >= 0) {
					podatak = temp.podatak;
					temp.podatak = temp.sljedeci.podatak;
					temp.sljedeci.podatak = podatak;
					swaped = true;
				}
				temp = temp.sljedeci;
			}
			temp = cvor;
		}
		return cvor;
	}
}
