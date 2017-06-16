package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.beans.PollOption;
import hr.fer.zemris.java.hw14.dao.sql.DAOProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlasanjeRezultatiServlet class extends {@link HttpServlet}. It creates http
 * servlet with function of creating report on current state in voting process
 * from database.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 3785142952044491607L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<PollOption> results = null;
        try {
            Long pollId = (Long) req.getSession().getAttribute("pollID");
            results = DAOProvider.getDao().getPollOptions(pollId);
        } catch (Exception ex) {
            results = new ArrayList<>();
        }

        List<PollOption> best = new ArrayList<>();
        if (!results.isEmpty()) {
            int firstCount = results.get(0).getVotes();
            for (PollOption d : results) {
                if (d.getVotes() == firstCount) {
                    best.add(d);
                }
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("best", best);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
                resp);
    }
}