package hr.fer.zemris.java.tecaj.hw4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleHashtableTest {

	SimpleHashtable hashtable;
	
	void fillHashtable() {
		hashtable = new SimpleHashtable(3);
		hashtable.put("Mercedes", "Njemacka");
		hashtable.put("Fiat", "Italija");
		hashtable.put("BMW", "Njemacka");
		hashtable.put("Apple", "SAD");
		hashtable.put("Matematika", 4);
		hashtable.put("Fizika", 3);
		hashtable.put("OE", 2);
		hashtable.put("Java", 1);
		hashtable.put("Ante", 3);
		hashtable.put("Jasna", 4);
		hashtable.put("Kristina", 5);
		hashtable.put("Ivana", 3);
	}
	
	void fillHashtable_1() {
		hashtable = new SimpleHashtable(3);
		hashtable.put("Mercedes", "Njemacka");
		hashtable.put("Fiat", "Italija");
		hashtable.put("BMW", "Njemacka");
		hashtable.put("Apple", "SAD");
	}
	
	@Test
	public void constructors_Test() {
		hashtable = new SimpleHashtable();
		hashtable = new SimpleHashtable(17);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionIfSize0() {
		hashtable = new SimpleHashtable(0);
	}
	
	@Test
	public void getMethodTest() {
		fillHashtable();
		
		assertEquals("SAD", hashtable.get("Apple"));
		assertEquals("Njemacka", hashtable.get("Mercedes"));
		assertEquals("Njemacka", hashtable.get("BMW"));
		assertEquals("Italija", hashtable.get("Fiat"));
	}
	
	@Test
	public void getMethodTest_2() {
		fillHashtable();
		
		assertEquals(4, hashtable.get("Matematika"));
		assertEquals(3, hashtable.get("Fizika"));
		assertEquals(2, hashtable.get("OE"));
		assertEquals(1, hashtable.get("Java"));
	}

	@Test
	public void getMethodTestWithNull() {
		fillHashtable();
		
		assertEquals(null, hashtable.get("ante"));
		assertEquals(null, hashtable.get(null));
	}
	
	@Test
	public void getAndPutMethodTest() {
		fillHashtable();
		hashtable.put("Kristina", 2);
		
		assertEquals(3, hashtable.get("Ivana"));
		assertEquals(2, hashtable.get("Kristina"));
		assertEquals(4, hashtable.get("Jasna"));
		assertEquals(3, hashtable.get("Ante"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionIfPuttingNull() {
		hashtable = new SimpleHashtable(1);
		hashtable.put(null, 1);
	}
	
	@Test
	public void removeMethodTest() {
		fillHashtable();
		
		hashtable.remove("Kristina");
		hashtable.remove("Kristina");
		hashtable.remove(null);
		hashtable.remove("Ante");
		hashtable.remove("Antes");
		
		assertEquals(false, hashtable.containsKey("Kristina"));
	}
	
	@Test
	public void sizeMethodTest() {
		fillHashtable();
		assertEquals(12, hashtable.size());
	}
	
	@Test
	public void sizeAndClearMethodTest() {
		fillHashtable();
		hashtable.clear();
		assertEquals(0, hashtable.size());
	}
	
	@Test
	public void isEmptyMethodTest() {
		fillHashtable();
		
		assertEquals(false, hashtable.isEmpty());
		hashtable.clear();
		assertEquals(true, hashtable.isEmpty());
	}
	
	@Test
	public void containsKeyMethodTest() {
		fillHashtable();

		assertEquals(true, hashtable.containsKey("Fiat"));
		assertEquals(false, hashtable.containsKey("Audi"));
		assertEquals(false,  hashtable.containsKey(null));
	}
	
	@Test
	public void containsValueMethodTest() {
		fillHashtable();
		
		assertEquals(true, hashtable.containsValue("Njemacka"));
		assertEquals(false, hashtable.containsValue("Francuska"));
		assertEquals(false,  hashtable.containsValue(null));
	}
	
	@Test
	public void containsNullValueTest() {
		fillHashtable();
		hashtable.put("BMW", null);
		assertEquals(true,  hashtable.containsValue(null));
	}
	
	@Test
	public void toStringMethodTest() {
		hashtable = new SimpleHashtable(8);
		hashtable.put("BMW", "Njemacka");
		hashtable.put("Apple", "SAD");
		
		assertEquals("[Apple=SAD, BMW=Njemacka]", hashtable.toString());
		hashtable.clear();
		assertEquals("[]", hashtable.toString());
	}
	
	@Test
	public void iteratorTest() {
		fillHashtable();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getKey().equals("OE")) {
				iterator.remove();
			}
		}
		
		assertEquals(false, hashtable.containsKey("OE"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void iteratorThrowExceptionIfRemovingInARow() {
		fillHashtable();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getKey().equals("BMW")) {
				iterator.remove();
				iterator.remove();
			}
		}	
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorThrowExceptionIfPuttingElement() {
		fillHashtable();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getKey().equals("BMW")) {
				hashtable.put("Toyota","Japan");
			}
		}	
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorThrowExceptionIfPuttingAndRemovingElement() {
		fillHashtable();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getValue().equals("Njemacka")) {
				hashtable.put("Toyota","Japan");
				iterator.remove();
			}
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorThrowExceptionIfRemovingWithHashtableRemove() {
		fillHashtable();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			SimpleHashtable.TableEntry pair = iterator.next();
			if(pair.getKey().equals("BMW")) {
				hashtable.remove("BMW");
			}
		}	
	}
	
	@Test(expected=NoSuchElementException.class)
	public void iteratorThrowExceptionIfThereIsNoMoreElements() {
		fillHashtable_1();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			iterator.next();
		}
		iterator.next();
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void iteratorThrowExceptionIfUpdatedWhileIterating() {
		fillHashtable_1();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		while(iterator.hasNext()) {
			hashtable.put("BMW", "NE");
			iterator.next();
		}
	}
	
	@Test
	public void iteratorRemovingNullElement() {
		fillHashtable_1();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		hashtable.remove(null);	
		while (iterator.hasNext()) {
			iterator.next();			
		}
	}
	
	@Test
	public void iteratorRemovingMissingElement() {
		fillHashtable();
		
		Iterator<SimpleHashtable.TableEntry> iterator = hashtable.iterator();
		hashtable.remove("Jure");	
		while (iterator.hasNext()) {
			iterator.next();			
		}
	}
}
