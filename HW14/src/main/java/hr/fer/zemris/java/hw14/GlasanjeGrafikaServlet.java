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

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * GlasanjeGrafikaServlet class extends {@link HttpServlet}. It creates http
 * servlet with function of creating a chart image based on current number of
 * votes for each poll option in given poll. Poll is given as parameter
 * 'pollID'.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -9178585745034238966L;

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

        JFreeChart chart = DBUtility.getChart(results);
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 700, 400);
    }
}