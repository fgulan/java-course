package hr.fer.zemris.java.tecaj_15.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_15.model.BlogUser;

/**
 * UserForm class represents an user form in web application for registration of
 * a new blog user.
 * 
 * @author Filip
 * @version 1.0
 *
 */
public class UserForm {

    /** First name. */
    private String firstName;
    /** Last name. */
    private String lastName;
    /** Users nick. */
    private String nick;
    /** Email address. */
    private String email;
    /** Password. */
    private String password;
    /** Form errors. */
    private Map<String, String> errors = new HashMap<>();

    /**
     * Returns current user first name.
     * 
     * @return Current user first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new first name for current user.
     * 
     * @param firstName A new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns current user last name.
     * 
     * @return Current user last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new last name for current user.
     * 
     * @param lastName A new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns current user nickname.
     * 
     * @return Current user nickname.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets a new nickname for current user.
     * 
     * @param nick A new nickname.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Returns current user email address.
     * 
     * @return Current user email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email address for current user.
     * 
     * @param email A new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a password for current user.
     * 
     * @return Current user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a new password for current user.
     * 
     * @param password A new password.
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Returns errors status for current user form.
     * 
     * @return <code>true</code> if current form have errors,
     *         <code>false</code> otherwise.
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
     * Factory method for creating an UserForm from given
     * {@link HttpServletRequest} instance.
     * 
     * @param req {@link HttpServletRequest} instance.
     */
    public void fillFromHttpReq(HttpServletRequest req) {
        firstName = prepareArgument(req.getParameter("firstName"));
        lastName = prepareArgument(req.getParameter("lastName"));
        email = prepareArgument(req.getParameter("email"));
        nick = prepareArgument(req.getParameter("nick"));
        password = prepareArgument(req.getParameter("password"));
    }

    /**
     * Prepares given argument.
     * 
     * @param arg Input argument.
     * @return Trimmed and prepared argument.
     */
    private String prepareArgument(String arg) {
        if (arg == null) {
            return "";
        }
        return arg.trim();
    }

    /**
     * Fills given blog user with data from user form.
     * 
     * @param user Output blog user instance.
     */
    public void fillBlogUser(BlogUser user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNick(nick);
        user.setPasswordHash(password);
    }

    /**
     * Checks and validates current user from.
     */
    public void validate() {
        if (email.isEmpty()) {
            errors.put("email", "Korisnik mora imati e-mail adresu!");
        } else if (!emailMatch(email)) {
            errors.put("email", "Neispravana e-mail adresa!");
        }
        if (nick.isEmpty()) {
            errors.put("nick", "Korisnik mora imati nadimak!");
        }
        if (firstName.isEmpty()) {
            errors.put("firstName", "Korisnik mora imati ime!");
        }
        if (lastName.isEmpty()) {
            errors.put("lastName", "Korisnik mora imati prezime!");
        }
        if (password.isEmpty()) {
            errors.put("password", "Korisnik mora imati lozinku!");
        }
    }

    /**
     * Puts an error with given name and message in errors map.
     * 
     * @param name Error title.
     * @param message Error message.
     */
    public void putError(String name, String message) {
        errors.put(name, message);
    }

    /**
     * Fill current form instance from given blog user.
     * 
     * @param user {@link BlogUser} instance.
     */
    public void fillFromBlogUser(BlogUser user) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        nick = user.getNick();
        password = user.getPasswordHash();
    }

    /**
     * Checks if given arguments is valid email address.
     * 
     * @param email Input argument.
     * @return <code>true</code> if given argument is valid email address,
     *         <code>false</code> otherwise.
     */
    private boolean emailMatch(String email) {
        return email
                .matches("[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
    }
}
