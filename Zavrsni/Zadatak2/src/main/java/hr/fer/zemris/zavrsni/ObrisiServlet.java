package hr.fer.zemris.zavrsni;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/obrisi")
public class ObrisiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ModelCrteza model = (ModelCrteza) req.getSession()
                .getAttribute("model");
        int index = Integer.parseInt(req.getParameter("index"));
        model.ukloniKrug(index);
        
        req.getSession().setAttribute("model", model);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }
}
