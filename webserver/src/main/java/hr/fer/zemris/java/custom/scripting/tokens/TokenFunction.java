package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class extends Token class. It represent function token in text.
 * Overrides asText() method from parent class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TokenFunction extends Token {

    /** Function name. */
    private String name;

    /**
     * Constructor of TokenFunction class.
     * 
     * @param name
     *            Name of function.
     */
    public TokenFunction(String name) {
        this.name = name;
    }

    /**
     * Returns a function name.
     * 
     * @return Function name.
     */
    public String getValue() {
        return name;
    }

    /**
     * Returns formatted textual representation of function token with added '@'
     * at beginning.
     * 
     * @return Formatted textual representation of function token.
     */
    @Override
    public String asText() {
        return "@" + name;
    }
}
