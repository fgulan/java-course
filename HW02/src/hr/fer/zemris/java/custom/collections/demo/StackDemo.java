package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;
/**
 * Program for testing ObjectStack collection with evaluating simple mathematical expressions using only +, -, *, / and % as operators and integer number as operands.
 * Program accepts single command line argument as a string with expression to evaluate, e.g. "8 -2 / -1 *" results as 4.
 * Always enter the phrase in quotation marks as shown in example ("8 -2 / -1 *").
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class StackDemo {
	
	/**
	 * Start point of the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		String[] arguments = null;
		
		try {
			arguments = args[0].split("\\s+");
			System.out.println("Expression evaluates to " + evaluateExpression(arguments) + ".");
		} catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("There is no input arguments!");
			System.exit(-1);
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	/**
	 * Evaluates simple mathematical expressions according to the order that is specified in the argument.
	 * @param arguments Array of operands and operators to evaluate.
	 * @return Result of expression.
	 */
	public static int evaluateExpression(String[] arguments) {
		if(arguments.length == 0) {
			throw new IllegalArgumentException("There is no expression to evaluate!");
		}
		
		ObjectStack stack = new ObjectStack();
		
		for (String data : arguments) {
			if(isNumeric(data)) {
				stack.push(Integer.parseInt(data));
			} else if(isMathSymbol(data)) {
				
				if (stack.size() < 2)  {
					throw new IllegalArgumentException("Invalid expression! Insufficient number of operands or operators.");
				}
				
				int secondArgument = (int) stack.pop();
				int firstArgument = (int) stack.pop();
				
				switch (data) {
					case "+":
						stack.push(firstArgument+secondArgument);
						break;
					case "-":
						stack.push(firstArgument-secondArgument);
						break;
					case "*":
						stack.push(firstArgument*secondArgument);
						break;
					case "/":
						if(secondArgument == 0) {
							throw new IllegalArgumentException("Invalid expression. Division by zero!");
						}
						stack.push(firstArgument/secondArgument);
						break;
					case "%":
						if(secondArgument == 0) {
							throw new IllegalArgumentException("Invalid expression. Division by zero!");
						}
						stack.push(firstArgument%secondArgument);
						break;
				}
			}
		}
		
		if(stack.size() != 1) {
			throw new IllegalArgumentException("Invalid expression! Insufficient number of operands or operators.");
		}
		return (int) stack.pop();
	}
	
	/**
	 * Checks if given string is integer number.
	 * @param data String to check.
	 * @return <code>true</code> if <code>data</code> is integer number, false otherwise.
	 */
	public static boolean isNumeric(String data) {
		try {
			Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if given string is mathematical operator (+, -, *, /, %).
	 * @param data String to check.
	 * @return <code>true</code> if <code>data</code> is mathematical operator (+, -, *, /, %), false otherwise.
	 */
	public static boolean isMathSymbol(String data) {
		return (data.equals("+") || data.equals("-") || data.equals("*") || data.equals("/") || data.equals("%"));
	}
}
