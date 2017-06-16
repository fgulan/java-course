package hr.fer.zemris.java.gui.charts;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser utility used for parsing lines from given input file. Result is used
 * for drawing a {@link BarChartComponent}.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ParserUtilty {

    /** Number of lines to read. */
    private static final int NUMBER_OF_LINES = 6;
    /** Read lines. */
    private String[] lines;

    /** Description for x-axis. */
    private String xDescription;
    /** Description for y-axis. */
    private String yDescription;

    /** Start point on y-axis. */
    private int yStart;
    /** End point on y-axis. */
    private int yEnd;
    /** Distance between two points on y-axis. */
    private int step;

    /** List of values. */
    List<XYValue> values;

    /**
     * Constructor for ParserUtilty class. It accepts path to a file to parse
     * 
     * @param filePath
     *            Path to a file to parse.
     * @throws IOException
     *             On error with reading given file.
     */
    public ParserUtilty(Path filePath) throws IOException {
        lines = new String[NUMBER_OF_LINES];

        parseFile(filePath);
    }

    /**
     * Parse given file.
     * 
     * @param filePath
     *            Path to a file to parse.
     * @throws IOException
     *             On error with reading given file.
     */
    private void parseFile(Path filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath,
                StandardCharsets.UTF_8)) {
            for (int i = 0; i < NUMBER_OF_LINES; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IllegalArgumentException(
                            "Missing line! Please check given file");
                }
                lines[i] = line;
            }
            parseDescription();
            parseData();
            parseLimits();
        }
    }

    /**
     * Parse limits for y-axis.
     */
    private void parseLimits() {
        try {
            yStart = Integer.parseInt(lines[3]);
            yEnd = Integer.parseInt(lines[4]);
            step = Integer.parseInt(lines[5]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numbers in file!");
        }

    }

    /**
     * Parse values into a list of {@link XYValue }.
     */
    private void parseData() {
        String[] data = lines[2].trim().split("\\s");
        int size = data.length;
        values = new ArrayList<XYValue>(5);

        for (int i = 0; i < size; i++) {
            String[] points = data[i].split(",");
            if (points.length != 2) {
                throw new IllegalArgumentException("Invalid bar arguments!");
            }
            try {
                int x = Integer.parseInt(points[0]);
                int y = Integer.parseInt(points[1]);
                values.add(new XYValue(x, y));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid numbers in file!");
            }
        }
    }

    /**
     * Parse description for axis.
     */
    private void parseDescription() {
        xDescription = lines[0];
        yDescription = lines[1];
    }

    /**
     * Returns description for x-axis.
     * 
     * @return Description for x-axis.
     */
    public String getxDescription() {
        return xDescription;
    }

    /**
     * Returns description for y-axis.
     * 
     * @return Description for y-axis.
     */
    public String getyDescription() {
        return yDescription;
    }

    /**
     * Returns first number on y-axis.
     * 
     * @return First number on y-axis.
     */
    public int getyStart() {
        return yStart;
    }

    /**
     * Returns last number y-axis.
     * 
     * @return Last number y-axis.
     */
    public int getyEnd() {
        return yEnd;
    }

    /**
     * Returns distance between two points on y-axis.
     * 
     * @return Distance between two points on y-axis.
     */
    public int getStep() {
        return step;
    }

    /**
     * Returns list of {@link XYValue} values.
     * 
     * @return List of {@link XYValue} values.
     */
    public List<XYValue> getValues() {
        return values;
    }

}
