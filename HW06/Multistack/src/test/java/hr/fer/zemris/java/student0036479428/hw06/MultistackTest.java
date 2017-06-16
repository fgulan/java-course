package hr.fer.zemris.java.student0036479428.hw06;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MultistackTest {

	ObjectMultistack multistack = null;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	

	@Before
	public void fillStack() {
		multistack = new ObjectMultistack();
		ValueWrapper price = new ValueWrapper(Integer.valueOf(2000));
		ValueWrapper grade = new ValueWrapper(Double.valueOf(3.72));
		ValueWrapper year = new ValueWrapper(new String("2015"));

		multistack.push("price", price);
		multistack.push("grade", grade);
		multistack.push("year", year);

		multistack.push("price", new ValueWrapper(Integer.valueOf(5000)));
		multistack.push("grade", new ValueWrapper(Double.valueOf(5)));
		multistack.push("year", new ValueWrapper(new String("2016")));
	}


	@Test
	public void isEmptyMethod() {
		assertEquals(false, multistack.isEmpty("price"));
		assertEquals(true, multistack.isEmpty("town"));
		assertEquals(false, multistack.isEmpty("price"));
	}

	@Test
	public void popMethod() {
		assertEquals(5000, multistack.pop("price").getValue());
		assertEquals(2000, multistack.pop("price").getValue());
		assertEquals(true, multistack.isEmpty("price"));
	}

	@Test
	public void popMethodExcpetion() {
		thrown.expect(IllegalArgumentException.class);
		assertEquals(5000, multistack.pop("price").getValue());
		assertEquals(2000, multistack.pop("price").getValue());
		assertEquals(2000, multistack.pop("price").getValue());
	}

	@Test
	public void peekMethod() {
		assertEquals(5000, multistack.peek("price").getValue());
		assertEquals(5000, multistack.peek("price").getValue());
		assertEquals(false, multistack.isEmpty("price"));
	}

	@Test
	public void peekMethodExcpetion() {
		thrown.expect(IllegalArgumentException.class);
		assertEquals(5000, multistack.peek("prices").getValue());

	}

	@Test
	public void divideValueWrapper() {
		multistack.peek("price").divide(5);
		assertEquals(1000, multistack.peek("price").getValue());

		multistack.peek("price").divide("5");
		assertEquals(200, multistack.peek("price").getValue());

		multistack.peek("price").divide(10.0);
		assertEquals(20.0, multistack.peek("price").getValue());

		multistack.peek("price").divide("4.0");
		assertEquals(5.0, multistack.peek("price").getValue());

		multistack.peek("price").setValue(15);
		multistack.peek("price").divide(4);
		assertEquals(3, multistack.peek("price").getValue());
	}

	@Test
	public void multiplyValueWrapper() {
		multistack.peek("price").setValue(20);
		multistack.peek("price").multiply(5);
		assertEquals(100, multistack.peek("price").getValue());

		multistack.peek("price").multiply("5");
		assertEquals(500, multistack.peek("price").getValue());

		multistack.peek("price").multiply(10.0);
		assertEquals(5000.0, multistack.peek("price").getValue());

		multistack.peek("price").multiply("1.5");
		assertEquals(7500.0, multistack.peek("price").getValue());
	}

	@Test
	public void incrementValueWrapper() {
		multistack.peek("price").setValue(20);
		multistack.peek("price").increment(5);
		assertEquals(25, multistack.peek("price").getValue());

		multistack.peek("price").increment("5");
		assertEquals(30, multistack.peek("price").getValue());

		multistack.peek("price").increment(10.5);
		assertEquals(40.5, multistack.peek("price").getValue());

		multistack.peek("price").increment("1.5");
		assertEquals(42.0, multistack.peek("price").getValue());
	}

	@Test
	public void decrementValueWrapper() {
		multistack.peek("price").setValue(50);
		multistack.peek("price").decrement(5);
		assertEquals(45, multistack.peek("price").getValue());

		multistack.peek("price").decrement("5");
		assertEquals(40, multistack.peek("price").getValue());

		multistack.peek("price").decrement(10.5);
		assertEquals(29.5, multistack.peek("price").getValue());

		multistack.peek("price").decrement("1.5");
		assertEquals(28.0, multistack.peek("price").getValue());
	}

	@Test
	public void divisionByZero() {
		thrown.expect(IllegalArgumentException.class);
		multistack.peek("price").setValue(50);
		multistack.peek("price").divide(null);
	}

	@Test
	public void zeroDivision() {
		multistack.peek("price").setValue(null);
		multistack.peek("price").divide(50);
		assertEquals(0, multistack.peek("price").getValue());
	}

	@Test
	public void invalidElement() {
		thrown.expect(RuntimeException.class);
		multistack.peek("price").setValue(new Object());
		multistack.peek("price").divide(null);
	}

	@Test
	public void invalidElement2() {
		thrown.expect(RuntimeException.class);
		multistack.peek("price").setValue(null);
		multistack.peek("price").divide(new Object());
	}

	@Test
	public void invalidValue() {
		thrown.expect(RuntimeException.class);
		multistack.peek("price").setValue(null);
		multistack.peek("price").multiply("a125.34");
	}

	@Test
	public void compareNumbers() {
		multistack.peek("price").setValue(null);
		assertEquals(0, multistack.peek("price").numCompare(new Integer(0)));
		
		multistack.peek("price").setValue(15);
		assertEquals(0, multistack.peek("price").numCompare(new Integer(15)));
		
		multistack.peek("price").setValue(-15);
		assertEquals(-1, multistack.peek("price").numCompare(new Integer(-14)));
		
		multistack.peek("price").setValue(-15);
		assertEquals(1, multistack.peek("price").numCompare(new Integer(-16)));
	}
	
	@Test
	public void compareNumbers2() {
		multistack.peek("price").setValue(null);
		assertEquals(0, multistack.peek("price").numCompare(null));
		
		multistack.peek("price").setValue(15);
		assertEquals(1, multistack.peek("price").numCompare(null));
	}
	
	@Test
	public void invalidCompateValue() {
		thrown.expect(RuntimeException.class);
		multistack.peek("price").setValue(15);
		assertEquals(1, multistack.peek("price").numCompare("ab12334"));
	}
	
	@Test
	public void testMain() {
		ObjectMultistackDemo.main(null);
		assertTrue(true);
	}
}
