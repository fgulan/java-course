package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * SquareValue is implementation of {@link IntegerStorageObserver} interface.
 * Calculates square value of {@link IntegerStorageChange} new value on every change.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints the square value of a new value. 
	 * @param istorage The subject.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.println("Provided new value: " + value + ", square is " + value*value);
	}

}
