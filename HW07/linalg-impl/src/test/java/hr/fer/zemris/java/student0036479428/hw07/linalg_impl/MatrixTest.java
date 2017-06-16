package hr.fer.zemris.java.student0036479428.hw07.linalg_impl;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

	private static final double DELTA = 1e-8;
	private IMatrix matrix;

	private double[][] fillArray(int size1, int size2) {
		double[][] array = new double[size1][size2];
		for (int i = 0; i < size1; i++) {
			for (int j = 0; j < size2; j++) {
				array[i][j] = i;
			}
		}
		return array;
	}

	@Before
	public void setUp() throws Exception {
		matrix = Matrix.parseSimple("1 2 3|4 5 6|7 8 9");
	}

	@Test
	public void getDimension() {
		assertEquals(3, matrix.getColsCount());
		assertEquals(3, matrix.getRowsCount());
	}

	@Test
	public void getElement() {
		assertEquals(1, matrix.get(0, 0), DELTA);
		assertEquals(4, matrix.get(1, 0), DELTA);
	}

	@Test
	public void setElement() {
		matrix.set(0, 0, -1);
		assertEquals(-1, matrix.get(0, 0), DELTA);
		assertEquals(4, matrix.get(1, 0), DELTA);
	}

	@Test
	public void constructorsTest1() {
		matrix = new Matrix(2, 3);
		assertEquals(3, matrix.getColsCount());
		assertEquals(2, matrix.getRowsCount());
	}

	@Test
	public void constructorsTest2() {
		double[][] array = fillArray(3, 3);
		matrix = new Matrix(3, 3, array, false);
		assertEquals(1, matrix.get(1, 0), DELTA);
	}

	@Test
	public void copyMethod() {
		double[][] array = fillArray(3, 3);
		matrix = new Matrix(3, 3, array, false);
		IMatrix copyMatrix = matrix.copy();
		assertEquals(1, copyMatrix.get(1, 0), DELTA);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void parseSimpleException() {
		matrix = Matrix.parseSimple("1 2 3|4 5 6 8|7 8 9");
	}

	@Test(expected = IncompatibleOperandException.class)
	public void getException() {
		matrix.get(3, 0);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void setException() {
		matrix.set(3, 0, 500);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void constructorException() {
		double[][] array = fillArray(3, 3);
		matrix = new Matrix(4, 3, array, false);
	}
}
