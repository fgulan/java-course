package hr.fer.zemris.java.hw10.jnotepadpp.actions.editor;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

/**
 * UniqueLinesAction extends {@link LocalizableAction} for removing duplicate
 * lines from text.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class UniqueLinesAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = -4303868259824694907L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for UniqueLinesAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public UniqueLinesAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea currentEditor = observer.getActiveEditor().getEditor();
        Document document = currentEditor.getDocument();
        Caret caret = currentEditor.getCaret();

        int startCaret = Math.min(caret.getDot(), caret.getMark());
        int endCaret = Math.max(caret.getDot(), caret.getMark());

        try {

            int startLine = currentEditor.getLineOfOffset(startCaret);
            int endLine = currentEditor.getLineOfOffset(endCaret);
            int fromPos = currentEditor.getLineStartOffset(startLine);
            int length = currentEditor.getLineEndOffset(endLine) - fromPos + 1;

            String text = currentEditor.getText(fromPos, length);
            text = removeLines(text);
            document.remove(fromPos, length - 1);
            document.insertString(fromPos, text, null);
            caret.setDot(fromPos);
            caret.moveDot(length - 1);
            currentEditor.requestFocus();
        } catch (BadLocationException e1) {
            observer.errorMessage(e1);
        }
    }

    /**
     * Removes duplicate lines from given text.
     * 
     * @param text
     *            Input text.
     * @return Text without two same lines.
     */
    private String removeLines(String text) {
        String[] lines = text.split("\\n");
        Set<String> linesSet = new LinkedHashSet<String>(Arrays.asList(lines));
        int length = text.length();
        StringBuilder builder = new StringBuilder(length + 1);
        for (String line : linesSet) {
            builder.append(line);
            builder.append('\n');
        }
        builder.setLength(length - 1);
        return builder.toString();
    }

}
