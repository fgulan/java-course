package hr.fer.zemris.java.tecaj_15.forms;

import hr.fer.zemris.java.tecaj_15.model.BlogEntry;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * EntryForm class represents an input form in web application for creating a
 * new blog entry.
 *
 * @author Filip
 * @version 1.0
 *
 */
public class EntryForm {

    /** Entry id. */
    private String id;
    /** Entry title. */
    private String title;
    /** Entry text. */
    private String text;
    /** Entry errors. */
    private Map<String, String> errors = new HashMap<>();

    /**
     * Constructor for empty entry form.
     */
    public EntryForm() {
        this("", "", "");
    }

    /**
     * Constructor for entry form with given input parameters.
     *
     * @param id Entry id.
     * @param title Entry title
     * @param text Entry text.
     */
    public EntryForm(String id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    /**
     * Returns current entry ID.
     *
     * @return Current entry ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets a new ID for current entry form.
     *
     * @param id New ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns current entry form title.
     *
     * @return Current entry form title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for current entry form.
     *
     * @param title New title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns current entry form text.
     *
     * @return Current entry form text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets a new text for current entry form.
     *
     * @param text New text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns description for given error key.
     *
     * @param title Error key.
     * @return Error description.
     */
    public String getError(String title) {
        return errors.get(title);
    }

    /**
     * Returns errors status for current entry form.
     *
     * @return <code>true</code> if current form have errors, <code>false</code>
     *         otherwise.
     */
    public boolean haveErrors() {
        return !errors.isEmpty();
    }

    /**
     * Checks if form has an error with given key.
     *
     * @param title Error key.
     * @return <code>true</code> if current form have error with given name,
     *         <code>false</code> otherwise.
     */
    public boolean hasError(String title) {
        return errors.containsKey(title);
    }

    /**
     * Factory method for creating an EntryForm from given
     * {@link HttpServletRequest} instance.
     *
     * @param req {@link HttpServletRequest} instance.
     * @return EntryForm created from http request.
     */
    public static EntryForm createFromHttpReq(HttpServletRequest req) {
        String title = prepareArgument(req.getParameter("title"));
        String text = prepareArgument(req.getParameter("text"));
        String id = prepareArgument(req.getParameter("id"));
        return new EntryForm(id, title, text);
    }

    /**
     * Prepares given argument.
     *
     * @param arg Input argument.
     * @return Trimmed and prepared argument.
     */
    private static String prepareArgument(String arg) {
        if (arg == null) {
            return "";
        }
        return arg.trim();
    }

    /**
     * Fills given blog entry with data from entry form.
     *
     * @param entry Output entry.
     */
    public void fillBlogEntry(BlogEntry entry) {
        entry.setTitle(title);
        entry.setText(text);
    }

    /**
     * Checks and validates current entry from.
     */
    public void validate() {
        if (title.isEmpty()) {
            errors.put("title", "Naslov mora biti zadan");
        }
    }

    /**
     * Factory method for creating an entry form from given {@link BlogEntry}
     * instance.
     *
     * @param entry Input blog entry.
     * @return EntryForm created from BlogEntry.
     */
    public static EntryForm createFromBlogEntry(BlogEntry entry) {
        String text = entry.getText();
        String title = entry.getTitle();
        String id;
        if (entry.getId() != null) {
            id = String.valueOf(entry.getId());
        } else {
            id = "";
        }
        return new EntryForm(id, title, text);
    }

}
