package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ColorServlet class extends {@link HttpServlet}. It creates http servlet with function of changing
 * background color for whole web-page.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/setcolor")
public class ColorServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 1685587582773833866L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String backgroundColor = (String) req.getParameter("pickedBgCol");
        if (backgroundColor == null) {
            backgroundColor = "white";
        }
        req.getSession().setAttribute("pickedBgCol", backgroundColor);
        resp.sendRedirect("index.jsp");
    }
}
