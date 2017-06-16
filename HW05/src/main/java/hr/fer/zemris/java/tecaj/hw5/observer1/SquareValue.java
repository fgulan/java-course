package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * SquareValue is implementation of {@link IntegerStorageObserver} interface.
 * Calculates square value of {@link IntegerStorage} new value on every change.
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
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ", square is " + value*value);
	}

}
