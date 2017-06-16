package hr.fer.zemris.java.hw12.jvdraw.panels;

import hr.fer.zemris.java.hw12.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * {@link JPanel} factory class using singleton pattern and lazy implementation.
 * It creates a update panel for a line object.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LinePanel {
    /** A update panel. */
    private static JPanel linePanel;
    /** Line start point. */
    private static JTextField startPoint;
    /** Line end point. */
    private static JTextField endPoint;
    /** Line color. */
    private static JColorArea fgColor;

    /**
     * Constructor for LinePanel class.
     */
    protected LinePanel() {

    }

    /**
     * Creates and returns a new panel for changing and updating line object. It
     * uses a lazy implementation, so once created panel is used every new time
     * when method is called.
     * 
     * @param object
     *            {@link Line} object.
     * @return A new panel for updating a {@link Line} object.
     */
    public static JPanel getPanel(Line object) {
        if (linePanel == null) {
            createCirclePanel(object);
        }
        updateLinePanel(object);
        return linePanel;

    }

    /**
     * Updates created panel with new values.
     * 
     * @param line
     *            {@link Line} object.
     */
    private static void updateLinePanel(Line line) {
        startPoint.setText(line.getStart().x + ", " + line.getStart().y);
        endPoint.setText(line.getEnd().x + ", " + line.getEnd().y);
        fgColor.setCurrentColor(line.getColor());
    }

    /**
     * Creates a new panel for given {@link Line} object.
     * 
     * @param line
     *            {@link Line} object.
     */
    private static void createCirclePanel(Line line) {
        linePanel = new JPanel(new GridLayout(0, 2));
        startPoint = new JTextField(line.getStart().x + ", "
                + line.getStart().y);
        endPoint = new JTextField(line.getEnd().x + ", " + line.getEnd().y);
        fgColor = new JColorArea(line.getColor());

        linePanel.add(new JLabel("Start point: "));
        linePanel.add(startPoint);
        linePanel.add(new JLabel("End point: "));
        linePanel.add(endPoint);
        linePanel.add(new JLabel("FG Color: "));
        linePanel.add(fgColor);
    }

    /**
     * Returns a selected color from {@link JColorArea} object.
     * 
     * @return Selected color from {@link JColorArea} object.
     */
    public static Color getForeground() {
        if (fgColor == null) {
            return Color.WHITE;
        }
        return fgColor.getCurrentColor();
    }

    /**
     * Returns a new start point given in update panel.
     * 
     * @return New start point from update panel.
     */
    public static Point getStart() {
        String[] point = startPoint.getText().split(",");
        return new Point(Integer.parseInt(point[0].trim()),
                Integer.parseInt(point[1].trim()));
    }

    /**
     * Returns a new end point given in update panel.
     * 
     * @return New end point from update panel.
     */
    public static Point getEnd() {
        String[] point = endPoint.getText().split(",");
        return new Point(Integer.parseInt(point[0].trim()),
                Integer.parseInt(point[1].trim()));
    }
}
