package hr.fer.zemris.zavrsni;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

@SuppressWarnings("javadoc")
public class Krug {
    public Point centar;
    public int radius;
    public Color bojaPozadine;
    public Color bojaObruba;

    public Krug(Point centar, int radius, Color bojaPozadine,
            Color bojaObruba) {
        super();
        this.centar = centar;
        this.radius = radius;
        this.bojaPozadine = bojaPozadine;
        this.bojaObruba = bojaObruba;
    }

    public void iscrtaj(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        if (bojaPozadine != null) {
            g2.setColor(bojaPozadine);
            g2.fillOval(centar.x - radius, centar.y - radius, radius * 2,
                    radius * 2);
        }

        g2.setColor(bojaObruba);
        g2.drawOval(centar.x - radius, centar.y - radius, radius * 2,
                radius * 2);

    }
    public String getOpis() {
        String boja;
        if (bojaPozadine == null) {
            boja = "-";
        } else {
            String hex = String.format("#%02x%02x%02x", bojaPozadine.getRed(), bojaPozadine.getGreen(), bojaPozadine.getBlue()).substring(1).toUpperCase();
            boja = hex;
        }
        String hex = String.format("#%02x%02x%02x", bojaObruba.getRed(), bojaObruba.getGreen(), bojaObruba.getBlue()).substring(1).toUpperCase();
        return centar.x + " " + centar.y +" " + radius + " " + " " +hex  + " " + boja;
    }
}
