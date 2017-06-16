package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;

/**
 * IShellCommand interface represents shell command.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface IShellCommand {

	/**
	 * Execute current command.
	 * @param env Environment from which command is called.
	 * @param arguments Command arguments.
	 * @return ShellStatus after execution. (CONTINUE or TERMINATE)
	 */
	ShellStatus executeCommand(IEnvironment env, String arguments);

	/**
	 * Returns command name;
	 * @return Command name.
	 */
	String getCommandName();

	/**
	 * Returns command description as list.
	 * @return Command description as list.
	 */
	List<String> getCommandDescription();
}
