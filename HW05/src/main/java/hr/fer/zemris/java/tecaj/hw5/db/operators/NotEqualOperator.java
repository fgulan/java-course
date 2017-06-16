package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * NotEqualOperator class represents "!=" comparison operator.
 * It implements IComparisonOperator interface.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class NotEqualOperator implements IComparisonOperator{

	/**
	 * Checks if given value1 is not equal to value2. 
	 * @param value1 Value to compare.
	 * @param value2 Value to compare with.
	 * @return <code>true</code> if given strings satisfy comparison condition,
	 * <code>false</code> otherwise.
	 * @throws IllegalArgumentException If wildcard is used.
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if (value2.contains("*")) {
			throw new IllegalArgumentException("Wildcard can only be used with equal operator!");
		}
		return !value1.equals(value2);
	}
	

}
