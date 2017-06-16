package hr.fer.zemris.java.hw10.jnotepadpp.actions.localization;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

/**
 * DeLanguageAction extends {@link LocalizableAction} for changing JNotepadPP
 * language to German.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class DeLanguageAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 2797111436791255843L;

    /**
     * Constructor for DeLanguageAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     */
    public DeLanguageAction(String key, ILocalizationProvider lp) {
        super(key, lp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        LocalizationProvider.getInstance().setLanguage("de");
    }

}
