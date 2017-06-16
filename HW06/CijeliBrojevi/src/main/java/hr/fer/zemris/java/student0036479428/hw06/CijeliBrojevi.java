package hr.fer.zemris.java.student0036479428.hw06;

/**
 * Razred s nizom metoda koje provjeravaju osnovna svojstva cijelog broja, tj.
 * parnost te je li dani broj prost.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CijeliBrojevi {

	/**
	 * Provjerava je li dani broj paran.
	 * @param broj Broj kojeg provjeravamo.
	 * @return <code>true</code> ukoliko je dani broj paran, <code>false</code> inače.
	 */
	public static boolean jeNeparan(int broj) {
		return broj % 2 != 0;
	}
	
	/**
	 * Provjerava je li dani broj neparan.
	 * @param broj Broj kojeg provjeravamo.
	 * @return <code>true</code> ukoliko je dani broj neparan, <code>false</code> inače.
	 */
    public static boolean jeParan(int broj) {
    	return broj % 2 == 0;
    }
    
    /**
	 * Provjerava je li dani broj prost.
	 * @param broj Broj kojeg provjeravamo
	 * @return <code>true</code> ukoliko je dani broj prost, <code>false</code> inače.
	 */
    public static boolean jeProst(int broj) {
		if (broj == 2 || broj == 3){
			return true;
		} else if (broj % 2 == 0 || broj % 3 == 0) {
			return false;
		} else {
			double numSqrt = Math.floor(Math.sqrt(broj));
			for (int i = 5; i <= numSqrt; i +=6) {
				if(broj % i == 0 || broj % (i+2) == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
