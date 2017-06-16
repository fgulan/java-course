package hr.fer.zemris.java.student0036479428.hw08.newton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Class Complex implements representation of a complex number and defines
 * complex arithmetic and mathematical functions. The Complex object is
 * immutable, so once created complex number cannot be changed.
 * 
 * <p>
 * The class <code>Complex</code> include several basic math methods: for
 * adding, subtracting, multiplication and division two complex numbers.
 * 
 * <p>
 * The class <code>Complex</code> also includes several static constant complex
 * numbers like representation for "0 + 0i", "1 + 0i", "-1 + 0i", "0 + 1i" and
 * "0 - 1i".
 * 
 * <p>
 * Complex number can be created from a string (description what is acceptable
 * as input is given in javadoc of a method parse()).
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Complex {

	/** Real part of a number. */
	private double re;
	/** Imaginary part of a number. */
	private double im;
	/** Minimal value before treated as zero. */
	private static final double MIN_NUMBER = 1e-15;
	/** "0 + 0i" complex number. */
	public static final Complex ZERO = new Complex(0, 0);
	/** "1 + 0i" complex number. */
	public static final Complex ONE = new Complex(1, 0);
	/** "-1 + 0i" complex number. */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** "0 + 1i" complex number. */
	public static final Complex IM = new Complex(0, 1);
	/** "0 - 1i" complex number. */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Creates a new complex number with real and imaginary part equals to zero.
	 */
	public Complex() {
		this(0, 0);
	}

	/**
	 * Creates a new complex number from given input parameters.
	 * 
	 * @param re
	 *            The real part of a complex number.
	 * @param im
	 *            The imaginary part of a complex number.
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Calculates and returns module for current complex number.
	 * 
	 * @return Module of current complex number.
	 */
	public double module() {
		double module = Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
		return module;
	}

	/**
	 * Multiplies current complex number and input complex number and returns
	 * the result as a new complex number.
	 * 
	 * @param c
	 *            Complex number to multiply with.
	 * @return The product of current and input complex number as a new complex
	 *         number.
	 */
	public Complex multiply(Complex c) {
		double real = this.re * c.re - this.im * c.im;
		double imaginary = this.re * c.im + this.im * c.re;
		return new Complex(real, imaginary);
	}

	/**
	 * Divides current complex number with input complex number and returns the
	 * result as a new complex number.
	 * 
	 * @param c
	 *            Complex number to divide with.
	 * @return The result of division of current and input complex number as a
	 *         new complex number.
	 */
	public Complex divide(Complex c) {
		if (c.module() <= MIN_NUMBER) {
			throw new IllegalArgumentException(
					"Division by zero! Unable to divde complex numbers!");
		}
		double real = this.re * c.re + this.im * c.im;
		double imaginary = this.im * c.re - this.re * c.im;
		double div = Math.pow(c.module(), 2);
		return new Complex(real / div, imaginary / div);
	}

	/**
	 * Adds a current complex number and input complex number and returns the
	 * result as a new complex number.
	 * 
	 * @param c
	 *            Complex number to add.
	 * @return The sum of current and input complex number as a new complex
	 *         number.
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Subtracts input complex number from current complex and returns the
	 * result as a new complex number.
	 * 
	 * @param c
	 *            Complex number to sub with.
	 * @return The result of subtracting current and input complex number as a
	 *         new complex number.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Negates current number.
	 * 
	 * @return Negated current number as new complex number.
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}

	/**
	 * Parse input string and creates a new complex number.
	 * <p>
	 * Appropriate forms are: <b>a+ib, a, ib </b>, where a and b are real
	 * numbers.
	 * 
	 * @param input
	 *            Input complex number as a string.
	 * @return A new complex number created from input string.
	 */
	public static Complex parse(String input) {
		input = input.replaceAll("\\s+", "");
		if (!isValidArgument(input)) {
			throw new IllegalArgumentException("Invalid input string!");
		}
		int size = input.length();
		boolean imFlag = false;
		StringBuilder realPart = new StringBuilder();
		StringBuilder imaginaryPart = new StringBuilder();

		for (int i = 0; i < size; i++) {
			char c = input.charAt(i);
			if (c == 'i' && i == size - 1) {
				imaginaryPart.append("1");
				break;
			}
			if (c == 'i') {
				imFlag = true;
				continue;
			}
			if (imFlag) {
				imaginaryPart.append(c);
			} else if (isOperator(c) && realPart.length() != 0) {
				imaginaryPart.append(c);
				imFlag = true;
			} else if (isOperator(c) && i < size - 1
					&& input.charAt(i + 1) == 'i') {
				imaginaryPart.append(c);
			} else {
				realPart.append(c);
			}
		}

		if (!input.contains("i") && imaginaryPart.length() != 0) {
			throw new IllegalArgumentException("Invalid input string!");
		}
		if (realPart.length() == 0) {
			realPart.append("0");
		}

		if (imaginaryPart.length() == 0) {
			imaginaryPart.append("0");
		}

		try {
			double real = Double.parseDouble(realPart.toString());
			double imaginary = Double.parseDouble(imaginaryPart.toString());
			return new Complex(real, imaginary);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid input string!");
		}
	}

	/**
	 * Checks if input string is in a form of a valid complex number.
	 * 
	 * @param s
	 *            Input string to check.
	 * @return <code>true</code> if input string is in a valid form of a complex
	 *         number, <code>false</code> otherwise.
	 */
	private static boolean isValidArgument(String s) {
		int length = s.length();
		if (s.endsWith("i") && !s.endsWith("-i") && !s.endsWith("+i")
				&& (length > 2 || (length == 2 && !isOperator(s.charAt(0))))) {
			return false;
		}
		int numberOfOperators = 0;
		for (int i = 0; i < length; i++) {
			if (s.charAt(i) != 'i' && !isOperator(s.charAt(i))
					&& s.charAt(i) != '.' && s.charAt(i) != ','
					&& !Character.isDigit(s.charAt(i))) {
				return false;
			}
			if (isOperator(s.charAt(i))) {
				if (numberOfOperators == 2) {
					return false;
				}
				numberOfOperators++;
			}
		}
		return true;
	}

	/**
	 * Checks if input character is '+' or '-' math operator.
	 * 
	 * @param c
	 *            Input character to check.
	 * @return <code>true</code> if input character is '+' or '-',
	 *         <code>false</code> otherwise.
	 */
	private static boolean isOperator(char c) {
		if (c == '+' || c == '-') {
			return true;
		}
		return false;
	}

	/**
	 * Returns formated string of complex number in form
	 * a(+/-)ib.
	 */
	@Override
	public String toString() {
		DecimalFormat formatImaginary = new DecimalFormat("+i#.##;-i#.##");
		DecimalFormat formatReal = new DecimalFormat("#.##;#.##");
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		formatImaginary.setDecimalFormatSymbols(custom);
		formatReal.setDecimalFormatSymbols(custom);

		return formatReal.format(this.re) + formatImaginary.format(this.im);
	}
}
