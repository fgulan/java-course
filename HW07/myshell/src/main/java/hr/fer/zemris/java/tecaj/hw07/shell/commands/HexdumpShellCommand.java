package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command hexdump prints contents of a given file in a hex format.
 * It accepts only one argument, a path to directory to inspect.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class HexdumpShellCommand implements IShellCommand {

	/** Byte format */
	private static final int BYTES = 8;
	/** Command name */
	private static final String COMMAND_NAME = "hexdump";

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		ParserUtilty argumentParser = new ParserUtilty(arguments);
		String[] argument = argumentParser.getArguments();
		try {
			if (argument.length != 1) {
				env.writeln("Invalid number of arguments for copy command!");
				return ShellStatus.CONTINUE;
			}
			Path srcFilePath;
			try {
				srcFilePath = Paths.get(argument[0]);
			} catch (Exception e) {
				env.writeln("Given path is invalid!");
				return ShellStatus.CONTINUE;
			}
			
			InputStream inputStream;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(
						srcFilePath.toString()));
			} catch (FileNotFoundException e) {
				env.writeln("Unable to load file!");
				return ShellStatus.CONTINUE;
			}

			byte[] inputBuffer = new byte[16];
			int line = 0;
			while (true) {
				int readBytes = inputStream.read(inputBuffer);
				if (readBytes < 1) {
					break;
				}
				env.write(String.format("%08x: ", line));
				env.write(bytesToHex(inputBuffer, readBytes));
				env.writeln(bytesToText(inputBuffer, readBytes));
				line += 16;
			}
			inputStream.close();
		} catch (Exception e) {
			System.out.println("Unexpected error with hexdump command!");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Converts given byte array to textual representation.
	 * @param buffer Byte array.
	 * @param readBytes Number of bytes.
	 * @return Textual representation.
	 */
	private String bytesToText(byte[] buffer, int readBytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < readBytes; i++) {
			if (buffer[i] < 37 || buffer[i] > 127) {
				sb.append(".");
			} else {
				sb.append(String.format("%c", buffer[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * Converts given byte array to hex textual representation.
	 * @param buffer Byte array.
	 * @param readBytes Number of bytes.
	 * @return Hex textual representation.
	 */
	private String bytesToHex(byte[] buffer, int readBytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {
			if (i < readBytes) {
				String hex = Integer.toHexString(0xff & buffer[i]);
				if (hex.length() == 1) {
					sb.append('0');
				}
				sb.append(hex);
			} else {
				sb.append("  ");
			}

			if (i == BYTES - 1) {
				sb.append("|");
			} else {
				sb.append(" ");
			}
		}
		return sb.append("|").toString();
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<String>();
		description
				.add("Command hexdump prints contents of a given file in a hex format.");
		description
				.add("It accepts only one argument, a path to directory to inspect.");
		description = Collections.unmodifiableList(description);
		return description;
	}

}
