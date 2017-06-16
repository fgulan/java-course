package hr.fer.zemris.java.custom.collections;
/**
 * Implements a stack collection of objects
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ObjectStack {
	
	private ArrayBackedIndexedCollection stack;
	
	/**
	 * Constructor of ObjectStack collection.
	 */
	public ObjectStack() {
		stack = new ArrayBackedIndexedCollection();
	}
	
	/**
	 * Checks if collection is empty
	 * @return <code>true</code> if collection is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns current number of elements in collection.
	 * @return Number of elements in collection.
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Adds element in collection.
	 * @param value Value to add.
	 * @throws IllegalArgumentException Value cannot be null.
	 */
	public void push(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value cannot be null.");
		}
		
		stack.add(value);
	}
	
	/**
	 * Gets the last element added in collection and removes it.
	 * @return Last element from collection and removes it.
	 * @throws EmptyStackException Collection is empty.
	 */
	public Object pop() {
		if(stack.isEmpty()) {
			throw new EmptyStackException("Collection is empty.");
		}
		
		Object value = peak();
		stack.remove(stack.size()-1);
		
		return value;
	}
	
	/**
	 * Gets the last element from collection without removing it.
	 * @return Last element from collection without removing it.
	 * @throws EmptyStackException Collection is empty.
	 */
	public Object peak() {
		if(stack.isEmpty()) {
			throw new EmptyStackException("Collection is empty.");
		}
		
		return stack.get(stack.size()-1);
	}
	
	/**
	 * Removes all elements from collection.
	 */
	public void clear() {
		stack.clear();
	}
}
