package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

/**
 * StatisticsAction extends {@link LocalizableAction} for counting characters
 * in editor.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class StatisticsAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 4132158656588919930L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;
    /** Frame localization provider. */
    private ILocalizationProvider lp;

    /**
     * Constructor for StatisticsAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public StatisticsAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
        this.lp = lp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        int characters = 0;
        int nonBlank = 0;
        int lines = 1;

        String text = observer.getActiveEditor().getEditor().getText();
        int size = text.length();
        for (int i = 0; i < size; i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                lines++;
            } else if (ch != '\t' && ch != ' ' && ch != '\r') {
                nonBlank++;
            }
            characters++;
        }
        String linesText = lp.getString("charactersLineNum");
        String charText = lp.getString("charactersNum");
        String nonBlankText = lp.getString("charactersBlankNum");

        String data = String.format(charText + ": %d%n" + nonBlankText
                + ": %d%n" + linesText + ": %d%n", characters, nonBlank, lines);

        JOptionPane.showMessageDialog(observer, data);
    }
}
