package hr.fer.zemris.linearna;

/**
 * AbstractMatrix razred predstavlja osnovni razred za sve
 * tipove matrica.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public abstract class AbstractMatrix implements IMatrix {

	@Override
	public abstract int getRowsCount();

	@Override
	public abstract int getColsCount();

	@Override
	public abstract double get(int row, int col);

	@Override
	public abstract IMatrix set(int row, int col, double value);

	@Override
	public abstract IMatrix copy();

	@Override
	public abstract IMatrix newInstance(int rows, int cols);

	@Override
	public IMatrix nTranspose(boolean liveView) {
		if (liveView) {
			return new MatrixTransposeView(this);
		} else {
			int rows = this.getRowsCount();
			int cols = this.getColsCount();
			IMatrix matrix = newInstance(cols, rows);
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					matrix.set(j, i, this.get(i, j));
				}
			}
			return matrix;
		}
	}

	@Override
	public IMatrix add(IMatrix other) {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = Math.min(this.getRowsCount(), other.getRowsCount());
		int cols = Math.min(this.getColsCount(), other.getColsCount());

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.set(i, j, this.get(i, j) + other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nAdd(IMatrix other) {
		return this.copy().add(other);
	}

	@Override
	public IMatrix sub(IMatrix other) {
		if (this.getColsCount() != other.getColsCount()
				|| this.getRowsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = Math.min(this.getRowsCount(), other.getRowsCount());
		int cols = Math.min(this.getColsCount(), other.getColsCount());

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.set(i, j, this.get(i, j) - other.get(i, j));
			}
		}
		return this;
	}

	@Override
	public IMatrix nSub(IMatrix other) {
		return this.copy().sub(other);
	}

	@Override
	public IMatrix nMultiply(IMatrix other) {
		if (this.getColsCount() != other.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		int rows = this.getRowsCount();
		int cols = other.getColsCount();
		int colsFirst = this.getColsCount();

		IMatrix matrix = this.newInstance(rows, cols);
		double value;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < colsFirst; k++) {
					value = matrix.get(i, j);
					value += this.get(i, k) * other.get(k, j);
					matrix.set(i, j, value);
				}
			}
		}
		return matrix;
	}

	@Override
	public double determinant() throws IncompatibleOperandException {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException();
		}
		return determinantCalc(this);
	}

	/**
	 * Računa determinantu nad danom matricom.
	 * Algroitam URL: professorjava.weebly.com/matrix-determinant.html
	 * @param mat Matrica nad kojom računamo.
	 * @return Determinanta dane matrice.
	 */
	public static double determinantCalc(IMatrix mat) {
		int s;
		double det = 0;
		if (mat.getRowsCount() == 1) {
			det = mat.get(0, 0);
		} else if (mat.getRowsCount() == 2) {
			det = mat.get(0, 0) * mat.get(1, 1) - mat.get(1, 0) * mat.get(0, 1);
		} else {
			int size = mat.getRowsCount();
			for (int i = 0; i < size; i++) {
				if (i % 2 == 0) {
					s = 1;
				} else {
					s = -1;
				}
				det += s * mat.get(0, i) * (determinantCalc(mat.subMatrix(0, i, true)));
			}
		}
		return det;
	}

	/**
	 * Računa matricu kofaktora nad trenutnom matricom.
	 * @return Matricu kofaktora trenutne matrice.
	 */
	private IMatrix cofactor() {
		int size = this.getColsCount();
		IMatrix temp = newInstance(size - 1, size - 1);
		IMatrix cofactor = newInstance(size, size);
		if (size == 1) {
			cofactor.set(0, 0, 1.0);
			return cofactor;
		}
		int ii, jj, i1, j1;
		double det;

		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				i1 = 0;
				for (ii = 0; ii < size; ii++) {
					if (ii == i) {
						continue;
					}
					j1 = 0;
					for (jj = 0; jj < size; jj++) {
						if (jj == j) {
							continue;
						}
						temp.set(i1, j1, this.get(ii, jj));
						j1++;
					}
					i1++;
				}
				det = temp.determinant();
				double value = Math.pow(-1.0, i + j + 2.0) * det;
				cofactor.set(i, j, value);
			}
		}
		return cofactor;
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			return new MatrixSubMatrixView(this, row, col);
		} else {
			int rows = this.getRowsCount();
			int cols = this.getColsCount();
			IMatrix matrix = this.newInstance(rows - 1, cols - 1);

			for (int i = 0; i < rows - 1; i++) {
				for (int j = 0; j < cols - 1; j++) {
					if (i >= row && j >= col) {
						matrix.set(i, j, this.get(i + 1, j + 1));
					} else if (i >= row) {
						matrix.set(i, j, this.get(i + 1, j));
					} else if (j >= col) {
						matrix.set(i, j, this.get(i, j + 1));
					} else {
						matrix.set(i, j, this.get(i, j));
					}
				}
			}
			return matrix;
		}
	}

	@Override
	public IMatrix nInvert() {
		if (this.determinant() == 0.0) {
			throw new UnsupportedOperationException();
		}
		double det = this.determinant();
		IMatrix adjoint = this.cofactor().nTranspose(false);
		return adjoint.scalarMultiply(1.0 / det);
	}

	@Override
	public double[][] toArray() {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		double[][] matrix = new double[rows][cols];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = this.get(i, j);
			}
		}
		return matrix;
	}

	@Override
	public IVector toVector(boolean liveView) {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		if (cols != 1 && rows != 1) {
			throw new IncompatibleOperandException();
		}
		if (liveView) {
			return new VectorMatrixView(this);
		} else {
			IVector vector;
			if (rows == 1) {
				vector = LinAlgDefaults.defaultVector(cols);
				for (int i = 0; i < cols; i++) {
					vector.set(i, this.get(0, i));
				}
			} else {
				vector = LinAlgDefaults.defaultVector(rows);
				for (int i = 0; i < rows; i++) {
					vector.set(i, this.get(i, 0));
				}
			}
			return vector;
		}
	}

	@Override
	public IMatrix nScalarMultiply(double value) {
		return this.copy().scalarMultiply(value);
	}

	@Override
	public IMatrix scalarMultiply(double value) {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	@Override
	public IMatrix makeIdentity() {
		if (this.getColsCount() != this.getRowsCount()) {
			throw new IncompatibleOperandException();
		}

		int rows = this.getRowsCount();
		int cols = this.getColsCount();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j) {
					this.set(i, j, 1.0);
				} else {
					this.set(i, j, 0.0);
				}
			}
		}

		return this;
	}

	/**
	 * Formatirano ispisuje matricu s tri decimalna mjesta
	 * @return Tekstualna reprezentacija matrice.
	 */
	public String toString() {
		return toString(3);
	}

	/**
	 * Formatirano ispisuje matricu s odabranim brojem decimalnih
	 * mjesta za elemente matrice.
	 * @param decimals Broj decimalnih mjesta.
	 * @return Tekstualna reprezentacija matrice.
	 */
	public String toString(int decimals) {
		int rows = this.getRowsCount();
		int cols = this.getColsCount();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			builder.append("[");
			for (int j = 0; j < cols; j++) {
				builder.append(String.format("%." + decimals + "f",
						this.get(i, j)));
				if (j < cols - 1) {
					builder.append("; ");
				}
			}
			builder.append(String.format("]%n"));
		}
		return builder.toString();
	}
}
