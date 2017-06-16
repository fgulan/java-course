package hr.fer.zemris.java.hw10.jnotepadpp.local;

/**
 * LocalizationProviderBridge extends {@link AbstractLocalizationProvider}
 * class. It represents connection between localization listener and
 * localization provider.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    /** Connection status. */
    private boolean connected;
    /** Localization provider. */
    private ILocalizationProvider parent;

    /** Localization listener. */
    private ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
            fire();
        }
    };

    /**
     * Constructor for LocalizationProviderBridge class.
     * 
     * @param parent
     *            Current localization provider.
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        this.parent = parent;
        connect();
    }

    /**
     * Connect current localization listener to localization provider.
     */
    public void connect() {
        if (!connected) {
            this.connected = true;
            this.parent.addLocalizationListener(listener);
        }
    }

    /**
     * Disconnect current localization listener from localization provider.
     */
    public void disconnect() {
        this.connected = false;
        this.parent.removeLocalizationListener(listener);
    }

    @Override
    public String getString(String key) {
        if (this.connected) {
            return parent.getString(key);
        } else {
            return key;
        }
    }

}
