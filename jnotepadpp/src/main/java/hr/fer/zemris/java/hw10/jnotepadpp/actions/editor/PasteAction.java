package hr.fer.zemris.java.hw10.jnotepadpp.actions.editor;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.text.DefaultEditorKit;

/**
 * PasteAction extends {@link LocalizableAction} for pasting text.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class PasteAction extends LocalizableAction {
    
    /** Serial. */
    private static final long serialVersionUID = 7442465143032442148L;

    /**
     * Constructor for PasteAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     */
    public PasteAction(String key, ILocalizationProvider lp) {
        super(key, lp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        Action paste = new DefaultEditorKit.PasteAction();
        paste.actionPerformed(arg0);
    }

}
