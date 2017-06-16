package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;

/**
 * IntegerSequence is a class which implements iteration trough 
 * integer sequence from the start index till the end index using given step.
 * 
 * <p>IntegerSequence implements Iterable interface so it can be used in for each loop.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class IntegerSequence implements Iterable<Integer> {

	private int startIndex;
	private int endIndex;
	private int step;
	
	/**
	 * Creates a new iterable IntegerSequence.
	 * @param startIndex Start of a sequence.
	 * @param endIndex End of a sequence.
	 * @param step Given step.
	 * @throws IntegerSequenceException If step is equal to 0, or start index and end index and given step would go to infinite loop.
	 */
	public IntegerSequence(int startIndex, int endIndex, int step) {
		if(step == 0) {
			throw new IntegerSequenceException("Step cannot be a 0!");
		}
		if (startIndex < endIndex && step < 0) {
			throw new IntegerSequenceException("If end index is bigger than start index then step must be a natural number greater than 0!");
		}
		if (startIndex > endIndex && step > 0) {
			throw new IntegerSequenceException("If start index is bigger than end index then step must be a number less than 0!");
		}
		
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.step = step;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new numberIterator();
	}
	
	/**
	 * This private class implements number iterator for integer sequence.
	 * 
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	private class numberIterator implements Iterator<Integer> {
		private int current;
		
		/**
		 * Creates a new numberIterator.
		 */
		public numberIterator() {
			current = startIndex;
		}
		
		@Override
		public boolean hasNext() {
			//If start index is bigger than end index
			if(step < 0) {
				return current >= endIndex;
			}
			//If end index is bigger than start index
			return current <= endIndex;
		}

		@Override
		public Integer next() {
			int result = current;
			current += step;
			return result;
		}
	}
}


