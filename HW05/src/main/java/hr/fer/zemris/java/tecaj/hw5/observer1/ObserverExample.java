package hr.fer.zemris.java.tecaj.hw5.observer1;

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
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
