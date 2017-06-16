package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrEcho represents assembler instruction for printing data.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrEcho implements Instruction {

	/** First argument register */
	private int registerIndex1;

	/**
	 * Constructor for InstrEcho class. Creates a new instruction for 
	 * printing data. It accepts list with one registers
	 * where object for printing is stored.
	 * @param arguments List of registers.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}

		this.registerIndex1 = ((Integer) arguments.get(0).getValue())
				.intValue();
	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>false</code> so processor is not halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		System.out.print(computer.getRegisters().getRegisterValue(
				registerIndex1));
		return false;
	}

}
