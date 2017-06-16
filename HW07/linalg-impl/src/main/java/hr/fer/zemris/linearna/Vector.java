package hr.fer.zemris.linearna;

/**
 * Razred Vector predstavlja osnovnu implementaciju vektora.
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Vector extends AbstractVector {
	/** Dimezija vektora */
	private int dimensions;
	/** Elementi vektora */
	private double[] elements;
	/** Samo za čitanje */
	private boolean readOnly;

	/**
	 * Konsturktor razreda Vector. Stvara promijenjivi vektor
	 * iz danih elemenata.
	 * @param elems Elementi vektora.
	 */
	public Vector(double... elems) {
		this(false, false, elems);
	}

	/**
	 * Konsturktor razreda Vector. Stvara vektor iz danih elemenata.
	 * @param readOnly Ukoliko je <code>true</code> vektor je samo za čitanje.
	 * @param useGiven <code>true</code> koristi dano polje, inače kopira dano polje.
	 * @param elems Elementi vektora.
	 */
	public Vector(boolean readOnly, boolean useGiven, double... elems) {
		this.readOnly = readOnly;
		if (useGiven) {
			this.elements = elems;
		} else {
			this.elements = new double[elems.length];
			for (int i = elems.length - 1; i >= 0; i--) {
				this.elements[i] = elems[i];
			}
		}
		dimensions = elems.length;
	}

	@Override
	public double get(int index) {
		if (index < 0 || index >= dimensions) {
			throw new IncompatibleOperandException("Invalid index!");
		}
		return elements[index];
	}

	@Override
	public IVector set(int index, double value)
			throws UnmodifiableObjectException {
		if (this.readOnly) {
			throw new UnmodifiableObjectException("Given vector is read only!");
		}
		if (index < 0 || index >= dimensions) {
			throw new IncompatibleOperandException("Invalid index!");
		}
		this.elements[index] = value;
		return this;
	}

	@Override
	public int getDimension() {
		return dimensions;
	}

	@Override
	public IVector copy() {
		int size = this.getDimension();
		IVector vector = this.newInstance(size);
		for (int i = 0; i < size; i++) {
			vector.set(i, this.get(i));
		}
		return vector;
	}

	@Override
	public IVector newInstance(int dimension) {
		double[] components = new double[dimension];
		return new Vector(components);
	}


	/**
	 * Stvara vektor iz danog teksta. Elemeti vektora moraju
	 * biti razdovjeni prazninama.
	 * @param input Ulazni niz znakova.
	 * @return Novi promijenjivi vektor.
	 * @throws IncompatibleOperandException Ukoliko ulazni niz nije ispravno zadan.
	 */
	public static Vector parseSimple(String input) {
		String[] elems = input.split("\\s+");
		double[] components = new double[elems.length];
		for (int i = 0; i < components.length; i++) {
			try {
				components[i] = Double.parseDouble(elems[i]);
			} catch (Exception e) {
				throw new IncompatibleOperandException("Neispravan ulazni niz!");
			}
		}
		return new Vector(false, true, components);
	}
}
