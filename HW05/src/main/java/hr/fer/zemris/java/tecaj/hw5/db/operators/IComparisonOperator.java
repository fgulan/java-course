package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Interface strategy for comparison operators classes.
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public interface IComparisonOperator {
	/**
	 * Checks if given strings satisfy comparison operator, where value1
	 * is on the left side of operator and value2 on the right side.
	 * @param value1 Value to compare.
	 * @param value2 Value to compare with.
	 * @return <code>true</code> if given strings satisfy comparison condition,
	 * <code>false</code> otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
