package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * ConditionalExpression class represents conditional expression given 
 * by user to iterate through database elements on given conditions.
 * 
 * <p>Conditional expression is consisted of a field getter, comparison
 * operator and string literal to search in database.
 * 
 * <p>Constructor of ConditionalExpression class accepts {@link IFieldValueGetter} 
 * object as field getter, {@link String} object as string literal and 
 * {@link IComparisonOperator} object as comparison operator.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ConditionalExpression {

	/** Field getter object */
	private IFieldValueGetter fieldGetter;
	/** Literal to search for */
	private String stringLiteral;
	/** Comparison operator object */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor of ConditionalExpression class. Creates a new conditional
	 * expression on given field getter, literal and comparison operator.
	 * @param fieldGetter Field getter object.
	 * @param stringLiteral String literal object.
	 * @param comparisonOperator Comparison operator object.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter,
			String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns field getter object.
	 * @return Field getter.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns string literal.
	 * @return String literal.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns comparison operator object.
	 * @return Comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
}
