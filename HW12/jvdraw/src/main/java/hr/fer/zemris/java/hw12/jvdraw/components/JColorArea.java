package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * JColorArea represents a color chooser component. It extends
 * {@link JComponent} class and implements {@link IColorProvider} interface. On
 * mouse click it open {@link JColorChooser} component and notify all registered
 * listeners for color change.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

    /** Serial number. */
    private static final long serialVersionUID = 5242892168183711170L;
    /** Component size. */
    private static final int SIZE = 15;
    /** Preferred dimension. */
    private static final Dimension PREFERRED_SIZE = new Dimension(SIZE, SIZE);
    /** Color change listeners. */
    private List<ColorChangeListener> listeners;
    /** Selected color. */
    private Color selectedColor;
    /** Mouse listener. */
    private MouseAdapter mouseListener = new MouseAdapter() {
        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            Color newColor = JColorChooser.showDialog(JColorArea.this,
                    "Chose color", Color.BLUE);
            if (newColor != null) {
                Color oldColor = selectedColor;
                selectedColor = newColor;
                repaint();
                notifyListeners(oldColor, newColor);
            }
        };
    };

    /**
     * Constructor for JColorArea class. Creates a new JColorArea component with
     * given color.
     * 
     * @param selectedColor
     *            Default selected color.
     */
    public JColorArea(Color selectedColor) {
        this.selectedColor = selectedColor;
        listeners = new ArrayList<>();
        addMouseListener(mouseListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(selectedColor);
        g.fillRect(getInsets().left, getInsets().top, SIZE, SIZE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    /**
     * Sets a new selected color.
     * 
     * @param color
     *            New color.
     */
    public void setCurrentColor(Color color) {
        selectedColor = color;
    }

    /**
     * Adds a new color change listener.
     * 
     * @param l
     *            {@link ColorChangeListener} object.
     */
    public void addColorChangeListener(ColorChangeListener l) {
        if (l == null) {
            throw new IllegalArgumentException(
                    "Listener cannot be a null reference.");
        }
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    /**
     * Removes given {@link ColorChangeListener} object from listeners list.
     * 
     * @param l
     *            {@link ColorChangeListener} object.
     */
    public void removeColorChangeListener(ColorChangeListener l) {
        listeners.remove(l);
    }

    /**
     * Notify all registered listeners on color change.
     * 
     * @param oldColor
     *            Old selected color.
     * @param newColor
     *            New selected color.
     */
    public void notifyListeners(Color oldColor, Color newColor) {
        for (ColorChangeListener colorListener : listeners) {
            colorListener.newColorSelected(this, oldColor, newColor);
        }
    }
}
