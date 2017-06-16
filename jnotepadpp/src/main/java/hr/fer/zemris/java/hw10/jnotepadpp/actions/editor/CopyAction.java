package hr.fer.zemris.java.hw10.jnotepadpp.actions.editor;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.local.swing.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.text.DefaultEditorKit;

/**
 * CopyAction extends {@link LocalizableAction} for copying text.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class CopyAction extends LocalizableAction {
    
    /** Serial. */
    private static final long serialVersionUID = 7662465149032402148L;

    /**
     * Constructor for CopyAction action class.
     * 
     * @param key
     *            Localization key.
     * @param lp
     *            Localization provider.
     */
    public CopyAction(String key, ILocalizationProvider lp) {
        super(key, lp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        Action copy = new DefaultEditorKit.CopyAction();
        copy.actionPerformed(arg0);
    }

}
