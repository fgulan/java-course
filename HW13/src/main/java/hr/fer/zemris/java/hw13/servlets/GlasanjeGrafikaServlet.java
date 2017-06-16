package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.beans.Band;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * GlasanjeGrafikaServlet class extends {@link HttpServlet}. It creates http servlet with function
 * of creating a chart image based on current number of votes for each band.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -7978630174726259821L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String resultFile = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-rezultati.txt");
        String definitionFile = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-definicija.txt");

        List<Band> results = ServerUtilty
                .getResults(definitionFile, resultFile);

        JFreeChart chart = ServerUtilty.getChart(results);
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 700, 400);
        resp.getOutputStream().flush();
    }
}
