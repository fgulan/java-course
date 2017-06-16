package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class extends Node class without overriding any of methods from base
 * class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class DocumentNode extends Node {

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitDocumentNode(this);
    }
}
