package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrJumpIfTrue represents assembler instruction for jumping
 * on given address if processor flag is set on value <code>true</code>.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrJumpIfTrue implements Instruction{

	/** Address to jump on */
	private int adress;

	/**
	 * Constructor for InstrJumpIfTrue class. Creates a new instruction for 
	 * jumping to a given address if processor flag is set on value 
	 * <code>true</code>. It accepts list with one element which
	 * represent memory location as number.
	 * @param arguments List of arguments.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.adress = ((Integer) arguments.get(0).getValue()).intValue();
	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>false</code> so processor is not halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		if(computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(adress-1);
		}
		return false;
	}
	
}
