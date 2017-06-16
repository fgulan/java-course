package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.beans.PollOption;
import hr.fer.zemris.java.hw14.dao.sql.DAOProvider;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlasanjeServlet class extends {@link HttpServlet}. It creates http servlet
 * with function of loading and giving a client all polls which are currently
 * stored in database.
 * 
 * @author Filip
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
        try {
            Long pollId = Long.valueOf((String) req.getParameter("pollID"));
            Poll poll = DAOProvider.getDao().getPoll(pollId);
            List<PollOption> options = DAOProvider.getDao().getPollOptions(
                    pollId);

            req.getSession().setAttribute("pollID", pollId);
            req.setAttribute("poll", poll);
            req.setAttribute("options", options);
            req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp")
                    .forward(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher("/WEB-INF/pages/votingError.jsp").forward(
                    req, resp);
            return;
        }

    }
}
