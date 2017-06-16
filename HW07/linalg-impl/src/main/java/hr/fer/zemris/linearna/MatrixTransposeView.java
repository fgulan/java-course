package hr.fer.zemris.linearna;

/**
 * MatrixTransposeView razred predstavlja pogled na transponiranu danu matricu.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class MatrixTransposeView extends AbstractMatrix {
	
	/** Referenca na originalnu matricu */
	private IMatrix original;

	/**
	 * Stvara pogled na danu transponiranu matricu.
	 * @param original Referenca na originalnu matricu.
	 */
	public MatrixTransposeView(IMatrix original) {
		this.original = original;
	}

	@Override
	public int getRowsCount() {
		return original.getColsCount();
	}

	@Override
	public int getColsCount() {
		return original.getRowsCount();
	}

	@Override
	public double get(int row, int col) {
		return original.get(col, row);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		original.set(col,row, value);
		return this;
	}

	@Override
	public IMatrix copy() {
		int rows = original.getRowsCount();
		int cols = original.getColsCount();
		IMatrix matrix = this.newInstance(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix.set(i, j, this.get(i, j));
			}
		}
		return matrix;
	}

	@Override
	public IMatrix newInstance(int rows, int cols) {
		IMatrix matrix = LinAlgDefaults.defaultMatrix(rows, cols );
		return new MatrixTransposeView(matrix);
	}

}
