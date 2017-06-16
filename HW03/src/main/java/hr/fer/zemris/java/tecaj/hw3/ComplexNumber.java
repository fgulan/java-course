package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class ComplexNumber implements representation of a complex number and defines complex
 * arithmetic and mathematical functions. The ComplexNumber object is immutable, so once
 * created complex number cannot be changed.
 * 
 * <p>The class <code>ComplexNumber</code> include several basic math methods:
 * for adding, subtracting, multiplication and division two complex numbers.
 * Also it implements function for computing nth root and nth power of complex
 * number.
 * 
 * <p> Complex number can be created from real and imaginary part, or from given magnitude
 * and angle or also from a string (description what is acceptable as input is given in 
 * javadoc of a method parse()).
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	private double magnitude;
	private double angle;
	
	/**
	 * Creates a new complex number from given input parameters.
	 * @param real The real part of a complex number.
	 * @param imaginary The imaginary part of a complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		magnitude = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		angle = Math.atan2(imaginary,real);
	}
	
	/**
	 * Creates and returns new complex number only with real part of it, where imaginary part is equal to zero.
	 * @param real The real part of a complex number.
	 * @return A new complex number with real part.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates and returns new complex number only with imaginary part of it, where real part is equal to zero.
	 * @param imaginary The imaginary part of a complex number.
	 * @return A new complex number with imaginary part.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Creates and returns new complex number from given magnitude and angle.
	 * @param magnitude Magnitude of a complex number.
	 * @param angle Angle of a complex number.
	 * @return A new complex number created from given magnitude and angle.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude*Math.cos(angle), magnitude*Math.sin(angle));
	}
	
	/**
	 * Parse input string and creates from it a new complex number.
	 * <p>Appropriate forms are:
	 * <b>a+bi, a, bi </b>, where a, b are real numbers.
	 * @param s Input complex number as a string.
	 * @return A new complex number created from input string.
	 */
	public static ComplexNumber parse(String s) {
		//remove all empty spaces in input string
		String input = s.replaceAll("\\s*", "");
		
		//Cheks if input argument is valid complex number else throw an exception
		if(!isValidArgument(input)) {
			throw new ComplexNumberException("Cannot parse as a complex number! Please check input string.");
		}
		
		//if there is only imaginary part without number in front
		if(input.equals("i") || input.equals("-i") || input.equals("+i")) {
			input = input.replaceAll("i", "1i");
		}
		
		//Set pattern for getting real and imaginary part from input string
		Pattern patternReal = Pattern.compile("[-+]?\\d+\\.?\\d*(?![.])(?![A-Za-z])(?![0-9])");
		Pattern patternImaginary = Pattern.compile("[+-]?\\d+?\\.?\\d*?[i]");
		                                            
		double real = 0;
		Matcher matchReal = patternReal.matcher(input);
		if(matchReal.find()) {
			real = Double.parseDouble(matchReal.group(0));	
			//if there is a more of one real number in input string throw an exception
			if(matchReal.find()) {
				throw new ComplexNumberException("Cannot parse as a complex number! Please check input string.");
			}
		}
		
		double imaginary = 0;
		Matcher matchImaginary= patternImaginary.matcher(input);
		if(matchImaginary.find()) {
			imaginary = Double.parseDouble(matchImaginary.group(0).replace("i", ""));
		}

		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Returns a real part of the complex number.
	 * @return Real part of the complex number.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns a imaginary part of the complex number
	 * @return Imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns a magnitude of the complex number.
	 * @return Magnitude of the complex number.
	 */
	public double getMagnitude() {
		return magnitude;
	}
	
	/**
	 * Returns an angle of the complex number.
	 * @return Angle of the complex number.
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Checks if input string is in a form of a valid complex number.
	 * @param s Input string to check.
	 * @return <code>true</code> if input string is in a valid form of a complex number, <code>false</code> otherwise.
	 */
	private static boolean isValidArgument(String s) {
		int numberOfOperators=0;
		
		for (int i = 0, length = s.length(); i < length; i++) {
			if(s.charAt(i) != 'i' && !isOperator(s.charAt(i)) &&
			   s.charAt(i) != '.' && !Character.isDigit(s.charAt(i))) {
				return false;
			}
			if ((s.charAt(i) == 'i' && i != length-1) || (isOperator(s.charAt(i)) && i == length-1) ||
			   (isOperator(s.charAt(i)) && isOperator(s.charAt(i+1)))) {
				return false;
			}
			if(isOperator(s.charAt(i))) {
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
	 * @param c Input character to check.
	 * @return <code>true</code> if input character is '+' or '-', <code>false</code> otherwise.
	 */
	private static boolean isOperator(char c) {
		if(c == '+' || c == '-') {
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a current complex number and input complex number and returns the result as a new complex number.
	 * @param c Complex number to add.
	 * @return The sum of current and input complex number as a new complex number.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.getReal(), this.imaginary + c.getImaginary());
	}
	
	/**
	 * Multiplies current complex number and input complex number and returns the result as a new complex number.
	 * @param c Complex number to multiply with.
	 * @return The product of current and input complex number as a new complex number.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double real = this.real*c.getReal()-this.imaginary*c.getImaginary();
		double imaginary =  this.real*c.getImaginary()+this.imaginary*c.getReal();
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Divides current complex number with input complex number and returns the result as a new complex number.
	 * @param c Complex number to divide with.
	 * @return The result of division of current and input complex number as a new complex number.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c.getMagnitude() == 0.0) {
			throw new ComplexNumberException("Division by zero! Unable to divde complex numbers!");
		}
		double real = this.real*c.getReal() + this.imaginary*c.getImaginary();
		double imaginary =  this.imaginary*c.getReal() - this.real*c.getImaginary();
		double div = Math.pow(c.getMagnitude(), 2);
		return new ComplexNumber(real/div, imaginary/div);
	}

	/**
	 * Subtracts input complex number from current complex and returns the result as a new complex number.
	 * @param c Complex number to add.
	 * @return The result of subtracting current and input complex number as a new complex number.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.getReal(), this.imaginary - c.getImaginary());
	}
	
	/**
	 * Computes a nth power of current complex number and returns the result as a new complex number.
	 * n is natural number greater or equal to 0.
	 * @param n nth power of a complex number.
	 * @return Current number at power of nth as a new complex number.
	 * @throws ComplexNumberException If input parameter is less than 0.
	 */
	public ComplexNumber power(int n) {
		if (n == 0) {
			return new ComplexNumber(1, 0);
		} else if (n == 1) {
			return new ComplexNumber(this.real, this.imaginary);
		} else if (n >= 2) {
			double powMagnitude = Math.pow(magnitude, n);
			double real = powMagnitude*Math.cos(n*angle);
			double imaginary = powMagnitude*Math.sin(n*angle);
			return new ComplexNumber(real, imaginary);
		} else {
			throw new ComplexNumberException("Unable to compute power of a complex number! Please check input argument!");
		}
	}

	/**
	 * Computes nth root of current complex number and returns the results as array of new complex numbers.
	 * n is natural number greater than 1.
	 * @param n nth root to compute.
	 * @return Array of new complex numbers.
	 * @throws ComplexNumberException If input parameter is equal or less than 1.
	 */
	public ComplexNumber[] root(int n) {
		if ( n < 2) {
			throw new ComplexNumberException("Unable to compute roots of a complex number! Please check input argument!");
		}
		ComplexNumber[] array = new ComplexNumber[n];
		double magnitudeRoot = Math.pow(magnitude, (1./n));
		double real, imaginary;
		for (int i = 0; i < n; i++) {
			real = magnitudeRoot * Math.cos((angle + 2*Math.PI*i)/n);
			imaginary = magnitudeRoot * Math.sin((angle + 2*Math.PI*i)/n);
			array[i] = new ComplexNumber(real,imaginary);
		}
		return array;
	}
	
	@Override
	public String toString(){
		DecimalFormat formatImaginary = new DecimalFormat(" + #.###i; - #.###i");
		DecimalFormat formatReal = new DecimalFormat("#.###;#.###");
		
		return formatReal.format(real) + formatImaginary.format(imaginary);
	}

}
