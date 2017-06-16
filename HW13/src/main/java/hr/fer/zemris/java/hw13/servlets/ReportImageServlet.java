package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * ReportImageServlet class extends {@link HttpServlet}. It creates an chart image with OS usage
 * statistics.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -7570897580654781522L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        JFreeChart chart = getChart();
        ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, 700, 400);
    }

    /**
     * Creates 3D pie chart with OS usage statistics.
     * 
     * @return Filled 3D pie chart.
     */
    public JFreeChart getChart() {
        DefaultPieDataset data = new DefaultPieDataset();

        data.setValue("Mac", 29);
        data.setValue("Windows", 51);
        data.setValue("Linux", 20);

        JFreeChart chart = ChartFactory.createPieChart3D("", data);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        return chart;
    }
}
