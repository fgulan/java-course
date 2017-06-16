package hr.fer.zemris.java.tecaj_15.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_15.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_15.forms.EntryForm;
import hr.fer.zemris.java.tecaj_15.model.BlogComment;
import hr.fer.zemris.java.tecaj_15.model.BlogEntry;
import hr.fer.zemris.java.tecaj_15.model.BlogUser;

/**
 * AuthorServlet extends {@link HttpServlet}. AuthorServlet is servlet used for
 * operating with user, his blog entries and comments.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 572045697455941626L;
    /** Login status. */
    private boolean logged = false;
    /** Resolved path. */
    private String path;
    /** Current nick. */
    private String nick;
    /** Current entry ID. */
    private Long eid;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        path = req.getPathInfo().substring(1);
        logged = req.getSession().getAttribute("current.user.id") != null;

        if (path.indexOf('/') == -1) {
            nick = path;
            checkLogin(req);
            getBlogs(req, resp);
            return;
        } else if (path.indexOf("/new") != -1) {
            resolveNick();
            checkLogin(req);
            newBlog(req, resp);
            return;
        } else if (path.indexOf("/edit") != -1) {
            resolveNick();
            checkLogin(req);
            editBlog(req, resp);
            return;
        } else if (path.matches(".+/\\d+")) {
            int index = path.indexOf('/');
            nick = path.substring(0, index);
            checkLogin(req);
            eid = Long.valueOf(path.substring(index + 1));
            getBlogEntry(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String blogAction = req.getParameter("newBlogAction");
        if (blogAction != null) {
            addBlog(req, resp, blogAction);
        }

        String commentAction = req.getParameter("commentAction");
        if (commentAction != null) {
            addComment(req, resp);
        }
    }

    /**
     * Gets all blogs for which given users is author.
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @throws ServletException If the request could not be handled.
     * @throws IOException If an input or output error is detected.
     */
    private void getBlogs(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isNickUsed(nick)) {
            resp.sendRedirect(req.getContextPath() + "/servleti/error");
            return;
        }
        List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(nick);
        req.setAttribute("entries", entries);
        req.setAttribute("author", nick);
        req.getRequestDispatcher("/WEB-INF/pages/BlogsList.jsp").forward(req,
                resp);
    }

    /**
     * Creates a new blog from for given author (if author is logged).
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @throws ServletException If the request could not be handled.
     * @throws IOException If an input or output error is detected.
     */
    private void newBlog(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!logged || !isNickUsed(nick)) {
            req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req,
                    resp);
            return;
        }
        EntryForm form = new EntryForm();
        req.setAttribute("author", nick);
        req.setAttribute("form", form);
        req.setAttribute("action", "new");
        req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
                resp);
    }

    /**
     * Edits given blog for currently logged user.
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @throws ServletException If the request could not be handled.
     * @throws IOException If an input or output error is detected.
     */
    private void editBlog(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!logged || !isNickUsed(nick)) {
            resp.sendRedirect(req.getContextPath() + "/servleti/error");
            return;
        }

        eid = (Long) req.getSession().getAttribute("blogID");
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
        if (entry == null) {
            resp.sendRedirect(req.getContextPath() + "/servleti/error");
            return;
        }
        EntryForm form = EntryForm.createFromBlogEntry(entry);

        req.setAttribute("author", nick);
        req.setAttribute("form", form);
        req.setAttribute("action", "edit");
        req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp").forward(req,
                resp);
    }

    /**
     * Gets blog with given ID for currently logged user.
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @throws ServletException If the request could not be handled.
     * @throws IOException If an input or output error is detected.
     */
    private void getBlogEntry(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(eid);
        if (entry == null || !isNickUsed(nick)) {
            resp.sendRedirect(req.getContextPath() + "/servleti/error");
            return;
        }
        req.setAttribute("entry", entry);
        req.setAttribute("author", nick);
        req.getSession().setAttribute("blogID", entry.getId());
        req.getRequestDispatcher("/WEB-INF/pages/ShowBlog.jsp").forward(req,
                resp);
    }

    /**
     * Adds a new blog for currently logged user.
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @param blogAction Selected action in application form.
     * @throws ServletException If the request could not be handled.
     * @throws IOException If an input or output error is detected.
     */
    private void addBlog(HttpServletRequest req, HttpServletResponse resp,
            String blogAction) throws IOException, ServletException {
        String path = req.getPathInfo().substring(1);
        String author = path.substring(0, path.indexOf('/'));
        if (!"Spremi".equals(blogAction)) {
            resp.sendRedirect(
                    req.getContextPath() + "/servleti/author/" + author);
            return;
        }

        BlogEntry entry = new BlogEntry();
        EntryForm form = EntryForm.createFromHttpReq(req);
        form.validate();

        if (form.haveErrors()) {
            req.setAttribute("form", form);
            req.setAttribute("action", path.contains("/edit") ? "edit" : "new");
            req.setAttribute("author", nick);
            req.getRequestDispatcher("/WEB-INF/pages/NewEditBlog.jsp")
                    .forward(req, resp);
            return;
        }
        form.fillBlogEntry(entry);
        entry.setCreatedAt(new Date());
        entry.setLastModifiedAt(new Date());

        BlogUser user = DAOProvider.getDAO().getBlogUser(author);
        if (user != null) {
            entry.setCreator(user);
        }

        if (req.getParameter("id") != null) {
            Long id = Long.valueOf(req.getParameter("id"));

            BlogEntry newEntry = DAOProvider.getDAO().getBlogEntry(id);
            newEntry.setLastModifiedAt(entry.getLastModifiedAt());
            newEntry.setTitle(entry.getTitle());
            newEntry.setText(entry.getText());

            DAOProvider.getDAO().updateBlogEntry(newEntry);
            resp.sendRedirect(
                    req.getContextPath() + "/servleti/author/" + author);
            return;
        }

        DAOProvider.getDAO().addBlogEntry(entry);
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author);
        return;
    }

    /**
     * Adds a new comment to currently selected blog entry.
     * 
     * @param req Http servlet request.
     * @param resp Http servlet response.
     * @throws IOException If an input or output error is detected.
     */
    private void addComment(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String commentText = req.getParameter("comment");
        Long blogID = Long.valueOf(req.getParameter("blogID"));
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(blogID);

        BlogComment comment = new BlogComment();
        comment.setBlogEntry(entry);
        comment.setMessage(commentText);
        comment.setPostedOn(new Date());

        nick = (String) req.getSession().getAttribute("current.user.nick");
        BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
        if (nick != null && user != null) {
            comment.setUsersEMail(user.getEmail());
        } else {
            comment.setUsersEMail("unregistered@unregistered.com");
        }

        DAOProvider.getDAO().addComment(comment);
        resp.sendRedirect(req.getContextPath() + req.getServletPath()
                + req.getPathInfo());
        return;
    }

    /**
     * Checks if given nick exists in database.
     * 
     * @param nick Input nick.
     * @return <code>true</code> if given nick exists, <code>false</code>
     *         otherwise.
     */
    private boolean isNickUsed(String nick) {
        return DAOProvider.getDAO().getBlogUser(nick) != null;
    }

    /**
     * Resolves nick from input path.
     */
    private void resolveNick() {
        nick = path.substring(0, path.indexOf('/'));
    }

    /**
     * Checks if given users is currently logged in application.
     * 
     * @param req Http servlet request.
     */
    private void checkLogin(HttpServletRequest req) {
        if (logged) {
            logged = nick
                    .equals(req.getSession().getAttribute("current.user.nick"));
        }
    }
}
