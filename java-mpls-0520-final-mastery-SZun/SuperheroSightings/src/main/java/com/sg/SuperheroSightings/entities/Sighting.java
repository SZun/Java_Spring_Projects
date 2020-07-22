/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.entities;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author samg.zun
 */
public class Sighting {

    private int id;
    private LocalDate sightingDate;
    @Pattern(regexp = "^(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", message = "Date improperly formatted")
    @NotBlank(message = "Date is required")
    @Size(min = 10, max = 10, message = "Date must be 10 characters")
    private String sightingDateStr;
    private Location location;
    private Superhero superhero;

    public Sighting() {
    }

    public Sighting(int id, LocalDate sightingDate, Location location, Superhero superhero) {
        this.id = id;
        this.sightingDate = sightingDate;
        this.location = location;
        this.superhero = superhero;
    }

    public Sighting(int id, LocalDate sightingDate, int locId, int heroId) {
        this.id = id;
        this.sightingDate = sightingDate;
        this.location.setId(locId);
        this.superhero.setId(heroId);
    }

    public Sighting(int id, LocalDate sightingDate) {
        this.id = id;
        this.sightingDate = sightingDate;
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
     * @return the sightingDate
     */
    public LocalDate getSightingDate() {
        return sightingDate;
    }

    /**
     * @param sightingDate the sightingDate to set
     */
    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    /**
     * @return the sightingDateStr
     */
    public String getSightingDateStr() {
        return sightingDateStr;
    }

    /**
     * @param sightingDate the sightingDate to set
     */
    public void setSightingDateStr(String sightingDateStr) {
        this.sightingDateStr = sightingDateStr;
    }

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return the superhero
     */
    public Superhero getSuperhero() {
        return superhero;
    }

    /**
     * @param superhero the superhero to set
     */
    public void setSuperhero(Superhero superhero) {
        this.superhero = superhero;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.sightingDate);
        hash = 71 * hash + Objects.hashCode(this.location);
        hash = 71 * hash + Objects.hashCode(this.superhero);
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
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superhero, other.superhero)) {
            return false;
        }
        return true;
    }

}
