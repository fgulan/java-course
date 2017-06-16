package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class extends Token class. It represent constant double token in text.
 * Overrides asText() method from parent class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TokenConstantDouble extends Token {

    /** Token value. */
    private double value;

    /**
     * Constructor of TokenConstantDouble class.
     * 
     * @param value
     *            Constant value.
     */
    public TokenConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Returns value of double constant.
     * 
     * @return Value of double constant.
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns formatted textual representation of double integer token.
     * 
     * @return Formatted textual representation of double integer token.
     */
    @Override
    public String asText() {
        return Double.toString(value);
    }
}
