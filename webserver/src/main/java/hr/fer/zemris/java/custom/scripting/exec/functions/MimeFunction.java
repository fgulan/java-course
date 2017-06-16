package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * MimeFunction class implements {@link ITokenFunction}. Sets mime type for
 * given request context.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class MimeFunction implements ITokenFunction {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        String mimeType = (String) stack.pop();
        request.setMimeType(mimeType);
    }
}
