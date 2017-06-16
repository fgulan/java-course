package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * This class extends Node class. It represent echo node in text. Overrides
 * asText() method from parent class.
 * 
 * @author Filip Gulan
 * @Version 1.0
 *
 */
public class EchoNode extends Node {

	/**
	 * Storage for echo node tokens 
	 */
	private Token[] tokens;

	/**
	 * Constructor for EchoNode class.
	 * @param tokens Array of tokens to add in EchoNode.
	 */
	public EchoNode(Token[] tokens) {
		this.tokens = tokens;
	}

	/**
	 * Returns array of tokens currently located under EchoNode.
	 * @return Array of tokens.
	 */
	public Token[] getTokens() {
		return tokens;
	}

	/**
	 * Returns formatted textual representation of EchoNode.
	 * @return Formatted textual representation of EchoNode.
	 */
	@Override
	public String asText() {
		StringBuilder builder = new StringBuilder("{$= ");
		int size = tokens.length;

		for (int i = 0; i < size; i++) {
			builder.append(tokens[i].asText() + " ");
		}

		builder.append("$}");
		return builder.toString();
	}
}
