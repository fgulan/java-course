package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of document parser. It creates a document structure based on
 * nodes. It accept text, for loop and echo nodes.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SmartScriptParser {

    /** Stack. */
    private ObjectStack stack;
    /** Document body. */
    private String docBody;
    /** Document node. */
    private DocumentNode documentNode;
    /** Text buffer. */
    private StringBuilder buffer;

    /** Current symbol. */
    private char symbol;
    /** Current state. */
    private State currentState = State.INITAL_STATE;
    /** Previous for loop state. */
    private State previousForState = null;
    /** Current echo tokens. */
    private ArrayBackedIndexedCollection echoTokens;
    /** Current for loop tokens. */
    private ArrayBackedIndexedCollection forTokens;

    /**
     * Constructor of SmartScriptParser class.
     * 
     * @param docBody
     *            Text to parse.
     */
    public SmartScriptParser(String docBody) {
        this.stack = new ObjectStack();
        this.docBody = docBody;
        buffer = new StringBuilder();
        echoTokens = new ArrayBackedIndexedCollection();
        forTokens = new ArrayBackedIndexedCollection();
        parseDocument();
    }

    /**
     * Parse given document and creates tree of document nodes.
     */
    private void parseDocument() {
        documentNode = new DocumentNode();
        stack.push(documentNode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(
                        docBody.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8));

        try {
            previousForState = null;
            currentState = State.INITAL_STATE;
            int inputData;

            while ((inputData = reader.read()) != -1) {
                symbol = (char) inputData;
                switch (currentState) {

                case INITAL_STATE:
                    initialState();
                    break;
                case OPENING_TAG:
                    openingTag();
                    break;
                case ESCAPED_TEXT:
                    escapedText();
                    break;
                case TEXT_STATE:
                    textState();
                    break;
                case OPENED_TAG:
                    openedTag();
                    break;
                case NEW_COMMAND:
                    newCommand();
                    break;
                case CLOSING_TAG:
                    closingTag();
                    break;
                case FOR_LOOP_VARIABLE:
                    forLoopVariable();
                    break;
                case FOR_LOOP_START_EXPRESSION:
                    forLoopStart();
                    break;
                case FOR_LOOP_START_EXPRESSION_STRING:
                    forLoopStartString();
                    break;
                case ESCAPED_STRING_FOR:
                    escapedStringFor();
                    break;
                case FOR_LOOP_END_EXPRESSION:
                    forLoopEnd();
                    break;
                case FOR_LOOP_END_EXPRESSION_STRING:
                    forLoopEndString();
                    break;
                case FOR_LOOP_STEP_EXPRESSION:
                    forLoopStep();
                    break;
                case FOR_LOOP_STEP_EXPRESSION_STRING:
                    forLoopStepString();
                    break;
                case END_NODE:
                    endNode();
                    break;
                case ECHO_STATE:
                    echoState();
                    break;
                case STRING_ECHO:
                    stringEcho();
                    break;
                case ESCAPED_STRING_ECHO:
                    escapedStringEcho();
                    break;
                case VARIABLE_ECHO:
                    variableEcho();
                    break;
                case FUNCTION_ECHO:
                    functionEcho();
                    break;
                case NUMBER_OPERATOR_ECHO:
                    numberOperatorEcho();
                    break;
                default:
                    break;
                }
            }
            reader.close();
            endParsing();
        } catch (IOException e) {
            throw new SmartScriptParserException("Error while reading a text.");
        }
    }

    /**
     * Ends with parsing current document.
     */
    private void endParsing() {
        // If stuck on reading ordinary text or escaped text save text, or
        // if tag is opened but never closed throw an exception
        if (currentState == State.TEXT_STATE) {
            newTextNode(buffer.toString());
        } else if (currentState == State.ESCAPED_TEXT) {
            buffer.append("\\");
            newTextNode(buffer.toString());
        } else if (currentState != State.INITAL_STATE) {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Tag opened but never closed!");
        }

        // Clear stack
        if (stack.isEmpty()) {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Stack is empty! Please check number of end nodes.");
        } else {
            stack.pop();
        }

        // If there is a more end nodes than for loop nodes in text throw an
        // exception
        if (!stack.isEmpty()) {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Stack is not empty! Please check number of end nodes.");
        }
    }

    /**
     * Process initial state.
     */
    private void initialState() {
        if (symbol == '{') {
            currentState = State.OPENING_TAG;
        } else if (symbol == '\\') {
            currentState = State.ESCAPED_TEXT;
        } else {
            buffer.append(symbol);
            currentState = State.TEXT_STATE;
        }
    }

    /**
     * Process opening tag.
     */
    private void openingTag() {
        if (symbol == '$') {
            currentState = State.OPENED_TAG;
        } else {
            throw new SmartScriptParserException("Invalid opening tag!");
        }
    }

    /**
     * Process escaped text.
     */
    private void escapedText() {
        if (symbol == '\\') {
            buffer.append(symbol);
        } else if (symbol == '{') {
            buffer.append(symbol);
        } else {
            buffer.append('\\');
            buffer.append(symbol);
        }
        currentState = State.TEXT_STATE;
    }

    /**
     * Process text state.
     */
    private void textState() {
        if (symbol == '{') {
            newTextNode(buffer.toString());
            buffer.setLength(0);
            currentState = State.OPENING_TAG;
        } else if (symbol == '\\') {
            currentState = State.ESCAPED_TEXT;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process opened tag.
     */
    private void openedTag() {
        if (isBlank(symbol)) {
            // skip empty spaces
        } else if (Character.isLetter(symbol)) {
            buffer.append(symbol);
            currentState = State.NEW_COMMAND;
        } else if (symbol == '=') {
            buffer.setLength(0);
            currentState = State.ECHO_STATE;
        } else {
            throw new SmartScriptParserException("Unkonown tag name!");
        }
    }

    /**
     * Process new command.
     */
    private void newCommand() {
        if (isBlank(symbol)) {
            if (buffer.toString().toUpperCase().equals("FOR")) {
                buffer.setLength(0);
                currentState = State.FOR_LOOP_VARIABLE;
            } else if (buffer.toString().toUpperCase().equals("END")) {
                buffer.setLength(0);
                currentState = State.END_NODE;
            } else {
                throw new SmartScriptParserException(
                        "Cannot parse the document! Parser only accepts end or for tag as command!");
            }
        } else if (symbol == '$') {
            buffer.setLength(0);

            if (stack.isEmpty()) {
                throw new SmartScriptParserException("Too much ends");
            } else {
                stack.pop();
                currentState = State.CLOSING_TAG;
            }
        } else if (Character.isLetter(symbol)) {
            buffer.append(symbol);
        } else {
            throw new SmartScriptParserException("Command exception!");
        }
    }

    /**
     * Process closing tag.
     */
    private void closingTag() {
        if (symbol == '}') {
            buffer.setLength(0);
            currentState = State.INITAL_STATE;
        } else {
            throw new SmartScriptParserException("Closing tag exception");
        }
    }

    /**
     * Process for loop variable.
     */
    private void forLoopVariable() {
        if ((isBlank(symbol)) && isEmptyOrWhiteSpace(buffer)) {
            // skip empty spaces
        } else if (symbol == '"') {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FOR_LOOP_START_EXPRESSION_STRING;
        } else if ((isBlank(symbol)) && !isEmptyOrWhiteSpace(buffer)) {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            currentState = State.FOR_LOOP_START_EXPRESSION;
        } else if (Character.isLetter(symbol)) {
            buffer.append(symbol);
        } else if ((Character.isDigit(symbol) || symbol == '_')
                && buffer.length() > 0) {
            buffer.append(symbol);
        } else {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Invalid for loop variable!");
        }
    }

    /**
     * Process for loop start expression.
     */
    private void forLoopStart() {
        if (symbol == '"' && buffer.length() == 0) {
            buffer.append(symbol);
            currentState = State.FOR_LOOP_START_EXPRESSION_STRING;
        } else if (symbol == '"' && buffer.length() != 0) {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FOR_LOOP_END_EXPRESSION_STRING;
        } else if (isBlank(symbol) && isEmptyOrWhiteSpace(buffer)) {
            // skip empty spaces
        } else if (isBlank(symbol) && !isEmptyOrWhiteSpace(buffer)) {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            currentState = State.FOR_LOOP_END_EXPRESSION;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process for loop string start expression.
     */
    private void forLoopStartString() {
        if (symbol == '"') {
            buffer.append(symbol);
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            currentState = State.FOR_LOOP_END_EXPRESSION;
        } else if (symbol == '\\') {
            previousForState = State.FOR_LOOP_START_EXPRESSION_STRING;
            currentState = State.ESCAPED_STRING_FOR;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process for loop escaped string.
     */
    private void escapedStringFor() {
        if (symbol == '\\' || symbol == '"') {
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
    }

    /**
     * Process for loop end expression.
     */
    private void forLoopEnd() {
        if (symbol == '"' && buffer.length() == 0) {
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FOR_LOOP_END_EXPRESSION_STRING;
        } else if (symbol == '"' && buffer.length() != 0) {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FOR_LOOP_STEP_EXPRESSION_STRING;
        } else if (isBlank(symbol) && isEmptyOrWhiteSpace(buffer)) {
            // skip empty spaces
        } else if (isBlank(symbol) && !isEmptyOrWhiteSpace(buffer)) {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            currentState = State.FOR_LOOP_STEP_EXPRESSION;
        } else if (symbol == '$') {
            newToken(buffer.toString(), forTokens);
            newForLoopNode(forTokens);
            buffer.setLength(0);
            currentState = State.CLOSING_TAG;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process for loop string end expression.
     */
    private void forLoopEndString() {
        if (symbol == '"') {
            buffer.append(symbol);
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            currentState = State.FOR_LOOP_STEP_EXPRESSION;
        } else if (symbol == '\\') {
            previousForState = State.FOR_LOOP_END_EXPRESSION_STRING;
            currentState = State.ESCAPED_STRING_FOR;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process for loop step expression.
     */
    private void forLoopStep() {
        if (isBlank(symbol) && isEmptyOrWhiteSpace(buffer)) {
            // skip empty spaces
        } else if (isBlank(symbol) && !isEmptyOrWhiteSpace(buffer)) {
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
            newForLoopNode(forTokens);
        } else if (symbol == '"' && buffer.length() == 0) {
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FOR_LOOP_STEP_EXPRESSION_STRING;
        } else if (symbol == '$') {
            if (buffer.length() != 0) {
                newToken(buffer.toString(), forTokens);
                newForLoopNode(forTokens);
                buffer.setLength(0);
            } else if (forTokens.size() == 3) {
                newForLoopNode(forTokens);
            }
            currentState = State.CLOSING_TAG;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process for loop string step expression.
     */
    private void forLoopStepString() {
        if (symbol == '"') {
            buffer.append(symbol);
            newToken(buffer.toString(), forTokens);
            buffer.setLength(0);
        } else if (symbol == '$' && isEmptyOrWhiteSpace(buffer)) {
            newForLoopNode(forTokens);
            currentState = State.CLOSING_TAG;
        } else if (symbol == '\\') {
            previousForState = State.FOR_LOOP_STEP_EXPRESSION_STRING;
            currentState = State.ESCAPED_STRING_FOR;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process end node.
     */
    private void endNode() {
        if (isBlank(symbol)) {
            // skip empty spaces
        } else if (symbol == '$') {
            if (stack.isEmpty()) {
                throw new SmartScriptParserException(
                        "Cannot parse a document! Stack is empty! Please check number of end nodes.");
            } else {
                stack.pop();
                currentState = State.CLOSING_TAG;
            }
        } else {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Failed to get end node!");
        }
    }

    /**
     * Process echo state.
     */
    private void echoState() {
        if (isBlank(symbol)) {
            // Skip empty spaces
        } else if (symbol == '"') {
            buffer.append(symbol);
            currentState = State.STRING_ECHO;
        } else if (symbol == '@') {
            buffer.append(symbol);
            currentState = State.FUNCTION_ECHO;
        } else if (Character.isDigit(symbol)
                || isOperator(Character.toString(symbol))) {
            buffer.append(symbol);
            currentState = State.NUMBER_OPERATOR_ECHO;
        } else if (Character.isLetter(symbol)) {
            buffer.append(symbol);
            currentState = State.VARIABLE_ECHO;
        } else if (symbol == '$') {
            newEchoNode(echoTokens);
            buffer.setLength(0);
            currentState = State.CLOSING_TAG;
        } else {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Invalid echo token!");
        }
    }

    /**
     * Process string echo.
     */
    private void stringEcho() {
        if (symbol == '"') {
            buffer.append(symbol);
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            currentState = State.ECHO_STATE;
        } else if (symbol == '\\') {
            currentState = State.ESCAPED_STRING_ECHO;
        } else {
            buffer.append(symbol);
        }
    }

    /**
     * Process escaped string echo.
     */
    private void escapedStringEcho() {
        if (symbol == '\\' || symbol == '"') {
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
        currentState = State.STRING_ECHO;
    }

    /**
     * Process echo variable.
     */
    private void variableEcho() {
        if (Character.isLetter(symbol) || Character.isDigit(symbol)
                || symbol == '_') {
            buffer.append(symbol);
        } else if (isBlank(symbol)) {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            currentState = State.ECHO_STATE;
        } else if (symbol == '$') {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            newEchoNode(echoTokens);
            currentState = State.CLOSING_TAG;
        } else if (symbol == '@') {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FUNCTION_ECHO;
        } else if (symbol == '"') {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.STRING_ECHO;
        } else {
            throw new SmartScriptParserException(
                    "Cannot parse a document! Invalid variable name! Variable name must start with letter.");
        }
    }

    /**
     * Process echo function.
     */
    private void functionEcho() {
        // Checks if first symbol of function is letter
        if (buffer.length() == 1) {
            if (Character.isLetter(symbol)) {
                buffer.append(symbol);
            } else {
                throw new SmartScriptParserException(
                        "Cannot parse a document! Invalid function name! Function name must start with letter.");
            }
        } else {
            if (Character.isLetter(symbol) || Character.isDigit(symbol)
                    || symbol == '_') {
                buffer.append(symbol);
            } else if (isBlank(symbol)) {
                newToken(buffer.toString(), echoTokens);
                buffer.setLength(0);
                currentState = State.ECHO_STATE;
            } else if (symbol == '$') {
                newToken(buffer.toString(), echoTokens);
                buffer.setLength(0);
                newEchoNode(echoTokens);
                currentState = State.CLOSING_TAG;
            } else if (symbol == '"') {
                newToken(buffer.toString(), echoTokens);
                buffer.setLength(0);
                buffer.append(symbol);
                currentState = State.STRING_ECHO;
            } else if (isOperator(Character.toString(symbol))) {
                newToken(buffer.toString(), echoTokens);
                buffer.setLength(0);
                buffer.append(symbol);
                currentState = State.NUMBER_OPERATOR_ECHO;
            } else {
                throw new SmartScriptParserException(
                        "Cannot parse a document! Function can contain letters, numbers and underscores!");
            }
        }
    }

    /**
     * Process echo number operator.
     */
    private void numberOperatorEcho() {
        if (Character.isDigit(symbol)
                || (symbol == '.' && Character.isDigit(buffer.charAt(buffer
                        .length() - 1)))) {
            buffer.append(symbol);
        } else if (isBlank(symbol)) {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            currentState = State.ECHO_STATE;
        } else if (symbol == '$') {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            newEchoNode(echoTokens);
            currentState = State.CLOSING_TAG;
        } else if (symbol == '"') {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.STRING_ECHO;
        } else if (symbol == '@') {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.FUNCTION_ECHO;
        } else if (Character.isLetter(symbol)) {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.VARIABLE_ECHO;
        } else if (isOperator(Character.toString(symbol))
                || Character.isDigit(symbol)) {
            newToken(buffer.toString(), echoTokens);
            buffer.setLength(0);
            buffer.append(symbol);
            currentState = State.NUMBER_OPERATOR_ECHO;
        } else {
            throw new SmartScriptParserException(
                    "Cannot parse a document! "
                            + "Invalid number format or unsupported math operator in echo node!");
        }
    }

    /**
     * Checks if given StringBuilder is empty or if last character is
     * whitespace.
     * 
     * @param buffer
     *            StringBuilder instance.
     * @return <code>true</code> if StringBuilder is empty or last character is
     *         whitespace, <code>false</code> otherwise.
     */
    private boolean isEmptyOrWhiteSpace(StringBuilder buffer) {
        return buffer.length() == 0
                || buffer.charAt(buffer.length() - 1) == ' ';
    }

    /**
     * Checks if input string is type of math operator (+, -, *, / and %).
     * 
     * @param data
     *            Input string
     * @return <code>true</code> if input string is type of math operator,
     *         <code>false</code> otherwise.
     */
    private boolean isOperator(String data) {
        return (data.equals("+") || data.equals("-") || data.equals("*")
                || data.equals("/") || data.equals("%"));
    }

    /**
     * Checks if input string is type of integer.
     * 
     * @param data
     *            Input string
     * @return <code>true</code> if input string is type of integer,
     *         <code>false</code> otherwise.
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
     * 
     * @param data
     *            Input string
     * @return <code>true</code> if input string is type of double,
     *         <code>false</code> otherwise.
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
     * Creates a new for loop node from given tokens and adds it as a child to
     * current node on stack and also adds it on stack as a parent.
     * 
     * @param forTokens
     *            Array of strings from which method creates a tokens for for
     *            loop node (variable, start expression, end expression and step
     *            expression).
     * @throws SmartScriptParserException
     *             If stack is empty or there is invalid number of tokens
     */
    private void newForLoopNode(ArrayBackedIndexedCollection forTokens) {
        if (stack.isEmpty()) {
            throw new SmartScriptParserException(
                    "Cannot create new ForLoopNode! "
                            + "Stack is empty! Please check number of end tags.");
        } else if (forTokens.size() > 4 || forTokens.size() < 3) {
            throw new SmartScriptParserException(
                    "Cannot create new ForLoopNode! "
                            + "Number of ForLoopNode elements is invalid.");
        } else {
            Token[] token = new Token[4];
            int numberOfTokens = forTokens.size();

            for (int i = 0; i < numberOfTokens; i++) {
                token[i] = (Token) forTokens.get(i);
                if (token[i] instanceof TokenFunction
                        || token[i] instanceof TokenOperator) {
                    throw new SmartScriptParserException(
                            "Cannot create new ForLoopNode! "
                                    + "ForLoopNode elements cannot be function or operator.");
                }
            }

            forTokens.clear();
            ForLoopNode forNode = new ForLoopNode((TokenVariable) token[0],
                    token[1], token[2], token[3]);
            ((Node) stack.peak()).addChildNode(forNode);
            stack.push(forNode);
        }

    }

    /**
     * Creates a new echo node from given tokens and add it as a child to
     * current node on stack.
     * 
     * @param echoTokens
     *            Collection of tokens to add.
     * @throws SmartScriptParserException
     *             If stack is empty.
     */
    private void newEchoNode(ArrayBackedIndexedCollection echoTokens) {
        if (stack.isEmpty()) {
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
     * Creates a new text node and adds it to current node on stack as a child.
     * 
     * @param text
     *            Text to add.
     * @throws SmartScriptParserException
     *             If stack is empty.
     */
    private void newTextNode(String text) {
        if (stack.isEmpty()) {
            throw new SmartScriptParserException(
                    "Cannot create new TextNode! "
                            + "Stack is empty! Please check the number of the end tags.");
        } else {
            ((Node) stack.peak()).addChildNode(new TextNode(text));
        }
    }

    /**
     * Adds a new token to collection.
     * 
     * @param text
     *            Token text.
     * @param collection
     *            Collection.
     */
    private void newToken(String text, ArrayBackedIndexedCollection collection) {
        collection.add(createToken(text));
    }

    /**
     * Creates a new token based on input parameter.
     * 
     * @param data
     *            Value of token.
     * @return New token.
     * @throws SmartScriptParserException
     *             If input parameter does not fit to any of tokens or input
     *             parameter is empty.
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
                return new TokenString(data.substring(1, data.length() - 1));
            } else {
                throw new SmartScriptParserException(
                        "Cannot parse document! Unknown token!");
            }
        } else {
            throw new SmartScriptParserException(
                    "Cannot parse document! Token is empty!");
        }

    }

    /**
     * Checks if current symbol is gap in a text.
     * 
     * @param symbol
     *            Symbol to check
     * @return <code>true</code> if symbol is blank space, new line or tab,
     *         <code>false</code> otherwise.
     */
    private boolean isBlank(char symbol) {
        return (symbol == ' ' || symbol == '\n' || symbol == '\t' || symbol == '\r');
    }

    /**
     * Returns a document node.
     * 
     * @return Document node.
     */
    public DocumentNode getDocumentNode() {
        return documentNode;
    }
}