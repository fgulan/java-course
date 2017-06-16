package hr.fer.zemris.java.hw10.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw10.jnotepadpp.local.ILocalizationProvider;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * LJStatusBar extends {@link JPanel} class. It represents JNotepadPP status bar
 * with line counter, caret position and time label.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class LJStatusBar extends JPanel {

    /** Serial. */
    private static final long serialVersionUID = -8508916074609265679L;
    /** Localization provider. */
    private ILocalizationProvider lp;

    /** Localization listener. */
    ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
            updateText(lengthNum, lineNum, columnNum, selectionNum);
        }
    };

    /** Length label. */
    private JLabel length;
    /** Current line label. */
    private JLabel line;
    /** Current column label. */
    private JLabel column;
    /** Selection label. */
    private JLabel selection;
    /** Current time label. */
    private JLabel time;
    /** Swing timer. */
    private Timer timer;

    /** Length. */
    private int lengthNum;
    /** Current line. */
    private int lineNum = 1;
    /** Current column. */
    private int columnNum;
    /** Selection. */
    private int selectionNum;

    /**
     * Constructor for LJStatusBar class.
     * 
     * @param lp
     *            Localization provider.
     */
    public LJStatusBar(ILocalizationProvider lp) {
        super();
        this.lp = lp;
        this.lp.addLocalizationListener(listener);
        initGUI();
    }

    /**
     * Initializes status bar GUI.
     */
    private void initGUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        length = new JLabel(lp.getString("length") + ": 0");
        line = new JLabel(lp.getString("line") + ": 1");
        column = new JLabel(lp.getString("column") + ": 0");
        selection = new JLabel(lp.getString("length") + ": 0");
        time = new JLabel();

        add(length);
        add(Box.createHorizontalGlue());

        add(line);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(column);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(selection);
        add(Box.createHorizontalGlue());
        add(time);

        timer = new Timer(500, timerListener);
        timer.start();
    }

    /** Timer listener. */
    private ActionListener timerListener = new ActionListener() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        public void actionPerformed(ActionEvent event) {
            if (getParent() == null) {
                timer.stop();
            }
            time.setText(formatter.format(new Date()));
        }
    };

    /**
     * Update status bar content.
     * 
     * @param length
     *            Current document length.
     * @param line
     *            Current caret line position.
     * @param column
     *            Current caret column position.
     * @param selection
     *            Current selection.
     */
    public void updateText(int length, int line, int column, int selection) {
        selectionNum = selection;
        lengthNum = length;
        lineNum = line;
        columnNum = column;

        this.length.setText(lp.getString("length") + ": " + length);
        this.line.setText(lp.getString("line") + ": " + line);
        this.column.setText(lp.getString("column") + ": " + column);
        this.selection.setText(lp.getString("selection") + ": " + selection);
    }

    /**
     * Get Swing timer.
     * 
     * @return Swing timer.
     */
    public Timer getTimer() {
        return timer;
    }

}
