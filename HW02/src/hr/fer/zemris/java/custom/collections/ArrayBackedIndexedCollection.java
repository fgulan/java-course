package hr.fer.zemris.java.custom.collections;

/**
 * Implements array-based indexed collection of objects.
 * 
 * @author Filip Gulan.
 * @version 1.0
 */
public class ArrayBackedIndexedCollection {

	private static final int DEFAULT_CAPACITY = 16;
	private int size;
	private int capacity;
	private Object[] elements;

	/**
	 * Creates a new collection with capacity set to <code>initialCapacity</code>
	 * @param initialCapacity Initial capacity of collection.
	 * @throws IllegalArgumentException Capacity of collection must be greater than 0.
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity of collection must be greater than 0.");
		}
		
		this.elements = new Object[initialCapacity];
		this.capacity = initialCapacity;
	}
	
	/**
	 * Creates a new collection with capacity set to <code>DEFAULT_CAPACITY</code> = 16.
	 */
	public ArrayBackedIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Checks if collection is empty.
	 * @return true if collection is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
	
	/**
	 * Returns a current number of elements in collection.
	 * @return Number of elements in collection.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds <code>value</code> into first empty place in collection.
	 * @param value Value to add.
	 * @throws IllegalArgumentException Value cannot be null.
	 */
	public void add(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value cannot be null.");
		}
		
		if(size == capacity) {
			reallocDouble();
		}
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Reallocate current collection and doubles its capacity.
	 */
	private void reallocDouble() {
		int newCapacity = capacity*2;
		Object[] newElements = new Object[newCapacity];
		
		for (int i = 0; i < capacity; i++) {
			newElements[i] = elements[i];			
		}
		
		elements = newElements;
		capacity = newCapacity;
	}
	
	/**
	 * Returns the element at a specified index in collection.
	 * @param index Index of element to retrieve.
	 * @return Element at the specified position in collection.
	 * @throws IndexOutOfBoundsException The index must be greater than or equal to the 0 or less than to the number of elements in collection.
	 */
	public Object get(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The index must be greater than or equal to the 0 or less than to the number of elements in collection.");
		}
		
		return elements[index];
	}
	
	/**
	 * Deletes the element at a specified index in collection
	 * @param index Index of element to delete.
	 * @throws IndexOutOfBoundsException The index must be greater than or equal to the 0 or less than to the number of elements in collection.
	 */
	public void remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("The index must be greater than or equal to the 0 or less than to the number of elements in collection.");
		}
		
		for(int i = index; i < size - 1; i++) {
			elements[i] = elements[i+1];
		}
		
		elements[--size] = null;
	}
	
	/**
	 * Inserts an element into the collection at the specified index.
	 * @param value Value to add.
	 * @param position Index position for the insertion.
	 * @throws IndexOutOfBoundsException The position must be greater than or equal to the 0 or less than or equal to the number of elements in collection.
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("The position must be greater than or equal to the 0 or less than or equal to the number of elements in collection.");
		}
		
		if(size == capacity) {
			this.reallocDouble();
		}
		
		if(elements[position] == null) {
			elements[position] = value;
		} else {
			for(int i = size; i > position; i--) {
				elements[i] = elements[i-1];
			}
			elements[position] = value;
		}
		size++;
	}
	/**
	 * Returns index of an element with value <code>value</code>.
	 * @param value Value to search for.
	 * @return Index of an element.
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns true if collection contains element with value <code>value</code>.
	 * @param value Value to search for.
	 * @return true if collection contains element, false, otherwise.
	 */
	public boolean contains(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Removes all elements from collection.
	 */
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
		size = 0;
	}
}
