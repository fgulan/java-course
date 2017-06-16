package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * Environment interface represents current computer environment for executing
 * given methods.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public interface IEnvironment {
	/**
	 * Read line from given input.
	 * @return Read line.
	 * @throws IOException Error with given input.
	 */
	String readLine() throws IOException;

	/**
	 * Writes given string to given output.
	 * @param text String to write.
	 * @throws IOException Error with given output.
	 */
	void write(String text) throws IOException;

	/**
	 * Writes given string and a new line to given output.
	 * @param text String to write.
	 * @throws IOException Error with given output.
	 */
	void writeln(String text) throws IOException;

	/**
	 * Returns iterator with implemented commands in current environment.
	 * @return Iterator with implemented commands in current environment.
	 */
	Iterable<IShellCommand> commands();

	/**
	 * Returns current MULTILINE symbol.
	 * @return MULTILINE symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets MULTILINE symbol in current environment to the given symbol.
	 * @param symbol New symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns current PROMPT symbol.
	 * @return PROMPT symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Sets PROMPT symbol in current environment to the given symbol.
	 * @param symbol New symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns current MORELINES symbol.
	 * @return MORELINES symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets MORELINES symbol in current environment to the given symbol.
	 * @param symbol New symbol.
	 */
	void setMorelinesSymbol(Character symbol);
}
