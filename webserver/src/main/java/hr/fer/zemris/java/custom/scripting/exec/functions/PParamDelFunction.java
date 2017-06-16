package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * PParamDelFunction class implements {@link ITokenFunction}. Removes persistent
 * parameter with given name from request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class PParamDelFunction implements ITokenFunction {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        String name = (String) stack.pop();
        request.removePersistentParameter(name);
    }
}
