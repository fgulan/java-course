package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * PParamSetFunction class implements {@link ITokenFunction}. Adds a new
 * persistent parameter to a request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class PParamSetFunction implements ITokenFunction {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        String name = (String) stack.pop();
        Object value = stack.pop();

        request.setPersistentParameter(name, value.toString());
    }
}
