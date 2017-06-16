package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command help prints description for command given as argument.
 * It there is no argument, command lists all supported commands.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class HelpShellCommand implements IShellCommand {
	/** Command name*/
	private static final String COMMAND_NAME = "help";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		boolean found = false;
		try {
			if (arguments.equals("")) {
				for (IShellCommand command : env.commands()) {
					env.writeln(command.getCommandName());
				}
				return ShellStatus.CONTINUE;
			}

			for (IShellCommand command : env.commands()) {
				if (command.getCommandName().equals(arguments)) {
					found = true;
					for (String description : command.getCommandDescription()) {
						env.writeln(description);
					}
					return ShellStatus.CONTINUE;
				}
			}
			if (!found) {
				env.writeln("Command with given name does not exist!");
			}
		} catch (Exception e) {
			System.out.println("Unexpected error with help command!");
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
				.add("Command help prints description for command given as argument.");
		description
				.add("It there is no argument, command lists all supported commands.");
		description = Collections.unmodifiableList(description);
		return description;

	}
}
