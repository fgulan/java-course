package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * SwapFunction class implements {@link ITokenFunction}. Swaps two last elements
 * on stack.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SwapFunction implements ITokenFunction {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        ValueWrapper first = new ValueWrapper(stack.pop());
        ValueWrapper second = new ValueWrapper(stack.pop());
        stack.push(first.getValue());
        stack.push(second.getValue());
    }
}
