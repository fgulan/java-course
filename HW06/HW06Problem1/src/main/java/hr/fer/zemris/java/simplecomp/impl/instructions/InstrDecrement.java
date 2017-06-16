package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrDecrement represents assembler instruction for 
 * decrementing given number.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrDecrement implements Instruction {
	
	/** First argument register */
	private int registerIndex1;

	/**
	 * Constructor for InstrDecrement class. Creates a new instruction for 
	 * decrementing given number. It accepts list with one register
	 * where is integer number to decrement 
	 * @param arguments List of registers.
	 */
	public InstrDecrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 3 arguments!");
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
		Object value1 = computer.getRegisters()
				.getRegisterValue(registerIndex1);

		computer.getRegisters().setRegisterValue(registerIndex1,
				Integer.valueOf(((Integer) value1).intValue() - 1));
		return false;
	}

}
