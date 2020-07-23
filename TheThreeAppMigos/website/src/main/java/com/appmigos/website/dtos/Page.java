/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.dtos;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class Page {
    
    private int id;
    
    @NotBlank(message="Please insert a title")
    @Size(max=60, message="Title cannot be more than 60 characters long.")
    private String title;
    @NotBlank(message="Please insert some content") //TODO: make this more user friendly 
    @Size(max=65535, message="Content of article must be less than 65,535 characters")
    private String content;
    
    // blank constructor
    public Page(){
        
    }
    
    // this constructor is used in Page creation
    public Page(String title, String content){
        this.title = title;
        this.content = content;
    }
    
    // this constructor is used in Testing primarily, if you find another use for it, GREAT!
    public Page(int id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.title);
        hash = 89 * hash + Objects.hashCode(this.content);
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
        final Page other = (Page) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
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
}
