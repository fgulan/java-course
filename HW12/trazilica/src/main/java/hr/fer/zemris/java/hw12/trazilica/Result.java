package hr.fer.zemris.java.hw12.trazilica;

import java.io.File;

/**
 * Result represents query result for each file with cosine similarity to query
 * input.
 * 
 * @author Filip Gulan.
 * @version 1.0
 *
 */
public class Result {

    /** Input file. */
    private File file;
    /** Search result. */
    private double result;
    /** File index. */
    private int fileIndex;

    /**
     * Constructor for {@link Result} class. It accepts file, its result and its
     * index in stored list.
     * 
     * @param file
     *            Input file.
     * @param result
     *            Search result.
     * @param fileIndex
     *            File index in list.
     */
    public Result(File file, double result, int fileIndex) {
        this.file = file;
        this.result = result;
        this.fileIndex = fileIndex;
    }

    /**
     * Returns file path.
     * 
     * @return File path.
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /**
     * Returns current file.
     * 
     * @return Current file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns search result for current file.
     * 
     * @return Search result for current file.
     */
    public double getResult() {
        return result;
    }

    /**
     * Returns current file index in list.
     * 
     * @return Current file index in list.
     */
    public int getFileIndex() {
        return fileIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        long temp;
        temp = Double.doubleToLongBits(this.result);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Result other = (Result) obj;
        if (file == null) {
            if (other.file != null) {
                return false;
            }
        } else if (!file.equals(other.file)) {
            return false;
        }
        if (Double.doubleToLongBits(result) != Double
                .doubleToLongBits(other.result)) {
            return false;
        }
        return true;
    }

}
