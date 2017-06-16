package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * IntegerStorageChange class is used for dispatching notifications to
 * registered observers when value in IntegerStorage is changed to a new one.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class IntegerStorageChange {

	/** Old value */
	private int currentValue;
	/** New value */
	private int newValue;
	/** Reference to the subject */
	private IntegerStorage istorage;
	
	/**
	 * Constructor for IntegerStorageChange. It accpets current value, 
	 * new value and reference to the subject.
	 * @param currentValue Old value.
	 * @param newValue New value.
	 * @param istorage The subject.
	 */
	public IntegerStorageChange(int currentValue, int newValue,
			IntegerStorage istorage) {
		this.currentValue = currentValue;
		this.newValue = newValue;
		this.istorage = istorage;
	}
	
	/**
	 * Returns old value.
	 * @return Old value.
	 */
	public int getCurrentValue() {
		return currentValue;
	}
	
	/**
	 * Returns new value.
	 * @return New value
	 */
	public int getNewValue() {
		return newValue;
	}
	
	/**
	 * Returns reference to the subject.
	 * @return The subject.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}
	
	
}
