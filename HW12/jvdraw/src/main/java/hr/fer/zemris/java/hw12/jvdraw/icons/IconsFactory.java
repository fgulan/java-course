package hr.fer.zemris.java.hw12.jvdraw.icons;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * IconsFactroy class holds all supported icons.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class IconsFactory {
    /** Circle icon. */
    public final Icon CIRCLE_ICON = new ImageIcon(getClass().getResource(
            "circle.png"));
    /** Filled circle icon. */
    public final Icon FCIRCLE_ICON = new ImageIcon(getClass().getResource(
            "fcircle.png"));
    /** Line icon. */
    public final Icon LINE_ICON = new ImageIcon(getClass().getResource(
            "line.png"));
}
