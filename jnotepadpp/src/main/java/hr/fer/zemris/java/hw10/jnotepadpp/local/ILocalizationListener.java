package hr.fer.zemris.java.hw10.jnotepadpp.local;

/**
 * ILocalizationListener represents observer for localization change.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface ILocalizationListener {
    /**
     * Notify observers that there was a change.
     */
    public void localizationChanged();
}
