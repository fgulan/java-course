package hr.fer.zemris.java.custom.collections;

/**
 * Program for testing ArrayBackedIndexedCollection.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Test {
	
	/**
	 * Start point of the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection(2);
		
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		
		System.out.println(col.contains("New York")); // writes: true
		
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		
		col.insert("Tokyo", 1);
		System.out.println(col.get(1));
		
		col.remove(1);
		System.out.println(col.get(1)); // writes: "San Francisco"
		
		col.clear();
		System.out.println(col.size());
	}

}
