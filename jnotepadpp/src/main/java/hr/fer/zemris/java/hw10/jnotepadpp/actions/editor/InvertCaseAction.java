package hr.fer.zemris.java.hw10.jnotepadpp.actions.editor;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * InvertCaseAction extends {@link LocalizableAction} for reversing
 * characters case.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class InvertCaseAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 5289605289022137284L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for InvertCaseAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public InvertCaseAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        JTextArea currentEditor = observer.getActiveEditor().getEditor();
        Document doc = currentEditor.getDocument();
        int len = Math.abs(currentEditor.getCaret().getDot()
                - currentEditor.getCaret().getMark());
        int offset = Math.min(currentEditor.getCaret().getDot(), currentEditor
                .getCaret().getMark());

        try {
            String text = doc.getText(offset, len);
            text = toggleCase(text);
            doc.remove(offset, len);
            doc.insertString(offset, text, null);
            currentEditor.getCaret().setDot(offset);
            currentEditor.getCaret().moveDot(offset + len);
            currentEditor.requestFocus();
        } catch (BadLocationException e1) {

        }
    }

    /**
     * Invert case in given text.
     * 
     * @param text
     *            Input text.
     * @return Text with inverted case.
     */
    private String toggleCase(String text) {
        char[] symbols = text.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            char ch = symbols[i];
            if (Character.isLowerCase(ch)) {
                symbols[i] = Character.toUpperCase(ch);
            } else if (Character.isUpperCase(ch)) {
                symbols[i] = Character.toLowerCase(ch);
            }
        }
        return new String(symbols);
    }

}
