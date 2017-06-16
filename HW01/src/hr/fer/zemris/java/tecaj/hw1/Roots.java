package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;
/**
 * Program compute Nth root of a given complex number.
 * Gets data from command line where first argument is real part, second imaginary part and third root to calculate (natural number greater than 1) of a complex number.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class Roots {
	/**
	 * Start point of program Roots.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int root = 0;
		double real = 0, imaginary = 0;
		
		if(args.length == 3 && (root = Integer.parseInt(args[2])) > 1) {
			real = Double.parseDouble(args[0]);
			imaginary = Double.parseDouble(args[1]);
		} else {
			System.err.println("Invalid argument!");
			System.exit(-1);
		}

		getRoot(real, imaginary, root);
	}
	/**
	 * Compute nth root of complex number.
	 * Method prints on the screen all nth roots of the given complex number rounded to three decimal places.
	 * @param Real Real part of complex number.
	 * @param Imaginary Imaginary part of complex number.
	 * @param root nth root of complex number.
	 */
	static void getRoot(double Real, double Imaginary, int root) {
		DecimalFormat formatImaginary = new DecimalFormat(" + #.###i; - #.###i");
		DecimalFormat formatReal = new DecimalFormat("#.###");
		double Modul = Math.sqrt(Math.pow(Real, 2) + Math.pow(Imaginary, 2));
		double Argument = Math.atan2(Imaginary,Real);
		double x, y;
		
		System.out.println("You requested calculation of "+ root+ ". roots. Solutions are:");
		for (int i = 0; i < root; i++) {
			x = Math.pow(Modul,(1.0/root)) * Math.cos((Argument + 2*Math.PI*i)/root );
			y = Math.pow(Modul, (1.0/root)) * Math.sin((Argument + 2*Math.PI*i)/root );
			System.out.println((i+1)+") " + formatReal.format(x) + formatImaginary.format(y));
		}		
	}
}
