package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * ParserUtility class is utility used for parsing paths.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ParserUtilty {

	/** Paths */
	private List<String> arguments;
	/** Input string */
	private String input;

	/**
	 * Constructor for ParserUtility class.
	 * @param input Input string.
	 */
	public ParserUtilty(String input) {
		this.input = input;
		parse();
	}

	/**
	 * Parse given input.
	 */
	private void parse() {
		arguments = new ArrayList<String>();
		boolean openQuotation = false;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (c == '"' && openQuotation == false) {
				openQuotation = true;
			} else if (c == '"' && openQuotation == true
					&& sb.charAt(sb.length() - 1) == '\\') {
				sb.setLength(sb.length() - 1);
				sb.append("\"");
			} else if (c == '\\' && openQuotation == true
					&& sb.charAt(sb.length() - 1) == '\\') {
				sb.setLength(sb.length() - 1);
				sb.append("\\");
			} else if (c == '"' && openQuotation == true) {
				arguments.add(sb.toString());
				sb.setLength(0);
				openQuotation = false;
			} else if (c == ' ' && openQuotation == false) {
				if (sb.length() != 0) {
					arguments.add(sb.toString());
				}
				sb.setLength(0);
			} else {
				sb.append(c);
			}
		}
		arguments.remove(" ");
		if (sb.length() != 0) {
			arguments.add(sb.toString());
		}
	}

	/**
	 * Returns parsed paths.
	 * @return Parsed paths.
	 */
	public String[] getArguments() {
		return arguments.toArray(new String[0]);
	}
}
