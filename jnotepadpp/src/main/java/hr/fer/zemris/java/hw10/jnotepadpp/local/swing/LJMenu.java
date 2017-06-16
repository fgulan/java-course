package hr.fer.zemris.java.hw10.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;

import javax.swing.JMenu;

/**
 * LJMenu extends {@link JMenu} class. It represents localizable menu.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LJMenu extends JMenu {

    /** Serial. */
    private static final long serialVersionUID = 2421572668765947855L;
    /** Localization provider. */
    private ILocalizationProvider provider;
    /** Localization key. */
    private String key;

    /** Localization listener. */
    ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
            updateText();
        }
    };

    /**
     * Constructor for LJMenu class.
     * 
     * @param provider
     *            Localization provider.
     * @param key
     *            Localization key.
     */
    public LJMenu(ILocalizationProvider provider, String key) {
        this.provider = provider;
        this.key = key;
        this.provider.addLocalizationListener(listener);
        updateText();
    }

    /**
     * Update menu text.
     */
    private void updateText() {
        this.setText(this.provider.getString(key));
    }

}
