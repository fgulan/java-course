package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * SinFunction class implements {@link ITokenFunction}. Computes sin(x) over
 * given number.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SinFunction implements ITokenFunction {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        ValueWrapper x = new ValueWrapper(stack.pop());
        double angleR = Math.toRadians(Double.parseDouble(x.getValue()
                .toString()));
        double result = Math.sin(angleR);
        stack.push(result);
    }
}
