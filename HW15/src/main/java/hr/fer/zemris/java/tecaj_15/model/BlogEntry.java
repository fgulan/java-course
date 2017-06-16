package hr.fer.zemris.java.tecaj_15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BlogEntry class represents blog entry in web application. It is an entity in
 * blog_entries table. It has an author from blog_users and list of comments
 * stored in blog_comments table.
 * 
 * @author Filip
 * @version 1.0
 *
 */
@Cacheable(true)
@Entity
@Table(name = "blog_entries")
public class BlogEntry {
    /** Blog entry id. */
    private Long id;
    /** List of current entry comments. */
    private List<BlogComment> comments = new ArrayList<>();
    /** Creation date. */
    private Date createdAt;
    /** Last modified date. */
    private Date lastModifiedAt;
    /** Entry title. */
    private String title;
    /** Blog post. */
    private String text;
    /** Blog author. */
    private BlogUser creator;

    /**
     * Returns current blog post ID.
     * 
     * @return Current blog post ID.
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets a new id for current blog entry.
     * 
     * @param id New ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns blog entry author.
     * 
     * @return Blog entry author.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Sets a given user as a author for current blog entry.
     * 
     * @param creator Author.
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    /**
     * Get all comments for current blog entry.
     * 
     * @return List of all comments for current blog entry.
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets given list of comments as comments for current blog entry.
     * 
     * @param comments List of comments.
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Returns creation date for current blog entry.
     * 
     * @return Creation date for curretn blog entry.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets a new creation date for current blog entry.
     * 
     * @param createdAt New creation date.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns a last modification date for current blog entry.
     * 
     * @return A last modification date for current blog entry.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets a new modification date for current blog entry.
     * 
     * @param lastModifiedAt A new modification date.
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Returns current blog entry title.
     * 
     * @return Current blog entry title.
     */
    @Column(nullable = false, length = 200)
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for current blog entry.
     * 
     * @param title New title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns current blog entry text.
     * 
     * @return Current blog entry text.
     */
    @Column(nullable = false, length = 4096)
    public String getText() {
        return text;
    }

    /**
     * Sets a new text for current blog entry.
     * 
     * @param text A new text.
     */
    public void setText(String text) {
        this.text = text;
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
        BlogEntry other = (BlogEntry) obj;
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
