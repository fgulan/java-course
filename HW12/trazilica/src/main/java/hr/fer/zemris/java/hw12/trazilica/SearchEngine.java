package hr.fer.zemris.java.hw12.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * SearchEngine is simple implementation of document search engine. Using tf-idf
 * vectors, calculates similarity between each given file with given query
 * input. On creation, it accepts path to a folder with documents which user
 * wants to analyze and path to a file which contains stopwords for documents
 * language.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class SearchEngine {
    /** Stopwords set. */
    private Set<String> stopWords;
    /** Vocabulary. */
    private Set<String> vocabulary;
    /** Documents folder path. */
    private String documentsPath;

    /** Tf-Idf vectors. */
    private List<double[]> tfIdfVectors = new ArrayList<double[]>(61);
    /** Map with tf vector for each file. */
    private Map<File, Map<String, Integer>> tfMap;
    /** Vocabulary idf map. */
    private Map<String, Integer> idfMap;
    /** List of files. */
    private List<File> files;
    /** Stopwords file input stream. */
    private InputStream stopwords;

    /**
     * Constructor for SearchEngine class. It accepts path to a folder with
     * documents to analyze.
     * 
     * @param documentsPath
     *            Documents folder path.
     * @param stopwords
     *            Stopwords file stream.
     * @throws IOException
     *             On reading given folder.
     */
    public SearchEngine(String documentsPath, InputStream stopwords)
            throws IOException {

        this.stopwords = stopwords;
        this.documentsPath = documentsPath;
        getStopWords();
        loadFiles();
        parseFiles();
        tfIdfCalculator();
    }

    /**
     * Parse given files. Fills vocabulary, tf and idf collections.
     * 
     * @throws IOException
     *             On error with reading document.
     */
    private void parseFiles() throws IOException {
        idfMap = new HashMap<String, Integer>();
        tfMap = new HashMap<File, Map<String, Integer>>();
        vocabulary = new HashSet<String>();
        Pattern p = Pattern.compile("[a-zA-ZšđžčćŠĐŽČĆ]+");
        for (File file : files) {
            if (file.isFile()) {
                Map<String, Integer> temp = new HashMap<String, Integer>();
                Set<String> terms = new HashSet<String>();
                String document = getTextFile(file);
                Matcher m = p.matcher(document);
                while (m.find()) {
                    String word = document.substring(m.start(), m.end())
                            .toLowerCase();
                    if (!stopWords.contains(word)) {
                        vocabulary.add(word);
                        fillIdfMap(terms, word);
                        fillTfMap(word, temp);
                    }
                }
                tfMap.put(file, temp);
            }
        }
    }

    /**
     * Fills idf map with given term.
     * 
     * @param terms
     *            Document terms.
     * @param term
     *            Current term.
     */
    private void fillIdfMap(Set<String> terms, String term) {
        if (terms.add(term)) {
            Integer value = idfMap.get(term);
            if (value == null) {
                value = 0;
            }
            idfMap.put(term, value + 1);
        }
    }

    /**
     * Fills given map with tf values based on given term.
     * 
     * @param term
     *            Current term.
     * @param temp
     *            Temporary tf map.
     */
    private void fillTfMap(String term, Map<String, Integer> temp) {
        Integer value = temp.get(term);
        if (value == null) {
            temp.put(term, 1);
        } else {
            temp.put(term, value + 1);
        }
    }

    /**
     * Returns a text representation of a given file.
     * 
     * @param file
     *            File to read.
     * @return A text representation of a given file.
     * @throws IOException
     *             On error with reading given file.
     */
    private String getTextFile(File file) throws IOException {
        StringBuilder docBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

            while (line != null) {
                docBody.append(line);
                docBody.append(System.lineSeparator());
                line = reader.readLine();
            }
        }
        return docBody.toString();
    }

    /**
     * Fills stopwords collection from stopwords file.
     * 
     * @throws IOException
     *             On error with reading a file.
     */
    private void getStopWords() throws IOException {
        stopWords = new HashSet<String>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                stopwords, StandardCharsets.UTF_8))) {
            String word = reader.readLine().toLowerCase();

            while (word != null && !word.isEmpty()) {
                stopWords.add(word);
                word = reader.readLine();
            }
        }
    }

    /**
     * Loads all files from given folder path.
     * 
     * @throws IOException
     *             On error with reading a file.
     */
    private void loadFiles() throws IOException {
        files = Files.walk(Paths.get(documentsPath))
                .filter(Files::isRegularFile).map(Path::toFile)
                .collect(Collectors.toList());
    }

    /**
     * Calculates tf-idf vector for each file.
     */
    private void tfIdfCalculator() {
        double idf;
        double tfidf;
        int size = files.size();
        for (File file : files) {
            double[] tfidfvectors = new double[vocabulary.size()];
            int count = 0;
            for (String terms : vocabulary) {
                Integer tf2 = tfMap.get(file).get(terms);
                if (tf2 == null) {
                    tf2 = 0;
                }
                idf = Math.log(size / (double) idfMap.get(terms));
                tfidf = tf2 * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfIdfVectors.add(tfidfvectors);
        }
    }

    /**
     * Returns current vocabulary size.
     * 
     * @return Current vocabulary size.
     */
    public int getVocabularySize() {
        return vocabulary.size();
    }

    /**
     * Checks if given term is in vocabulary.
     * 
     * @param word
     *            Given term.
     * @return <code>true</code> if term is in vocabulary, <code>false</code>
     *         otherwise.
     */
    public boolean inVocabulary(String word) {
        return vocabulary.contains(word);
    }

    /**
     * Returns file at given position.
     * 
     * @param index
     *            Given index.
     * @return File at given position.
     */
    public File getFile(int index) {
        return files.get(index);
    }

    /**
     * Returns array of tf-idf vectors for each file.
     * 
     * @return Array of tf-idf vectors for each file.
     */
    public List<double[]> getTfidfVectors() {
        return tfIdfVectors;
    }

    /**
     * Returns tf-idf vector for given query input.
     * 
     * @param words
     *            Query input.
     * @return Tf-idf vector for given query input.
     */
    public double[] getQuerryVector(List<String> words) {
        double tf;
        double idf;
        double tfidf;
        double[] tfidfvectors = new double[vocabulary.size()];
        int count = 0;
        for (String terms : vocabulary) {
            if (words.contains(terms)) {
                tf = 1;
                idf = Math.log((files.size()) / (double) (idfMap.get(terms)));
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            } else {
                tfidfvectors[count] = 0;
                count++;
            }
        }
        return tfidfvectors;
    }

    /**
     * Calculates cosine similarity between two given vectors.
     * 
     * @param first
     *            First vector.
     * @param second
     *            Second vector.
     * @return Cosine similarity between two given vectors.
     * @throws IllegalArgumentException
     *             If given vectors does not have the same size.
     */
    public double getCosineSimilarity(double[] first, double[] second) {

        if (first.length != second.length) {
            throw new IllegalArgumentException(
                    "Given vectors must have same size!");
        }
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < first.length; i++) {
            dotProduct += first[i] * second[i];
            magnitude1 += Math.pow(first[i], 2);
            magnitude2 += Math.pow(second[i], 2);
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        }
        return cosineSimilarity;
    }
}
