package hr.fer.zemris.java.student0036479428.hw07.linalg_impl;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.linearna.IMatrix;
import hr.fer.zemris.linearna.IVector;
import hr.fer.zemris.linearna.IncompatibleOperandException;
import hr.fer.zemris.linearna.Matrix;

import org.junit.Before;
import org.junit.Test;

public class AbstractMatrixTest {

	private static final double DELTA = 1e-8;
	private IMatrix matrix;
	private IVector vector;

	@Before
	public void setUp() throws Exception {
		matrix = Matrix.parseSimple("1 2 3|4 5 6|7 8 9");
	}

	@Test
	public void determinantTest() {
		assertEquals(0.0, matrix.determinant(), DELTA);
	}

	@Test
	public void transposeTest() {
		IMatrix matrixT = matrix.nTranspose(false);
		assertEquals(7, matrixT.get(0, 2), DELTA);

		IMatrix matrixS = matrixT.nTranspose(true);
		assertEquals(3, matrixS.get(0, 2), DELTA);
		matrixT.set(0, 0, 15);
		assertEquals(15, matrixS.get(0, 0), DELTA);
	}

	@Test
	public void add() {
		matrix.add(matrix);
		assertEquals(2, matrix.get(0, 0), DELTA);
	}

	@Test
	public void nAdd() {
		IMatrix matrixS = matrix.nAdd(matrix);
		assertEquals(2, matrixS.get(0, 0), DELTA);
	}

	@Test
	public void sub() {
		matrix.sub(matrix);
		assertEquals(0, matrix.get(0, 0), DELTA);
	}

	@Test
	public void nSub() {
		IMatrix matrixS = matrix.nSub(matrix);
		assertEquals(0, matrixS.get(0, 0), DELTA);
	}

	@Test
	public void nMultiply() {
		IMatrix matrixS = matrix.nMultiply(matrix);
		assertEquals(81, matrixS.get(1, 1), DELTA);
	}

	@Test
	public void scalarMultiply() {
		matrix.scalarMultiply(5);
		assertEquals(25, matrix.get(1, 1), DELTA);
	}

	@Test
	public void nScalarMultiply() {
		IMatrix matrixS = matrix.nScalarMultiply(5);
		assertEquals(25, matrixS.get(1, 1), DELTA);
	}

	@Test
	public void inverse() {
		matrix = Matrix.parseSimple("1 2 3| 0 1 4| 5 6 0");
		IMatrix matrixR = matrix.nInvert();
		assertEquals(-15, matrixR.get(1, 1), DELTA);
	}

	@Test
	public void inverse2() {
		matrix = Matrix.parseSimple("0.25");
		IMatrix matrixR = matrix.nInvert();
		assertEquals(4, matrixR.get(0, 0), DELTA);
	}

	@Test
	public void subMatrix() {
		IMatrix matrixR = matrix.subMatrix(0, 0, false);
		assertEquals(9, matrixR.get(1, 1), DELTA);

		IMatrix matrixS = matrix.subMatrix(1, 1, true);
		assertEquals(1, matrixS.get(0, 0), DELTA);
		matrix.set(0, 0, 15);
		assertEquals(15, matrixS.get(0, 0), DELTA);
	}

	@Test
	public void subMatrix2() {
		matrix = Matrix.parseSimple("1 2 3|4 5 6|7 8 9| 1 5 9");
		IMatrix matrixR = matrix.subMatrix(0, 0, false);
		assertEquals(5, matrixR.get(0, 0), DELTA);
	}

	@Test
	public void subMatrix3() {
		matrix = Matrix.parseSimple("1 2|4 5|7 8| 1 5| 8 9");
		IMatrix matrixR = matrix.subMatrix(0, 0, false);
		assertEquals(5, matrixR.get(0, 0), DELTA);
	}

	@Test
	public void makeIdentity() {
		IMatrix matrixI = matrix.makeIdentity();
		assertEquals(1, matrixI.get(0, 0), DELTA);
		assertEquals(0, matrixI.get(0, 1), DELTA);
	}

	@Test
	public void toStringMethos() {
		matrix = Matrix.parseSimple("14 15");
		assertEquals(true, matrix.toString().startsWith("[14"));
	}

	@Test
	public void toVector() {
		matrix = Matrix.parseSimple("1 2 3");
		vector = matrix.toVector(false);
		assertEquals(2, vector.get(1), DELTA);
	}

	@Test
	public void toVector2() {
		matrix = Matrix.parseSimple("1 2 3");
		vector = matrix.toVector(true);
		assertEquals(2, vector.get(1), DELTA);
		matrix.set(0, 1, 5);
		assertEquals(5, vector.get(1), DELTA);
	}

	@Test
	public void toVector3() {
		matrix = Matrix.parseSimple("1|2|3");
		vector = matrix.toVector(false);
		assertEquals(2, vector.get(1), DELTA);
	}

	@Test
	public void toArrayTest() {
		double[][] mat = matrix.toArray();
		assertEquals(3, mat.length);
		assertEquals(3, mat[0].length);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void invertMatrixWithZeroDet() {
		matrix.nInvert();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void multiplyException() {
		IMatrix matrixM = Matrix.parseSimple("56 | 25");
		matrixM.nMultiply(matrix);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void addException() {
		IMatrix matrixM = Matrix.parseSimple("56 | 25");
		matrixM.add(matrix);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void subException() {
		IMatrix matrixM = Matrix.parseSimple("56 | 25");
		matrixM.sub(matrix);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void determinantException() {
		IMatrix matrixM = Matrix.parseSimple("56 | 25");
		matrixM.determinant();
	}

	@Test(expected = IncompatibleOperandException.class)
	public void toVectorException() {
		matrix.toVector(false);
	}

	@Test(expected = IncompatibleOperandException.class)
	public void makeIdentityException() {
		IMatrix matrixM = Matrix.parseSimple("56 | 25");
		matrixM.makeIdentity();
	}

}
