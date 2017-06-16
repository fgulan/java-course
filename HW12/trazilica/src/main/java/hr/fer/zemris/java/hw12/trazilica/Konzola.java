package hr.fer.zemris.java.hw12.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Programs simulate simple console for setting query over given documents.
 * Program accepts single command line argument, path to a folder with documents
 * to analyze.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class Konzola {

    /** Result map. */
    private static Map<Integer, Result> resultMap;
    /** Result set. */
    private static Set<Result> resultSet;
    /** Search engine. */
    private static SearchEngine engine;

    /**
     * Start point of program.
     * 
     * @param args
     *            Command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out
                    .println("Please provide path to a folder with articles!");
            System.exit(-1);
        }

        try {
            InputStream stopwords = Konzola.class.getClassLoader()
                    .getResourceAsStream("stopwords_hr.txt");
            String folderPath = args[0];
            System.out.println("Loading...");
            engine = new SearchEngine(folderPath, stopwords);
            System.out
                    .println("Vocabulary size: " + engine.getVocabularySize());
            console();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " Closing...");
            return;
        }
    }

    /**
     * Emulates simple console to inquiry over given documents.
     * 
     * @throws IOException
     *             On error with reading from console.
     */
    private static void console() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in, StandardCharsets.UTF_8));
        while (true) {
            System.out.println();
            System.out.print("Enter command > ");
            String command = reader.readLine().trim();
            if (command == null || command.isEmpty()) {
                continue;
            } else if (command.equals("exit")) {
                return;
            } else if (command.startsWith("query ")) {
                processQuery(command);
            } else if (command.equals("results")) {
                if (resultSet == null) {
                    System.out.println("There is no last result!");
                } else {
                    printLastResult();
                }
            } else if (command.startsWith("type ")) {
                if (resultSet == null) {
                    System.out.println("There is no last result!");
                } else {
                    printDocument(command);
                }
            } else {
                System.out.println("Unkonwn command: " + command);
            }
        }
    }

    /**
     * Prints given string on console.
     * 
     * @param command
     *            Input string.
     */
    private static void printDocument(String command) {
        command = command.substring(4).trim();
        String[] input = command.split("\\s+");
        if (input.length != 1) {
            System.out
                    .println("Invalid number of arguments for command 'type'.");
        } else {
            try {
                int index = Integer.parseInt(input[0]);
                Result result = resultMap.get(index);
                if (result == null) {
                    System.out.println("Invalid index!");
                    return;
                }
                printFile(result.getFile());
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument for command 'type'. "
                        + "Please provide result index.");
            }
        }
        return;
    }

    /**
     * Prints given file text.
     * 
     * @param file
     *            Input file.
     */
    private static void printFile(File file) {
        System.out
                .println("---------------------------------------------------"
                        + "-------------------------------------------------");
        System.out.println("Document: " + file.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Problem with reading a file!");
        } catch (IOException e) {
            System.out.println("Problem with reading a file!");
        }

        System.out
                .println("---------------------------------------------------"
                        + "-------------------------------------------------");
    }

    /**
     * Process given query command.
     * 
     * @param command
     *            Query command.
     */
    private static void processQuery(String command) {
        command = command.substring(5).trim();
        String[] words = command.split("\\s+");
        List<String> queryInput = new ArrayList<String>();

        for (String word : words) {
            if (!engine.inVocabulary(word)) {
                continue;
            }
            queryInput.add(word);
        }
        printQueryInput(queryInput);
        printResult(queryInput);
    }

    /**
     * Prints calculated result from query.
     * 
     * @param queryInput
     *            Input words.
     */
    private static void printResult(List<String> queryInput) {
        double[] vector = engine.getQuerryVector(queryInput);
        int counter = 0;
        resultSet = new TreeSet<Result>(new ResultComparator());
        resultMap = new HashMap<Integer, Result>();

        for (double[] d : engine.getTfidfVectors()) {
            double result = engine.getCosineSimilarity(vector, d);
            if (result >= 0.00005) {
                resultSet.add(new Result(engine.getFile(counter), result,
                        counter));
            }
            counter++;
        }

        counter = 0;
        for (Result result : resultSet) {
            if (counter >= 10) {
                break;
            }
            resultMap.put(counter, result);
            counter++;
        }
        printLastResult();
    }

    /**
     * Prints last calculated result.
     */
    private static void printLastResult() {
        int counter = 0;
        DecimalFormat df = new DecimalFormat("####0.0000");
        for (Result result : resultSet) {
            if (counter >= 10) {
                break;
            }
            System.out.println("[" + counter + "] ("
                    + df.format(result.getResult()) + ") " + result.getPath());
            counter++;
        }
    }

    /**
     * Prints query terms.
     * 
     * @param words
     *            Query terms.
     */
    private static void printQueryInput(List<String> words) {
        if (words.isEmpty()) {
            System.out
                    .println("Query does not contain any word from vocabulary.");
            return;
        }
        System.out.print("Query is: [");
        for (int i = 0, size = words.size(); i < size; i++) {
            if (i == size - 1) {
                System.out.println(words.get(i) + "]");
                break;
            }
            System.out.print(words.get(i) + ", ");
        }
        System.out.println("First ten results: ");
    }
}
