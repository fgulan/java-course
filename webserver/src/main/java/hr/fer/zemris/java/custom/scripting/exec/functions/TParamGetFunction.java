package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * TParamGetFunction class implements {@link ITokenFunction}. Gets temporary
 * parameter with given name from request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TParamGetFunction implements ITokenFunction {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        Object defValue = stack.pop();
        String name = (String) stack.pop();
        Object newValue = request.getTemporaryParameter(name);
        stack.push(newValue != null ? newValue : defValue);
    }
}
