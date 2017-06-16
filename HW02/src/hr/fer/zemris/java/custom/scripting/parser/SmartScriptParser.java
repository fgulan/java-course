package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;
import hr.fer.zemris.java.custom.collections.*;

/**
 * Implementation of document parser. It creates a document structure based on nodes. It accept text, for loop and echo nodes.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SmartScriptParser {
	
	/**
	 * Collection of acceptable states.
	 * @author Filip Gulan
	 *
	 */
	private enum state {
		INITAL_STATE, OPENING_TAG, OPENED_TAG, NEW_COMMAND,
		FOR_LOOP_VARIABLE, FOR_LOOP_START_EXPRESSION, FOR_LOOP_END_EXPRESSION, FOR_LOOP_STEP_EXPRESSION,
		FOR_LOOP_START_EXPRESSION_STRING, FOR_LOOP_END_EXPRESSION_STRING, FOR_LOOP_STEP_EXPRESSION_STRING,
		ESCAPED_STRING_FOR, ESCAPED_TEXT, TEXT_STATE, END_NODE, CLOSING_TAG,
		ECHO_STATE, STRING_ECHO, FUNCTION_ECHO, NUMBER_OPERATOR_ECHO, 
		VARIABLE_ECHO, ESCAPED_STRING_ECHO  
	}
	
	//Structure for text par
	private ObjectStack stack;
	private String docBody;
	private DocumentNode documentNode;
	

	/**
	 * Constructor of SmartScriptParser class.
	 * @param docBody Text to parse.
	 */
	public SmartScriptParser(String docBody) {
		this.stack = new ObjectStack();
		this.docBody = docBody;
		parseDocument();
	}
	
	/**
	 * Parse given document and create tree of document nodes.
	 */
	private void parseDocument() {
		
		//Create new document node for current text and place it as a parent on stack
		documentNode = new DocumentNode();
		stack.push(documentNode);
		
		//Put whole text into ByteArrayInputStream and read character by character
		Reader reader = new InputStreamReader(new ByteArrayInputStream(docBody.getBytes()));
		StringBuilder buffer = new StringBuilder();
		
		//Temp collections for echo node and for node tokens
		ArrayBackedIndexedCollection echoTokens = new ArrayBackedIndexedCollection();
		ArrayBackedIndexedCollection forTokens = new ArrayBackedIndexedCollection();
		
		try {
			//Temp state if for loop node contains string token with escaping
			state previousForState = null;
			state currentState = state.INITAL_STATE;
			int inputData;
			
			//For each input symbol transition to a new state until all input symbols have been consumed
			while((inputData = reader.read()) != -1) {
				char symbol = (char) inputData;
				
				switch (currentState) {
				
					case INITAL_STATE:
						if(symbol == '{') {
							currentState = state.OPENING_TAG;
						} else if (symbol == '\\') {
							currentState = state.ESCAPED_TEXT;
						} else {
							buffer.append(symbol);
							currentState = state.TEXT_STATE;
						}
						break;
						
					case OPENING_TAG:
						if(symbol == '$') {
							currentState = state.OPENED_TAG;
						} else {
							throw new SmartScriptParserException("Invalid opening tag!");
						}
						break;
						
					case ESCAPED_TEXT:
						if(symbol == '\\') {
							buffer.append(symbol);
						} else if (symbol == '{') {
							buffer.append(symbol);
						} else {
							buffer.append('\\');
							buffer.append(symbol);
						}
						currentState = state.TEXT_STATE;
						break;
						
					case TEXT_STATE:
						if(symbol == '{') {
							newTextNode(buffer.toString());
							buffer.setLength(0);
							currentState = state.OPENING_TAG;
						} else if (symbol == '\\') {
							currentState = state.ESCAPED_TEXT;						
						} else {
							buffer.append(symbol);
						}
						break;
						
					case OPENED_TAG:
						if(isBlank(symbol)) {
							//skip empty spaces
						} else if (Character.isLetter(symbol)) {
							buffer.append(symbol);
							currentState = state.NEW_COMMAND;
						} else if (symbol == '=') {
							buffer.setLength(0);
							currentState = state.ECHO_STATE;
						} else {
							throw new SmartScriptParserException("Unkonown tag name!");
						}
						break;
						
					case NEW_COMMAND:
						if(isBlank(symbol)) {
							if(buffer.toString().toUpperCase().equals("FOR")) {
								buffer.setLength(0);
								currentState = state.FOR_LOOP_VARIABLE;
							} else if (buffer.toString().toUpperCase().equals("END")) {
								buffer.setLength(0);
								currentState = state.END_NODE;
							} else {
								throw new SmartScriptParserException("Cannot parse the document! Parser only accepts end or for tag as command!");
							}
						} else if (symbol == '$') {
							buffer.setLength(0);
							
							if(stack.isEmpty()) {
								throw new SmartScriptParserException("Too much ends");
							} else {
								stack.pop();
								currentState = state.CLOSING_TAG;
							}
						} else if(Character.isLetter(symbol)) {
							buffer.append(symbol);
						} else {
							throw new SmartScriptParserException("Command exception!");
						}
						break;
						
					case CLOSING_TAG:
						if(symbol == '}') {
							buffer.setLength(0);
							currentState = state.INITAL_STATE;
						} else {
							throw new SmartScriptParserException("Closing tag exception");
						}
						break;
						
					case FOR_LOOP_VARIABLE:
						if((isBlank(symbol)) && isEmptyOrWhiteSpace(buffer)) {
							//skip empty spaces
						} else if (symbol == '"') {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FOR_LOOP_START_EXPRESSION_STRING;
						} else if ((isBlank(symbol)) && !isEmptyOrWhiteSpace(buffer)) {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							currentState = state.FOR_LOOP_START_EXPRESSION;
						} else if(Character.isLetter(symbol)) {
							buffer.append(symbol);
						} else if ((Character.isDigit(symbol) || symbol == '_') && buffer.length() > 0) {
							buffer.append(symbol);
						} else {
							throw new SmartScriptParserException("Cannot parse a document! Invalid for loop variable!");
						}
						break;
						
					case FOR_LOOP_START_EXPRESSION:
						if (symbol == '"' && buffer.length() == 0) {
							buffer.append(symbol);
							currentState = state.FOR_LOOP_START_EXPRESSION_STRING;
						} else if(symbol == '"' && buffer.length() != 0) {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FOR_LOOP_END_EXPRESSION_STRING;
						} else if(isBlank(symbol) && isEmptyOrWhiteSpace(buffer)) {
							//skip empty spaces
						} else if (isBlank(symbol) && !isEmptyOrWhiteSpace(buffer)) {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							currentState = state.FOR_LOOP_END_EXPRESSION;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case FOR_LOOP_START_EXPRESSION_STRING:
						if (symbol == '"') {
							buffer.append(symbol);
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							currentState = state.FOR_LOOP_END_EXPRESSION;
						} else if (symbol == '\\') {
							previousForState = state.FOR_LOOP_START_EXPRESSION_STRING;
							currentState = state.ESCAPED_STRING_FOR;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case ESCAPED_STRING_FOR:
						if(symbol == '\\' || symbol == '"') {
							buffer.append(symbol);
			    		} else if (symbol == 'n') {
			    			buffer.append('\n');
			    		} else if (symbol == 'r') {
			    			buffer.append('\r');
			    		} else if (symbol == 't') {
			    			buffer.append('\t');
			    		} else {
			    			buffer.append('\\');
			    			buffer.append(symbol);
			    		}
			    		currentState = previousForState;
			    		break;
						
					case FOR_LOOP_END_EXPRESSION:
						if (symbol == '"' && buffer.length() == 0) {
							buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FOR_LOOP_END_EXPRESSION_STRING;
						} else if(symbol == '"' && buffer.length() != 0) {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FOR_LOOP_STEP_EXPRESSION_STRING;
						} else if(isBlank(symbol) && isEmptyOrWhiteSpace(buffer)) {
							//skip empty spaces
						} else if(isBlank(symbol) && !isEmptyOrWhiteSpace(buffer)) {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							currentState = state.FOR_LOOP_STEP_EXPRESSION;
						} else if (symbol == '$') {
							newToken(buffer.toString(), forTokens);
							newForLoopNode(forTokens);
							buffer.setLength(0);
							currentState = state.CLOSING_TAG;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case FOR_LOOP_END_EXPRESSION_STRING:
						if (symbol == '"') {
							buffer.append(symbol);
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							currentState = state.FOR_LOOP_STEP_EXPRESSION;
						} else if (symbol == '\\') {
							previousForState = state.FOR_LOOP_END_EXPRESSION_STRING;
							currentState = state.ESCAPED_STRING_FOR;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case FOR_LOOP_STEP_EXPRESSION:
						if(isBlank(symbol) && isEmptyOrWhiteSpace(buffer)) {
							//skip empty spaces
						} else if(isBlank(symbol) && !isEmptyOrWhiteSpace(buffer)) {
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);
							newForLoopNode(forTokens);
						} else if(symbol == '"' && buffer.length() == 0) {
							buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FOR_LOOP_STEP_EXPRESSION_STRING;
						} else if (symbol == '$') {
							if (buffer.length() != 0) {
								newToken(buffer.toString(), forTokens);
								newForLoopNode(forTokens);
								buffer.setLength(0);
							} else if (forTokens.size() == 3) {
								newForLoopNode(forTokens);
							}
							currentState = state.CLOSING_TAG;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case FOR_LOOP_STEP_EXPRESSION_STRING:
						if (symbol == '"') {
							buffer.append(symbol);
							newToken(buffer.toString(), forTokens);
							buffer.setLength(0);					
						} else if (symbol == '$' && isEmptyOrWhiteSpace(buffer)) {
							newForLoopNode(forTokens);
							currentState = state.CLOSING_TAG;
						} else if (symbol == '\\') {
							previousForState = state.FOR_LOOP_STEP_EXPRESSION_STRING;
							currentState = state.ESCAPED_STRING_FOR;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case END_NODE:
						if(isBlank(symbol)) {
							//skip empty spaces
						} else if (symbol == '$') {
							if(stack.isEmpty()) {
								throw new SmartScriptParserException("Cannot parse a document! Stack is empty! Please check number of end nodes.");
							} else {
								stack.pop();
								currentState = state.CLOSING_TAG;
							}
						} else {
							throw new SmartScriptParserException("Cannot parse a document! Failed to get end node!");
						}
						break;
						
					case ECHO_STATE:
						if (isBlank(symbol)) {
							//Skip empty spaces
						} else if (symbol == '"') {
							buffer.append(symbol);
							currentState = state.STRING_ECHO;
						} else if (symbol == '@') {
							buffer.append(symbol);
							currentState = state.FUNCTION_ECHO;
						} else if (Character.isDigit(symbol) || isOperator(Character.toString(symbol))) {
							buffer.append(symbol);
							currentState = state.NUMBER_OPERATOR_ECHO;
						} else if (Character.isLetter(symbol)) {
							buffer.append(symbol);
			    			currentState = state.VARIABLE_ECHO;
			    		} else if (symbol == '$') {
			    			newEchoNode(echoTokens);
			    			buffer.setLength(0);
			    			currentState = state.CLOSING_TAG;
			    		} else {
			    			throw new SmartScriptParserException("Cannot parse a document! Invalid echo token!");
			    		}
						break;
						
					case STRING_ECHO:
						if (symbol == '"') {
							buffer.append(symbol);
							newToken(buffer.toString(), echoTokens);
							buffer.setLength(0);
							currentState = state.ECHO_STATE;
						} else if (symbol == '\\') {
							currentState = state.ESCAPED_STRING_ECHO;
						} else {
							buffer.append(symbol);
						}
						break;
						
					case ESCAPED_STRING_ECHO:
						if(symbol == '\\' || symbol == '"') {
							buffer.append(symbol);
			    		} else if (symbol == 'n') {
			    			buffer.append('\n');
			    		} else if (symbol == 'r') {
			    			buffer.append('\r');
			    		} else if (symbol == 't') {
			    			buffer.append('\t');
			    		} else {
			    			buffer.append('\\');
			    			buffer.append(symbol);
			    		}
			    		currentState = state.STRING_ECHO;
			    		break;
			    		
					case VARIABLE_ECHO:
						if(Character.isLetter(symbol) || Character.isDigit(symbol) || symbol == '_') {
							buffer.append(symbol);
		    			} else if (isBlank(symbol)) {
		    				newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
		    				currentState = state.ECHO_STATE;
		    			} else if (symbol == '$') {
		    				newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
			    			newEchoNode(echoTokens);
			    			currentState = state.CLOSING_TAG;
			    		} else if (symbol == '@') {
			    			newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FUNCTION_ECHO;
			    		} else if (symbol == '"') {
			    			newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.STRING_ECHO;
			    		} else {
		    				throw new SmartScriptParserException("Cannot parse a document! Invalid variable name! Variable name must start with letter.");
		    			}
						break;
						
					case FUNCTION_ECHO:
						//Checks if first symbol of function is letter
						if(buffer.length() == 1) {
							if(Character.isLetter(symbol)) {
								buffer.append(symbol);
							} else {
								throw new SmartScriptParserException("Cannot parse a document! Invalid function name! Function name must start with letter.");
							}
						} else {
							if(Character.isLetter(symbol) || Character.isDigit(symbol) || symbol == '_') {
								buffer.append(symbol);
			    			} else if (isBlank(symbol)) {
			    				newToken(buffer.toString(), echoTokens);
			    				buffer.setLength(0);
			    				currentState = state.ECHO_STATE;
			    			} else if (symbol == '$') {
			    				newToken(buffer.toString(), echoTokens);
			    				buffer.setLength(0);
				    			newEchoNode(echoTokens);
				    			currentState = state.CLOSING_TAG;
				    		}  else if (symbol == '"') {
				    			newToken(buffer.toString(), echoTokens);
			    				buffer.setLength(0);
								buffer.append(symbol);
								currentState = state.STRING_ECHO;
				    		} else if (isOperator(Character.toString(symbol))) {
				    			newToken(buffer.toString(), echoTokens);
			    				buffer.setLength(0);
								buffer.append(symbol);
								currentState = state.NUMBER_OPERATOR_ECHO;
				    		} else {
			    				throw new SmartScriptParserException("Cannot parse a document! Function can contain letters, numbers and underscores!");
			    			}
						}
						break;
						
					case NUMBER_OPERATOR_ECHO:
						if(Character.isDigit(symbol) || (symbol == '.' && Character.isDigit(buffer.charAt(buffer.length()-1)))) {
							buffer.append(symbol);
			    		} else if (isBlank(symbol)) {
			    			newToken(buffer.toString(), echoTokens);
			    			buffer.setLength(0);
			    			currentState = state.ECHO_STATE;
			    		} else if (symbol == '$') {
			    			newToken(buffer.toString(), echoTokens);
			    			buffer.setLength(0);
			    			newEchoNode(echoTokens);
			    			currentState = state.CLOSING_TAG;
			    		} else if (symbol == '"') {
			    			newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.STRING_ECHO;
			    		} else if (symbol == '@') {
			    			newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.FUNCTION_ECHO;
			    		} else if (Character.isLetter(symbol)) {
			    			newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.VARIABLE_ECHO;
			    		} else if (isOperator(Character.toString(symbol)) || Character.isDigit(symbol)) {
			    			newToken(buffer.toString(), echoTokens);
		    				buffer.setLength(0);
							buffer.append(symbol);
							currentState = state.NUMBER_OPERATOR_ECHO;
			    		} else {
			    			throw new SmartScriptParserException("Cannot parse a document! "
			    					+ "Invalid number format or unsupported math operator in echo node!");
			    		}
			    	break;
					default:
						break;
				}
			}
			reader.close();
			
			//If stuck on reading ordinary text or escaped text save text, or if tag is opened but never closed throw an exception
			if(currentState == state.TEXT_STATE) {
				newTextNode(buffer.toString());
			} else if (currentState == state.ESCAPED_TEXT) {
				buffer.append("\\");
				newTextNode(buffer.toString());
			} else if (currentState != state.INITAL_STATE) {
				throw new SmartScriptParserException("Cannot parse a document! Tag opened but never closed!");
			}
			
			//Clear stack
			if(stack.isEmpty()) {
				throw new SmartScriptParserException("Cannot parse a document! Stack is empty! Please check number of end nodes.");
			} else {
				stack.pop();
			}
			
			//If there is a more end nodes than for loop nodes in text throw an exception
			if(!stack.isEmpty()) {
				throw new SmartScriptParserException("Cannot parse a document! Stack is not empty! Please check number of end nodes.");
			}
			
		} catch (IOException e) {
			throw new SmartScriptParserException("Error while reading a text.");
		}
		
		
	}

	/**
	 * Checks if given StringBuilder is empty or last character is whitespace.
	 * @return <code>true</code> if StringBuilder is empty or last character is whitespace, <code>false</code> otherwise.
	 */
	private boolean isEmptyOrWhiteSpace(StringBuilder buffer) {
		return buffer.length() == 0 || buffer.charAt(buffer.length()-1) == ' ';
	}
	
	/**
	 * Checks if input string is type of math operator (+, -, *, / and %).
	 * @param data Input string
	 * @return <code>true</code> if input string is type of math operator, <code>false</code> otherwise.
	 */
	private boolean isOperator(String data) {
		return (data.equals("+") || data.equals("-") || data.equals("*") || data.equals("/") || data.equals("%"));
	}
	
	/**
	 * Checks if input string is type of integer.
	 * @param data Input string
	 * @return <code>true</code> if input string is type of integer, <code>false</code> otherwise.
	 */
	public static boolean isInteger(String data) {
		try {
			Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if input string is type of double.
	 * @param data Input string
	 * @return <code>true</code> if input string is type of double, <code>false</code> otherwise.
	 */
	public static boolean isDouble(String data) {
		try {
			Double.parseDouble(data);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Creates a new for loop node from given tokens and adds it as a child
	 * to current node on stack and also adds it on stack as a parent.
	 * @param forTokens Array of strings from which method creates a tokens for for loop node
	 * (variable, start expression, end expression and step expression).
	 * @throws SmartScriptParserException If stack is empty or there is invalid number of tokens
	 */
	private void newForLoopNode(ArrayBackedIndexedCollection forTokens) {
		if(stack.isEmpty()) {
			throw new SmartScriptParserException("Cannot create new ForLoopNode! "
					+ "Stack is empty! Please check number of end tags.");
		} else if (forTokens.size() > 4 || forTokens.size() <3) {
			throw new SmartScriptParserException("Cannot create new ForLoopNode! "
					+ "Number of ForLoopNode elements is invalid.");			
		} else {
			Token[] token = new Token[4];
			int numberOfTokens = forTokens.size();
			
			for(int i = 0; i < numberOfTokens; i++) {
				token[i] = (Token) forTokens.get(i);
				if(token[i] instanceof TokenFunction || token[i] instanceof TokenOperator) {
					throw new SmartScriptParserException("Cannot create new ForLoopNode! "
							+ "ForLoopNode elements cannot be function or operator.");
				}
			}
			
			forTokens.clear();
			ForLoopNode forNode = new ForLoopNode((TokenVariable) token[0], token[1], token[2], token[3]);
			((Node) stack.peak()).addChildNode(forNode);
			stack.push(forNode);
		}

	}
	
	/**
	 * Creates a new echo node from given tokens and add it as a child to current node on stack.
	 * @param echoTokens Collection of tokens to add.
	 * @throws SmartScriptParserException If stack is empty. 
	 */
	private void newEchoNode(ArrayBackedIndexedCollection echoTokens) {
		if(stack.isEmpty()) {
			throw new SmartScriptParserException("Cannot create new EchoNode! "
					+ "Stack is empty! Please check number of end tags.");
		} else if (echoTokens != null) {
			int size = echoTokens.size();
			Token[] tokens = new Token[size];
			
			for (int i = 0; i < size; i++) {
				tokens[i] = (Token) echoTokens.get(i);	
			}
				
			echoTokens.clear();
			((Node) stack.peak()).addChildNode(new EchoNode(tokens));
		}
	}
	
	/**
	 * Creates a new text node and adds it to current node on stack as a child
	 * @param text Text to add.
	 * @throws SmartScriptParserException If stack is empty.
	 */
	private void newTextNode(String text) {
		if(stack.isEmpty()) {
			throw new SmartScriptParserException("Cannot create new TextNode! "
					+ "Stack is empty! Please check the number of the end tags.");
		} else {
			((Node) stack.peak()).addChildNode(new TextNode(text));
		}
	}
	
	/**
	 * Adds a new token to collection.
	 * @param collection Token value to add.
	 */
	private void newToken(String text, ArrayBackedIndexedCollection collection) {
		collection.add(createToken(text));
	}
	
	/**
	 * Creates a new token based on input parameter.
	 * @param data Value of token.
	 * @return New token.
	 * @throws SmartScriptParserException If input parameter does not fit to any of tokens or input parameter is empty.
	 */
	private Token createToken(String data) {
		if (!data.isEmpty()) {
			if (isInteger(data)) {
				return new TokenConstantInteger(Integer.parseInt(data));
			} else if (isDouble(data)) {
				return new TokenConstantDouble(Double.parseDouble(data));
			} else if (Character.isLetter(data.charAt(0))) {
				return new TokenVariable(data);
			} else if (data.startsWith("@")) {
				return new TokenFunction(data.substring(1));
			} else if (isOperator(data)) {
				return new TokenOperator(data);
			} else if (data.startsWith("\"") && data.endsWith("\"")) {
				return new TokenString(data.substring(1, data.length()-1));
			} else {
				throw new SmartScriptParserException("Cannot parse document! Unknown token!");
			}
		} else {
			throw new SmartScriptParserException("Cannot parse document! Token is empty!");
		}

	}
	
	/**
	 * Checks if current symbol is gap in a text.
	 * @param symbol Symbol to check
	 * @return <code>true</code> if symbol is blank space, new line or tab, <code>false</code> otherwise.
	 */
	private boolean isBlank(char symbol) {
		return (symbol == ' ' || symbol == '\n' || symbol == '\t' || symbol == '\r');	
	}
	
	/**
	 * Returns a document node.
	 * @return Document node.
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}