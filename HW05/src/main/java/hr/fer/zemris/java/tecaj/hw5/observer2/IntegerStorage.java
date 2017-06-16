package hr.fer.zemris.java.tecaj.hw5.observer2;

import java.util.LinkedList;
import java.util.List;

/**
 * IntegerStorage class stores given integer value and has ability
 * to register {@link IntegerStorageObserver} observers and notify
 * them when stored value changes.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class IntegerStorage {

	/** Integer value */
	private int value;
	/** List of observers */
	private List<IntegerStorageObserver> observers;
	
	/**
	 * Constructor of IntegerStorage class. Creates IntegerStorage object
	 * with given value.
	 * @param initialValue The inital value.
	 */
	public IntegerStorage(int initialValue) {
		observers = new LinkedList<IntegerStorageObserver>();
		this.value = initialValue;
	}
	
	/**
	 * Adds a new observer if it is not already added.
	 * @param observer Observer to add
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(observer != null && !observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes given observer.
	 * @param observer Observer to remove.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Removes all observers.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Returns the current value.
	 * @return Current value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the new value and notify all observers.
	 * @param value New value.
	 */
	public void setValue(int value) {
		IntegerStorageChange change = new IntegerStorageChange(this.value, value, this);
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if(observers!=null) {
				for (int i = 0, endSize = observers.size(); i < endSize; i++) {
					observers.get(i).valueChanged(change);
					if(endSize != observers.size()) {
						i--;
						endSize = observers.size();
					}
				}
			}
		}
	}
}
