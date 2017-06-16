package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface which uses visitor design pattern to process parsed document tree.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface INodeVisitor {

    /**
     * Process given {@link TextNode} instance using visitor design pattern.
     * 
     * @param node
     *            {@link TextNode} instance.
     */
    public void visitTextNode(TextNode node);

    /**
     * Process given {@link ForLoopNode} instance using visitor design pattern.
     * 
     * @param node
     *            {@link ForLoopNode} instance.
     */
    public void visitForLoopNode(ForLoopNode node);

    /**
     * Process given {@link EchoNode} instance using visitor design pattern.
     * 
     * @param node
     *            {@link EchoNode} instance.
     */
    public void visitEchoNode(EchoNode node);

    /**
     * Process given {@link DocumentNode} instance using visitor design pattern.
     * 
     * @param node
     *            {@link DocumentNode} instance.
     */
    public void visitDocumentNode(DocumentNode node);
}
