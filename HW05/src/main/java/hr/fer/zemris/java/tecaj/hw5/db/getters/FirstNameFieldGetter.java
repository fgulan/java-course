package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * FirstNameFieldGetter class represents getter for student first
 * name value. It implements IFieldValueGetter interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class FirstNameFieldGetter implements IFieldValueGetter {

	/**
	 * Returns value of first name field for given student record.
	 * @param record Student record.
	 * @return Value of first name field.
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getFirstName();
	}

}
