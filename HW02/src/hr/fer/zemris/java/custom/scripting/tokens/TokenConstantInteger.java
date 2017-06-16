package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class extends Token class. It represent constant integer token in text.
 * Overrides asText() method from parent class.
 * 
 * @author Filip Gulan
 * @Version 1.0
 *
 */
public class TokenConstantInteger extends Token {
	
	private int value;
	
	/**
	* Constructor of TokenConstantInteger class.
	* @param value Constant value.
	*/
	public TokenConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Returns value of integer constant.
	 * @return Value of integer constant.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns formatted textual representation of constant integer token.
	 * @return Formatted textual representation of constant integer token.
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
