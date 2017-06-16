package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * LastNameFieldGetter class represents getter for student last
 * name value. It implements IFieldValueGetter interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LastNameFieldGetter implements IFieldValueGetter {

	/**
	 * Returns value of last name field for given student record.
	 * @param record Student record.
	 * @return Value of last name field.
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getLastName();
	}

}
