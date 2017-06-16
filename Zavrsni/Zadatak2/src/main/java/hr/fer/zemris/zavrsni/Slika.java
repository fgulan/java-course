package hr.fer.zemris.zavrsni;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class Slika extends BufferedImage implements Promatrac {

    public Slika(int width, int height, int imageType) {
        super(width, height, imageType);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void notifyObservers(ModelCrteza model) {
        Graphics g = createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        List<Krug> krugovi = model.getKrugovi();
        if (krugovi != null) {
            for (Krug krug : krugovi) {
                krug.iscrtaj(g);
            }
        }

    }

}
