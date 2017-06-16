package hr.fer.zemris.zavrsni;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/klik")
public class KlikServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ModelCrteza model = (ModelCrteza) req.getSession()
                .getAttribute("model");
        String query = req.getQueryString();
        String[] params = query.split(",");
        int point1 = Integer.parseInt(params[0]);
        int point2 = Integer.parseInt(params[1]);
       int index =  model.najblizi(point1, point2);
       model.postaviSelektirani(index);

        req.getSession().setAttribute("model", model);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);

    }
}
