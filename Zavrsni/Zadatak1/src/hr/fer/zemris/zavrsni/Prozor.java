package hr.fer.zemris.zavrsni;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("javadoc")
public class Prozor extends JFrame {

    private static final long serialVersionUID = -7852114661349232680L;
    private static ModelCrteza model;
    private JCanvas canvas;
    private JButton izvrsi = new JButton("Izvrši");
    public static JBroj brKrugova = new JBroj("Broj krugova: ");
    public static JSelektiran selKrug = new JSelektiran("Selektirani krug: -");
    private JTextField naredba = new JTextField();
    private static final Path INPUT = Paths.get("D:/krug.txt");

    public Prozor() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocation(100, 100);
        setTitle("Prozor");
        initGUI();
    }

    private void initGUI() {
        model = new ModelCrteza();
        model.addListener(brKrugova);
        model.addListener(selKrug);
        canvas = new JCanvas(model);
        model.addListener(canvas);

        getContentPane().setLayout(new BorderLayout());
        JPanel status = new JPanel(new GridLayout(0, 2));
        status.add(brKrugova);
        status.add(selKrug);
        getContentPane().add(status, BorderLayout.PAGE_START);
        getContentPane().add(canvas, BorderLayout.CENTER);

        JPanel naredbePanel = new JPanel(new GridLayout(0, 2));
        naredbePanel.add(naredba);
        naredbePanel.add(izvrsi);

        izvrsi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = naredba.getText();
                try {
                    izvrsiNaredbu(command);
                    naredba.setText("");
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null,
                            "Naredba nije korektna",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
               

            }
        });

        getContentPane().add(naredbePanel, BorderLayout.PAGE_END);

        ucitajDatoteku();
    }

    public static void izvrsiNaredbu(String command) {
        try {
            if (command.startsWith("obrisi")) {
                String[] args = command.split("\\s");
                if(args.length == 1) {
                    model.ukloniSelKrug();
                } else if(args.length == 2) {
                    int index = Integer.parseInt(args[1]);
                    model.ukloniKrug(index);
                }
               
            } else if (command.startsWith("dodaj")) {
                String[] args = command.split("\\s");
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int radius = Integer.parseInt(args[3]);
                String obrub = args[4];
                String ispuna = "#FFFFFF";
                if(args.length == 6) {
                    ispuna = "#"+args[5];
                }
                model.dodajKrug(x, y, radius, Color.decode("#"+obrub), Color.decode(ispuna));
            } else if (command.startsWith("selektiraj")) {
                String index = command.split("\\s")[1];
                model.postaviSelektirani(Integer.parseInt(index));
            } else if (command.startsWith("deselektiraj")) {
                model.deSel();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException();
            
        }
        
    }
    
    public static void ucitajDatoteku() {
        try {
            List<String> lines  = Files.readAllLines(INPUT);
            parseLines(lines);
        } catch (IOException e) {
            
        }
    }
    
    private static void parseLines(List<String> lines) {
        
        for (String line : lines) {
            try {
                String[] args = line.split("\\s+");
                int x = Integer.parseInt(args[0].trim());
                int y = Integer.parseInt(args[1].trim());
                int radius = Integer.parseInt(args[2].trim());
                String obrub = args[3];
                String ispuna =  args[4];
                if(!ispuna.equals("-")) {
                    model.dodajKrug(x, y, radius, Color.decode("#"+obrub), Color.decode("#"+ ispuna));
                } else {
                    model.dodajKrug(x, y, radius, Color.decode("#"+obrub),null);
                }
                
               
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
           
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Prozor();
            frame.setVisible(true);
        });
    }
}
