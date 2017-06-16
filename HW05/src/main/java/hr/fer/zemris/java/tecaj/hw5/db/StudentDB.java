package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Program used for testing queries over StudentDatabase database.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class StudentDB {

	/**
	 * Start point of program StudentDB. Command line arguments are not used.
	 * @param args Not used.
	 * @throws IOException If there is no "studenti.txt" file in main directory of project.
	 */
	public static void main(String[] args) throws IOException {
		StudentDatabase database = null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in,
				StandardCharsets.UTF_8));

		List<String> lines = Files.readAllLines(Paths.get("studenti.txt"),
				StandardCharsets.UTF_8);

		try {
			database = new StudentDatabase(lines);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		while (true) {
			System.out.print("> ");
			String line = reader.readLine().trim();

			if (line == null || line.isEmpty()) {
				continue;
			}

			if (!line.startsWith("query ")) {
				System.out.println("Unknown command! Please enter again.");
				continue;
			}

			line = line.replace("query", "");
			if (line.startsWith("exit", line.indexOf("e"))) {
				break;
			}
			try {
				QueryFilter filter = new QueryFilter(line);
				List<StudentRecord> records = database.filter(filter);
				if(!records.isEmpty()) {
					printDatabase(records);
				}
				System.out.println(String.format("Records selected: " + records.size()
						+ "%n"));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}	
	}

	/**
	 * Prints formated all student records from given list.
	 * @param data List with student records.
	 */
	public static void printDatabase(List<StudentRecord> data) {
		int[] lenghts = { 0, 0, 0 };

		for (StudentRecord student : data) {
			lenghts[0] = Math.max(lenghts[0], student.getJmbag().length());
			lenghts[1] = Math.max(lenghts[1], student.getLastName().length());
			lenghts[2] = Math.max(lenghts[2], student.getFirstName().length());
		}
		printHeaderAndFooterLine(lenghts);
		for (StudentRecord rec : data) {
			StringBuilder printer = new StringBuilder();
			printer.append("|");
			printer.append(String.format(" %-" + lenghts[0] + "s |",
					rec.getJmbag()));
			printer.append(String.format(" %-" + lenghts[1] + "s |",
					rec.getLastName()));
			printer.append(String.format(" %-" + lenghts[2] + "s |",
					rec.getFirstName()));
			printer.append(String.format(" %-" + 1 + "d |", rec.getFinalGrade()));
			System.out.println(printer);
		}
		printHeaderAndFooterLine(lenghts);
	}

	/**
	 * Prints header and footer line of formated table.
	 * @param lenghts Array of lengths for dimension of line.
	 */
	private static void printHeaderAndFooterLine(int[] lenghts) {
		System.out.print('+');
		for (Integer len : lenghts) {
			for (int i = 0; i < len + 2; i++) {
				System.out.print('=');
			}
			System.out.print('+');
		}
		System.out.println("===+");
	}
}
