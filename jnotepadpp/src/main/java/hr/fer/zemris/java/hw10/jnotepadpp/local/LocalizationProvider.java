package hr.fer.zemris.java.hw10.jnotepadpp.local;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

    /** Instance of localization provider. */
    private static LocalizationProvider instance = new LocalizationProvider();
    /** Current language. */
    private String language;
    /** Localization bundle. */
    private ResourceBundle bundle;

    /**
     * Private constructor for LocalizationProvider class. Sets English language
     * as default.
     */
    private LocalizationProvider() {
        setLanguage("en");
    }

    /**
     * Get localization provider instance.
     * 
     * @return Localization provider instance.
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /**
     * Sets new localization language. Currently supported are Croatian ("hr"),
     * English ("en") and German ("de").
     * 
     * @param language
     *            New language.
     */
    public void setLanguage(String language) {
        this.language = language;
        Locale locale = Locale.forLanguageTag(this.language);
        bundle = ResourceBundle.getBundle(
                "hr.fer.zemris.java.hw10.jnotepadpp.local.translation", locale);

        fire();
    }

    /**
     * Get current active language.
     * 
     * @return Current active language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String key) {
        try {
            String value = bundle.getString(key);
            return new String(value.getBytes(StandardCharsets.ISO_8859_1),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            return key;
        }
    }

}
