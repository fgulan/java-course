package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Program used for testing observers.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class ObserverExample {

	/**
	 * Start point of a program ObserverExample.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver doubleValueObserver = new DoubleValue();
		IntegerStorageObserver squareValueObserver = new SquareValue();
		IntegerStorageObserver changeObserver = new ChangeCounter();
		
		istorage.addObserver(doubleValueObserver);
		istorage.addObserver(squareValueObserver);
		istorage.addObserver(changeObserver);
		
		istorage.setValue(4);
		istorage.setValue(7);
		istorage.setValue(6);
		istorage.setValue(3);
		istorage.setValue(3);
		istorage.setValue(7);
		istorage.removeObserver(changeObserver);
		
		istorage.addObserver(new ChangeCounter());
		istorage.setValue(11);
		istorage.setValue(12);
		istorage.setValue(15);
	}

}
