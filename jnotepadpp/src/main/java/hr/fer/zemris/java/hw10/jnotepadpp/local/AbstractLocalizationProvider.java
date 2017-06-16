/**
 * 
 */
package hr.fer.zemris.java.hw10.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractLocalizationProvider implements {@link ILocalizationProvider} class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public abstract class AbstractLocalizationProvider implements
        ILocalizationProvider {

    /** Localization listeners. */
    private List<ILocalizationListener> listeners = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    /**
     * Notify all listeners for localization change.
     */
    public void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }
}
