package hr.fer.zemris.linearna;

/**
 * AbstractVector razred predstavlja osnovni razred za sve
 * tipove vektora.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public abstract class AbstractVector implements IVector {

	@Override
	public abstract double get(int index);

	@Override
	public abstract IVector set(int index, double value)
			throws UnmodifiableObjectException;

	@Override
	public abstract int getDimension();

	@Override
	public abstract IVector copy();

	@Override
	public IVector copyPart(int n) {
		IVector vector = newInstance(n);
		int size = Math.min(n, this.getDimension());

		for (int i = 0; i < size; i++) {
			vector.set(i, this.get(i));
		}
		return vector;
	}

	@Override
	public abstract IVector newInstance(int dimension);

	@Override
	public IVector add(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) + other.get(i));
		}
		return this;
	}

	@Override
	public IVector nAdd(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		return this.copy().add(other);
	}

	@Override
	public IVector sub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) - other.get(i));
		}
		return this;
	}

	@Override
	public IVector nSub(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		return this.copy().sub(other);
	}

	@Override
	public IVector scalarMultiply(double byValue) {
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) * byValue);
		}
		return this;
	}

	@Override
	public IVector nScalarMultiply(double byValue) {
		return this.copy().scalarMultiply(byValue);
	}

	@Override
	public double norm() {
		double sum = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			sum += Math.pow(this.get(i), 2);
		}
		return Math.sqrt(sum);
	}

	@Override
	public IVector normalize() {
		double norm = this.norm();
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			this.set(i, this.get(i) / norm);
		}
		return this;
	}

	@Override
	public IVector nNormalize() {
		return this.copy().normalize();
	}

	@Override
	public double cosine(IVector other) throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		return this.scalarProduct(other) / (this.norm() * other.norm());
	}

	@Override
	public double scalarProduct(IVector other)
			throws IncompatibleOperandException {
		if (this.getDimension() != other.getDimension()) {
			throw new IncompatibleOperandException();
		}
		double sum = 0;
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			sum += this.get(i) * other.get(i);
		}
		return sum;
	}

	@Override
	public IVector nVectorProduct(IVector other)
			throws IncompatibleOperandException {
		if(this.getDimension() !=3 || other.getDimension() != 3) {
			throw new IncompatibleOperandException();
		}
		
		double[] component = new double[3];
		component[0] = this.get(1)*other.get(2) - this.get(2)*other.get(1);
		component[1] = this.get(2)*other.get(0) - this.get(0)*other.get(2);
		component[2] = this.get(0)*other.get(1) - this.get(1)*other.get(0);
		
		IVector vector = newInstance(3);
		for (int i = 0; i < 3; i++) {
			vector.set(i, component[i]);
		}
		return vector;
	}

	@Override
	public IVector nFromHomogeneus() {
		if (this.getDimension() == 1) {
			throw new IncompatibleOperandException();
		}
		double homc = this.get(this.getDimension() - 1);
		if (homc == 0.0) {
			throw new IncompatibleOperandException();
		}
		IVector vector = this.newInstance(this.getDimension() - 1);
		for (int i = vector.getDimension() - 1; i >= 0; i--) {
			vector.set(i, this.get(i) / homc);
		}
		return vector;
	}

	@Override
	public IMatrix toRowMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(this, true);
		} else {
			int size = this.getDimension();
			IMatrix matrix = LinAlgDefaults.defaultMatrix(1, size);
			for (int i = 0; i < size; i++) {
				matrix.set(0, i, this.get(i));
			}
			return matrix;
		}
	}

	@Override
	public IMatrix toColumnMatrix(boolean liveView) {
		if (liveView) {
			return new MatrixVectorView(this, false);
		} else {
			int size = this.getDimension();
			IMatrix matrix = LinAlgDefaults.defaultMatrix(size, 1);
			for (int i = 0; i < size; i++) {
				matrix.set(i, 0, this.get(i));
			}
			return matrix;
		}
	}

	@Override
	public double[] toArray() {
		double[] vectorComponents = new double[this.getDimension()];
		for (int i = this.getDimension() - 1; i >= 0; i--) {
			vectorComponents[i] = this.get(i);
		}
		return vectorComponents;
	}

	/**
	 * Formatirano ispisuje vektor s tri decimalna mjesta
	 * @return Tekstualna reprezentacija vektora.
	 */
	public String toString() {
		return toString(3);
	}
	
	/**
	 * Formatirano ispisuje vektor s odabranim brojem decimalnih
	 * mjesta za elemente vektora.
	 * @param decimals Broj decimalnih mjesta.
	 * @return Tekstualna reprezentacija vektora.
	 */
	public String toString(int decimals) {
		int size = this.getDimension();
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < size; i++) {
			builder.append(String.format("%." + decimals + "f", this.get(i)));
			if (i < size - 1) {
				builder.append("; ");
			}
		}
		builder.append(String.format("]%n"));
		return builder.toString();
	}

}
