package hr.fer.zemris.java.tecaj.hw3;

/**
 * Thrown to indicate that there is error with CString object.
 * @author Filip Gulan
 * @version 1.0
 */
public class CStringException extends RuntimeException {

	/**
	 * A new serialVersionUID.
	 */
	private static final long serialVersionUID = -708381441530830654L;
	
	/**
	 * Constructs <code>CStringException</code> with no detailed message.
	 */
	public CStringException() {
		super();
	}

	/**
	 * Constructs <code>CStringException</code> with the specified detailed message and cause.  
	 * @param message Detail message.
	 * @param cause Cause of exception.
	 */
	public CStringException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs <code>CStringException</code> with detailed message.
	 * @param message Specified message.
	 */
	public CStringException(String message) {
		super(message);
	}

	/**
	 * Constructs <code>CStringException</code> with the specified cause. 
	 * @param cause Specified cause.
	 */
	public CStringException(Throwable cause) {
		super(cause);
	}
}
