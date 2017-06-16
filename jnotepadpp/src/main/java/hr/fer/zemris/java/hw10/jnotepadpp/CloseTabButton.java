package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.TabButtonAction;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

/**
 * CloseTabButton extends {@link JButton} class. CloseTabButton represents close
 * button for each tab in {@link JTabbedPane} component.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class CloseTabButton extends JButton {
    /** Serial. */
    private static final long serialVersionUID = 1L;
    /** Button size. */
    private static final int SIZE = 16;

    /**
     * Constructor for CloseTabButton class.
     * 
     * @param tab
     *            Current tab component.
     */
    public CloseTabButton(TabComponent tab) {
        setPreferredSize(new Dimension(SIZE, SIZE));

        setIcon(new ImageIcon(this.getClass().getResource("res/tab_uns.png")));
        setRolloverIcon(new ImageIcon(this.getClass().getResource(
                "res/tab_hoover.png")));
        setPressedIcon(new ImageIcon(this.getClass().getResource(
                "res/tab_press.png")));

        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);

        setRolloverEnabled(true);

        Action closeTab = new TabButtonAction(tab.getLp().getString(
                "closeTabButton"), tab.getLp(), tab.getObserver(), tab);
        addActionListener(closeTab);
    }
}
