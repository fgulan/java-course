package hr.fer.zemris.java.student0036479428.hw08.newton;

import java.util.Arrays;

/**
 * Class ComplexPolynomial represents polynomial of complex numbers. It only
 * stores factors for given polynomial and implements several methods for easy
 * using it.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ComplexPolynomial {

	/** Polynomial factors */
	private Complex[] factors;

	/**
	 * Constructor for ComplexPolynomial class. Creates complex polynomial based
	 * on given array of factors. Example of polynomial and factors: polynomial
	 * 3x^3+(2-i)x+1 has factors (3, 0, (2-1), 1) and in that order they must be
	 * entered.
	 * 
	 * @param factors
	 *            Polynomial factors.
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = Arrays.copyOf(factors, factors.length);
	}

	/**
	 * Returns order of a current polynomial.
	 * 
	 * @return Order of a current polynomial.
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Derives current polynomial.
	 * 
	 * @return First derivation of current polynomial.
	 */
	public ComplexPolynomial derive() {
		int order = this.factors.length - 1;
		Complex[] derivationFactors = new Complex[order];

		for (int i = 0; i < order; i++) {
			derivationFactors[i] = factors[i]
					.multiply(new Complex(order - i, 0));
		}

		return new ComplexPolynomial(derivationFactors);
	}

	/**
	 * Computes polynomial value for given complex number.
	 * 
	 * @param z
	 *            Complex number.
	 * @return Polynomial value at given point.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		Complex exp = Complex.ONE;

		for (int i = this.factors.length - 1; i >= 0; i--) {
			result = result.add(exp.multiply(this.factors[i]));
			exp = exp.multiply(z);
		}

		return result;
	}

	/**
	 * Multiplies current polynomial with given polynomial.
	 * 
	 * @param p
	 *            Polynomial to multiply with.
	 * @return New polynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] mulFactors = new Complex[this.factors.length
				+ p.factors.length - 1];

		for (int i = 0; i < mulFactors.length; i++) {
			mulFactors[i] = Complex.ZERO;
		}
		for (int i = 0; i < p.factors.length; i++) {
			for (int j = 0; j < this.factors.length; j++) {
				Complex result = p.factors[i].multiply(this.factors[j]);
				mulFactors[i + j] = mulFactors[i + j].add(result);
			}
		}
		return new ComplexPolynomial(mulFactors);
	}

	
	/**
	 * Returns formated string of ComplexPolynomial in form 
	 * (z1)z^n + ... + (zn-1)z^1 + zn
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int order = this.factors.length - 1;
		
		for (int i = 0; i < this.factors.length; i++) {
			if(order -i == 0) {
				builder.append("(" + factors[i].toString() + ")");
				break;
			}
			builder.append("(" + factors[i].toString() + ")z^" + (order - i) + " + ");
		}

		return builder.toString();

	}
	
	
}
