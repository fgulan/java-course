package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * StudentRecord class represent each student as an object with first name,
 * last name, JMBAG number and final grade. Its constructor accepts those listed
 * arguments.
 * 
 * <p>Two StudentRecord objects are the same if they have same JMBAG number. So
 * StudentRecord overrides hashCode() which computes integer representation of
 * object based on JMBAG number.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class StudentRecord {

	/** JMBAG number of student */
	private String jmbag;
	/** Student's last name */
	private String lastName;
	/** Student's first name */
	private String firstName;
	/** Student's final grade */
	private int finalGrade;

	/**
	 * StudentRecord class constructor. Creates a new student record from given arguments.
	 * @param jmbag JMBAG number of student.
	 * @param lastName Student's last name
	 * @param firstName Student's first name
	 * @param finalGrade Student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns student's JMBAG number.
	 * @return JMBAG number.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name
	 * @return Last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name.
	 * @return First name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns student's final grade.
	 * @return Final grade.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Checks if two StudentRecords are the same.
	 * @param student Student to compare with.
	 * @return <code>true</code> if two StudentRecords are the same, 
	 * <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object student) {
		if (!(student instanceof StudentRecord)) {
            return false;
		}
		
		StudentRecord record = (StudentRecord) student;
		return this.jmbag.equals(record.getJmbag());
	}

	/**
	 * Computes StudentRecord object integer representation based
	 * on its JMBAG number.
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return this.jmbag.hashCode();
	}

}
