package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;
import hr.fer.zemris.java.hw12.jvdraw.objects.RectBounds;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FileFactory class implements a number of static methods used for opening and
 * saving files in {@link JVDraw} program.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class FIleFactory {

    /**
     * Opens a .jvd file, parse it and fill given drawing model with elements
     * from opened file.
     * 
     * @param model
     *            Drawing model.
     * @param frame
     *            Parent frame.
     * @return Opened file.
     */
    public static File openJVDFile(DrawingModel model, JVDraw frame) {
        JFileChooser fc = new JFileChooser("./");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".jvd", "jvd"));
        int status = fc.showOpenDialog(frame);
        if (status == JFileChooser.OPEN_DIALOG) {
            File file = fc.getSelectedFile();
            if (!Files.isRegularFile(file.toPath())) {
                return null;
            }
            model.clear();
            readJVDFile(file, model);
            return file;
        }
        return null;
    }

    /**
     * Reads given file and fills drawing model with parsed objects from file.
     * 
     * @param file
     *            .jvd file to read.
     * @param model
     *            Drawing model.
     */
    private static void readJVDFile(File file, DrawingModel model) {
        List<String> shapes = readLines(file);
        if (shapes == null) {
            return;
        }
        int id = 1;
        for (String shape : shapes) {
            GeometricalObject object = getObject(shape, id);
            if (object == null) {
                continue;
            }
            id++;
            model.add(object);
        }

    }

    /**
     * Parses line from .jvd file.
     * 
     * @param line
     *            Input line.
     * @param id
     *            Current shape id.
     * @return Created object.
     */
    private static GeometricalObject getObject(String line, int id) {
        String[] shape = line.split("\\s+");
        GeometricalObject object = null;
        try {
            if (shape[0].equals("LINE")) {
                object = new Line("Line " + id++, new Point(
                        Integer.parseInt(shape[1].trim()),
                        Integer.parseInt(shape[2].trim())), new Point(
                        Integer.parseInt(shape[3].trim()),
                        Integer.parseInt(shape[4].trim())), new Color(
                        Integer.parseInt(shape[5].trim()),
                        Integer.parseInt(shape[6].trim()),
                        Integer.parseInt(shape[7].trim())));

            } else if (shape[0].equals("CIRCLE")) {
                object = new Circle("Circle " + id++, new Point(
                        Integer.parseInt(shape[1].trim()),
                        Integer.parseInt(shape[2].trim())),
                        Integer.parseInt(shape[3].trim()), new Color(
                                Integer.parseInt(shape[4].trim()),
                                Integer.parseInt(shape[5].trim()),
                                Integer.parseInt(shape[6].trim())));
            } else if (shape[0].equals("FCIRCLE")) {
                object = new FilledCircle("Circle " + id++, new Point(
                        Integer.parseInt(shape[1].trim()),
                        Integer.parseInt(shape[2].trim())),
                        Integer.parseInt(shape[3].trim()), new Color(
                                Integer.parseInt(shape[4].trim()),
                                Integer.parseInt(shape[5].trim()),
                                Integer.parseInt(shape[6].trim())), new Color(
                                Integer.parseInt(shape[7].trim()),
                                Integer.parseInt(shape[8].trim()),
                                Integer.parseInt(shape[9].trim())));
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return object;
    }

    /**
     * Reads all lines from given file into a list of strings.
     * 
     * @param file
     *            Input file.
     * @return List of lines.
     */
    private static List<String> readLines(File file) {
        List<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            while (line != null && !line.isEmpty()) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            return null;
        }
        return lines;
    }

    /**
     * Saves given drawing model into given file.
     * 
     * @param model
     *            Input drawing model.
     * @param file
     *            Output .jvd file.
     */
    public static void saveCanvas(DrawingModel model, File file) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new BufferedOutputStream(new FileOutputStream(file)),
                StandardCharsets.UTF_8))) {
            for (int i = 0, size = model.getSize(); i < size; i++) {
                writer.write(model.getObject(i).getDescription());
            }
        } catch (IOException e) {

        }
    }

    /**
     * Opens a file from disk.
     * 
     * @param frame
     *            Parent frame.
     * @return Opened file.
     */
    public static File getFile(JVDraw frame) {
        JFileChooser fc = new JFileChooser("./");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".jvd", "jvd"));
        int status = fc.showSaveDialog(frame);

        if (status == JFileChooser.OPEN_DIALOG) {
            return setExtension(fc.getSelectedFile(), ".jvd");
        }
        return null;
    }

    /**
     * Exports given model from given canvas into image file (png, jpg or gif).
     * 
     * @param model
     *            Drawing model.
     * @param canvas
     *            Drawing canvas.
     */
    public static void exportCanvas(DrawingModel model, JDrawingCanvas canvas) {
        RectBounds bounds = new RectBounds(canvas.getWidth(),
                canvas.getHeight(), 0, 0);

        for (int i = 0, size = model.getSize(); i < size; i++) {
            model.getObject(i).setBounds(bounds);
        }

        JFileChooser fc = new JFileChooser("./");

        FileNameExtensionFilter png = new FileNameExtensionFilter(
                "PNG (*.png)", ".png");
        FileNameExtensionFilter gif = new FileNameExtensionFilter(
                "GIF (*.gif)", ".gif");
        FileNameExtensionFilter jpg = new FileNameExtensionFilter(
                "JPG (*.jpg)", ".jpg");

        fc.addChoosableFileFilter(png);
        fc.addChoosableFileFilter(gif);
        fc.addChoosableFileFilter(jpg);
        fc.setFileFilter(png);
        int status = fc.showSaveDialog(null);

        if (status != JFileChooser.OPEN_DIALOG) {
            return;
        }
        File file = fc.getSelectedFile();

        int width = Math.abs(bounds.getMaxX() - bounds.getMinX());
        int height = Math.abs(bounds.getMaxY() - bounds.getMinY());
        BufferedImage image = null;
        try {
            image = new BufferedImage(width, height,
                    BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);

            for (int i = 0; i < model.getSize(); i++) {
                model.getObject(i).draw(g, bounds);
            }
            g.dispose();

            if (fc.getFileFilter().equals(png)) {
                file = setExtension(file, ".png");
                ImageIO.write(image, "png", file);
            } else if (fc.getFileFilter().equals(gif)) {
                file = setExtension(file, ".gif");
                ImageIO.write(image, "gif", file);
            } else if (fc.getFileFilter().equals(jpg)) {
                file = setExtension(file, ".jpg");
                ImageIO.write(image, "jpg", file);
            }
        } catch (Exception e) {
            return;
        }

    }

    /**
     * Sets given extension to a given file.
     * 
     * @param file
     *            Input file
     * @param extension
     *            Input extension.
     * @return File with given extension.
     */
    private static File setExtension(File file, String extension) {
        if (file.getAbsoluteFile().toString().toLowerCase().endsWith(extension)) {
            return file;
        }
        return new File(file.getAbsolutePath() + extension);
    }

}
