package hr.fer.zemris.java.tecaj_15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LogoutServlet extends {@link HttpServlet}. LogoutServlet servlet is used only
 * for logging out currently logged blog user.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -5435664814497532638L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(
                req.getServletContext().getContextPath() + "/servleti/main");
    }
}
