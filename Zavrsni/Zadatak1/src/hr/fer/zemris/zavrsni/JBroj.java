package hr.fer.zemris.zavrsni;

import javax.swing.JLabel;

@SuppressWarnings("javadoc")
public class JBroj extends JLabel implements Promatrac {

    /**
     * 
     */
    private static final long serialVersionUID = 935510857973323652L;

    public JBroj(String text) {
        super(text);
    }
    @Override
    public void notifyObservers(ModelCrteza model) {
        setText("Broj krugova: " + model.brojKrugova());

    }

}
