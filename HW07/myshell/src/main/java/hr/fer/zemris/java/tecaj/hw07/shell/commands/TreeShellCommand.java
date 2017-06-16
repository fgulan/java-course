package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command tree prints all files and directories (children also) 
 * under given directory path. It accepts only one argument,
 * a path to directory to inspect. Output is formatted.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TreeShellCommand implements IShellCommand {

	/** Environment */
	private IEnvironment env;
	/** Command name */
	private static final String COMMAND_NAME = "tree";

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		ParserUtilty argumentParser = new ParserUtilty(arguments);
		String[] argument = argumentParser.getArguments();
		this.env = env;
		try {
			Path root;
			try {
				root = Paths.get(argument[0]);
			} catch (Exception e) {
				env.writeln("Given path is invalid!");
				return ShellStatus.CONTINUE;
			}
			if (!root.toFile().isDirectory()) {
				env.writeln("Given path is not a directory!");
				return ShellStatus.CONTINUE;
			}
			try {
				Files.walkFileTree(root, new Visitor());
			} catch (IOException e) {
				env.writeln("Error with given path!");
			}
		} catch (IOException e) {
			System.out.println("Unexpected error with tree command!");
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
				.add("Command tree prints all files and directories (children also) under given directory path.");
		description
				.add("It accepts only one argument, a path to directory to inspect.");
		description.add("Output is formatted.");
		description = Collections.unmodifiableList(description);
		return description;
	}

	/**
	 * Visitor class for walking through directories.
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	private class Visitor implements FileVisitor<Path> {
		/** Depth */
		int depth;

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attra) throws IOException {
			if (depth == 0) {
				env.writeln(dir.toFile().getPath());
			} else {
				env.writeln(String.format("%" + depth + "s", "")
						+ dir.toFile().getName());
			}

			depth += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1)
				throws IOException {
			String fileName = file.toFile().getName();
			env.writeln(String.format("%" + depth + "s", "") + fileName);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException e)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException e)
				throws IOException {
			depth -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

}
