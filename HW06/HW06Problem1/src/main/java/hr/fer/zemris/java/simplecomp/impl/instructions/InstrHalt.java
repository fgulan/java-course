package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrHalt represents assembler instruction for halting processor.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Constructor for InstrHalt class. Creates a new instruction
	 * for halting processor.
	 * @param arguments List of registers. Not used.
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		super();
	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>true</code> so processor is halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		return true;
	}

}
