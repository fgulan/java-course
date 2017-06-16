package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command copy copies given file to a given path. It accepts two arguments.
 * First argument must be a path to a file to copy. Second argument is a 
 * path to a destination director with or without new file name.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class CopyShellCommand implements IShellCommand {
	/** Command name */
	private static final String COMMAND_NAME = "copy";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		ParserUtilty argumentParser = new ParserUtilty(arguments);
		String[] argument = argumentParser.getArguments();
		try {
			if (argument.length != 2) {
				env.writeln("Invalid number of arguments for copy command!");
				return ShellStatus.CONTINUE;
			}

			Path srcFilePath;
			Path destFilePath;
			try {
				srcFilePath = Paths.get(argument[0]);
				destFilePath = Paths.get(argument[1]);
			} catch (Exception e) {
				env.writeln("Given path is invalid!");
				return ShellStatus.CONTINUE;
			}

			if (!Files.exists(srcFilePath) || Files.isDirectory(srcFilePath)) {
				env.writeln("Invalid source file path! Please check if given path is file and that exists.");
				return ShellStatus.CONTINUE;
			}

			if (Files.isSameFile(srcFilePath, destFilePath)) {
				env.writeln("Destination file and source file are the same.");
				return ShellStatus.CONTINUE;
			}
			if (!Files.exists(destFilePath) && destFilePath.getParent() != null) {
				if (!Files.exists(destFilePath.getParent())) {
					env.writeln("Invalid destination file path!");
					return ShellStatus.CONTINUE;
				}
			} else if (Files.isDirectory(destFilePath)) {
				String path = destFilePath.toString();
				if (!path.endsWith(File.separator))
					path += File.separator;
				destFilePath = Paths.get(path += srcFilePath.getFileName());
			}

			if (Files.exists(destFilePath)) {
				String answer;
				env.write("Destination file already exists, do you want to overwrite it (yes, no)? ");
				answer = env.readLine().toLowerCase();
				if (answer.equals("no")) {
					env.writeln("Old file would not be replaced.");
					return ShellStatus.CONTINUE;
				} else if (!answer.equals("yes")) {
					env.writeln("Unknown statement!");
					return ShellStatus.CONTINUE;
				}
			}
			copy(srcFilePath, destFilePath);
		} catch (IOException e) {
			System.out.println("Unexpected error with copy command!");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies given file to a given destination directory.
	 * @param src Source file path.
	 * @param dest Destination path.
	 * @throws IOException Error with copying.
	 */
	private void copy(Path src, Path dest) throws IOException {
		FileInputStream inputStream = new FileInputStream(src.toString());
		FileOutputStream outputStream = new FileOutputStream(dest.toString());

		byte[] inputBuffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = inputStream.read(inputBuffer)) > 0) {
			outputStream.write(inputBuffer, 0, bytesRead);
		}
		inputStream.close();
		outputStream.close();
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description.add("Command copy copies given file to a given path.");
		description.add("It accepts two arguments.");
		description.add("First argument must be a path to a file to copy.");
		description
				.add("Second argument is a path to a destination director with or without new file name.");
		description = Collections.unmodifiableList(description);
		return description;
	}
}
