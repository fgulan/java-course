package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * This class extends Token class. It represent mathematical operator token in text.
 * Overrides asText() method from parent class.
 * 
 * @author Filip Gulan
 * @Version 1.0
 *
 */
public class TokenOperator extends Token {
	
	 private String symbol;
	
	/**
	* Constructor of TokenOperator class.
	* @param symbol Symbol of math operator.
	*/
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Returns a symbol of math operator.
	 * @return Math operator.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Returns formatted textual representation of math operator token.
	 * @return Formatted textual representation of math operator token.
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
