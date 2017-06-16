package hr.fer.zemris.java.tecaj.hw3.demo;

import hr.fer.zemris.java.tecaj.hw3.IntegerSequence;
/**
 * Program which tests IntegerSequence functionality using foreach loop.
 * Program does not use a command line arguments.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class IntegerSequenceDemo {

	/**
	 * Start point of a program.
	 * @param args Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		IntegerSequence range = new IntegerSequence(1, 11, 2);
		
		for(int i : range) {
			for(int j : range) {
				System.out.println("i="+i+", j="+j);
			}
		}
	}
}
