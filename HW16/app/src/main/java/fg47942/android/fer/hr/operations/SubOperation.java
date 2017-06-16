package fg47942.android.fer.hr.operations;

/**
 * SubOperation implements {@link IOperation} interface. It represents subtracting operation over
 * two number.
 */
public class SubOperation implements IOperation {
    /**
     * Operation symbol.
     */
    private static final String SYMBOL = "-";

    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculate(Double firstParam, Double secondParam) {
        return firstParam - secondParam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return SYMBOL;
    }
}
