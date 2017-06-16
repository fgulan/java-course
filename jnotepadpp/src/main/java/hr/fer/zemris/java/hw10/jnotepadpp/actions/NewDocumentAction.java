package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPDocument;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * NewDocumentAction extends {@link LocalizableAction} for creating a new
 * document in a new tab.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class NewDocumentAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = -352436840454962184L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for NewDocumentAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public NewDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);

        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            observer.addNewTab(new JNotepadPPDocument(observer));
        } catch (IOException e) {
            observer.errorMessage(e);
        }
    }

}
