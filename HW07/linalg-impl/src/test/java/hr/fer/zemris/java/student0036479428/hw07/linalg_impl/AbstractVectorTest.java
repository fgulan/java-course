package hr.fer.zemris.java.student0036479428.hw07.linalg_impl;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Vector;

import org.junit.Before;
import org.junit.Test;

public class AbstractVectorTest {

	private static final double DELTA = 1e-8;
	private IVector vector;

	@Before
	public void setUp() throws Exception {
		vector = new Vector(1, 2, 3);
	}

	@Test
	public void add() {
		vector.add(new Vector(1, 2, 3));
		assertEquals(4, vector.get(1), DELTA);
	}

	@Test
	public void nAdd() {
		IVector vector2 = vector.nAdd(new Vector(1, 2, 3));
		assertEquals(2, vector.get(1), DELTA);
		assertEquals(4, vector2.get(1), DELTA);
	}

	@Test
	public void sub() {
		vector.sub(new Vector(1, 2, 3));
		assertEquals(0, vector.get(1), DELTA);
	}

	@Test
	public void nSub() {
		IVector vector2 = vector.nSub(new Vector(1, 2, 3));
		assertEquals(2, vector.get(1), DELTA);
		assertEquals(0, vector2.get(1), DELTA);
	}

	@Test
	public void scalarMultiply() {
		vector.scalarMultiply(2);
		assertEquals(4, vector.get(1), DELTA);
	}

	@Test
	public void nscalarMultiply() {
		IVector vector2 = vector.nScalarMultiply(2);
		assertEquals(2, vector.get(1), DELTA);
		assertEquals(4, vector2.get(1), DELTA);
	}

	@Test
	public void norm() {
		vector = new Vector(3, 4);
		assertEquals(5, vector.norm(), DELTA);
	}

	@Test
	public void normalize() {
		vector = new Vector(3, 4);
		vector.normalize();
		assertEquals(3.0 / 5, vector.get(0), DELTA);

		IVector vector2 = vector.nNormalize();
		assertEquals(3.0 / 5, vector2.get(0), DELTA);
	}

	@Test
	public void cosine() {
		vector = new Vector(0, 10);
		IVector vector2 = new Vector(10, 0);
		assertEquals(0, vector.cosine(vector2), DELTA);

		vector = new Vector(10, 0);
		vector2 = new Vector(10, 0);
		assertEquals(1, vector.cosine(vector2), DELTA);
	}

	@Test
	public void vectorProduct() {
		vector = new Vector(1, -5, 4);
		IVector vector2 = new Vector(2, 3, 5);
		IVector result = vector.nVectorProduct(vector2);
		assertEquals(-37, result.get(0), DELTA);
		assertEquals(3, result.get(1), DELTA);
		assertEquals(13, result.get(2), DELTA);
	}

	@Test
	public void fromHomogeneus() {
		vector = new Vector(9, 6, 3);
		vector = vector.nFromHomogeneus();
		assertEquals(2, vector.get(1), DELTA);
		assertEquals(2, vector.getDimension());
	}

	@Test
	public void toRowMatrix() {
		IMatrix matrix = vector.toRowMatrix(false);
		assertEquals(1, matrix.getRowsCount());
		assertEquals(3, matrix.getColsCount());
		vector.set(0, 2);
		assertEquals(1, matrix.get(0, 0), DELTA);
	}

	@Test
	public void toRowMatrix2() {
		IMatrix matrix = vector.toRowMatrix(true);
		assertEquals(1, matrix.getRowsCount());
		assertEquals(3, matrix.getColsCount());
		vector.set(0, 2);
		assertEquals(2, matrix.get(0, 0), DELTA);
	}

	@Test
	public void toColumnMatrix() {
		IMatrix matrix = vector.toColumnMatrix(false);
		assertEquals(3, matrix.getRowsCount());
		assertEquals(1, matrix.getColsCount());
		vector.set(0, 2);
		assertEquals(1, matrix.get(0, 0), DELTA);
	}

	@Test
	public void toColumnMatrix2() {
		IMatrix matrix = vector.toColumnMatrix(true);
		assertEquals(3, matrix.getRowsCount());
		assertEquals(1, matrix.getColsCount());
		vector.set(1, 2);
		assertEquals(2, matrix.get(1, 0), DELTA);
	}

	@Test
	public void toArrayMethod() {
		double[] array = vector.toArray();
		assertEquals(1, array[0], DELTA);
	}

	@Test
	public void toStringMethod() {
		assertEquals(true, vector.toString().startsWith("[1"));
	}

	@Test
	public void copyPart() {
		vector = vector.copyPart(5);
		assertEquals(5, vector.getDimension());
		assertEquals(1, vector.get(0), DELTA);

	}

	@Test
	public void scalarProduct() {
		assertEquals(14, vector.scalarProduct(vector), DELTA);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void addException() {
		vector.add(new Vector(1));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nAddException() {
		vector.nAdd(new Vector(1, 2));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void subException() {
		vector.sub(new Vector(1));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void nSubException() {
		vector.nSub(new Vector(1, 2));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void cosineException() {
		vector.cosine(new Vector(1));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void scalarException() {
		vector.scalarProduct(new Vector(1));
	}

	@Test(expected = IncompatibleOperandException.class)
	public void homogeneusException1() {
		IVector vector1 = new Vector(1);
		vector1.nFromHomogeneus();
	}
	
	@Test(expected = IncompatibleOperandException.class)
	public void homogeneusException2() {
		IVector vector1 = new Vector(1,2, 0);
		vector1.nFromHomogeneus();
	}
	
	@Test(expected = IncompatibleOperandException.class)
	public void vectorProductException() {
		vector.nVectorProduct(new Vector(1));
	}
}
