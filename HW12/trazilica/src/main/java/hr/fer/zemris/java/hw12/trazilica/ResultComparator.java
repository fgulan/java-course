package hr.fer.zemris.java.hw12.trazilica;

import java.util.Comparator;

/**
 * ResultComparator implements {@link Comparator}. It is used for sorting
 * {@link Result} objects.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ResultComparator implements Comparator<Result> {

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Result o1, Result o2) {
        if (o1.getResult() < o2.getResult()) {
            return 1;
        } else {
            return -1;
        }
    }
}
