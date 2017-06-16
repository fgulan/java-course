package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class extends Node class. It represent text node in text. Overrides
 * asText() method from parent class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TextNode extends Node {

    /** Text. */
    String text;

    /**
     * Constructor of TextNode class.
     * 
     * @param text
     *            Text of TextNode object.
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Returns text representation of text node.
     * 
     * @return Text representation of text node.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns formatted textual representation of text node where character '{'
     * is replaced with "\{".
     * 
     * @return Formatted textual representation of text node.
     */
    @Override
    public String asText() {
        return text.replace("\\", "\\\\").replace("{", "\\{");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
    }
}
