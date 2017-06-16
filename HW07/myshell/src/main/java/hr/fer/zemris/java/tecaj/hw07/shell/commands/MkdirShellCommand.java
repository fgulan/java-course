package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command mkdir creates a new directory under given path.
 * It accepts only one argument, a path to a new directory.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class MkdirShellCommand implements IShellCommand {

	/** Command name */
	private static final String COMMAND_NAME = "mkdir";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		ParserUtilty argumentParser = new ParserUtilty(arguments);
		String[] argument = argumentParser.getArguments();

		try {
			if (argument.length != 1) {
				env.writeln("Invalid number of arguments for copy command!");
				return ShellStatus.CONTINUE;
			}
			try {
				Path destDir = Paths.get(argument[0]);
				Files.createDirectories(destDir);
			} catch (Exception e) {
				env.writeln("Unable to create a directory with given path/name!");
			}
		} catch (Exception e) {
			System.out.println("Unexpected error with mkdir command!");
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
				.add("Command mkdir creates a new directory under given path.");
		description
				.add("It accepts only one argument, a path to a new directory.");
		description = Collections.unmodifiableList(description);
		return description;
	}

}
