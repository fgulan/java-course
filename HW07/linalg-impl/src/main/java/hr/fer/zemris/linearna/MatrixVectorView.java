package hr.fer.zemris.linearna;

/**
 * MatrixVectorView razred predstavlja pogled na vektor kao na jednoretčanu
 * ili jednostupčanu matricu.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class MatrixVectorView extends AbstractMatrix {
	/** Referenca na vektor */
	private IVector original;
	/** Jednoretčana matrcia */
	boolean asRowMatrix;

	/**
	 * Konstruktor razreda MatrixVectorView. Stvara pogled na dani vektor
	 * kao na matricu.
	 * @param original Vektor.
	 * @param asRowMatrix Ukoliko <code>true</code> pogled kao jednoretčana matrica,
	 * inače jednostupčana.
	 */
	public MatrixVectorView(IVector original, boolean asRowMatrix) {
		this.original = original;
		this.asRowMatrix = asRowMatrix;
	}

	@Override
	public int getRowsCount() {
		if (asRowMatrix) {
			return 1;
		} else {
			return original.getDimension();
		}
	}

	@Override
	public int getColsCount() {
		if (asRowMatrix) {
			return original.getDimension();
		} else {
			return 1;
		}
	}

	@Override
	public double get(int row, int col) {
		if (asRowMatrix) {
			if (row != 0 || col < 0 || col >= original.getDimension()) {
				throw new IncompatibleOperandException();
			}
			return original.get(col);
		}
		if (col != 0 || row < 0 || row >= original.getDimension()) {
			throw new IncompatibleOperandException();
		}
		return original.get(row);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if (asRowMatrix) {
			if (row != 0 || col < 0 || col >= original.getDimension()) {
				throw new IncompatibleOperandException();
			}
			original.set(col, value);
		} else {
			if (col != 0 || row < 0 || row >= original.getDimension()) {
				throw new IncompatibleOperandException();
			}
			original.set(row, value);
		}
		return this;
	}

	@Override
	public IMatrix copy() {
		IMatrix matrix;
		int size = original.getDimension();
		if (asRowMatrix) {
			matrix = this.newInstance(1, size);
			for (int i = 0; i < size; i++) {
				matrix.set(0, i, original.get(i));
			}
		} else {
			matrix = this.newInstance(size, 1);
			for (int i = 0; i < size; i++) {
				matrix.set(i, 0, original.get(i));
			}
		}
		return matrix;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		if (rows != 1 && cols != 1) {
			throw new IncompatibleOperandException();
		}
		IVector vector;
		if (rows == 1) {
			vector = LinAlgDefaults.defaultVector(cols);
			return new MatrixVectorView(vector, true);
		}
		vector = LinAlgDefaults.defaultVector(rows);
		return new MatrixVectorView(vector, true);
	}

}
