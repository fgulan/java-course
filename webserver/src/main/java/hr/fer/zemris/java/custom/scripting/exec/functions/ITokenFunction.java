package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Interface used for executing function over current token.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface ITokenFunction {

    /**
     * Execute current function.
     * 
     * @param stack
     *            Stack.
     * @param request
     *            Request context.
     */
    public void execute(Stack<Object> stack, RequestContext request);
}
