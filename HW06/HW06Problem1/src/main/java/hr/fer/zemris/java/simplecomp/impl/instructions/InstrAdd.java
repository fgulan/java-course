package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrAdd represents assembler instruction for adding two numbers.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrAdd implements Instruction {
	
	/** First argument register */
	private int registerIndex1;
	/** Second argument register */
	private int registerIndex2;
	/** Third argument register */
	private int registerIndex3;

	/**
	 * Constructor for InstrAdd class. Creates a new instruction for 
	 * adding two numbers. It accepts list with three registers
	 * where first register is result register, and other two are 
	 * integer numbers to add. 
	 * @param arguments List of registers.
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}
		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 2!");
		}

		this.registerIndex1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.registerIndex2 = ((Integer) arguments.get(1).getValue())
				.intValue();
		this.registerIndex3 = ((Integer) arguments.get(2).getValue())
				.intValue();
	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>false</code> so processor is not halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters()
				.getRegisterValue(registerIndex2);
		
		Object value2 = computer.getRegisters()
				.getRegisterValue(registerIndex3);
		
		computer.getRegisters().setRegisterValue(
				registerIndex1,
				Integer.valueOf(((Integer) value1).intValue()
						+ ((Integer) value2).intValue()));
		return false;
	}

}
