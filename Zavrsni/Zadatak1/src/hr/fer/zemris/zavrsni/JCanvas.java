package hr.fer.zemris.zavrsni;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.jws.WebParam.Mode;
import javax.swing.JComponent;

public class JCanvas extends JComponent implements Promatrac {

    private ModelCrteza model;

    public JCanvas(ModelCrteza model) {
        super();
        this.model = model;
        addMouseListener(mouseListener);
    }

    @Override
    public void notifyObservers(ModelCrteza model) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        List<Krug> krugovi = model.getKrugovi();
        if (krugovi != null) {
            for (Krug krug : krugovi) {
                krug.iscrtaj(g);
            }
        }
    }

    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            model.najblizi(point.x, point.y);
    
            int index = model.najblizi(point.x, point.y);
            model.postaviSelektirani(index);
        }
    };
}
