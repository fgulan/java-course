package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class extends Token class. It represent variable token in text. Overrides
 * asText() method from parent class.
 * 
 * @author Filip Gulan
 * @Version 1.0
 *
 */
public class TokenVariable extends Token {
	
	private String name;
	
	/**
	 * Constructor of TokenVariable class.
	 * @param name Name of variable token.
	 */
	public TokenVariable(String name) {
		this.name = name;
	}
	/**
	 * Returns name of variable token.
	 * @return Name of variable token.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns formatted textual representation of variable token.
	 * @return Formatted textual representation of variable token.
	 */
	@Override
	public String asText() {
		return name;
	}

}
