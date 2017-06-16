package hr.fer.zemris.java.hw10.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * FormLocalizationProvider extends {@link LocalizationProviderBridge} class.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Constructor for FormLocalizationProvider class
     * 
     * @param parent
     *            Localization provider.
     * @param frame
     *            Frame as observer.
     */
    public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
        super(parent);
        frame.addWindowListener(listener);
    }

    /** Window listener. */
    private WindowAdapter listener = new WindowAdapter() {
        /**
         * {@inheritDoc}
         */
        @Override
        public void windowClosed(WindowEvent e) {
            FormLocalizationProvider.this.disconnect();
        };

        /**
         * {@inheritDoc}
         */
        @Override
        public void windowOpened(WindowEvent e) {
            FormLocalizationProvider.this.connect();
        };
    };

}
