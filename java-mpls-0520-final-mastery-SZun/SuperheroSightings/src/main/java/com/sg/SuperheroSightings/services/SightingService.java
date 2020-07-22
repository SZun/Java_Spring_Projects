/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.SightingDao;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class SightingService {

    @Autowired
    SightingDao dao;

    public List<Sighting> getAll() throws NoItemsException {
        List<Sighting> allSightings = dao.getAllSightings();

        if (allSightings.isEmpty()) {
            throw new NoItemsException("No sightings found");
        }

        return allSightings;
    }

    public Sighting getSightingById(Integer id) throws InvalidIdException {
        Sighting toReturn = null;

        try {
            toReturn = dao.getSightingById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("The Id entered is invalid");
        }

        return toReturn;
    }

    public Sighting addSighting(Sighting toAdd) throws InvalidEntityException {
        validateSighting(toAdd);

        return dao.createSighting(toAdd);
    }

    public void editSighting(Sighting toEdit) throws InvalidEntityException, InvalidIdException {
        validateSighting(toEdit);

        if (dao.editSighting(toEdit) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    public void deleteSightingById(Integer id) throws InvalidIdException {
        if (dao.removeSighting(id) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    private void validateSighting(Sighting toAdd) throws InvalidEntityException {
        if (toAdd.getSightingDate() == null || toAdd.getSightingDate().isAfter(LocalDate.now())
                || toAdd.getLocation() == null || toAdd.getSuperhero() == null) {
            throw new InvalidEntityException("Fields are invalid");
        }
    }

}
