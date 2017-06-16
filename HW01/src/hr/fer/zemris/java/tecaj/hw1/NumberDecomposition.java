package hr.fer.zemris.java.tecaj.hw1;
/**
 * The program calculates and prints the decomposition of given number onto prime factors.
 * Gets a natural number greater than 1 from command line.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class NumberDecomposition {
	/**
	 * Start point of program NumberDecomposition.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int input = Integer.parseInt(args[0]);
		if(input <= 1) {
			System.err.println("Invalid argument!");
			System.exit(-1);
		}
		PrimeDecomp(input);
	}
	/**
	 * Decomposes the number on prime factor and prints them on the screen.
	 * @param number Number to decompose.
	 */
	static void PrimeDecomp(int number) {
		int counter = 1;
		System.out.println("You requested decomposition of number "+number+" onto prime factors. Here they are:");
		for (int divisor = 2; divisor <= number; divisor++) {
			if(number % divisor == 0) {
				System.out.println(counter + ". " + divisor);
				number /= divisor;
				divisor--;
				counter++;
			}
		}
	}
}
