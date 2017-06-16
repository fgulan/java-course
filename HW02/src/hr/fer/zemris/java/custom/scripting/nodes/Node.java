package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Implementation of base class for all nodes in document body.
 * @author Filip Gulan
 * @Version 1.0
 */
public class Node {
	
	/** 
	 * Storage for child nodes.
	 */
	ArrayBackedIndexedCollection data;

	/**
	 * Adds a new node as child to current node.
	 * @param child Child to add.
	 */
	public void addChildNode(Node child) {
		if (data == null) {
			data = new ArrayBackedIndexedCollection();
		}

		data.add(child);
	}

	/**
	 * Returns a current number of children of current parent.
	 * @return Current number of children of this node.
	 */
	public int numberOfChildern() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	/**
	 * Returns a child node at selected index.
	 * @param index Index of child.
	 * @return Child node at selected index.
	 * @throws IndexOutOfBoundsException If index is not in range between 0 and size-1.
	 */
	public Node getChild(int index) {
		if (index < 0 || index >= data.size()) {
			throw new IndexOutOfBoundsException("Index must be in range from 0 to size-1!");
		}

		return (Node) data.get(index);
	}

	/**
	 * Returns textual representation of node.
	 * @return Textual representation of node.
	 */
	public String asText() {
		return "";
	}
}
