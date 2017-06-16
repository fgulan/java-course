package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * LastNameFieldGetter class represents getter for student JMBAG
 * number value. It implements IFieldValueGetter interface.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JmbagFieldGetter implements IFieldValueGetter {

	/**
	 * Returns value of JMBAG number field for given student record.
	 * @param record Student record.
	 * @return Value of JMBAG number field.
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getJmbag();
	}

}
