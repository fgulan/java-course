package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * SmartScriptEngine class provides implementation for executing SmartScript
 * files.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SmartScriptEngine {

    /** Document node. */
    private DocumentNode documentNode;
    /** Node visitor. */
    private INodeVisitor visitor;

    /**
     * Constructor for SmartScriptEngine.
     * 
     * @param documentNode
     *            Document node.
     * @param requestContext
     *            Request context.
     */
    public SmartScriptEngine(DocumentNode documentNode,
            RequestContext requestContext) {
        this.documentNode = documentNode;
        this.visitor = new NodeVisitor(requestContext);
    }

    /**
     * Executes current SmartScript.
     */
    public void execute() {
        documentNode.accept(visitor);
    }

}
