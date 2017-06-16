package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * PowersServlet class extends {@link HttpServlet}. It creates http servlet with function of
 * creating a XLS document of powers based on given input parameters. Parameter "a" represents start
 * number, "b" end number and "n" number of last power. Start and end number must be in interval
 * from -100 to 100 and "n" must be between 1 and 5.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

    /** Serial number. */
    private static final long serialVersionUID = 1117760725744409282L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer a = null;
        Integer b = null;
        Integer n = null;

        try {
            a = Integer.valueOf(req.getParameter("a"));
            b = Integer.valueOf(req.getParameter("b"));
            n = Integer.valueOf(req.getParameter("n"));
        } catch (Exception ex) {
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
                    resp);
            return;
        }

        if (!isValid(a) || !isValid(b) || n < 1 || n > 5) {
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
                    resp);
            return;
        }

        HSSFWorkbook workbook = getXLS(a, b, n);
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");
        workbook.write(resp.getOutputStream());
        workbook.close();
    }

    /**
     * Creates XLS document based on given input parameters.
     * 
     * @param a Start number.
     * @param b End number.
     * @param n Last power.
     * @return Created XLS document.
     */
    private HSSFWorkbook getXLS(Integer a, Integer b, Integer n) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        for (int i = 1; i <= n; i++) {
            HSSFSheet sheet = workbook.createSheet(i + "-th power.");
            HSSFRow rowHead = sheet.createRow(0);
            rowHead.createCell(0).setCellValue("x");
            rowHead.createCell(1).setCellValue("x^" + i);

            int rowCounter = 1;
            for (int j = a; j <= b; j++) {
                HSSFRow row = sheet.createRow(rowCounter++);
                row.createCell(0).setCellValue(Double.valueOf(j));
                row.createCell(1).setCellValue(Math.pow(Double.valueOf(j), i));
            }
        }
        return workbook;
    }

    /**
     * Checks if input parameter is in interval from -100 to 100.
     * 
     * @param input Input number.
     * @return <code>true</code> if number is in interval from -100 to 100, <code>false</code>
     *         otherwise.
     */
    private boolean isValid(Integer input) {
        if (input >= -100 && input <= 100) {
            return true;
        }
        return false;
    }
}
