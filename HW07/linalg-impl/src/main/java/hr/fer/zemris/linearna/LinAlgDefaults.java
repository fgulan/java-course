package hr.fer.zemris.linearna;

/**
 * LinAlgDefault razred implementira statičke metode za izradu
 * predpostavljenih razreda Matrix i Vector.
 *  
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LinAlgDefaults {

	/**
	 * Stvara matricu ispunjenu nulama sa zadanim brojem
	 * redaka i stupaca.
	 * @param rows Broj redaka.
	 * @param cols Broj stupaca.
	 * @return Nova matrica.
	 */
	public static IMatrix defaultMatrix(int rows, int cols) {
		return new Matrix(rows, cols);
	}

	/**
	 * Stvara vektor ispunjen nulama sa zadanom dimenzijom.
	 * @param dimension Broj elemenata.
	 * @return Novi vektor.
	 */
	public static IVector defaultVector(int dimension) {
		double[] elems = new double[dimension];
		return new Vector(elems);
	}
}
