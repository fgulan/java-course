package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.beans.Band;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * GlasanjeXLSServlet class extends {@link HttpServlet}. It creates http servlet with function of
 * creating a XLS document based on current number of votes for each band.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = -7978630174726259821L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String definitionFile = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-definicija.txt");
        String resultFile = req.getServletContext().getRealPath(
                "/WEB-INF/glasanje-rezultati.txt");

        List<Band> results = ServerUtilty
                .getResults(definitionFile, resultFile);

        HSSFWorkbook workbook = ServerUtilty.getXLS(results);
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition",
                "attachment; filename=votingResults.xls");
        workbook.write(resp.getOutputStream());
        workbook.close();
    }
}
