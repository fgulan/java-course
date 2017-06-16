package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;

/**
 * Represents a listener object which listens on @ JColorArea}, as color
 * provider, for color change.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public interface ColorChangeListener {
    /**
     * Notify all registered listeners on color change.
     * 
     * @param source
     *            Color change provider.
     * @param oldColor
     *            Old color.
     * @param newColor
     *            New selected color.
     */
    public void newColorSelected(IColorProvider source, Color oldColor,
            Color newColor);
}