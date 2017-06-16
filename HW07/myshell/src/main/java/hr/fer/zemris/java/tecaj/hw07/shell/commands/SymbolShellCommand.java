package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Command symbol gets and sets current symbol for PROMPT, MORELINES
 * and MULTILINE variables. It accepts one or two argument. First 
 * argument must be one of mentioned variables. Second argument is 
 * a new character representation for given variable. If there is no 
 * second argument, command print current character representation of given variable.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SymbolShellCommand implements IShellCommand {
	
	/** Command name */
	private static final String COMMAND_NAME = "symbol";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		try {
			if (arguments.equals("")) {
				env.writeln("Invalid number of arguments for command symbol!");
				return ShellStatus.CONTINUE;
			}
			if (arguments.startsWith("PROMPT")) {
				String[] argument = arguments.trim().split("\\s+");
				if (argument.length == 1) {
					env.writeln("Symobl for PROMPT is '"
							+ env.getPromptSymbol() + "'");
				} else if (argument.length == 2 && argument[1].length() == 1) {
					env.writeln("Symobl for PROMPT changed from '"
							+ env.getPromptSymbol() + "' to '" + argument[1]
							+ "'");
					env.setPromptSymbol(argument[1].charAt(0));
				} else {
					env.writeln("New symbol must be a character!");
				}
			} else if (arguments.startsWith("MORELINES")) {
				String[] argument = arguments.trim().split("\\s+");
				if (argument.length == 1) {
					env.writeln("Symobl for MORELINES is '"
							+ env.getMorelinesSymbol() + "'");
				} else if (argument.length == 2 && argument[1].length() == 1) {
					env.writeln("Symobl for MORELINES changed from '"
							+ env.getMorelinesSymbol() + "' to '" + argument[1]
							+ "'");
					env.setMorelinesSymbol(argument[1].charAt(0));
				} else {
					env.writeln("New symbol must be a character!");
				}
			} else if (arguments.startsWith("MULTILINE")) {
				String[] argument = arguments.trim().split("\\s+");
				if (argument.length == 1) {
					env.writeln("Symobl for MULTILINE is '"
							+ env.getMorelinesSymbol() + "'");
				} else if (argument.length == 2 && argument[1].length() == 1) {
					env.writeln("Symobl for MULTILINE changed from '"
							+ env.getMultilineSymbol() + "' to '" + argument[1]
							+ "'");
					env.setMultilineSymbol(argument[1].charAt(0));
				} else {
					env.writeln("New symbol must be a character!");
				}
			} else {
				env.writeln("Unkonown statement: " + arguments.split("\\s+")[0]);
			}
		} catch (Exception e) {
			System.out.println("Unexpected error with symbol command!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description
				.add("Command symbol gets and sets current symbol for PROMPT, MORELINES and MULTILINE variables.");
		description
				.add("It accepts one or two argument. First argument must be one of mentioned variables.");
		description
				.add("Second argument is a new character represenatation for given variable.");
		description
				.add("If there is no second argument, command print current character representation of given variable.");
		description = Collections.unmodifiableList(description);
		return description;
	}

}
