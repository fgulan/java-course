package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * StoriesServlet class extends {@link HttpServlet}. It creates http servlet with function of
 * creating a funny story each time in another text color which is randomly chosen.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/stories/funny.jsp")
public class StoriesServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -3979837207204009612L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Random random = new Random();
        String[] colors = new String[] { "Olive", "PaleGreen", "Black",
                "IndianRed", "Gold", "FireBrick", "Navy", "OrangeRed", };
        String textColor = colors[random.nextInt(colors.length)];

        req.getSession().setAttribute("textColor", textColor);
        req.getRequestDispatcher("/WEB-INF/pages/funny.jsp").forward(req, resp);
    }
}