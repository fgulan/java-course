package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * EqualOperator class represents "=" comparison operator.
 * It implements IComparisonOperator interface.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class EqualOperator implements IComparisonOperator {
	
	/**
	 * Checks if given strings are equal or, if wildcard is 
	 * used, a part of strings are equal.
	 * @param value1 Value to compare.
	 * @param value2 Value to compare with. Wildcard can be used
	 * to ignore part of string to check.
	 * @return <code>true</code> if given strings are equal,
	 * <code>false</code> otherwise.
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if(value2.endsWith("*")) {
			return value1.startsWith(value2.replace("*", ""));
		} else if (value2.startsWith("*")) {
			return value1.endsWith(value2.replace("*", ""));
		} else if (value2.contains("*")) {
			String[] part = value2.split("\\*");
			if(part.length != 2) {
				throw new IllegalArgumentException("Too many wildcards in expression: " + value2);
			}
			return value1.startsWith(part[0]) && value1.endsWith(part[1]);
		}
		return value1.equals(value2);
	}
	

}
