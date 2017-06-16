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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * GlasanjeXLSServlet class extends {@link HttpServlet}. It creates http servlet
 * with function of creating a XLS document based on current number of votes for
 * each poll option in given poll. Poll is given as parameter 'pollID'.
 * 
 * @author Filip
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

        List<PollOption> results = null;
        try {
            Long pollId = (Long) req.getSession().getAttribute("pollID");
            results = DAOProvider.getDao().getPollOptions(pollId);
        } catch (Exception ex) {
            results = new ArrayList<>();
        }

        HSSFWorkbook workbook = DBUtility.getXLS(results);
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition",
                "attachment; filename=votingResults.xls");
        workbook.write(resp.getOutputStream());
        workbook.close();
    }

}
