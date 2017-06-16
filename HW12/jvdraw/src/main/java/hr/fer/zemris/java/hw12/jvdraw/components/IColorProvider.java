package hr.fer.zemris.java.hw12.jvdraw.components;

import java.awt.Color;

/**
 * Represents an object which has ability to change color and notify all
 * registered listeners.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public interface IColorProvider {

    /**
     * Returns a current selected color in color provider interface.
     * 
     * @return Current selected color.
     */
    public Color getCurrentColor();
}
