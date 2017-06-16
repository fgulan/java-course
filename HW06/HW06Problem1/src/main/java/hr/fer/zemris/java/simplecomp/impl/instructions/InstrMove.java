package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrMove represents assembler instruction for moving values
 * from one to another register.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrMove implements Instruction {
	
	/** First argument register */
	private int registerIndex1;
	/** Second argument register */
	private int registerIndex2;

	/**
	 * Constructor for InstrMove class. Creates a new instruction for 
	 * moving register value to another register. It accepts list with
	 * two registers where first register is destination and second is
	 * source register.
	 * @param arguments List of registers.
	 */
	public InstrMove(List<InstructionArgument> arguments) {
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
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		computer.getRegisters().setRegisterValue(registerIndex1, value2);
		return false;
	}

}
