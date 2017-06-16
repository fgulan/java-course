package hr.fer.zemris.java.custom.collections;

/**
 * EmptyStackException represents exception thrown on empty stack.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

    /** Serial number. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs <code>EmptyStackException</code> with no detail message.
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructs <code>EmptyStackException</code> with detail message.
     * 
     * @param message
     *            Specified message.
     */
    public EmptyStackException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            Specified cause.
     */
    public EmptyStackException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message
     *            Detail message.
     * @param cause
     *            Cause of exception.
     */
    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }

}
