package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * TParamDelFunction class implements {@link ITokenFunction}. Removes temporary
 * parameter with given name from request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TParamDelFunction implements ITokenFunction {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        String name = (String) stack.pop();
        request.removeTemporaryParameter(name);
    }
}
