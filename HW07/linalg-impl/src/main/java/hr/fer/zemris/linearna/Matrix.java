package hr.fer.zemris.linearna;

/**
 * Razred Matrix predstavlja osnovnu implementaciju matrice.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Matrix extends AbstractMatrix {
	/** Elementi matrice */
	protected double[][] elements;
	/** Broj redaka */
	protected int rows;
	/** Broj stupaca */
	protected int cols;

	/**
	 * Konstruktor razreda Matrix. Stvara matricu ispunjenu nulama.
	 * @param rows Broj redaka.
	 * @param cols Broj stupaca
	 */
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		elements = new double[rows][cols];
	}

	/**
	 * Konstruktor razreda Matrix. Stvara matricu ispunjenu danim 2D poljem.
	 * @param rows Broj redaka.
	 * @param cols Broj stupaca.
	 * @param array 2D polje elemenata.
	 * @param useGiven <code>true</code> koristi dano polje, inače kopira dano polje.
	 */
	public Matrix(int rows, int cols, double[][] array, boolean useGiven) {
		if (rows < 0 || rows > array.length || cols < 0
				|| cols > array[0].length) {
			throw new IncompatibleOperandException();
		}
		if (useGiven) {
			this.rows = rows;
			this.cols = cols;
			elements = array;
		} else {
			this.rows = rows;
			this.cols = cols;
			elements = new double[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					elements[i][j] = array[i][j];
				}
			}
		}
	}

	@Override
	public int getRowsCount() {
		return rows;
	}

	@Override
	public int getColsCount() {
		return cols;
	}

	@Override
	public double get(int row, int col) {
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IncompatibleOperandException();
		}
		return elements[row][col];
	}

	@Override
	public IMatrix set(int row, int col, double value) {
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new IncompatibleOperandException();
		}
		elements[row][col] = value;
		return this;
	}

	@Override
	public IMatrix copy() {
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
		return new Matrix(rows, cols);
	}

	/**
	 * Stvara matricu iz danog teksta. Elemeti matrice moraju
	 * biti razdovjeni prazninama, dok retci moraju biti razdvojeni
	 * znakom "|".
	 * @param input Ulazni niz znakova.
	 * @return Nova matrica.
	 * @throws IncompatibleOperandException Ukoliko ulazni niz nije ispravno zadan.
	 */
	public static Matrix parseSimple(String input) {
		input = input.trim();
		String[] rows = input.split("\\|");
		String[][] table = new String[rows.length][];
		for (int i = 0; i < rows.length; i++) {
			table[i] = rows[i].trim().split("\\s+");
		}
		double[][] matrix = new double[table.length][table[0].length];
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i].length != table[i].length) {
				throw new IncompatibleOperandException(
						"Broj elemenata u svakom retku mora biti konzistentan!");
			}
			for (int j = 0; j < matrix[i].length; j++) {
				try {
					matrix[i][j] = Double.parseDouble(table[i][j]);
				} catch (NumberFormatException e) {
					throw new IncompatibleOperandException(
							"Neispravan ulazni niz!");
				}
				
			}
		}
		return new Matrix(matrix.length, matrix[0].length, matrix, true);
	}
}
