package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPPDocument;
import hr.fer.zemris.java.hw10.jnotepadpp.TabComponent;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**
 * CloseTabAction extends {@link LocalizableAction} for closing current tab.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class TabButtonAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = -339236136967712180L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;
    /** Frame localization provider. */
    private ILocalizationProvider lp;
    /** Tab component. */
    private TabComponent tab;

    /**
     * Constructor for CloseTabAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     * @param tab
     *            Tab component.
     */
    public TabButtonAction(String key, ILocalizationProvider lp,
            JNotepadPP observer, TabComponent tab) {
        super(key, lp);
        this.lp = lp;
        this.observer = observer;
        this.tab = tab;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTabbedPane pane = observer.getTabs();
        int i = pane.indexOfTabComponent(tab);
        JNotepadPPDocument editor = observer.getEditors().get(i);

        List<JNotepadPPDocument> editors = observer.getEditors();

        if (pane.getComponentCount() == 4) {
            return;
        }
        if (editor.isChanged()) {
            int answer = JOptionPane.showConfirmDialog(observer,
                    lp.getString("saveMessage"), lp.getString("saveDialog"),
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                editor.saveDocument(false);
            } else if (answer == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        pane.remove(i);
        editors.remove(i);
        int size = editors.size();

        if (i >= size) {
            observer.setActiveEditor(editors.get(size - 1));
            pane.setSelectedIndex(size - 1);
        } else {
            observer.setActiveEditor(editors.get(i));
            pane.setSelectedIndex(i);
        }
        observer.getActiveEditor().updateStatusBar();
    }

}
