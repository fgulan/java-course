package hr.fer.zemris.java.tecaj_15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_15.JPAUtilities;
import hr.fer.zemris.java.tecaj_15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_15.forms.UserForm;
import hr.fer.zemris.java.tecaj_15.model.BlogUser;

/**
 * RegisterServlet extends {@link HttpServlet}. RegisterServlet servlet is used
 * for registering a new blog user in web application database.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -3896352226298556753L;

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
        String action = req.getParameter("action");
        if (action != null && action.equals("Odustani")) {
            req.getSession().setAttribute("register", Boolean.FALSE);
            resp.sendRedirect(req.getServletContext().getContextPath()
                    + "/servleti/main");
            return;
        }

        if (req.getSession().getAttribute("register").equals(Boolean.FALSE)) {
            UserForm form = new UserForm();

            req.getSession().setAttribute("register", Boolean.TRUE);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req,
                    resp);
            return;
        } else {
            String nick = req.getParameter("nick");
            BlogUser tempUser = DAOProvider.getDAO().getBlogUser(nick);

            UserForm form = new UserForm();
            form.fillFromHttpReq(req);
            if (tempUser != null) {
                form.putError("nick", "Odabrani nadimak je već zauzet!");
            }
            form.validate();

            if (form.haveErrors()) {
                req.setAttribute("form", form);
                req.getRequestDispatcher("/WEB-INF/pages/Register.jsp")
                        .forward(req, resp);
                return;
            }

            req.getSession().setAttribute("register", Boolean.FALSE);
            BlogUser user = new BlogUser();
            form.fillBlogUser(user);

            String hash = JPAUtilities.getPasswordHash(user.getPasswordHash());
            user.setPasswordHash(hash);
            DAOProvider.getDAO().addBlogUser(user);
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
        }
    }
}
