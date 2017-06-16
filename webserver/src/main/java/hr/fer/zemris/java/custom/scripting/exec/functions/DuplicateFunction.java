package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * DuplicateFunction class implements {@link ITokenFunction}. Duplicates last
 * number on given stack.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DuplicateFunction implements ITokenFunction {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        ValueWrapper x = new ValueWrapper(stack.pop());
        stack.push(x.getValue());
        stack.push(new ValueWrapper(x.getValue()).getValue());
    }
}
