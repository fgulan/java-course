package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command cat prints the contents of a given file. It accepts 
 * only one argument, a path to a file to inspect. Output is not formated.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CatShellCommand implements IShellCommand {
	/** Command name */
	private static final String COMMAND_NAME = "cat";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		ParserUtilty argumentParser = new ParserUtilty(arguments);
		String[] argument = argumentParser.getArguments();

		try {
			if (argument.length != 1 && argument.length != 2) {
				env.writeln("Invalid number of arguments for cat command!");
				return ShellStatus.CONTINUE;
			}

			Path srcFilePath;
			try {
				srcFilePath = Paths.get(argument[0]);
			} catch (Exception e) {
				env.writeln("Given path is invalid!");
				return ShellStatus.CONTINUE;
			}
			
			String charset = Charset.defaultCharset().toString();
			if (argument.length == 2) {
				charset = argument[1];
			}

			BufferedReader inputStream;
			try {
				inputStream = new BufferedReader(new InputStreamReader(
						new FileInputStream(srcFilePath.toString()), charset));
			} catch (FileNotFoundException e) {
				env.writeln("Unable to load file! Please check given path!");
				return ShellStatus.CONTINUE;
			}

			String line;
			while ((line = inputStream.readLine()) != null) {
				env.writeln(line);
			}
			inputStream.close();
		} catch (IOException e) {
			System.out.println("Unexpected error with cat command!");
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
		description.add("Command cat prints the contents of a given file.");
		description
				.add("It accepts only one argument, a path to a file to inspect.");
		description.add("Output is not formated.");
		description = Collections.unmodifiableList(description);
		return description;
	}

}
