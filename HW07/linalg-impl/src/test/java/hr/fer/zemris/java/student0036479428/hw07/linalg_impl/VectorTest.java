package hr.fer.zemris.java.student0036479428.hw07.linalg_impl;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.UnmodifiableObjectException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Before;
import org.junit.Test;

public class VectorTest {

	private static final double DELTA = 1e-8;
	public IVector vector;

	@Before
	public void setUp() throws Exception {
		vector = Vector.parseSimple("1 2 8 9");
	}

	@Test
	public void get() {
		assertEquals(8, vector.get(2), DELTA);
	}

	@Test
	public void set() {
		vector.set(2, 7);
		assertEquals(7, vector.get(2), DELTA);
	}

	@Test
	public void getDimensions() {
		assertEquals(4, vector.getDimension());
	}

	@Test
	public void constructor() {
		vector = new Vector(1, 2, 3);
		assertEquals(3, vector.getDimension());
		vector = new Vector(true, true, 1, 2, 3);
	}

	@Test
	public void copy() {
		IVector vector2 = vector.copy();
		assertEquals(vector2.getDimension(), vector.getDimension());
	}

	@Test(expected = IncompatibleOperandException.class)
	public void getException() {
		vector.get(4);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void setException() {
		vector.set(4, 5);
	}

	@Test(expected = UnmodifiableObjectException.class)
	public void setException2() {
		vector = new Vector(true, false, 2, 5, 6);
		vector.set(4, 5);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void parseException() {
		vector = Vector.parseSimple("1a 2 3");
	}
}
