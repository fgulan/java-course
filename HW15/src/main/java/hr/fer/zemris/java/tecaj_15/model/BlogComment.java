package hr.fer.zemris.java.tecaj_15.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BlogComment class represents a blog comment. It is an entity in blog_comments
 * table.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@Entity
@Table(name = "blog_comments")
@Cacheable(true)
public class BlogComment {

    /** Comment id. */
    private Long id;
    /** Parent blog entry. */
    private BlogEntry blogEntry;
    /** Comment author email. */
    private String usersEMail;
    /** Comment message. */
    private String message;
    /** Post date. */
    private Date postedOn;

    /**
     * Returns generated comment id.
     * 
     * @return Comment id.
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets a new id for current comment.
     * 
     * @param id New ID for current comment.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns current comment parent blog entry.
     * 
     * @return Current comment parent blog entry.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Sets a blog entry as parent for current comment.
     * 
     * @param blogEntry Blog entry.
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Returns current comment author email address.
     * 
     * @return Current comment author email address.
     */
    @Column(length = 100, nullable = false)
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Sets a user email for current comment.
     * 
     * @param usersEMail Users email.
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Gets a comment message.
     * 
     * @return A comment message.
     */
    @Column(length = 4096, nullable = false)
    public String getMessage() {
        return message;
    }

    /**
     * Sets a new comment message.
     * 
     * @param message New message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns post date for current comment.
     * 
     * @return Post date for current comment.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Sets a post date for current comment.
     * 
     * @param postedOn Post date for current comment.
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
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
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
