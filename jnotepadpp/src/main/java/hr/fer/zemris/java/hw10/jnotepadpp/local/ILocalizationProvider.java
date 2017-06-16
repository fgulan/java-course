package hr.fer.zemris.java.hw10.jnotepadpp.local;

/**
 * Represents objects which holds observers and notify them on localization
 * change.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public interface ILocalizationProvider {
    /**
     * Register new localization listener.
     * 
     * @param l
     *            Localization listener.
     */
    public void addLocalizationListener(ILocalizationListener l);

    /**
     * Remove registered localization listener.
     * 
     * @param l
     *            Localization listener.
     */
    public void removeLocalizationListener(ILocalizationListener l);

    /**
     * Get translation for given key.
     * 
     * @param key
     *            A key.
     * @return Text in current selected language.
     */
    public String getString(String key);
}
