package hr.fer.zemris.java.tecaj.hw1;
/**
 * Program computes nth element of Hofstadter's Q sequence.
 * Gets a natural number from command line.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class HofstadterQ {
	/**
	 * Start point of the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int i = Integer.parseInt(args[0]);		
		if(i <= 0) System.err.println("Argument must be greater than 0!");
		else 
			System.out.println("You requested calculation of " +i+". number of Hofstadter's Q-sequence. The requested number is "+HofQ(i)+"." );
	}
	/**
	 * Compute the nth element of Hofstadter's Q sequence.
	 * @param n ordinal number of element to be found.
	 * @return nth element of Hofstadter's Q sequence.
	 */
	static long HofQ(int n) {
		long[] Hof = new long[n];
		Hof[0] = Hof[1] = 1;
		
		for(int i = 2; i < Hof.length; i++) {
			Hof[i] = Hof[(int) (i - Hof[i-1])] + Hof[(int) (i - Hof[i-2])];
		}
		return Hof[n-1];
	}
}
