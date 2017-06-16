package hr.fer.zemris.java.hw10.jnotepadpp.actions.editor;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

/**
 * SortLinesAscendingAction extends {@link LocalizableAction} for sorting text
 * lines ascending.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class SortLinesAscendingAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 351733795816948527L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for SortLinesAscendingAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public SortLinesAscendingAction(String key, ILocalizationProvider lp,
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
            int length = currentEditor.getLineEndOffset(endLine) - fromPos;

            String text = currentEditor.getText(fromPos, length);
            text = sortLinesAsc(text);
            document.remove(fromPos, length);
            document.insertString(fromPos, text, null);
            caret.setDot(fromPos);
            caret.moveDot(fromPos + length);
            currentEditor.requestFocus();
        } catch (BadLocationException e1) {
            observer.errorMessage(e1);
        }

    }

    /**
     * Sorts lines in given text ascending.
     * 
     * @param text
     *            Input text.
     * @return Text with sorted lines.
     */
    private String sortLinesAsc(String text) {
        String[] lines = text.split("\\n");

        Arrays.sort(lines, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String language = LocalizationProvider.getInstance()
                        .getLanguage();
                Locale locale = new Locale(language);
                Collator collator = Collator.getInstance(locale);
                int r = collator.compare(o1, o2);
                return r;
            }
        });

        StringBuilder builder = new StringBuilder(text.length());
        for (int i = 0; i < lines.length; ++i) {
            builder.append(lines[i]);
            builder.append("\n");
        }

        return builder.toString();
    }
}
