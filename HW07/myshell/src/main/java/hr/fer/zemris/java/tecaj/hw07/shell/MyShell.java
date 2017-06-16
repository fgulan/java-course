package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * MyShell program implements basic shell representation program. It implements
 * several easy to use commands like: mkdir, copy, tree... 
 * 
 * <p>For each command there is a description how to use it (help commandname).
 * Path as a command argument can be entered in two ways: without quote as a
 * relative or absolute path (i.e. C:/java) or with quote as a relative or
 * absolute path(i.e. "C:/Documents and settings", "/src/main"). Path with
 * quote must be used if path contains blank spaces.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class MyShell {

	/** Map with commands */
	private static Map<String, IShellCommand> commands;

	/** 
	 * IEnvironment interface implementation.
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	private static class Environment implements IEnvironment {

		/** Multiline symbol */
		private Character multilineSymbol;
		/** Prompt symbol */
		private Character promptSymbol;
		/** Morelines symbol */
		private Character morelinesSymbol;
		/** Input stream */
		private BufferedReader in;
		/** Output stream */
		private BufferedWriter out;

		/**
		 * Constructor for Environment class.
		 * @param in Input stream;
		 * @param out Output stream.
		 * @param multilineSymbol Multiline symbol.
		 * @param promptSymbol Prompt symbol.
		 * @param morelinesSymbol Morelines symbol.
		 */
		public Environment(BufferedReader in, BufferedWriter out,
				Character multilineSymbol, Character promptSymbol,
				Character morelinesSymbol) {
			this.in = in;
			this.out = out;
			this.multilineSymbol = multilineSymbol;
			this.promptSymbol = promptSymbol;
			this.morelinesSymbol = morelinesSymbol;
		}

		@Override
		public String readLine() throws IOException {
			return in.readLine();
		}

		@Override
		public void write(String text) throws IOException {
			out.write(text);
			out.flush();
		}

		@Override
		public void writeln(String text) throws IOException {
			out.write(text);
			out.newLine();
			out.flush();
		}

		@Override
		public Iterable<IShellCommand> commands() {
			return new Iterable<IShellCommand>() {
				@Override
				public Iterator<IShellCommand> iterator() {
					return commands.values().iterator();
				}
			};
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.morelinesSymbol = symbol;
		}
	}

	/**
	 * Fills map with commands implementation.
	 */
	private static void fillCommands() {
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
	}

	/**
	 * Read lines from standard input in given Environment.
	 * @param env Environment to read from.
	 * @return Formated read string.
	 */
	private static String readLines(IEnvironment env) {
		StringBuilder builder = new StringBuilder();

		try {
			builder.append(env.readLine());
			while (builder.toString().endsWith(
					Character.toString(env.getMorelinesSymbol()))) {
				env.write(Character.toString(env.getMultilineSymbol()) + " ");
				String newLine = env.readLine();
				builder.setLength(builder.length() - 1);
				builder.append(newLine);
			}
		} catch (IOException e) {
			System.out.println("Error with reading from input buffer.");
		}

		return builder.append(" ").toString();

	}

	/**
	 * Start point of MyShell program.
	 * @param args Command line arguments. Not used.
	 */
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, StandardCharsets.UTF_8));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				System.out, StandardCharsets.UTF_8));

		commands = new HashMap<>();
		fillCommands();

		IEnvironment env = new Environment(reader, writer, '|', '>', '\\');

		ShellStatus status = ShellStatus.CONTINUE;
		try {
			env.writeln("Welcome to MyShell v 1.0");
		} catch (IOException e) {

		}
		while (status == ShellStatus.CONTINUE) {
			try {
				env.write(env.getPromptSymbol() + " ");
				String input = readLines(env);
				IShellCommand command = commands.get(input.substring(0,
						input.indexOf(" ")));

				if (command == null) {
					env.writeln("Invalid command!");
					continue;
				} else {
					status = command.executeCommand(env,
							input.substring(input.indexOf(" ") + 1).trim());
				}
			} catch (IOException e) {
				System.out.println("Unexpected error!!!");
				System.exit(-1);
			}
		}

	}

}
