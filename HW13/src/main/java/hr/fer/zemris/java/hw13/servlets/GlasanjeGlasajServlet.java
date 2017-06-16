package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlasanjeGlasajServlet class extends {@link HttpServlet}. It creates http servlet with function of
 * giving vote to the band with given ID. ID is given as "a" parameter in the servlet URL.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    /** Serial number. */
    private static final long serialVersionUID = 4922746839797416568L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String fileName = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-rezultati.txt");

        Map<Integer, Integer> votes = ServerUtilty.getVotes(fileName);

        try {
            Integer a = Integer.valueOf(req.getParameter("id"));
            Integer vote = votes.get(a);
            if (vote == null) {
                vote = 0;
            }
            vote++;
            votes.put(a, vote);
        } catch (Exception e) {
            req.getRequestDispatcher("/WEB-INF/pages/votingError.jsp").forward(
                    req, resp);
            return;
        }

        ServerUtilty.votesToFile(votes, fileName);
        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
