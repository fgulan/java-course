package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.beans.PollOption;
import hr.fer.zemris.java.hw14.dao.sql.DAOProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * DBUtility class implements several public static methods used in web
 * application and database access.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class DBUtility {

    /**
     * Checks if table with given name is located in current database.
     * 
     * @param tableName Table name.
     * @param connection Current connection.
     * @return <code>true</code> if current database contains table with given
     *         name, <code>false</code> otherwise.
     */
    public static boolean isTablePresent(String tableName, Connection connection) {
        try {
            if (connection != null) {
                DatabaseMetaData dbmd = connection.getMetaData();
                ResultSet rs = dbmd.getTables(null, null,
                        tableName.toUpperCase(), null);
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {

        }

        return false;
    }

    /**
     * Creates polls table in current database connection.
     * 
     * @param connection Connection to a database.
     */
    public static void createPollsTable(Connection connection) {
        try {
            Statement sta = connection.createStatement();
            sta.executeUpdate("CREATE TABLE Polls ("
                    + "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "title VARCHAR(150) NOT NULL, "
                    + "message CLOB(2048) NOT NULL)");
            sta.close();

        } catch (Exception e) {
        }
    }

    /**
     * Creates poll options table in current database connection.
     * 
     * @param connection Connection to a database.
     */
    public static void createPollOptionsTable(Connection connection) {
        try {
            Statement sta = connection.createStatement();
            sta.executeUpdate("CREATE TABLE PollOptions ("
                    + "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                    + "optionTitle VARCHAR(100) NOT NULL, "
                    + "optionLink VARCHAR(150) NOT NULL, " + "pollID BIGINT, "
                    + "votesCount BIGINT, "
                    + "FOREIGN KEY (pollID) REFERENCES Polls(id))");
            sta.close();
        } catch (Exception e) {
        }
    }

    /**
     * Fills poll for voting for best band with poll options from given file.
     * 
     * @param path Path to a file with poll options.
     */
    public static void fillBandTable(String path) {
        long id = DAOProvider.getDao().getPollID("Glasanje za omiljeni bend");
        if (id == -1) {
            id = DAOProvider.getDao().addPoll(
                    "Glasanje za omiljeni bend",
                    "Od sljedećih bendova koji vam je najdraži? "
                            + "Kliknite na link kako biste glasali!");
        }
        if (id != -1) {
            List<PollOption> entries = getEntries(path);
            for (PollOption entry : entries) {
                if (DAOProvider.getDao().isPollOptionPresent(entry.getName(),
                        id)) {
                    continue;
                }
                DAOProvider.getDao().addPollOption(entry.getName(),
                        entry.getLink(), Long.valueOf(id), entry.getVotes());
            }
        }
    }

    /**
     * Fills poll for voting for best bass guitar with poll options from given
     * file.
     * 
     * @param path Path to a file with poll options.
     */
    public static void fillGuitarTable(String path) {
        long id = DAOProvider.getDao().getPollID(
                "Glasanje za najbolju bas gitaru");
        if (id == -1) {
            id = DAOProvider.getDao().addPoll(
                    "Glasanje za najbolju bas gitaru",
                    "Od sljedećih gitara koja vam pruža najbolji zvuk? "
                            + "Kliknite na link kako biste glasali!");
        }
        if (id != -1) {
            List<PollOption> entries = getEntries(path);
            for (PollOption entry : entries) {
                if (DAOProvider.getDao().isPollOptionPresent(entry.getName(),
                        id)) {
                    continue;
                }
                DAOProvider.getDao().addPollOption(entry.getName(),
                        entry.getLink(), Long.valueOf(id), entry.getVotes());
            }
        }
    }

    /**
     * Reads file with poll entries from given path and returns a list created
     * from read {@link PollOption} objects.
     * 
     * @param filePath File path
     * @return List of {@link PollOption} objects.
     */
    public static List<PollOption> getEntries(String filePath) {
        List<PollOption> entries = new ArrayList<>();
        Path path = Paths.get(filePath);
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split("\t");
                Integer id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String link = parts[2].trim();
                entries.add(new PollOption(id, name, link, 10));
            }
        } catch (Exception e) {
        }
        return entries;
    }

    /**
     * Creates XLS document from given list of {@link PollOption} objects.
     * 
     * @param results List of {@link PollOption} objects.
     * @return XLS document.
     */
    public static HSSFWorkbook getXLS(List<PollOption> results) {
        HSSFWorkbook document = new HSSFWorkbook();
        HSSFSheet sheet = document.createSheet("Results");
        HSSFRow rowhead = sheet.createRow(0);
        rowhead.createCell(0).setCellValue("Ime opcije:");
        rowhead.createCell(1).setCellValue("Broj glasova:");

        for (int i = 0; i < results.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(results.get(i).getName());
            row.createCell(1).setCellValue(
                    Double.valueOf(results.get(i).getVotes()));
        }
        return document;
    }

    /**
     * Creates 3D pie chart form given list of {@link PollOption} objects.
     * 
     * @param results List of {@link PollOption} objects.
     * @return Filled 3D pie chart.
     */
    public static JFreeChart getChart(List<PollOption> results) {
        DefaultPieDataset result = new DefaultPieDataset();
        for (PollOption r : results) {
            result.setValue(r.getName(), r.getVotes());
        }

        JFreeChart chart = ChartFactory.createPieChart3D("", result);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
