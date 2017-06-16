package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * ComputerImpl represents a computer registers and memory.
 * 
 * <p>Implements Computer interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ComputerImpl implements Computer {

	/** CPU registers */
	private Registers registers;
	/** Computer memory */
	private Memory memory;
	
	/**
	 * Constructor for ComputerImpl class. Creates computer with given size
	 * of memory and number of general purpose registers.
	 * @param memorySize Size of memory.
	 * @param regsLen Number of general purpose registers.
	 */
	public ComputerImpl(int memorySize, int regsLen) {
		memory = new MemoryImpl(memorySize);
		registers = new RegistersImpl(regsLen);
	}
	
	/**
	 * Returns register of computer.
	 * @return Registers
	 */
	public Registers getRegisters() {
		return registers;
	}

	/**
	 * Returns computer memory.
	 * @return Memory.
	 */
	public Memory getMemory() {
		return memory;
	}

}
