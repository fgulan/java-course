package hr.fer.zemris.java.hw12.jvdraw.panels;

import hr.fer.zemris.java.hw12.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * {@link JPanel} factory class using singleton pattern and lazy implementation.
 * It creates a update panel for a filled circle object.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class FilledCirclePanel {

    /** A update panel. */
    private static JPanel filledCPanel;
    /** Circle center point. */
    private static JTextField centerPoint;
    /** Circle radius value. */
    private static JTextField radiusText;
    /** Circle foreground color. */
    private static JColorArea fgColor;
    /** Circle background color. */
    private static JColorArea bgColor;

    /**
     * Constructor for FilledCirclePanel class.
     */
    protected FilledCirclePanel() {

    }

    /**
     * Creates and returns a new panel for changing and updating filled circle
     * object. It uses a lazy implementation, so once created panel is used
     * every new time when method is called.
     * 
     * @param object
     *            {@link FilledCircle} object.
     * @return A new panel for updating a {@link FilledCircle} object.
     */
    public static JPanel getPanel(FilledCircle object) {
        if (filledCPanel == null) {
            createFilledCPanel(object);
        }
        updateFilledCPanel(object);
        return filledCPanel;
    }

    /**
     * Updates created panel with new values.
     * 
     * @param circle
     *            {@link FilledCircle} object.
     */
    private static void updateFilledCPanel(FilledCircle circle) {
        centerPoint.setText(circle.getCenter().x + ", " + circle.getCenter().y);
        radiusText.setText(String.valueOf(circle.getRadius()));
        fgColor.setCurrentColor(circle.getColor());
        bgColor.setCurrentColor(circle.getBackground());
    }

    /**
     * Creates a new panel for given {@link FilledCircle} object.
     * 
     * @param circle
     *            {@link FilledCircle} object.
     */
    private static void createFilledCPanel(FilledCircle circle) {
        filledCPanel = new JPanel(new GridLayout(0, 2));
        centerPoint = new JTextField(circle.getCenter().x + ", "
                + circle.getCenter().y);
        radiusText = new JTextField(String.valueOf(circle.getRadius()));
        fgColor = new JColorArea(circle.getColor());
        bgColor = new JColorArea(circle.getBackground());

        filledCPanel.add(new JLabel("Center:"));
        filledCPanel.add(centerPoint);
        filledCPanel.add(new JLabel("Radius"));
        filledCPanel.add(radiusText);
        filledCPanel.add(new JLabel("FG Color:"));
        filledCPanel.add(fgColor);
        filledCPanel.add(new JLabel("BG Color:"));
        filledCPanel.add(bgColor);
    }

    /**
     * Returns a selected foreground color from {@link JColorArea} object.
     * 
     * @return Selected foreground color from {@link JColorArea} object.
     */
    public static Color getForeground() {
        if (fgColor == null) {
            return Color.WHITE;
        }
        return fgColor.getCurrentColor();
    }

    /**
     * Returns a selected background color from {@link JColorArea} object.
     * 
     * @return Selected background color from {@link JColorArea} object.
     */
    public static Color getBackground() {
        if (fgColor == null) {
            return Color.WHITE;
        }
        return bgColor.getCurrentColor();
    }

    /**
     * Returns a new center point given in update panel.
     * 
     * @return New center point from update panel.
     */
    public static Point getCenter() {
        String[] input = centerPoint.getText().split(",");
        Point center = new Point(Integer.parseInt(input[0].trim()),
                Integer.parseInt(input[1].trim()));
        return center;
    }

    /**
     * Returns a new circle radius given in update panel.
     * 
     * @return A new circle radius.
     */
    public static int getRadius() {
        int radius = Integer.parseInt(radiusText.getText().trim());
        return radius;
    }
}
