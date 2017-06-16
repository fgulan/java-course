package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.beans.Band;
import hr.fer.zemris.java.hw13.beans.BandComparator;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * ServerUtility class is factory class with number of static methods used in several created
 * servlets.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public final class ServerUtilty {

    /**
     * Private constructor.
     */
    private ServerUtilty() {

    }

    /**
     * Creates a list of bands from given definition file.
     * 
     * @param fileName Input definition file.
     * @return List of bands.
     */
    public static List<Band> getBands(String fileName) {
        List<Band> bands = new ArrayList<>();
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split("\t");
                Integer bandId = Integer.parseInt(parts[0].trim());
                String bandName = parts[1].trim();
                bands.add(new Band(bandName, "glasanje-glasaj?id=" + bandId, 0,
                        bandId));
            }
        } catch (Exception e) {
        }
        return bands;
    }

    /**
     * Creates textual file with current state of votes.
     * 
     * @param votes Map of votes.
     * @param fileName Output file.
     * @throws IOException On writing a file.
     */
    public static void votesToFile(Map<Integer, Integer> votes, String fileName)
            throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new BufferedOutputStream(new FileOutputStream(fileName)),
                "UTF-8"));

        synchronized (writer) {
            for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
                writer.write(String.format("%d\t%d%n", entry.getKey(),
                        entry.getValue()));
            }
            writer.flush();
            writer.close();
        }
    }

    /**
     * Loads votes from given input file to votes map.
     * 
     * @param fileName Input file path.
     * @return Map with loaded votes for each band.
     */
    public static Map<Integer, Integer> getVotes(String fileName) {
        Path filePath = Paths.get(fileName);
        Map<Integer, Integer> votes = new HashMap<>();
        if (Files.isRegularFile(filePath)) {
            try {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    String[] parts = line.split("\t");
                    votes.put(Integer.parseInt(parts[0].trim()),
                            Integer.parseInt(parts[1].trim()));
                }
            } catch (Exception ex) {
            }
        }
        return votes;
    }

    /**
     * Creates 3D pie chart form given list of {@link Band}.
     * 
     * @param results List of results.
     * @return Filled 3D pie chart.
     */
    public static JFreeChart getChart(List<Band> results) {
        DefaultPieDataset data = new DefaultPieDataset();
        for (Band result : results) {
            data.setValue(result.getName(), result.getVotes());
        }
        JFreeChart chart = ChartFactory.createPieChart3D("", data);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        return chart;
    }

    /**
     * Returns formated time interval with days, hours, minutes, seconds and milliseconds. If some
     * of mentioned values is equal to zero, that value is not added to formatted string.
     * 
     * @param startTime Start time.
     * @return Formatted string.
     */
    public static String getInterval(long startTime) {
        long diff = System.currentTimeMillis() - startTime;

        long days = diff / (1000 * 60 * 60 * 24);
        diff -= days * (1000 * 60 * 60 * 24);

        long hours = diff / (1000 * 60 * 60);
        diff -= hours * (1000 * 60 * 60);

        long mins = diff / (1000 * 60);
        diff -= mins * (1000 * 60);

        long secs = diff / 1000;
        diff -= secs * 1000;

        long mils = diff;

        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(days + " days, ");
        }
        if (hours > 0) {
            builder.append(hours + " hours, ");
        }
        if (mins > 0) {
            builder.append(mins + " minutes, ");
        }
        if (secs > 0) {
            builder.append(secs + " seconds, ");
        }
        if (mils > 0) {
            builder.append(mils + " milliseconds");
        }
        return builder.toString();
    }

    /**
     * Creates XLS document based from given list of bands.
     * 
     * @param results List of bands.
     * @return XLS document.
     */
    public static HSSFWorkbook getXLS(List<Band> results) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Voting results");
        HSSFRow rowHead = sheet.createRow(0);
        rowHead.createCell(0).setCellValue("Band name:");
        rowHead.createCell(1).setCellValue("Number of votes:");

        for (int i = 0, size = results.size(); i < size; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(results.get(i).getName());
            row.createCell(1).setCellValue(
                    Double.valueOf(results.get(i).getVotes()));
        }
        return workbook;
    }

    /**
     * Creates and fills list of bands with current voting state.
     * 
     * @param fileDefinition Input file with list of bands.
     * @param fileResults Input file with number of votes for each band.
     * @return Result list.
     * @throws IOException On reading a files.
     */
    public static List<Band> getResults(String fileDefinition,
            String fileResults) throws IOException {
        List<String> definition = Files.readAllLines(Paths.get(fileDefinition));
        Map<Integer, Integer> resultMap = getVotes(fileResults);
        List<Band> resultList = new ArrayList<>();

        for (String line : definition) {
            String[] parts = line.split("\t");
            Integer id = Integer.parseInt(parts[0].trim());
            Integer votes = resultMap.get(id) != null ? resultMap.get(id) : 0;
            resultList
                    .add(new Band(parts[1].trim(), parts[2].trim(), votes, id));
        }
        resultList.sort(new BandComparator());
        return resultList;
    }
}
