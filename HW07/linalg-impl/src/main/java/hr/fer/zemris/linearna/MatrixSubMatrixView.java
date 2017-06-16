package hr.fer.zemris.linearna;

/**
 * MatrixSubMatrixView razred predstavlja pogled na danu matricu.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class MatrixSubMatrixView extends AbstractMatrix {

	/** Referenca na originalnu matricu */
	private IMatrix original;
	/** Vidljivi retci */
	private int[] rowIndexes;
	/** Vidljivi stupci */
	private int[] colIndexes;

	/**
	 * Stvara pogled na danu matricu bez danog retka i stupca.
	 * @param original Referenca na matricu.
	 * @param row Redak kojeg želimo izbaciti.
	 * @param col Stupac kojeg želimo izbaciti.
	 */
	public MatrixSubMatrixView(IMatrix original, int row, int col) {
		this(original, getIndexArray(row, fillArray(original.getRowsCount())),
				getIndexArray(col, fillArray(original.getColsCount())));
	}

	/**
	 * Vraća polje ispunjenom brojevima od 0 do limit-1.
	 * @param limit Granica.
	 * @return Polje ispunjeno brojevima od 0 do limit-1.
	 */
	private static int[] fillArray(int limit) {
		int[] array = new int[limit];
		for (int i = 0; i < limit; i++) {
			array[i] = i;
		}
		return array;
	}

	/**
	 * Vraća elemente danog polja indeksa bez odabranog indeksa.
	 * @param skipIndex Indeks kojeg želimo izbaciti.
	 * @param currentIndex Polje indeksa.
	 * @return Novo polje indeksa bez odabranog indeksa.
	 */
	private static int[] getIndexArray(int skipIndex, int[] currentIndex) {
		int[] array = new int[currentIndex.length - 1];

		for (int i = 0; i < array.length; i++) {
			if (i >= skipIndex) {
				array[i] = currentIndex[i + 1];
			} else {
				array[i] = currentIndex[i];
			}
		}
		return array;
	}

	@Override
	public IMatrix subMatrix(int row, int col, boolean liveView) {
		if (liveView) {
			int[] rows = getIndexArray(row, rowIndexes);
			int[] cols = getIndexArray(col, colIndexes);
			return new MatrixSubMatrixView(original, rows, cols);
		}
		int cols = this.colIndexes.length - 1;
		int rows = this.rowIndexes.length - 1;
		IMatrix matrix = this.newInstance(rows, cols);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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

	/**
	 * Stvara pogled na danu matricu s odabranim retcima i stupcima.
	 * @param original Matrica.
	 * @param rowIndexes Odabrani retci.
	 * @param colIndexes Odabrani stupci.
	 */
	private MatrixSubMatrixView(IMatrix original, int[] rowIndexes,
			int[] colIndexes) {
		this.original = original;
		this.rowIndexes = rowIndexes;
		this.colIndexes = colIndexes;
	}

	@Override
	public int getRowsCount() {
		return rowIndexes.length;
	}

	@Override
	public int getColsCount() {
		return colIndexes.length;
	}

	@Override
	public double get(int row, int col) {
		if (row < 0 || row >= rowIndexes.length || col < 0
				|| col >= colIndexes.length) {
			throw new IncompatibleOperandException();
		}
		return original.get(rowIndexes[row], colIndexes[col]);
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if (row < 0 || row >= rowIndexes.length || col < 0
				|| col >= colIndexes.length) {
			throw new IncompatibleOperandException();
		}
		original.set(rowIndexes[row], colIndexes[col], value);
		return this;
	}

	@Override
	public IMatrix copy() {
		int rows = rowIndexes.length;
		int cols = colIndexes.length;
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
		IMatrix matrix = LinAlgDefaults.defaultMatrix(rows + 1, cols + 1);
		return new MatrixSubMatrixView(matrix, 0, 0);
	}

}
