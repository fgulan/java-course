package hr.fer.zemris.zavrsni;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/slika")
public class SlikaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Slika slika = (Slika) req.getSession().getAttribute("slika");
        ImageIO.write(slika, "PNG", resp.getOutputStream());
        resp.getOutputStream().flush();
        return;
    }

}
