package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class extends Token class. It represent string token in text. Overrides
 * asText() method from parent class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TokenString extends Token {

    /** Token value. */
    private String value;

    /**
     * Constructor of TokenString class.
     * 
     * @param value
     *            Value of string token.
     */
    public TokenString(String value) {
        this.value = value;
    }

    /**
     * Returns a value of string token.
     * 
     * @return Value of string token.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns formatted textual representation of string token with added '"'
     * at the beginning and at the end of string.
     * 
     * @return Formatted textual representation of string token.
     */
    @Override
    public String asText() {
        String tmpString = value.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\t", "\\t").replace("\r", "\\r");
        return tmpString;
    }
}
