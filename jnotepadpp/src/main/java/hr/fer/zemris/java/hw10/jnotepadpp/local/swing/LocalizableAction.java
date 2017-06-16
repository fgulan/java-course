package hr.fer.zemris.java.hw10.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * LocalizableAction extends {@link AbstractAction} class. It represents
 * localizable action.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public abstract class LocalizableAction extends AbstractAction {

    /** Serial. */
    private static final long serialVersionUID = 4061418654314895040L;
    /** Localization key. */
    private String key;
    /** Localization provider. */
    private ILocalizationProvider provider;
    /** Localization listener. */
    private ILocalizationListener listener = new ILocalizationListener() {

        @Override
        public void localizationChanged() {
            putValue(Action.NAME, provider.getString(key));
            putValue(Action.SHORT_DESCRIPTION,
                    provider.getString(key + "Description"));
        }
    };

    /**
     * Constructor for LocalizableAction class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.key = key;
        this.provider = lp;
        this.provider.addLocalizationListener(listener);
        putValue(Action.NAME, provider.getString(key));
        putValue(Action.SHORT_DESCRIPTION,
                provider.getString(key + "Description"));
    }
}
