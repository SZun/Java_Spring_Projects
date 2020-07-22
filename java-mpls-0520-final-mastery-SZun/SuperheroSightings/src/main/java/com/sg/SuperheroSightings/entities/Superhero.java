/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author samg.zun
 */
public class Superhero {

    private int id;
    @NotBlank(message = "Name must not be blank")
    @Size(max = 30, message = "Name must not be more than 30 characters")
    private String name;
    @NotBlank(message = "Description must not be blank")
    @Size(max = 255, message = "Description must not be more than 255 characters")
    private String description;
    private List<Organization> organizations;
    private Power power;

    public Superhero() {
    }
    
    public Superhero(Superhero that) {
        this.id = that.id;
        this.name = that.name;
        this.description = that.description;
        this.power = that.power;
        this.organizations = that.organizations;
    }

    public Superhero(int id, String name, String description, Power power, List<Organization> organizations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.power = power;
        this.organizations = organizations;
    }

    public Superhero(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.getId();
        hash = 29 * hash + Objects.hashCode(this.getName());
        hash = 29 * hash + Objects.hashCode(this.getDescription());
        hash = 29 * hash + Objects.hashCode(this.getOrganizations());
        hash = 29 * hash + Objects.hashCode(this.getPower());
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
        final Superhero other = (Superhero) obj;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        if (!Objects.equals(this.power, other.power)) {
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the organizations
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }

    /**
     * @param organizations the organizations to set
     */
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    /**
     * @return the power
     */
    public Power getPower() {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(Power power) {
        this.power = power;
    }
    
    

}
