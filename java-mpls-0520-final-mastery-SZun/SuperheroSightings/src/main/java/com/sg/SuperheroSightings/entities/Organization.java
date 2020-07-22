/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author samg.zun
 */
public class Organization {

    private int id;
    @NotBlank(message = "Name can not be blank")
    @Size(max = 50, message = "Name must not be more than 50 characters")
    private String name;
    @NotBlank(message = "Address must not be blank")
    @Size(max = 200, message = "Address must not be more than 200 characters")
    private String address;
    @Email(message = "Email is malformed")
    @NotBlank(message = "Email must not be blank")
    @Size(max = 255, message = "Email must not be more than 255 characters")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email must be properly formatted")
    private String email;

    private List<Superhero> members;

    public Organization() {
    }

    public Organization(int id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Organization(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Organization(int id, String name, String address, String email, List<Superhero> members) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.members = members;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the members
     */
    public List<Superhero> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(List<Superhero> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.address);
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.members);
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
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.members, other.members)) {
            return false;
        }
        return true;
    }

}
