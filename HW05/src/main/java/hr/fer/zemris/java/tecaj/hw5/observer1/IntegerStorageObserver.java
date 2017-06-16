package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Observer for {@link IntegerStorage} class. Defines action to implement
 * in order to track the given {@link IntegerStorage} object data modifications.
 *
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface IntegerStorageObserver {
	/**
	 * Executes the action if given subjet modify its state.
	 * @param istorage The subject.
	 */
	public void valueChanged(IntegerStorage istorage);
}
