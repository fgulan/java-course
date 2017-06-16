package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * ExecutionUnitImpl class represents computer central 
 * processing unit (CPU). It has only one method: go
 * which runs program from memory of given Computer instance.
 * 
 * <p>ExecutionUnitImpl class implements ExecutionUnit interface.
 * 
 * @author Filip Gulan
 * @version 1.0 
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	/**
	 * Method which runs program from memory of given Computer instance.
	 * @param computer Computer
	 * @return <code>true</code> if program ended correctly, <code>false</code> otherwise.
	 */
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		while (true) {
			int location = computer.getRegisters().getProgramCounter();

			if (computer.getMemory().getLocation(location) instanceof Instruction) {
				Instruction instruction = (Instruction) computer.getMemory()
						.getLocation(location);
				
				try {
					if (instruction.execute(computer)) {
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			}
			computer.getRegisters().incrementProgramCounter();
		}
	}
}
