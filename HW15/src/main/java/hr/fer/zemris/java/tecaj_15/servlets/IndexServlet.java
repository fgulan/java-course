package hr.fer.zemris.java.tecaj_15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IndexServlet extends {@link HttpServlet}. IndexServlet servlet is used only
 * for redirection short web application link to an {@link HomepageServlet}.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 2083900299887807721L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }
}
