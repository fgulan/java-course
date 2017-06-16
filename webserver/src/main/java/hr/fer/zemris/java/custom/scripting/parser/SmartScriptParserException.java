package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate that there is error with parsing the document.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

    /** Serial. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs <code>SmartScriptParserException</code> with no detail
     * message.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructs <code>SmartScriptParserException</code> with the specified
     * detail message and cause.
     * 
     * @param message
     *            Detail message.
     * @param cause
     *            Cause of exception.
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs <code>SmartScriptParserException</code> with detail message.
     * 
     * @param message
     *            Specified message.
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Constructs <code>SmartScriptParserException</code> with the specified
     * cause.
     * 
     * @param cause
     *            Specified cause.
     */
    public SmartScriptParserException(Throwable cause) {
        super(cause);
    }

}
