package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrTestEquals represents assembler instruction for comparing two numbers.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrTestEquals implements Instruction {

	/** First argument register */
	private int registerIndex1;
	/** Second argument register */
	private int registerIndex2;

	/**
	 * Constructor for InstrTestEquals class. Creates a new instruction for 
	 * comparing two numbers. It accepts list with two registers
	 * with numbers to compare.
	 * @param arguments List of registers.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.registerIndex2 = ((Integer) arguments.get(1).getValue())
				.intValue();

	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>false</code> so processor is not halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		if(value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}
