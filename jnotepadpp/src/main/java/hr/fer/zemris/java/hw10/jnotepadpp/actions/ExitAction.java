package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

/**
 * ExitAction extends {@link LocalizableAction} for exiting from JNotepadPP
 * application.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class ExitAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 3764925201728643044L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for ExitAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public ExitAction(String key, ILocalizationProvider lp, JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        observer.saveAllDocuments();
        observer.getStatusBar().getTimer().stop();
        observer.dispose();
    }
}
