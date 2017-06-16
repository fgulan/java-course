package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * JVDLabel class extends {@link JLabel} class. It holds and displays currently
 * selected foreground and background colors in {@link JColorArea} objects in
 * frame.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JVDLabel extends JLabel implements ColorChangeListener {

    /** Serial number. */
    private static final long serialVersionUID = 5258691211060874973L;
    /** Foreground color. */
    private Color foreground;
    /** Background color. */
    private Color background;
    /** Foreground color provider. */
    private IColorProvider fgProvider;
    /** Background color provider. */
    private IColorProvider bgProvider;

    /**
     * Constructor for JVDLabel class. It creates new JVDLabel instance with
     * given parameters.
     * 
     * @param foreground
     *            Foreground color provider.
     * @param background
     *            Background color provider.
     */
    public JVDLabel(IColorProvider foreground, IColorProvider background) {
        this.fgProvider = foreground;
        this.bgProvider = background;
        this.foreground = foreground.getCurrentColor();
        this.background = background.getCurrentColor();
        this.setText();
    }

    /**
     * Sets a new JVDLabel text.
     */
    private void setText() {
        String text = String.format(
                "Foreground color: (%d, %d, %d), background color (%d, %d,%d)",
                foreground.getRed(), foreground.getGreen(),
                foreground.getBlue(), background.getRed(),
                background.getGreen(), background.getBlue());
        super.setText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newColorSelected(IColorProvider source, Color oldColor,
            Color newColor) {
        if (source == fgProvider && foreground == oldColor) {
            foreground = newColor;
        }
        if (source == bgProvider && background == oldColor) {
            background = newColor;
        }
        setText();
    }
}
