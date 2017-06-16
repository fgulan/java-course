package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Collection of acceptable states.
 * 
 * @author Filip Gulan
 *
 */
public enum State {

    /** Initial state. */
    INITAL_STATE,
    /** Opening tag. */
    OPENING_TAG,
    /** Opened tag. */
    OPENED_TAG,
    /** New command. */
    NEW_COMMAND,
    /** For loop variable. */
    FOR_LOOP_VARIABLE,
    /** For loop start expression. */
    FOR_LOOP_START_EXPRESSION,
    /** For loop end expression. */
    FOR_LOOP_END_EXPRESSION,
    /** For loop step expression. */
    FOR_LOOP_STEP_EXPRESSION,
    /** For loop start expression string. */
    FOR_LOOP_START_EXPRESSION_STRING,
    /** For loop end expression string. */
    FOR_LOOP_END_EXPRESSION_STRING,
    /** For loop step expression string. */
    FOR_LOOP_STEP_EXPRESSION_STRING,
    /** For loop escaped string. */
    ESCAPED_STRING_FOR,
    /** Escaped text. */
    ESCAPED_TEXT,
    /** Text state. */
    TEXT_STATE,
    /** End node. */
    END_NODE,
    /** Closing tag. */
    CLOSING_TAG,
    /** Echo state. */
    ECHO_STATE,
    /** Echo string. */
    STRING_ECHO,
    /** Function echo. */
    FUNCTION_ECHO,
    /** Echo operator. */
    NUMBER_OPERATOR_ECHO,
    /** Echo variable. */
    VARIABLE_ECHO,
    /** Echo escaped string. */
    ESCAPED_STRING_ECHO
}
