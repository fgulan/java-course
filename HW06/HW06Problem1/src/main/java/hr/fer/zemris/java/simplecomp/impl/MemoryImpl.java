package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * MemoryImpl class represents computer memory. This memory
 * stores a reference to the objects (String and Integer) so on
 * each memory location can be stored arbitrarily large object.
 * 
 * <p>Size of memory is given through constructor
 * 
 * <p>{@code null} represents empty memory location.

 * @author Filip Gulan
 * @version 1.0
 * 
 */
public class MemoryImpl implements Memory {
	
	/** Memory */
	Object[] memory;
	
	/**
	 * Constructor for MemoryImpl class. Creates memory with given size.
	 * @param size Size of memory (number of Objects to hold).
	 */
	public MemoryImpl(int size) {
		memory = new Object[size];
	}

	/**
	 * Stores given object on given location in memory.
	 * @param location Memory address.
	 * @param value Object to store.
	 * @throws IndexOutOfBoundsException If index location is less than zero, or 
	 * greater or equal to size of memory.
	 */
	public void setLocation(int location, Object value) {
		if(location >= memory.length || location < 0) {
			throw new IndexOutOfBoundsException("Invalid memory address!");
		}
		
		memory[location] = value;
	}

	/**
	 * Returns object from given location in memory.
	 * @param location Memory address.
	 * @return Object from given location in memory.
	 * @throws IndexOutOfBoundsException If index location is less than zero, or 
	 * greater or equal to size of memory.
	 */
	public Object getLocation(int location) {
		if(location >= memory.length || location < 0) {
			throw new IndexOutOfBoundsException("Invalid memory address!");
		}
		
		return memory[location];
	}

}
