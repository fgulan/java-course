package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The SimpleHashtable class implements simple hash table collection. It has two
 * public constructors, one without argument which creates table size of 16 slots,
 * and another one with input number which creates table with size which is the 
 * nearest power of 2 to a given number.
 *  
 * <p>To keep a good performance, table can be dynamically reallocated and doubled 
 * its size. Only in that case, the complexity of method put() is bigger than O(1).
 * 
 * <p>If slot is consisted of an element and a new element get the same index as old,
 * then elements from same slot are connected in singly linked list.
 * 
 * <p>Also, SimpleHashtable implements Iterable interface so it can be used in
 * for each loops or it can create an iterator to iterate through the table.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {
	
	/** Array of slots of hash table */
	private TableEntry[] table;
	/** Current number of elements in table */
	private int size;
	/** Count of modifications since creation */
	private int modificationCount;
	/** Default number of slots */
	private static final int DEFAULT_SIZE = 16;
	/** Threshold for rehash */
	private static final double THRESHOLD =  0.75;
	
	/**
	 * Constructor for SimpleHashtable class. Creates new hash table 
	 * with <code>DEFAULT_SIZE = 16</code> number of slots.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * Constructor for SimpleHashtable class. Creates new hash table
	 * with number of slots equals to number which is power of 2 and 
	 * equals or greater than specified size.
	 * @param size Number of slots.
	 * @throws IllegalArgumentException If given size is less than 1.
	 */
	public SimpleHashtable(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Given size is less than 1!");
		}
		int s = largestPowerOf2(size);
		table = new TableEntry[s];
	}
	
	/**
	 * Returns first number which is power of 2 and which is equal
	 * or greater than input number.
	 * @param arg Input number
	 * @return Number which is power of 2 and which is equal
	 * or greater than <code>number</code>.
	 */
	private int largestPowerOf2(int arg) {
		int number = arg;
		//in case that number is already power of 2
		number--;
		
		number |= number >> 1;
		number |= number >> 2;
		number |= number >> 4;
		number |= number >> 8;
		number |= number >> 16;
		
		//greater or equal than input number
		number++;
		return number;
	}

	/**
	 * Puts a new element with specified key and value in hash table.
	 * If hash table already contains specified key, method only updates
	 * a value of an old element. Complexity of this method is O(1), but in 
	 * case that by putting a new element table would reach an threshold, new 
	 * table is created with doubled size.
	 * @param key Key of an element.
	 * @param value Value of an element.
	 * @throws IllegalArgumentException If given key is null.
	 */
	public void put(Object key, Object value) {
		if(key == null) {
			throw new IllegalArgumentException("You cannot add null value to collection!");
		}
		
		int index = getSlotIndex(key, table.length);
	
		//if current slot is empty		
		if(table[index] == null && !containsKey(key)) {
			table[index] = new TableEntry(key, value, null);
			size++;
		} else {
			TableEntry temp = table[index];
			//go to end of current slot list
			for(; temp.next != null; temp = temp.next) {
				if(temp.key.equals(key)) {
					temp.setValue(value);
					modificationCount++;
					return;
				}
			}
			//checks if last element of current slot list is equal to input key 
			if(temp.key.equals(key)) {
				temp.setValue(value);
				modificationCount++;
				return;
			}
			//if current table does not contains given key and value, add it to the end of current slot list.
			temp.next = new TableEntry(key, value, null);
			size++;
			modificationCount++;
		}
		//if count of elements is greater than 75 percent of table size double its size
		if(size >= THRESHOLD*table.length) {
			rehash();
			modificationCount++;
		}
	}
	
	/**
	 * Returns value of an element with given key. If hash table does
	 * not contains given key, it returns <code>null</code>.
	 * @param key Key of an element.
	 * @return If key exists in table, value of an element with given key, 
	 * <code>null</code> otherwise.
	 */
	public Object get(Object key) {
		if (key  != null) {
			int index = getSlotIndex(key, table.length);
			TableEntry temp = table[index];
			
			for(;temp != null; temp = temp.next) {
				if(temp.key.equals(key)) {
					return temp.getValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes element with specified key from hash table.
	 * If hash table does not contains element with given
	 * key, method does nothing.
	 * @param key Key of an element to remove.
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		
		int index = getSlotIndex(key, table.length);
		TableEntry temp = table[index];
		
		if(temp == null) {
			return;
		}
		
		if(temp.key.equals(key)) {
			table[index] = temp.next;
			size--;
			modificationCount++;
		} else {
			for(;temp.next != null; temp = temp.next) {
				if(temp.next.key.equals(key)) {
					temp.next = temp.next.next;
					size--;
					modificationCount++;
					break;
				}
			}
		}
		
	}
	
	/**
	 * Returns current number of elements in hash table.
	 * @return Current number of elements in hash table.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if hash table contains element with given key. Returns 
	 * <code>true</code> if contains, <code>false</code> otherwise.
	 * Complexity of this method is O(1).
	 * @param key Key of an element.
	 * @return <code>true</code> if hash table contains element with
	 * given key, <code>false</code> otherwise.
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		int index = getSlotIndex(key, table.length);

		TableEntry temp = table[index];
		for(;temp != null; temp = temp.next) {
			if(temp.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if hash table is empty.
	 * @return <code>true</code> if hash table does not contain
	 * any element, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Checks if hash table contains element with given value. Returns 
	 * <code>true</code> if contains, <code>false</code> otherwise.
	 * Complexity of this method is O(n).
	 * @param value Value of an element.
	 * @return <code>true</code> if hash table contains element with
	 * given value, <code>false</code> otherwise.
	 */
	public boolean containsValue(Object value) {
		if(value == null) {
			return containsNullValue();
		}
		
		Iterator<SimpleHashtable.TableEntry> iterator = iterator();
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if there is an element in hash table with null value.
	 * @return <code>true</code> if hash table contains element with
	 * <code>null</code> value, <code>false</code> otherwise.
	 */
	private boolean containsNullValue() {
		Iterator<SimpleHashtable.TableEntry> iterator = iterator();
		
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getValue() == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Computes an index in table for given key and number of slots in table.
	 * @param key Key of an element.
	 * @param length Number of slots in table,
	 * @return Computed index of a slot for given key.
	 */
	private int getSlotIndex(Object key, int length) {
		int hash = key.hashCode();
		return hash < 0 ? (-1*hash) % length : hash % length;
	}
	
	/**
	 * This method is called when size threshold is reached. It creates a new hash
	 * table with doubled size and fill new table with elements from an old table.
	 * Old table reference is replace with new table.
	 */
	private void rehash() {
		TableEntry[] newTable = new TableEntry[table.length*2];
		Iterator<SimpleHashtable.TableEntry> iterator = iterator();
		
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			fillNewTable(pair, newTable);
		}
		this.table = newTable;
	}
	
	/**
	 * Method used in rehash method for copying elements from old table to new table. 
	 * @param in Element to copy from an old table.
	 * @param table Reference to a new table.
	 */
	private void fillNewTable(TableEntry in, TableEntry[] table) {
		int index = getSlotIndex(in.getKey(), table.length);
		
		if(table[index] == null) {
			table[index] = new TableEntry(in.getKey(), in.getValue(), null);
		} else {
			TableEntry temp = table[index];
			while(temp.next != null) {
				temp = temp.next;
			}
			temp.next = new TableEntry(in.getKey(), in.getValue(), null);
		}
	}
	
	/**
	 * Removes all elements from hash table.
	 */
	public void clear() {
		this.size = 0;
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
	}
	
	/**
	 * Returns string representation of all elements from
	 * hash table in format "[key0=value0, ..., keyN=valueN]"
	 * @return String representation of all elements from hash table.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Iterator<SimpleHashtable.TableEntry> iterator = iterator();
		
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			sb.append(pair.toString() + ", ");
		}
		
		if(sb.length() == 1) {
			return sb.append("]").toString();
		}
		return sb.substring(0, sb.length()-2) + "]";
	}

	/**
	 * This class is representation of an element in hash table.
	 * It has three properties: key, value and next as a reference to next
	 * hash table element in same slot.
	 * 
	 * <p>Implements getters for key and value, and setter for value.
	 * Overrides toString() method which returns key and value in
	 * format "key=value".
	 * @author Filip Gulan
	 * @version 1.0
	 *
	 */
	 public static class TableEntry {
		
		/** Key of an element. */
		private Object key;
		/** Value of an element. */
		private Object value;
		/** Reference to next element. */
		private TableEntry next;
		
		/**
		 * Constructor for TableEntry class. Creates a new hash table
		 * element with given key and value.
		 * @param key Key of an element.
		 * @param value Value of an element.
		 * @param next Reference to the next element.
		 */
		public TableEntry(Object key, Object value, TableEntry next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns value of current hash table element.
		 * @return Value of current hash table element.
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Sets current element value to a new specified value.
		 * @param value New value to set.
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Returns key of current hash table element.
		 * @return Key of current hash table element.
		 */
		public Object getKey() {
			return key;
		}
		
		/**
		 * Returns string representation of current element
		 * in format of "key=value".
		 * @return String representation of a pair of key and value.
		 */
		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
		
	 }
	
	/**
	 * Returns an iterator over elements of type TableEntry.
	 * @return An iterator.
	 */
	@Override
	public Iterator<TableEntry> iterator() {
		return new HashTableIterator(table, size);
	}
	
	/**
	 * This class implements Iterator interface for SimpleHashtable.
	 * It has several properties: a reference to table to iterate through 
	 * and size as a number of elements in table.
	 * 
	 * <p>It overrides methods: hasNext(), next() and remove() which deletes
	 * current element in iteration step.
	 * @author Filip Gulan.
	 * @version 1.0
	 *
	 */
	private class HashTableIterator implements Iterator<SimpleHashtable.TableEntry> {
		
		/** Current slot in given table */
		private int currentIndex;
		/** Number of returned elements */
		private int count;
		/** Modification counter */
		private int expModificationCount;
		/** Number of elements in given table */
		private int size;
		/** Reference to a table */
		private TableEntry[] table;
		/** Current table element */
		private TableEntry current;
		/** Last returned element*/
		private TableEntry lastReturned;
		
		/**
		 * Constructor for HashTableIterator. It only copies reference to given hash table.
		 * @param table Specified hash table.
		 * @param size Number of elements in specified table.
		 */
		public HashTableIterator(TableEntry[] table, int size) {
			this.table = table;
			this.size = size;
			this.expModificationCount = modificationCount;
		}
		
		/**
		 * Checks if current iterator has remaining elements to iterate through.
		 * @return <code>true</code> if the iteration has more elements, <code>false</code> otherwise.
		 * @throws ConcurrentModificationException If collection is updated while iterating through.
		 */
		@Override
		public boolean hasNext() {
			if(expModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection is updated while iterating through!");
			}
			return count < size;
		}

		/**
		 * Returns the next element in the iteration.
		 * @return The next element in the iteration.
		 * @throws ConcurrentModificationException If collection is updated while iterating through.
		 * @throws NoSuchElementException If the iteration has no more elements.
		 */
		@Override
		public TableEntry next() {
			if(expModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection is updated while iterating through!");
			}
			for(; currentIndex < table.length; currentIndex++) {
				if(table[currentIndex] != null) {
					if(current == null) {
						current = lastReturned = table[currentIndex];
						current = current.next;
					} else {
						lastReturned = current;
						current = current.next;
					}
					if(current == null) {
						currentIndex++;
					}
					count++;
					return lastReturned;
				}
			}
			throw new NoSuchElementException("There is no more elements in collection!");
		}
		
		/**
		 * Removes from the underlying collection the last element returned by this iterator 
		 * next() method. This method can be called only once per call to next().
		 * @throws ConcurrentModificationException If collection is updated while iterating through.
		 * @throws IllegalStateException If the next method has not yet been called, or the 
		 * remove method has already been called after the last call to the next method 
		 */
		@Override
		public void remove() {
			if(expModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection is updated while iterating through!");
			}
			if(lastReturned == null) {
				throw new IllegalStateException("Remove method has already been called or the next method has not yet been called!");
			}
			
			SimpleHashtable.this.remove(lastReturned.key);
			
			expModificationCount++;
			lastReturned = null;
		}
	}
}
