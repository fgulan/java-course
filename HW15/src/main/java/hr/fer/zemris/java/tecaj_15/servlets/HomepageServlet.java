package hr.fer.zemris.java.tecaj_15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_15.JPAUtilities;
import hr.fer.zemris.java.tecaj_15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_15.model.BlogUser;

/**
 * HomepageServlet extends {@link HttpServlet}. HomepageServlet servlet is used
 * a main page for web application. It renders a list of all registered users,
 * login form and registration link.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet(urlPatterns = { "/servleti/main" })
public class HomepageServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -984643554152949453L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processAction(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processAction(req, resp);
    }

    /**
     * Process current get or post request.
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @throws ServletException If the request could not be handled.
     * @throws IOException If an input or output error is detected.
     */
    private void processAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("register", Boolean.FALSE);
        if (req.getParameter("action") != null) {
            checkLoginParameters(req);
        } else {
            req.getSession().setAttribute("error", false);
        }

        List<BlogUser> users = DAOProvider.getDAO().getAllUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);
    }

    /**
     * Checks input login parameters.
     * 
     * @param req Http servlet request.
     */
    private void checkLoginParameters(HttpServletRequest req) {
        if (req.getParameter("action").equals("Prijava")) {
            String nick = req.getParameter("nick");
            String password = req.getParameter("password");

            password = JPAUtilities.getPasswordHash(password);

            req.getSession().setAttribute("current.user.nick", nick);
            BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
            if (user != null && password.equals(user.getPasswordHash())) {
                req.getSession().setAttribute("current.user.id", user.getId());
                req.getSession().setAttribute("current.user.fn",
                        user.getFirstName());
                req.getSession().setAttribute("current.user.ln",
                        user.getLastName());
            } else {
                req.getSession().setAttribute("error", Boolean.TRUE);
            }
        }
    }
}
