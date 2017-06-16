package hr.fer.zemris.zavrsni;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("javadoc")
@WebServlet("/naredba")
public class NaredbaServ extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String naredba = req.getParameter("naredba");
        ModelCrteza model = (ModelCrteza) req.getSession()
                .getAttribute("model");
        try {
            izvrsiNaredbu(naredba, model);
            req.getSession().setAttribute("greska", null);
        } catch (Exception e) {
            req.getSession().setAttribute("greska", "naredba: " + naredba + " se ne može izvršiti");
        }

        req.getSession().setAttribute("model", model);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

    }

    public void izvrsiNaredbu(String command, ModelCrteza model) {
        try {
            if (command.startsWith("obrisi")) {
                String[] args = command.split("\\s");
                if (args.length == 1) {
                    model.ukloniSelKrug();
                } else if (args.length == 2) {
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
                if (args.length == 6) {
                    ispuna = "#" + args[5];
                }
                model.dodajKrug(x, y, radius, Color.decode("#" + obrub),
                        Color.decode(ispuna));
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
}
