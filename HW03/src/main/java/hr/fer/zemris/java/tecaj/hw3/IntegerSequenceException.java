package hr.fer.zemris.java.tecaj.hw3;

/**
 * Thrown to indicate that there is error with integer sequence.
 * @author Filip Gulan
 * @version 1.0
 */
public class IntegerSequenceException extends RuntimeException {

	/**
	 * A new serialVersionUID.
	 */
	private static final long serialVersionUID = 5074425483849716359L;

	/**
	 * Constructs <code>IntegerSequenceException</code> with no detailed message.
	 */
	public IntegerSequenceException() {
		super();
	}

	/**
	 * Constructs <code>IntegerSequenceException</code> with the specified detailed message and cause.  
	 * @param message Detail message.
	 * @param cause Cause of exception.
	 */
	public IntegerSequenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs <code>IntegerSequenceException</code> with detailed message.
	 * @param message Specified message.
	 */
	public IntegerSequenceException(String message) {
		super(message);
	}

	/**
	 * Constructs <code>IntegerSequenceException</code> with the specified cause. 
	 * @param cause Specified cause.
	 */
	public IntegerSequenceException(Throwable cause) {
		super(cause);
	}
}
