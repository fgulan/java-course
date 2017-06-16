package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.beans.Poll;
import hr.fer.zemris.java.hw14.dao.sql.DAOProvider;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IndexServlet class extends {@link HttpServlet}. It creates http servlet used
 * for filing .jsp parameters from database. Rest of the job servlet redirects
 * to index.jsp.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 2618878559475099536L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Poll> polls = DAOProvider.getDao().getAllPolls();
        req.setAttribute("polls", polls);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }
}