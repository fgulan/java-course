package hr.fer.zemris.java.tecaj_15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ErrorServlet is servlet used for displaying an error message.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/servleti/error")
public class ErrorServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 7060225261140250022L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
    }
}
