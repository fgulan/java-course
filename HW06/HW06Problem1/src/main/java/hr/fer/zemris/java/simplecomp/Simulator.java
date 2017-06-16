package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Program used for creating computer and executing assembler code on it. Path
 * to assembler code file is given through command line argument. If not,
 * program asks user to enter path to the file manually.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Simulator {

	/**
	 * Start point of program Simulator.
	 * 
	 * @param args Path to a file with assembler code.
	 * @throws Exception Problem with reading assembler code file.
	 */
	public static void main(String[] args) throws Exception {
		String filePath;

		if (args.length == 1) {
			filePath = args[0];
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in, StandardCharsets.UTF_8));
			filePath = reader.readLine();
		}

		Computer comp = new ComputerImpl(256, 16);
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");

		ProgramParser.parse(filePath, comp, creator);
		ExecutionUnit exec = new ExecutionUnitImpl();
		exec.go(comp);

	}

}
