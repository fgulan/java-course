package hr.fer.zemris.java.student0036479428.hw06;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * ValueWrapper class represents wrapper for any type of an object.
 * This class implements several operation for performing over given
 * object if they are instances of {@link Integer}, {@link Double} or
 * {@link String} (If given string is also double or integer number).
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ValueWrapper {

	/** Stored value */
	private Object value;

	/** 
	 * Constructor for ValueWrapper. Creates value wrapper which stores given object.
	 * @param value Object to store.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns stored value.
	 * @return Stored value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Store given value.
	 * @param value Object to store.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Increments stored value with given value.
	 * @param incValue Value to increment with.
	 */
	public void increment(Object incValue) {
		calculate(incValue, new IncrementOperator());
	}

	/**
	 * Decrements stored value with given value.
	 * @param decValue Value to decrement with.
	 */
	public void decrement(Object decValue) {
		calculate(decValue, new DecrementOperator());
	}

	/**
	 * Multiplies stored value with given value.
	 * @param mulValue Value to multiply with.
	 */
	public void multiply(Object mulValue) {
		calculate(mulValue, new MultiplyOperator());
	}

	/**
	 * Divides stored value with given value.
	 * @param divValue Value to divide with.
	 */
	public void divide(Object divValue) {
		calculate(divValue, new DivideOperator());
	}

	/**
	 * Checks if given and stored value are instance of {@link Integer}, {@link Double},
	 * {@link String} or {@code null}.
	 * @param value2 Value to check.
	 */
	private void checkArguments(Object value2) {
		if (!(value instanceof Integer || value instanceof Double
				|| value instanceof String || value == null
				|| !(value2 instanceof Integer || value2 instanceof Double
				|| value2 instanceof String || value2 == null))) {
			throw new RuntimeException("Unsupported type of value!");
		}
	}

	/**
	 * Checks if given string is integer number.
	 * @param value String to check.
	 * @return {@code true} if given string is integer number, {@code false} otherwise.
	 */
	private boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns parsed number.
	 * @param number String number representation.
	 * @return Parsed number.
	 * @throws ParseException Unable to parse given string.
	 */
	private Number getDecimalNumber(String number) throws ParseException {
		DecimalFormat format = new DecimalFormat();
		DecimalFormatSymbols custom=new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		format.setDecimalFormatSymbols(custom);
		return format.parse(number);
	}
	/**
	 * Perform given operation over stored value and given value.
	 * @param value2 Value to perform operation with.
	 * @param operation Operation to perform.
	 */
	private void calculate(Object value2, IOperator operation) {
		checkArguments(value2);
		
		if (value2 == null) {
			value2 = Integer.valueOf(0);
		}
		if (this.value == null) {
			this.value = Integer.valueOf(0);
		}

		double result = 0;
		Number number1 = 0;
		Number number2 = 0;
		try {
			number1 = getDecimalNumber(this.value.toString());
			number2 = getDecimalNumber(value2.toString());
		} catch (ParseException e) {
			throw new RuntimeException("Unsupported type of value!");
		}
		result = operation.calculate(number1.doubleValue(), number2.doubleValue());

		if (isInteger(value2.toString()) && isInteger(this.value.toString())) {
			this.value = Integer.valueOf((int) result);
		} else {
			this.value = Double.valueOf(result);
		}
	}
	
	/**
	 * Compares current stored number with given number.
	 * @param withValue Number to compare with.
	 * @return 0 if given number and currently stored number are equal; integer less 
	 * than zero if currently stored value is smaller than argument; integer greater 
	 * than zero if currently stored value is greater than argument.
	 */
	public int numCompare(Object withValue) {
		checkArguments(withValue);
		
		if(value == null && withValue == null) {
			return 0;
		}
		if (withValue == null) {
			withValue = Integer.valueOf(0);
		}
		if (this.value == null) {
			this.value = Integer.valueOf(0);
		}
		
		Number number1 = 0;
		Number number2 = 0;
		try {
			number1 = getDecimalNumber(this.value.toString());
			number2 = getDecimalNumber(withValue.toString());
		} catch (ParseException e) {
			throw new RuntimeException("Unsupported type of value!");
		}
		return Double.compare(number1.doubleValue(), number2.doubleValue());
	}

	/**
	 * IOperator represents mathematical operator.
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	private interface IOperator {
		/**
		 * Perform operation over given arguments.
		 * @param number1 First argument.
		 * @param number2 Second argument.
		 * @return Result of given operation.
		 */
		double calculate(double number1, double number2);
	}

	/**
	 * DivideOperator class represents divide operation.
	 * @author Filip Gulan.
	 * @version 1.0
	 */
	private static class DivideOperator implements IOperator {
		/**
		 * Divides number1 with number2.
		 * @param number1 First argument.
		 * @param number2 Second argument.
		 * @return Result of given operation.
		 */
		public double calculate(double number1, double number2) {
			if (number2 == 0.0) {
				throw new IllegalArgumentException("Division by zero!");
			}
			return number1 / number2;
		}
	}

	/**
	 * DivideOperator class represents multiply operation.
	 * @author Filip Gulan.
	 * @version 1.0
	 */
	private static class MultiplyOperator implements IOperator {
		/**
		 * Multiply given arguments.
		 * @param number1 First argument.
		 * @param number2 Second argument.
		 * @return Result of given operation.
		 */
		public double calculate(double number1, double number2) {
			return Double.valueOf(number1 * number2);
		}
	}

	/**
	 * DivideOperator class represents increment operation.
	 * @author Filip Gulan.
	 * @version 1.0
	 */
	private static class IncrementOperator implements IOperator {
		/**
		 * Adds given numbers.
		 * @param number1 First argument.
		 * @param number2 Second argument.
		 * @return Result of given operation.
		 */
		public double calculate(double number1, double number2) {
			return Double.valueOf(number1 + number2);
		}
	}

	/**
	 * DivideOperator class represents decrement operation.
	 * @author Filip Gulan.
	 * @version 1.0
	 */
	private static class DecrementOperator implements IOperator {
		/**
		 * Subs number2 from number1.
		 * @param number1 First argument.
		 * @param number2 Second argument.
		 * @return Result of given operation.
		 */
		public double calculate(double number1, double number2) {
			return Double.valueOf(number1 - number2);
		}
	}
}
