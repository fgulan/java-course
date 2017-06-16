package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * DoubleValue is implementation of {@link IntegerStorageObserver} interface.
 * Calculates double value of {@link IntegerStorageChange} new value on every change.
 * This observer de-registers itself from the subject after calculating the double 
 * value for the second time.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/** Changes counter */
	private int counter;
	
	/**
	 * Prints the new value multiplyed with 2. 
	 * @param istorage The subject.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		counter++;
		int value = istorage.getNewValue();
		System.out.println("Provided new value: " + value + ", double is " + 2*value);
		if(counter > 1) {
			istorage.getIstorage().removeObserver(this);
		}
	}

}
