/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.LocationDao;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class LocationService {

    @Autowired
    LocationDao dao;

    public List<Location> getAll() throws NoItemsException {
        List<Location> allLocations = dao.getAllLocations();
        if (allLocations.isEmpty()) {
            throw new NoItemsException("No locations found");
        }
        return allLocations;
    }

    public Location getLocationById(Integer id) throws InvalidIdException {
        Location toReturn = null;

        try {
            toReturn = dao.getLocationById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("The Id entered is invalid");
        }

        return toReturn;
    }

    public Location addLocation(Location toAdd) throws InvalidEntityException {
        validateLocation(toAdd);

        return dao.createLocation(toAdd);
    }

    public void editLocation(Location toEdit) throws InvalidEntityException, InvalidIdException {
        validateLocation(toEdit);

        if (dao.editLocation(toEdit) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    public void deleteLocationById(Integer id) throws InvalidIdException {
        if (dao.removeLocation(id) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    private void validateLocation(Location toAdd) throws InvalidEntityException {
        if (toAdd.getName() == null || toAdd.getName().trim().length() == 0
                || toAdd.getName().trim().length() > 30 || toAdd.getDescription() == null
                || toAdd.getDescription().trim().length() == 0 || toAdd.getDescription().trim().length() > 255
                || toAdd.getAddress() == null || toAdd.getAddress().trim().length() == 0 
                || toAdd.getAddress().trim().length() > 200
                || toAdd.getLatitude() == null || toAdd.getLatitude().compareTo(new BigDecimal("-90.0000000")) == -1
                || toAdd.getLatitude().compareTo(new BigDecimal("90.0000000")) == 1 || toAdd.getLongitude() == null
                || toAdd.getLongitude().compareTo(new BigDecimal("-180.0000000")) == -1
                || toAdd.getLongitude().compareTo(new BigDecimal("180.0000000")) == 1) {
            throw new InvalidEntityException("Fields are invalid");
        }
    }

    public Location getLocationByName(String name) throws InvalidNameException, InvalidEntityException {
        Location toReturn = null;
        
        if(name == null || name.trim().length() == 0){
            throw new InvalidEntityException("Invalid Name");
        }

        try {
            toReturn = dao.getLocationByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("The Name entered is invalid");
        }

        return toReturn;
    }

}
