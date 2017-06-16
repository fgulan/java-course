package hr.fer.zemris.zavrsni;

import javax.swing.JLabel;

public class JSelektiran extends JLabel implements Promatrac {

    public JSelektiran(String text) {
        super(text);
    }
    @Override
    public void notifyObservers(ModelCrteza model) {
        int index = model.dohvatiIndeksSelektiranogKruga();
        if(index < 0 ) setText("Selektirani krug: - ");
        else setText("Selektirani krug: " + index);
    }

}
