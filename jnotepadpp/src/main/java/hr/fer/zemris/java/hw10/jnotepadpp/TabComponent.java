package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * TabComponent extends {@link JPanel} class. TabCompnent is used as
 * tabComponent. Contains icon to show if document is changed, {@link JLabel} to
 * show document title and {@link CloseTabButton} to close the tab it belongs
 * to.
 *
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class TabComponent extends JPanel {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    /** JLabel containing icon. */
    private JLabel picLabel;
    /** Main JNotepadPP frame. */
    private JNotepadPP observer;
    /** Localization provider. */
    private ILocalizationProvider lp;
    /** CloseTabButton instance. */
    private JButton closeButton;

    /**
     * Constructor for TabComponent class.
     * 
     * @param pane
     *            {@link JTabbedPane} parent component.
     * @param observer
     *            Main JNotepadPP frame.
     * @param lp
     *            Localization provider.
     */
    public TabComponent(JTabbedPane pane, JNotepadPP observer,
            ILocalizationProvider lp) {

        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.lp = lp;
        this.observer = observer;
        setOpaque(false);

        JLabel label = new JLabel() {
            private static final long serialVersionUID = 1L;

            public String getText() {
                int i = pane.indexOfTabComponent(TabComponent.this);
                if (i != -1) {
                    this.revalidate();
                    return pane.getTitleAt(i);

                }
                return "";
            }
        };

        ImageIcon image = new ImageIcon(this.getClass().getResource(
                "res/green.png"));
        picLabel = new JLabel(image);
        add(picLabel);
        add(label);

        picLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        closeButton = new CloseTabButton(this);
        add(closeButton);

        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    /**
     * Update tab icon on document change.
     */
    public void updateTabIcon() {
        if (observer.getActiveEditor().isChanged()) {
            ImageIcon image = new ImageIcon(this.getClass().getResource(
                    "res/red.png"));
            picLabel.setIcon(image);
        } else {
            ImageIcon image = new ImageIcon(this.getClass().getResource(
                    "res/green.png"));
            picLabel.setIcon(image);
        }
    }

    /**
     * Get current JNotepadPP frame.
     * 
     * @return Current JNotepadPP frame.
     */
    public JNotepadPP getObserver() {
        return observer;
    }

    /**
     * Get current localization provider.
     * 
     * @return Current localization provider.
     */
    public ILocalizationProvider getLp() {
        return lp;
    }

    /**
     * Get tab close button.
     * @return Tab close button.
     */
    public JButton getCloseButton() {
        return closeButton;
    }

}