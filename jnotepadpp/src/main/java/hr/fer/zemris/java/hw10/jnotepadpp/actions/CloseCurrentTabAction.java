package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.TabComponent;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

/**
 * CloseCurrentTabAction implements {@link LocalizableAction} for current tab.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class CloseCurrentTabAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 3764925201728643044L;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;

    /**
     * Constructor for CloseCurrentTabAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     * @param observer
     *            Main JNotepadPP frame.
     */
    public CloseCurrentTabAction(String key, ILocalizationProvider lp,
            JNotepadPP observer) {
        super(key, lp);
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = observer.getEditors().indexOf(observer.getActiveEditor());
        ((TabComponent) observer.getTabs().getTabComponentAt(index))
                .getCloseButton().doClick();
    }
}
