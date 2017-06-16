package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.IShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * Command ls prints all files and directories under given directory path.
 * It accepts only one argument, a path to directory to inspect.
 * Output is consisted of file attributes: premissions, file 
 * size, date and time of creation and file name.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LsShellCommand implements IShellCommand {

	/** Environment */
	private IEnvironment env;
	/** Command name */
	private static final String COMMAND_NAME = "ls";
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) {
		this.env = env;
		ParserUtilty argumentParser = new ParserUtilty(arguments);
		String[] argument = argumentParser.getArguments();

		try {
			if (argument.length != 1) {
				env.writeln("Invalid number of arguments for ls command!");
				return ShellStatus.CONTINUE;
			}

			Path root;
			try {
				root = Paths.get(arguments);
			} catch (Exception e) {
				env.writeln("Please check given path!");
				return ShellStatus.CONTINUE;
			}

			if (!root.toFile().isDirectory()) {
				env.writeln("Given path is not a directory!");
				return ShellStatus.CONTINUE;
			}
			Files.walkFileTree(root, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
					1, new Visitor());

		} catch (Exception e) {
			System.out.println("Unexpected error with ls command!");
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
				.add("Command ls prints all files and directories under given directory path.");
		description
				.add("It accepts only one argument, a path to directory to inspect.");
		description
				.add("Output is consisted of file attributes: premissions, file size, date and time of creation and file name.");
		description = Collections.unmodifiableList(description);
		return description;
	}

	/**
	 * Prints formatted file attributes.
	 * @param permissions Permissions.
	 * @param fileSize File size.
	 * @param formattedDateTime Date of creation.
	 * @param fileName File name.
	 */
	private void printAttributes(String permissions, long fileSize,
			String formattedDateTime, String fileName) {
		try {
			env.writeln(String.format("%s %10d %s %s", permissions, fileSize,
					formattedDateTime, fileName));
		} catch (IOException e) {
			System.out.println("Error with output buffer.");
		}
	}

	/**
	 * Returns formatted permissions for current file.
	 * @param path Current file.
	 * @return Formatted permissions.
	 */
	private String getPermissions(Path path) {
		char directory = '-';
		if (Files.isDirectory(path))
			directory = 'd';

		char readable = '-';
		if (Files.isReadable(path))
			readable = 'r';

		char writeable = '-';
		if (Files.isWritable(path))
			writeable = 'w';

		char executable = '-';
		if (Files.isExecutable(path))
			executable = 'x';

		return new StringBuilder().append(directory).append(readable)
				.append(writeable).append(executable).toString();
	}

	/**
	 * Returns current directory size.
	 * @param dir Current directory.
	 * @return Directory size.
	 */
	private long getDirectorySize(File dir) {
		long size = 0;
		if(dir == null) {
			return 0;
		}
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					size += file.length();
				} else
					size += getDirectorySize(file);
			}
		} else if (dir.isFile()) {
			size += dir.length();
		}
		return size;
	}

	/**
	 * Returns formatted date of creation for current file.
	 * @param path Current file.
	 * @return Formatted date.
	 */
	private String getFormattedDate(Path path) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(path,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			return sdf.format(new Date(fileTime.toMillis()));
		} catch (IOException e) {
			return "";
		}
	}

	/**
	 * Visitor class for walking through directories.
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	private class Visitor implements FileVisitor<Path> {

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			String dirName = dir.toFile().getName();
			long directorySize = getDirectorySize(dir.toFile());

			printAttributes(getPermissions(dir), directorySize,
					getFormattedDate(dir), dirName);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			String fileName = file.toFile().getName();
			long fileSize = Files.size(file);

			printAttributes(getPermissions(file), fileSize,
					getFormattedDate(file), fileName);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

}
