package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.components.JVDLabel;
import hr.fer.zemris.java.hw12.jvdraw.icons.IconsFactory;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.shape.CircleShape;
import hr.fer.zemris.java.hw12.jvdraw.shape.FilledCircleShape;
import hr.fer.zemris.java.hw12.jvdraw.shape.LineShape;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * JVDraw is easy-to-use application for drawing circles, filled circles and
 * lines. Something similar to windows Paint application. It has ability to
 * export created drawings in png, jpg and gif image format or in its own
 * format, jvd. Drawings saved as jvd can be re-edited.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class JVDraw extends JFrame {

    /** Serial number. */
    private static final long serialVersionUID = 3899120805413223072L;

    /** Foreground color chooser. */
    private JColorArea foregroundColor;
    /** Background color chooser. */
    private JColorArea backgroundColor;

    /** Drawing canvas. */
    private JDrawingCanvas canvas;
    /** Drawing model. */
    private DrawingModelImpl model;
    /** JList of GeometricalObject objects on canvas. */
    private JList<GeometricalObject> objectList;
    /** JList list model. */
    private DrawingObjectListModel listModel;
    /** Scroll pane for JList. */
    private JScrollPane scrollPane;
    /** Current opened file. */
    private File currentFile;
    /** Loaded icons. */
    private IconsFactory icons = new IconsFactory();

    /**
     * Constructor for JVDraw class.
     */
    public JVDraw() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        initGUI();
    }

    /**
     * Initialize GUI.
     */
    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());
        createMenuBar();
        createToolbar();
        createCanvas();
        createList();
        addJVDLabel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (canvas.isChanged()) {
                    saveFile();
                }
                dispose();
            }
        });
        repaint();
    }

    /**
     * Adds a {@link JVDLabel} instance on bottom of the frame.
     */
    private void addJVDLabel() {
        JPanel panel = new JPanel();
        JVDLabel label = new JVDLabel(foregroundColor, backgroundColor);
        backgroundColor.addColorChangeListener(label);
        foregroundColor.addColorChangeListener(label);
        panel.add(label);

        getContentPane().add(panel, BorderLayout.PAGE_END);
    }

    /**
     * Adds {@link JList} instance on the right side of the frame which holds
     * all {@link GeometricalObject} objects from canvas.
     */
    private void createList() {
        listModel = new DrawingObjectListModel(model);
        model.addDrawingModelListener(listModel);
        objectList = new JList<GeometricalObject>(listModel);
        scrollPane = new JScrollPane(objectList);
        getContentPane().add(scrollPane, BorderLayout.EAST);

        objectList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int index = objectList.getSelectedIndex();
                    model.removeObject(index);
                } else if (e.getClickCount() == 2) {
                    int index = objectList.getSelectedIndex();
                    model.updateObject(index);
                    objectList.clearSelection();
                }
            }
        });
        scrollPane.setPreferredSize(new Dimension(120, 0));
    }

    /**
     * Creates and adds a drawing canvas.
     */
    private void createCanvas() {
        model = new DrawingModelImpl();
        canvas = new JDrawingCanvas(foregroundColor, backgroundColor, model);
        model.addDrawingModelListener(canvas);
        canvas.setOpaque(false);
        getContentPane().add(canvas, BorderLayout.CENTER);

    }

    /**
     * Creates and adds a tool-bar.
     */
    private void createToolbar() {
        JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
        toolbar.setFloatable(false);
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        foregroundColor = new JColorArea(Color.BLACK);
        backgroundColor = new JColorArea(Color.WHITE);
        toolbar.add(foregroundColor);
        toolbar.addSeparator();
        toolbar.add(backgroundColor);

        ButtonGroup group = new ButtonGroup();
        JButton line = new JButton("Line");
        line.setIcon(icons.LINE_ICON);
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setSelectedShape(new LineShape());
            }
        });

        JButton circle = new JButton("Circle");
        circle.setIcon(icons.CIRCLE_ICON);
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setSelectedShape(new CircleShape());
            }
        });

        JButton filledCircle = new JButton("Filled circle");
        filledCircle.setIcon(icons.FCIRCLE_ICON);
        filledCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setSelectedShape(new FilledCircleShape());
            }
        });
        group.add(line);
        group.add(circle);
        group.add(filledCircle);
        toolbar.addSeparator();
        toolbar.add(line);
        toolbar.add(circle);
        toolbar.add(filledCircle);

        getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Creates and adds a new {@link JMenuBar} instance.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as");
        JMenuItem export = new JMenuItem("Export");
        JMenuItem exit = new JMenuItem("Exit");

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canvas.isChanged()) {
                    saveFile();
                }
                currentFile = FIleFactory.openJVDFile(model,JVDraw.this);
                canvas.setChanged(false);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file;
                if (!canvas.isChanged()) {
                    return;
                }
                if (currentFile == null) {
                    file = FIleFactory.getFile(JVDraw.this);
                    if (file == null) {
                        return;
                    }
                    currentFile = file;
                }
                FIleFactory.saveCanvas(model, currentFile);
                canvas.setChanged(false);
            }
        });

        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFile = FIleFactory.getFile(JVDraw.this);
                if (currentFile == null) {
                    return;
                }
                FIleFactory.saveCanvas(model, currentFile);
                canvas.setChanged(false);
            }
        });

        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FIleFactory.exportCanvas(model, canvas);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canvas.isChanged()) {
                    saveFile();
                }
                dispose();
            }
        });

        menu.add(open);
        menu.add(save);
        menu.add(saveAs);
        menu.add(export);
        menu.addSeparator();
        menu.add(exit);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /**
     * Saves current file.
     */
    private void saveFile() {
        File file;
        int status = JOptionPane.showConfirmDialog(JVDraw.this,
                "Do you want to save current state?", "Save",
                JOptionPane.YES_NO_OPTION);

        if (currentFile == null && status == JOptionPane.OK_OPTION) {
            file = FIleFactory.getFile(JVDraw.this);
            if (file == null) {
                return;
            }
            currentFile = file;
            FIleFactory.saveCanvas(model, currentFile);
        } else if (status == JOptionPane.OK_OPTION) {
            FIleFactory.saveCanvas(model, currentFile);
        }
    }

    /**
     * Start point of program JVDraw.
     * 
     * @param args
     *            Command line arguments. Not used.
     */
    public static void main(String[] args) {
        SwingUtilities
                .invokeLater(() -> {
                    try {
                        UIManager
                                .setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    } catch (Exception e) {
                    }
                    final JFrame frame = new JVDraw();
                    frame.setVisible(true);
                });
    }
}
