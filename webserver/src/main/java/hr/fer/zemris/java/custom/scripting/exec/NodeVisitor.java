package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.collections.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.functions.DecfmtFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.DuplicateFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.ITokenFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.MimeFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.PParamDelFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.PParamGetFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.PParamSetFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.ParamGetFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.SinFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.SwapFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.TParamDelFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.TParamGetFunction;
import hr.fer.zemris.java.custom.scripting.exec.functions.TParamSetFunction;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * NodeVisitor implements {@link INodeVisitor} interface for processing parsed
 * document tree nodes using visitor pattern.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class NodeVisitor implements INodeVisitor {

    /** Request context. */
    private RequestContext requestContext;
    /** Multistack instance. */
    private ObjectMultistack multistack;
    /** Functions map. */
    private Map<String, ITokenFunction> functions;

    /**
     * Constructor for NodeVisitor class.
     * 
     * @param requestContext
     *            Request context.
     */
    public NodeVisitor(RequestContext requestContext) {
        super();
        this.requestContext = requestContext;
        this.multistack = new ObjectMultistack();
        initFunctions();
    }

    /**
     * Initializes supported functions.
     */
    private void initFunctions() {
        functions = new HashMap<String, ITokenFunction>();
        functions.put("sin", new SinFunction());
        functions.put("swap", new SwapFunction());
        functions.put("decfmt", new DecfmtFunction());
        functions.put("dup", new DuplicateFunction());
        functions.put("setMimeType", new MimeFunction());
        functions.put("paramGet", new ParamGetFunction());
        functions.put("pparamGet", new PParamGetFunction());
        functions.put("pparamSet", new PParamSetFunction());
        functions.put("pparamDel", new PParamDelFunction());
        functions.put("tparamGet", new TParamGetFunction());
        functions.put("tparamSet", new TParamSetFunction());
        functions.put("tparamDel", new TParamDelFunction());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitTextNode(TextNode node) {
        try {
            requestContext.write(node.getText());
        } catch (IOException e) {
            System.out.println("SERVER Error: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitForLoopNode(ForLoopNode node) {
        ValueWrapper endValue = new ValueWrapper(node.getEndExpression()
                .asText());
        ValueWrapper startValue = new ValueWrapper(node.getStartExpression()
                .asText());
        ValueWrapper currentValue = null;
        multistack.push(node.getVariable().getName(), startValue);

        Token stepToken = node.getStepExpression();
        ValueWrapper stepValue;
        if (stepToken == null) {
            stepValue = new ValueWrapper(1);
        } else {
            stepValue = new ValueWrapper(stepToken.asText());
        }

        do {
            int size = node.numberOfChildern();
            for (int i = 0; i < size; i++) {
                node.getChild(i).accept(this);
            }

            currentValue = multistack.pop(node.getVariable().getName());
            currentValue.increment(stepValue.getValue());
            multistack.push(node.getVariable().getName(), currentValue);

        } while (currentValue.numCompare(endValue.getValue()) != 1);

        multistack.pop(node.getVariable().getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEchoNode(EchoNode node) {
        Token[] tokens = node.getTokens();
        Stack<Object> stack = new Stack<Object>();

        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            if (token instanceof TokenConstantInteger) {
                stack.push(((TokenConstantInteger) token).getValue());
            } else if (token instanceof TokenConstantDouble) {
                stack.push(((TokenConstantDouble) token).getValue());
            } else if (token instanceof TokenString) {
                stack.push(((TokenString) token).getValue());
            } else if (token instanceof TokenVariable) {
                String varName = ((TokenVariable) token).getName();
                stack.push(multistack.peek(varName).getValue());
            } else if (token instanceof TokenFunction) {
                String function = ((TokenFunction) token).getValue();
                functions.get(function).execute(stack, requestContext);
            } else if (token instanceof TokenOperator) {
                String operator = ((TokenOperator) token).getSymbol();
                ValueWrapper firstArg = new ValueWrapper(stack.pop());
                ValueWrapper secondArg = new ValueWrapper(stack.pop());
                processOperator(firstArg, secondArg, operator);
                stack.push(firstArg.getValue());
            }
        }

        // Print elements left on temporary stack
        for (Object object : stack) {
            try {
                requestContext.write(object.toString());
            } catch (IOException e) {
                System.out.println("SERVER Error: " + e.getMessage());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitDocumentNode(DocumentNode node) {
        int size = node.numberOfChildern();
        for (int i = 0; i < size; i++) {
            node.getChild(i).accept(this);
        }
    }

    /**
     * Compute result for given operation and parameters.
     * 
     * @param firstArg
     *            First argument.
     * @param secondArg
     *            Second argument.
     * @param operator
     *            Operator.
     */
    private void processOperator(ValueWrapper firstArg, ValueWrapper secondArg,
            String operator) {
        switch (operator) {
        case "/":
            firstArg.divide(secondArg.getValue());
            break;
        case "*":
            firstArg.multiply(secondArg.getValue());
            break;
        case "+":
            firstArg.increment(secondArg.getValue());
            break;
        case "-":
            firstArg.decrement(secondArg.getValue());
            break;
        default:
            break;
        }
    }

}
