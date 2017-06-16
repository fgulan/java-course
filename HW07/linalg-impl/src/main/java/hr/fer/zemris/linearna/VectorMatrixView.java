package hr.fer.zemris.linearna;

/**
 * VectorMatrixView razred predstavlja pogled na jednostupčanu ili
 * na jednoretčanu matricu kao na vektor.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class VectorMatrixView extends AbstractVector {

	/** Dimenzije matrice */
	private int dimension;
	/** Oblik matrice */
	private boolean rowMatrix;
	/** Referenca na matricu */
	private IMatrix original;

	/**
	 * Konstruktor razreda VectorMatrixView. Stvara pogled
	 * na jednoretčanu ili jednostupčanu matricu kao na vektor.
	 * @param original Ulazna matrica.
	 */
	public VectorMatrixView(IMatrix original) {
		int cols = original.getColsCount();
		int rows = original.getRowsCount();
		if (cols != 1 && rows != 1) {
			throw new IncompatibleOperandException();
		}
		if (cols == 1) {
			dimension = rows;
			rowMatrix = false;
		} else {
			dimension = cols;
			rowMatrix = true;
		}
		this.original = original;
	}

	@Override
	public double get(int index) {
		if (index < 0 || index >= dimension) {
			throw new IncompatibleOperandException();
		}
		if (rowMatrix) {
			return original.get(0, index);
		} else {
			return original.get(index, 0);
		}
	}

	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {
		if (index < 0 || index >= dimension) {
			throw new IncompatibleOperandException();
		}
		if (rowMatrix) {
			original.set(0, index, value);
		} else {
			original.set(index, 0, value);
		}
		return this;
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public IVector copy() {
		IVector vector = this.newInstance(dimension);
		if (rowMatrix) {
			for (int i = 0; i < dimension; i++) {
				vector.set(i, original.get(0, i));
			}
		} else {
			for (int i = 0; i < dimension; i++) {
				vector.set(i, original.get(i, 0));
			}
		}
		return vector;
	}

	@Override
	public IVector newInstance(int dimension) {
		IMatrix matrix = LinAlgDefaults.defaultMatrix(1, dimension);
		return new VectorMatrixView(matrix);
	}

}
