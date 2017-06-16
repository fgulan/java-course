package hr.fer.zemris.java.tecaj.hw3;

/**
 * Thrown to indicate that there is error with complex number object.
 * @author Filip Gulan
 * @version 1.0
 */
public class ComplexNumberException extends RuntimeException {

	/**
	 * A new serialVersionUID.
	 */
	private static final long serialVersionUID = 2188668868628763045L;

	/**
	 * Constructs <code>ComplexNumberException</code> with no detailed message.
	 */
	public ComplexNumberException() {
		super();
	}

	/**
	 * Constructs <code>ComplexNumberException</code> with the specified detailed message and cause.  
	 * @param message Detail message.
	 * @param cause Cause of exception.
	 */
	public ComplexNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs <code>ComplexNumberException</code> with detailed message.
	 * @param message Specified message.
	 */
	public ComplexNumberException(String message) {
		super(message);
	}

	/**
	 * Constructs <code>ComplexNumberException</code> with the specified cause. 
	 * @param cause Specified cause.
	 */
	public ComplexNumberException(Throwable cause) {
		super(cause);
	}
}
