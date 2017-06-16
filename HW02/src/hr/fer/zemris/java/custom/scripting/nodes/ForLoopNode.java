package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * This class extends Node class. It represent for loop node in text. Overrides
 * asText() method from parent class.
 * 
 * @author Filip Gulan
 * @Version 1.0
 *
 */
public class ForLoopNode extends Node {

	private TokenVariable variable;
	private Token startExpression;
	private Token endExpression;
	private Token stepExpression;

	/**
	 * Constructor for ForLoopNode class.
	 * @param variable Variable token.
	 * @param start Start expression token.
	 * @param end End expression token.
	 * @param step Step expression token.
	 */
	public ForLoopNode(TokenVariable variable, Token start, Token end, Token step) {
		this.variable = variable;
		startExpression = start;
		endExpression = end;
		stepExpression = step;
	}

	/**
	 * Returns variable token of current ForLoopNode.
	 * @return Variable token.
	 */
	public TokenVariable getVariable() {
		return variable;
	}

	/**
	 * Returns start expression token of current ForLoopNode.
	 * @return Start expression token.
	 */
	public Token getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns end expression token of current ForLoopNode.
	 * @return End expression token.
	 */
	public Token getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns step expression token of current ForLoopNode.
	 * @return Step expression token.
	 */
	public Token getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Returns formatted textual representation of ForLoopNode.
	 * @return Formatted textual representation of ForLoopNode.
	 */
	@Override
	public String asText() {
		String text = "{$ FOR " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() + " ";
		if (stepExpression != null) {
			text += stepExpression.asText() + " ";
		}
		
		return text + "$}";
	}
}
