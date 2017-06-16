package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * InstrLoad represents assembler instruction for loading value
 * from memory to register.
 * 
 * @author Filip Gulan
 * @version 1.0
 */
public class InstrLoad implements Instruction {

	/** First argument register */
	private int registerIndex1;
	/** Address to load from */
	private int adress;

	/**
	 * Constructor for InstrLoad class. Creates a new instruction for 
	 * loading memory value to register. It accepts list where first element
	 * is destination register, and second is memory location as number.
	 * @param arguments List of arguments.
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Type mismatch for argument 0!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Type mismatch for argument 1!");
		}

		this.registerIndex1 = ((Integer) arguments.get(0).getValue())
				.intValue();
		this.adress = ((Integer) arguments.get(1).getValue()).intValue();

	}

	/**
	 * Executes this instruction on given computer. 
	 * @param computer Computer
	 * @return <code>false</code> so processor is not halted after this instruction.
	 */
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(registerIndex1,
				computer.getMemory().getLocation(adress));
		return false;
	}

}
