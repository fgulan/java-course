package hr.fer.zemris.java.tecaj_15.dao;

/**
 * DAOException extends {@link RuntimeException}. DAOException represents an
 * error with accessing a database using {@link DAO} interface.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class DAOException extends RuntimeException {

    /** Serial number. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DAOException exception with null as its detail message.
     * The cause is not initialized.
     */
    public DAOException() {
    }

    /**
     * Constructs a new DAOException exception with the specified detail
     * message.
     * 
     * @param message Detail message.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAOException exception with the specified detail message
     * and cause.
     * 
     * @param message Detail message.
     * @param cause The cause.
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DAOException exception with the specified cause and a
     * detail message of (cause==null ? null : cause.toString()) (which
     * typically contains the class and detail message of cause).
     * 
     * @param cause The cause.
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message,
     * cause, suppression enabled or disabled, and writable stack trace enabled
     * or disabled.
     * 
     * @param message Detail message.
     * @param cause The cause.
     * @param enableSuppression Whether or not suppression is enabled or
     *        disabled.
     * @param writableStackTrace Whether or not the stack trace should be
     *        writable.
     */
    public DAOException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}