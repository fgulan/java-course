package hr.fer.zemris.java.tecaj_15.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * BlogUser class represents a registered blog user. It is an entity in
 * blog_users table. It has an list of blog entries form blog_entries table for
 * which he is an author.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {
    /** User id. */
    private Long id;
    /** First name. */
    private String firstName;
    /** Last name. */
    private String lastName;
    /** Nick. */
    private String nick;
    /** Email address. */
    private String email;
    /** Password hash value. */
    private String passwordHash;
    /** List of blog entries. */
    private List<BlogEntry> entries;

    /**
     * Returns current user ID.
     * 
     * @return Current user ID.
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets a new id for current user.
     * 
     * @param id New ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns user first name.
     * 
     * @return User first name.
     */
    @Column(length = 50, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets a new first name.
     * 
     * @param firstName New first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns user last name.
     * 
     * @return User last name.
     */
    @Column(length = 50, nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets a new last name for current user.
     * 
     * @param lastName New last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns nick for current user.
     * 
     * @return Current user nickname.
     */
    @Column(length = 50, nullable = false, unique = true)
    public String getNick() {
        return nick;
    }

    /**
     * Sets a new nick for current user.
     * 
     * @param nick A new nick.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Returns current user email address.
     * 
     * @return Current user email address.
     */
    @Column(length = 50, nullable = false)
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
     * Returns hash value for current user password.
     * 
     * @return Hash value for current user password.
     */
    @Column(length = 50, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets a hashed password value for current user.
     * 
     * @param passwordHash Hash value of a password.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets all blog entries for which current user is author.
     * 
     * @return List of current user blog entries.
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("createdAt")
    public List<BlogEntry> getEntries() {
        return entries;
    }

    /**
     * Sets a given list of blog entries for current user.
     * 
     * @param entries A list of blog entries.
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (nick == null ? 0 : nick.hashCode());
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
        BlogUser other = (BlogUser) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (nick == null) {
            if (other.nick != null) {
                return false;
            }
        } else if (!nick.equals(other.nick)) {
            return false;
        }
        return true;
    }

}
