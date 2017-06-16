package hr.fer.zemris.java.hw10.jnotepadpp.actions.editor;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.text.DefaultEditorKit;

/**
 * CutAction extends {@link LocalizableAction} for cutting text.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class CutAction extends LocalizableAction {

    /** Serial. */
    private static final long serialVersionUID = 7662465139032402148L;

    /**
     * Constructor for CopyAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     */
    public CutAction(String key, ILocalizationProvider lp) {
        super(key, lp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        Action cut = new DefaultEditorKit.CutAction();
        cut.actionPerformed(arg0);
    }

}
