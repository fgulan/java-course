package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface strategy for field value getter classes.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public interface IFieldValueGetter {
	/**
	 * Returns value of field for given student record.
	 * @param record Student record.
	 * @return Value of field.
	 */
	public String get(StudentRecord record);
}
