package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.beans.Band;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlasanjeRezultatiServlet class extends {@link HttpServlet}. It creates http servlet with function
 * of creating report on current state in voting process.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
    /** Serial number. */
    private static final long serialVersionUID = 5916223553639592852L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fileResults = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-rezultati.txt");
        String fileDefinition = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-definicija.txt");

        List<Band> results = ServerUtilty.getResults(fileDefinition,
                fileResults);
        req.setAttribute("results", results);

        int maxVotes = results.get(0).getVotes();
        for (int i = 0, size = results.size(); i < size; i++) {
            if (results.get(i).getVotes() > maxVotes) {
                maxVotes = results.get(i).getVotes();
            }
        }
        List<Band> best = new ArrayList<>();
        for (Band band : results) {
            if (band.getVotes() == maxVotes) {
                best.add(band);
            }
        }
        req.setAttribute("best", best);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
                resp);
    }
}
