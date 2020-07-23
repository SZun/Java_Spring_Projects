/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.dtos;

import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Isaia
 */
public class User {

    private int id;
    @NotBlank(message="please enter a username")
    @Size(max=30, message="username cannot be more than 30 characters")
    private String userName;
    @NotBlank(message="please enter a password")
    @Size(max=100, message="password cannot be more than 100 characters")
    private String password;
    private boolean enabled;
    private Set<Role> role;

    
    // empty constructor
    public User(){
        
    }
    

    // creation constructor
    public User(String userName, String password, boolean enabled, Set<Role> role) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    // for testing purposes
    public User(int id, String userName, String password, boolean enabled, Set<Role> role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.userName);
        hash = 29 * hash + Objects.hashCode(this.password);
        hash = 29 * hash + (this.enabled ? 1 : 0);
        hash = 29 * hash + Objects.hashCode(this.role);
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.enabled != other.enabled) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the role
     */
    public Set<Role> getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
