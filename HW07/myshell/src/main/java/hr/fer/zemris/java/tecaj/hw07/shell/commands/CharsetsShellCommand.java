package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * Command charsets lists all supported charsets on current computer.
 * It does not accept any argument.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CharsetsShellCommand implements IShellCommand {
	/** Command name */
	private static final String COMMAND_NAME = "charsets";
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		try {
			if (!arguments.equals("")) {
				env.writeln("Invalid number of arguments for charsets command");
				return ShellStatus.CONTINUE;
			}
			SortedMap<String, Charset> charsets = Charset.availableCharsets();

			for (String charset : charsets.keySet()) {
				env.writeln(charset);
			}
		} catch (Exception e) {
			System.out.println("Unexpected error with command charsets!");
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
				.add("Command charsets lists all supported charsets on current computer.");
		description.add("It does not accept any argument.");
		description = Collections.unmodifiableList(description);
		return description;
	}

}
