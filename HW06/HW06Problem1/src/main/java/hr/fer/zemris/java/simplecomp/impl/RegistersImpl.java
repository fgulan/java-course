package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * RegistersImpl class represents registers available to the processor.
 * This class holds three types of registers: general purpose registers,
 * program counter and flag register.
 *  
 * <p>The number of general purpose registers is set through constructor.
 * 
 * <p>Implements Registers interface.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class RegistersImpl implements Registers {

	/** General purpose registers */
	private Object[] registers;
	/** Program counter */
	private int programCounter;
	/** Flag register */
	private boolean flag;

	/**
	 * Constructor for RegistersImpl class. Creates registers representation
	 * with given number of general purpose registers.
	 * @param regsLen Number of general purpose registers.
	 */
	public RegistersImpl(int regsLen) {
		registers = new Object[regsLen];
	}

	/**
	 * Gets object stored at general purpose register with given index.
	 * @param index Index of an general purpose registers.
	 * @return Object stored at general purpose register with given index.
	 * @throws IndexOutOfBoundsException If index value is less than zero, or 
	 * greater or equal to number of general purpose registers.
	 */
	public Object getRegisterValue(int index) {
		if(index < 0 || index >= registers.length) {
			throw new IndexOutOfBoundsException("Invalid index for general purpose registers!");
		}

		return registers[index];
	}

	/**
	 * Sets a new object to the general purpose register with given index.
	 * @param index Index of an general purpose registers.
	 * @param value Object to store.
	 * @throws IndexOutOfBoundsException If index value is less than zero, or 
	 * greater or equal to number of general purpose registers.
	 */
	public void setRegisterValue(int index, Object value) {
		if(index < 0 || index >= registers.length) {
			throw new IndexOutOfBoundsException("Invalid index for general purpose registers!");
		}

		registers[index] = value;
	}

	/**
	 * Returns the value from program counter register.
	 * @return The value from program counter register.
	 */
	public int getProgramCounter() {
		return programCounter;
	}

	/**
	 * Sets the value of program counter register to the given value.
	 * @param value New value.
	 */
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	/**
	 * Increments program counter register value.
	 */
	public void incrementProgramCounter() {
		programCounter++;
	}

	/**
	 * Returns value from flag register.
	 * @return Flag register value.
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * Sets the flag register to the given value.
	 * @param value New value.
	 */
	public void setFlag(boolean value) {
		flag = value;
	}

}
