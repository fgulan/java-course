package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * PrimListModel implements {@link ListModel} interface. It represents list of
 * prime numbers, where next number is generated on call of method next().
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class PrimListModel implements ListModel<Integer> {
    /** List of prime numbers. */
    private LinkedList<Integer> list;
    /** List of listeners. */
    private List<ListDataListener> listeners;

    /**
     * Constructor for PrimListModel class.
     */
    public PrimListModel() {
        list = new LinkedList<Integer>();
        listeners = new ArrayList<ListDataListener>();
        list.add(Integer.valueOf(1));
    }

    /**
     * Checks if given number is prime.
     * 
     * @param number
     *            Number to check.
     * @return <code>true</code> if given number is prime number,
     *         <code>false</code> otherwise.
     */
    private boolean isPrime(int number) {
        if (number == 2 || number == 3) {
            return true;
        } else if (number % 2 == 0 || number % 3 == 0) {
            return false;
        } else {
            double numSqrt = Math.floor(Math.sqrt(number));
            for (int i = 5; i <= numSqrt; i += 6) {
                if (number % i == 0 || number % (i + 2) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds next prime number to the list.
     */
    public void next() {
        int start = list.getLast();
        while (true) {
            start++;
            if (isPrime(start)) {
                list.addLast(start);
                break;
            }

        }
        notifyAllListeners();

    }

    /**
     * Notify all registered observer objects for change in list..
     */
    private void notifyAllListeners() {
        int newNumber = list.size() - 1;
        for (ListDataListener listener : listeners) {
            listener.intervalAdded(new ListDataEvent(this,
                    ListDataEvent.INTERVAL_ADDED, newNumber, newNumber));
        }

    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public Integer getElementAt(int index) {
        return list.get(index);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public int getSize() {
        return list.size();
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

}
