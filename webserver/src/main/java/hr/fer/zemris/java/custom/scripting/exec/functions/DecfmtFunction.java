package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Stack;

/**
 * DecfmtFunction class implements {@link ITokenFunction}. Formats number using
 * {@link DecimalFormat}.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DecfmtFunction implements ITokenFunction {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Stack<Object> stack, RequestContext request) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat((String) stack.pop(), symbols);
        ValueWrapper x = new ValueWrapper(stack.pop());
        String r = format.format(x.getValue());
        stack.push(r);
    }
}
