package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * ChangeCounter is implementation of {@link IntegerStorageObserver} interface.
 * Counts the number of value changes in {@link IntegerStorageChange} class.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** Changes counter */
	private int counter;

	/**
	 * Prints the number of value changes since the observer is created and added.
	 * @param istorage The subject.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}


}
