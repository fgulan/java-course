package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program BarChartDemo demonstrates use of BarChartComponentClass. Program
 * accepts one command line argument, a path to a textual file.
 * <p>
 * Textual file must have six text lines. First line represents description for
 * x-axis, second line represents y-axis description. Third line is consisted of
 * data for bars in form "1,25 2,30" where data for each bar is split with
 * space, and x value and y value are split with ",". Fourth line is first point
 * on y-axis, fifth line is last point on y-axis and last line represents
 * distance between two values on y-axis.
 * 
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class BarChartDemo extends JFrame {

    /** Serial. */
    private static final long serialVersionUID = 5151434034409506855L;
    /** Path to a file. */
    private String pathString;

    /**
     * Constructor for BarChartDemo class.
     * 
     * @param pathString
     *            Path to a file.
     */
    public BarChartDemo(String pathString) {
        this.pathString = pathString;
        setLocation(30, 50);
        setBackground(Color.RED);

        pack();
        setTitle("Graph");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Initializes GUI.
     */
    private void initGUI() {
        ParserUtilty parser = null;
        Path path = null;
        try {
            path = Paths.get(pathString);
            parser = new ParserUtilty(path);
        } catch (Exception e) {
            System.out.println("Unable to parse given file! " + e.getMessage());
            System.exit(-1);
        }

        BarChart model = new BarChart(parser.getValues(),
                parser.getxDescription(), parser.getyDescription(),
                parser.getyStart(), parser.getyEnd(), parser.getStep());

        BarChartComponent komponenta = new BarChartComponent(model);

        JLabel label = new JLabel(path.toAbsolutePath().toString());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(label, BorderLayout.PAGE_START);
        getContentPane().add(komponenta, BorderLayout.CENTER);
        getContentPane().setBackground(Color.WHITE);
        pack();
    }

    /**
     * Start point of program BarChartDemo.
     * 
     * @param args
     *            Command line arguments. Path to a file.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out
                    .println("Invalid number of arguments! Please provide path to a file.");
            System.exit(-1);
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BarChartDemo(args[0]);
            frame.pack();
            frame.setVisible(true);
        });
    }

}
