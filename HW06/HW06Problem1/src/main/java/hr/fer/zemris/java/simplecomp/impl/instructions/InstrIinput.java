package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * InstrIinput represents assembler instruction for reading user input from console.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrIinput implements Instruction {

	/** Adress to store read data */
	private int adress;
	/** Reader */
	private BufferedReader reader;

	/**
	 * Constructor for InstrIinput class. Creates a new instruction for 
	 * reading user input from console. It accepts list with one element
	 * which represents memory location as number where to store read data.
	 * @param arguments Address where to store.
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.adress = ((Integer) arguments.get(0).getValue()).intValue();
		reader = new BufferedReader(new InputStreamReader(System.in,
				StandardCharsets.UTF_8));
	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>false</code> so processor is not halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException("Unexpected error! Closing.");
		}
		
		if(line == null) {
			throw new RuntimeException("Unexpected error! Closing.");
		}
		
		int value;
		try {
			value=Integer.parseInt(line);
		} catch (Exception e) {
			computer.getRegisters().setFlag(false);
			return false;
		}
		
		computer.getMemory().setLocation(adress, value);
		computer.getRegisters().setFlag(true);
		
		return false;
	}

}
