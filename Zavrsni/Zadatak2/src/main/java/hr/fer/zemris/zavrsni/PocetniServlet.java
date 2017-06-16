package hr.fer.zemris.zavrsni;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/krugovi")
public class PocetniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ModelCrteza model = (ModelCrteza) req.getSession()
                .getAttribute("model");
        if (model == null) {
            model = new ModelCrteza();
        }
       
        Slika slika = (Slika) req.getSession()
                .getAttribute("slika");
        if (slika == null) {
            slika= new Slika(500, 500, BufferedImage.TYPE_INT_RGB);
            model.addListener(slika);
        }
        
     
        req.getSession().setAttribute("model", model);
       
 
        req.getSession().setAttribute("slika", slika);
        req.getSession().setAttribute("krugovi", model.getKrugovi());
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(
                req, resp);
        }
}
