package hr.fer.zemris.java.hw13.beans;

import java.util.Comparator;

/**
 * Band items comparator based on their number of votes.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class BandComparator implements Comparator<Band> {

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Band arg0, Band arg1) {
        return arg1.getVotes().compareTo(arg0.getVotes());
    }

}
