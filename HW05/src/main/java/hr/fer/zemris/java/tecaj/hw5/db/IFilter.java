package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * IFilter interface for objects that process given StudentRecord object.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface IFilter {
	
	/**
	 * Checks if given student record satisfy conditional expressions from query.
	 * @param record Student record to check.
	 * @return <code>true</code> if given record satisfy all conditional expressions,
	 * <code>false</code> otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
