package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command exit closes current instance of MyShell program.
 * It accepts no arguments.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ExitShellCommand implements IShellCommand {
	/** Command name*/
	private static final String COMMAND_NAME = "exit";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		if (!arguments.equals("")) {
			try {
				env.writeln("Invalid number of arguments for exit command!");
				return ShellStatus.CONTINUE;
			} catch (IOException e) {
				System.out.println("Unexpected error with exit command!");
			}
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description
				.add("Command exit closes current instance of MyShell program.");
		description.add("It accepts no arguments.");
		description = Collections.unmodifiableList(description);
		return description;
	}

}
