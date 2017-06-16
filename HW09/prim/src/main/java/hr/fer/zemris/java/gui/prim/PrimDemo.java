package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * PrimDemo program demonstrates use of JList, JScrollPane and ListModel
 * interface. Program is consisted of frame with two lists and button which
 * generates next prime number and adds it to the lists.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class PrimDemo extends JFrame {

    /** Serial. */
    private static final long serialVersionUID = 2552614965736703542L;

    /**
     * Constructor for PrimeDemo class.
     */
    public PrimDemo() {
        setLocation(150, 150);
        setTitle("PrimDemo");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Initializes GUI.
     */
    private void initGUI() {
        PrimListModel model = new PrimListModel();
        JList<Integer> firstList = new JList<Integer>(model);
        JList<Integer> secondList = new JList<Integer>(model);
        JButton next = new JButton("Next");

        firstList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        firstList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane firstScroller = new JScrollPane(firstList);

        secondList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        secondList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane secondSroller = new JScrollPane(secondList);

        next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.next();
                revalidate();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(firstScroller);
        panel.add(secondSroller);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(next, BorderLayout.PAGE_END);
    }

    /**
     * Start point of program PrimeDemo.
     * 
     * @param args
     *            Command line arguments. Not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            } catch (Exception e) {

            }
            JFrame frame = new PrimDemo();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
