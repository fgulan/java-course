package hr.fer.zemris.java.tecaj.hw1;
/**
 * Implementation of binary search tree with methods to add data, count the number of elements in the tree, find the element and print.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class ProgramStabla {
	/**
	 * Tree node with data type of String and reference to the left and right child.
	 * @author Filip Gulan
	 */
	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}
	/**
	 * Start point of ProgramStabla.
	 * @param args Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;
		
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");
		
		System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);

		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: "+vel);
		 
		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronaden: "+pronaden);
	}
	/**
	 * Adds the data in the tree.
	 * Compared to the root, lexicographical smaller element goes to the left and greater to the right side of root, 
	 * @param Reference to the root. 
	 * @param podatak Data to add in the tree.
	 * @return Reference to the root.
	 */
	static CvorStabla ubaci(CvorStabla korijen, String podatak) {
		if(korijen == null) {
			CvorStabla cvor = new CvorStabla();
			cvor.podatak = podatak;
			cvor.desni = cvor.lijevi = null;
			return cvor;
		} else if (korijen.podatak.compareToIgnoreCase(podatak) > 0) {
			korijen.lijevi = ubaci(korijen.lijevi, podatak);
		} else {
			korijen.desni = ubaci(korijen.desni, podatak);
		}
		return korijen;
	}
	/**
	 * Returns true if and only if tree contains the specified data.
	 * @param korijen Reference to the root.
	 * @param podatak Data to search for.
	 * @return <code>true</code> if tree contains <code>podatak</code>, otherwise <code>false</code>
	 */
	static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
		if (korijen == null) return false;
		
		int cmp = podatak.compareToIgnoreCase(korijen.podatak);
		
		if(cmp > 0) return sadrziPodatak(korijen.desni, podatak);
		else if (cmp < 0) return sadrziPodatak(korijen.lijevi, podatak);
		else return true;
	}
	/**
	 * Returns the number of elements in the tree.
	 * @param cvor Reference to the root.
	 * @return Number of elements in the tree.
	 */
	static int velicinaStabla(CvorStabla cvor) {
		if (cvor == null) return 0;
		return 1 + velicinaStabla(cvor.lijevi) + velicinaStabla(cvor.desni);
	}
	/**
	 * Prints the tree in inorder.
	 * @param cvor Reference to the root.
	 */
	static void ispisiStablo(CvorStabla cvor) {
		if(cvor != null) {
			ispisiStablo(cvor.lijevi);
			System.out.println(cvor.podatak);
			ispisiStablo(cvor.desni);
		}
	}
}
