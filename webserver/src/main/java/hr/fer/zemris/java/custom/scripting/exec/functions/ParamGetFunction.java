package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * ParamGetFunction class implements {@link ITokenFunction}. Gets parameter with
 * given name from request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ParamGetFunction implements ITokenFunction {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        Object defValue = stack.pop();
        String name = (String) stack.pop();
        Object newValue = request.getParameter(name);
        stack.push(newValue != null ? newValue : defValue);
    }
}
