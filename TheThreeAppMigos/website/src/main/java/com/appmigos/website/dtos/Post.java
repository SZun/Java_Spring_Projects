/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.dtos;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class Post {

    private int id;
    @NotBlank(message = "Please insert a title")
    @Size(max = 60, message = "Title cannot be more than 60 characters long.")
    private String title;
    @NotBlank(message = "Please insert an Author name")
    @Size(max = 50, message = "Author name must be less than 50 characters long.")
    private String author;
    @NotBlank(message = "Please insert some content") //TODO: make this more user friendly 
    @Size(max = 65535, message = "Content of article must be less than 65,535 characters long.")
    private String content;
    private Set<Tag> tags;
    private boolean approved;
    private LocalDate start;
    @Pattern(regexp = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "Date improperly formatted")
    @NotBlank(message = "Date is required")
    @Size(min = 10, max = 10, message = "Date must be 10 characters")
    private String startDateString;
    private LocalDate end;
    @Pattern(regexp = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "Date improperly formatted")
    @NotBlank(message = "Date is required")
    @Size(min = 10, max = 10, message = "Date must be 10 characters")
    private String endDateString;

    // blank constructor
    public Post() {

    }

    // used for Post creation and such
    public Post(String title, String author, String content, Set<Tag> tags, boolean approved, LocalDate start, LocalDate end) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.tags = tags;
        this.approved = approved;
        this.start = start;
        this.end = end;
    }

    // used for testing purposes
    public Post(int id, String title, String author, String content, Set<Tag> tags, boolean approved, LocalDate start, LocalDate end) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.tags = tags;
        this.approved = approved;
        this.start = start;
        this.end = end;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.author);
        hash = 37 * hash + Objects.hashCode(this.content);
        hash = 37 * hash + Objects.hashCode(this.tags);
        hash = 37 * hash + (this.approved ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.start);
        hash = 37 * hash + Objects.hashCode(this.startDateString);
        hash = 37 * hash + Objects.hashCode(this.end);
        hash = 37 * hash + Objects.hashCode(this.endDateString);
        return hash;
    }

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
        final Post other = (Post) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.approved != other.approved) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.startDateString, other.startDateString)) {
            return false;
        }
        if (!Objects.equals(this.endDateString, other.endDateString)) {
            return false;
        }
        if (!Objects.equals(this.tags, other.tags)) {
            return false;
        }
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        return true;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * @return the start
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(LocalDate start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * @return the startDateString
     */
    public String getStartDateString() {
        return startDateString;
    }

    /**
     * @param startDateString the startDateString to set
     */
    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    /**
     * @return the endDateString
     */
    public String getEndDateString() {
        return endDateString;
    }

    /**
     * @param endDateString the endDateString to set
     */
    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

}
