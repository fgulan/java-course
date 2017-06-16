package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPDocument;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * CloseTabAction extends {@link LocalizableAction} for opening document in a
 * new tab.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class OpenDocumentAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = -2823283447664046160L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;
    /** Frame localization provider. */
    private ILocalizationProvider lp;

    /**
     * Constructor for OpenDocumentAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public OpenDocumentAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
        this.lp = lp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open file");
        if (fc.showOpenDialog(observer) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        Path file = fc.getSelectedFile().toPath();
        if (!Files.isReadable(file)) {
            JOptionPane.showMessageDialog(observer,
                    lp.getString("readingError"), lp.getString("errorTitle"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JNotepadPPDocument openedDocument;
        try {
            openedDocument = new JNotepadPPDocument(observer, file);
        } catch (IOException e1) {
            observer.errorMessage(e1);
            return;
        }
        int index = observer.getEditors().indexOf(openedDocument);
        if (index > -1) {
            observer.getTabs().setSelectedIndex(index);
            return;
        }
        observer.addNewTab(openedDocument);
    }
}
