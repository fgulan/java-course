package hr.fer.zemris.zavrsni;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

@SuppressWarnings("javadoc")
public class ModelCrteza {
    List<Krug> krugovi;
    Krug selektiraniKrug;
    List<Promatrac> promatraci;

    public ModelCrteza() {
        super();
        promatraci = new ArrayList<>();
        krugovi = new ArrayList<>();
    }

    // dodaje novi krug u model:
    public void dodajKrug(int cx, int cy, int r, Color obrub, Color ispuna) {

        Krug krug = new Krug(new Point(cx, cy), r, ispuna, obrub);
        krugovi.add(krug);
        obavijestiPromatrace();
    }

    // vraća krug koji model pamti pod navedenim rednim brojem:
    public Krug dohvati(int index) {
        return krugovi.get(index);
    }

    // vraća broj krugova u modelu:
    public int brojKrugova() {
        return krugovi.size();
    }

    // briše iz modela zadani krug:
    public void ukloniKrug(int index) {
        if (index >= 0 && index < krugovi.size()) {
            krugovi.remove(index);
            obavijestiPromatrace();
        }
    }

    // briše iz modela zadani krug:
    public void ukloniSelKrug() {
        if (selektiraniKrug != null) {
            krugovi.remove(selektiraniKrug);
            obavijestiPromatrace();

        }
    }
    
    public void deSel(){
        if (selektiraniKrug != null) {
            Color first = selektiraniKrug.bojaObruba;
            Color invertObrub = new Color(255 - first.getRed(),
                    255 - first.getGreen(), 255 - first.getBlue());
            selektiraniKrug.bojaObruba = invertObrub;
            Color second = selektiraniKrug.bojaPozadine;

            Color invertIspuna = new Color(255 - second.getRed(),
                    255 - second.getGreen(), 255 - second.getBlue());
            selektiraniKrug.bojaPozadine = invertIspuna;
            selektiraniKrug = null;
            obavijestiPromatrace();
        }
    }

    // postavi selektirani krug (samo jedan može biti selektiran); -1 postavlja
    // da ništa nije selektirano:
    public void postaviSelektirani(int index) {
        if (index < 0 || index >= krugovi.size()) {
            selektiraniKrug = null;
        } else {
            if (selektiraniKrug == krugovi.get(index))
                return;
            else {
                if (selektiraniKrug != null) {
                    Color first = selektiraniKrug.bojaObruba;

                    Color invertObrub = new Color(255 - first.getRed(),
                            255 - first.getGreen(), 255 - first.getBlue());
                    selektiraniKrug.bojaObruba = invertObrub;
                    Color second = selektiraniKrug.bojaPozadine;
                    if (second != null) {
                        Color invertIspuna = new Color(255 - second.getRed(),
                                255 - second.getGreen(),
                                255 - second.getBlue());
                        selektiraniKrug.bojaPozadine = invertIspuna;
                    }

                }
                selektiraniKrug = krugovi.get(index);
                Color first = selektiraniKrug.bojaObruba;

                Color invertObrub = new Color(255 - first.getRed(),
                        255 - first.getGreen(), 255 - first.getBlue());
                selektiraniKrug.bojaObruba = invertObrub;
                Color second = selektiraniKrug.bojaPozadine;
                if (second != null) {
                    Color invertIspuna = new Color(255 - second.getRed(),
                            255 - second.getGreen(), 255 - second.getBlue());
                    selektiraniKrug.bojaPozadine = invertIspuna;
                }
            }

        }
        obavijestiPromatrace();
    }

    // vraća indeks selektiranog kruga ili -1 ako ništa nije selektirano:
    public int dohvatiIndeksSelektiranogKruga() {
        if (selektiraniKrug == null)
            return -1;
        return krugovi.indexOf(selektiraniKrug);
    }

    // dohvaća redni broj kruga najbližeg(1); vidi pojašnjenje ispod predanoj
    // točki:
    public int najblizi(int tx, int ty) {
        double minDistance = -1;
        Krug temp = null;
        for (Krug krug : krugovi) {
            double vX = tx - krug.centar.x;
            double vY = ty - krug.centar.y;
            double magV = Math.sqrt(vX * vX + vY * vY);
            double aX = krug.centar.x + vX / magV * krug.radius;
            double aY = krug.centar.y + vY / magV * krug.radius;
            double distance = Math
                    .sqrt(Math.pow(tx - aX, 2) + Math.pow(ty - aY, 2));
            if (minDistance == -1) {
                minDistance = distance;
                temp = krug;
            } else {
                if (distance < minDistance) {
                    minDistance = distance;
                    temp = krug;
                }
            }
        }
        
       if(minDistance > 5.0) return -1;
        if (temp != null) {
            return krugovi.indexOf(temp);
        } else
            return -1;
    }

    public List<Krug> getKrugovi() {
        return krugovi;
    }

    public void addListener(Promatrac promatrac) {
        promatraci.add(promatrac);
    }

    void obavijestiPromatrace() {
        for (Promatrac promatrac : promatraci) {
            promatrac.notifyObservers(ModelCrteza.this);
        }
    }

}
