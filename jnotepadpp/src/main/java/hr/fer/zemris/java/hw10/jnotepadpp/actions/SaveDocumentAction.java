package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPDocument;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

/**
 * SaveDocumentAction extends {@link LocalizableAction} for saving current
 * document.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class SaveDocumentAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 2198525296519509134L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for SaveDocumentAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public SaveDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JNotepadPPDocument currentEditor = observer.getActiveEditor();
        if (currentEditor.isChanged() || !currentEditor.isSaved()) {
            currentEditor.saveDocument(false);
            observer.updateTabTitle(currentEditor);
            observer.updateFrameTitle();
        }
    }

}
