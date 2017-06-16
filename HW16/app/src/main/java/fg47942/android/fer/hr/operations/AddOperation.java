package fg47942.android.fer.hr.operations;

/**
 * AddOperation implements {@link IOperation} interface. It represents addition operation over
 * two number.
 */
public class AddOperation implements IOperation {
    /**
     * Operation symbol.
     */
    private static final String SYMBOL = "+";

    /**
     * {@inheritDoc}
     */
    @Override
    public Double calculate(Double firstParam, Double secondParam) {
        return firstParam + secondParam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return SYMBOL;
    }
}
