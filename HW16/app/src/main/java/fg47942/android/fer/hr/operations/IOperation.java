package fg47942.android.fer.hr.operations;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * IOperation interface represents simple binary math operation.
 *
 * @author Gulan
 * @version 1.0
 */
public interface IOperation extends Serializable {
    /**
     * Executes current operation over given arguments.
     *
     * @param firstParam  First argument.
     * @param secondParam Second argument.
     * @return Result of current operation executed over given numbers.
     */
    Double calculate(Double firstParam, Double secondParam);
    
    /**
     * {@inheritDoc}
     */
    @Override
    String toString();
}
