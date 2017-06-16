package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.beans.Band;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlasanjeServlet class extends {@link HttpServlet}. It creates http servlet with function of
 * loading and giving a client all bands which are in voting process.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
    /** Serial number. */
    private static final long serialVersionUID = -7526325065304401961L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fileName = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-definicija.txt");

        List<Band> bands = ServerUtilty.getBands(fileName);
        req.setAttribute("bands", bands);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(
                req, resp);
    }
}
