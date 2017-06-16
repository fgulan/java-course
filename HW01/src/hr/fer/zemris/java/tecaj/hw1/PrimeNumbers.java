package hr.fer.zemris.java.tecaj.hw1;
/**
 * Program prints first n prime numbers.
 * Gets a natural number from command line.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class PrimeNumbers {
	/**
	 * Start point of program PrimeNumbers.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int input = Integer.parseInt(args[0]);
		if (input <= 0) {
			System.err.println("Invalid argument!");
			System.exit(-1);
		}
		printPrime(input);		
	}
	/**
	 * Prints first n prime numbers.
	 * Method finds first n prime numbers and print them on a screen.
	 * @param numberOfPrime Count of prime numbers.
	 */
	static void printPrime(int numberOfPrime) {
		int number = 2;
		for(int i = 1; i <= numberOfPrime;) {
			if(isPrime(number)) {
				System.out.println(i+ ". " +number);
				i++;
			}
			number++;
		}
	}
	/**
	 * Check if number is prime.
	 * @param number Number to check.
	 * @return <code>true</code> if number is prime, otherwise <code>false</code>
	 */
	static boolean isPrime(int number) {
		if (number == 2 || number == 3) return true;
		else if (number % 2 == 0 || number % 3 == 0) return false;
		else {
			double numSqrt = Math.floor(Math.sqrt(number));
			for (int i = 5; i <= numSqrt; i +=6) {
				if(number % i == 0 || number % (i+2) == 0) return false;
			}
		}
		return true;
	}
}
