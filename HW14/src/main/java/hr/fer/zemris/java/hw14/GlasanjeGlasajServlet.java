package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.dao.sql.DAOProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GlasanjeGlasajServlet class extends {@link HttpServlet}. It creates http
 * servlet with function of giving vote to the poll option with given ID in poll
 * with current poll ID. ID is sent through URL as parameter 'id'.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 2706768387114009895L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Long pollId = (Long) req.getSession().getAttribute("pollID");
            Long id = Long.valueOf((String) req.getParameter("id"));

            Integer count = DAOProvider.getDao().getOptionVotes(pollId, id);
            if (count != -1) {
                count++;
                DAOProvider.getDao().setOptionVotes(pollId, id, count);
            }
        } catch (Exception ignorable) {
            req.getRequestDispatcher("/WEB-INF/pages/votingError.jsp").forward(
                    req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}