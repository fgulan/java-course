package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.beans.TrigonometricFun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TrigonometricServlet class extends {@link HttpServlet}. It creates http servlet with function of
 * creating a table of trigonometric values (sine and cosine). It accepts two parameters: "a" as
 * start angle and "b" as end angle.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet(urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 1117760725744409282L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer startFrom = null;
        Integer endAt = null;

        try {
            startFrom = Integer.valueOf(req.getParameter("a"));
        } catch (Exception e) {
            startFrom = 0;
        }

        try {
            endAt = Integer.valueOf(req.getParameter("b"));
        } catch (Exception e) {
            endAt = 360;
        }

        if (startFrom > endAt) {
            int tmp = startFrom;
            startFrom = endAt;
            endAt = tmp;
        }

        if (endAt > startFrom + 720) {
            endAt = startFrom + 720;
        }

        List<TrigonometricFun> results = new ArrayList<>();
        for (int i = startFrom, n = endAt.intValue(); i <= n; i++) {
            results.add(new TrigonometricFun(i));
        }

        req.setAttribute("results", results);
        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(
                req, resp);

    }
}
