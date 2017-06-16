package hr.fer.zemris.java.student0036479428.hw08.newton;

import java.util.Arrays;

/**
 * Class ComplexRootedPolynomial represents polynomial of complex numbers in
 * form of roots.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class ComplexRootedPolynomial {

	/** Roots of current polynomial */
	private Complex[] roots;

	/**
	 * Constructor for ComplexRootedPolynomial class. It accepts array of roots
	 * for complex polynomial. Example: polynomial (x-1)(x+2)(x-(3+i)) have
	 * three roots and they must be entered in this form (1, -2, 3+1).
	 * 
	 * @param roots
	 *            Roots of a polynomial.
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = Arrays.copyOf(roots, roots.length);
	}

	/**
	 * Computes polynomial value for given complex number.
	 * 
	 * @param z
	 *            Complex number.
	 * @return Polynomial value at given point.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;

		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}

	/**
	 * Returns order of a current polynomial.
	 * 
	 * @return Order of a current polynomial.
	 */
	public int order() {
		return this.roots.length;
	}

	/**
	 * Transforms complex rooted polynomial to ordinary complex polynomial with
	 * factors.
	 * 
	 * @return New ComplexPolynomial object based on current rooted polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);

		for (Complex root : roots) {
			result = result.multiply(new ComplexPolynomial(Complex.ONE, root
					.negate()));
		}
		return result;
	}

	/**
	 * Finds index of closest root for given complex number that is within
	 * treshold. If there is no such root, returns -1.
	 * 
	 * @param z
	 *            Complex number.
	 * @param treshold
	 *            Treshold.
	 * @return Index of closest root for given complex. If there is no such
	 *         root, returns -1.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double minimalDistance = z.sub(roots[0]).module();

		for (int i = 0; i < roots.length; ++i) {
			double distance = z.sub(roots[i]).module();
			if (Double.compare(distance, treshold) < 0
					&& Double.compare(distance, minimalDistance) <= 0) {
				minimalDistance = distance;
				index = i;
			}
		}
		return index;
	}

	/**
	 * Returns formated string of ComplexRootedPolynomial in form 
	 * [z - (z1)][z - (z2)]...
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < roots.length; i++) {
			builder.append("[z - (" + roots[i].toString() + ")]");
		}
		return builder.toString();
	}

}
