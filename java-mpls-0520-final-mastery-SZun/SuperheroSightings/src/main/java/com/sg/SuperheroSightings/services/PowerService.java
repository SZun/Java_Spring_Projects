/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.PowerDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class PowerService {

    @Autowired
    PowerDao dao;

    public List<Power> getAll() throws NoItemsException {
        List<Power> allPowers = dao.getAllPowers();

        if (allPowers.isEmpty()) {
            throw new NoItemsException("No powers found");
        }

        return allPowers;
    }

    public Power getPowerByName(String name) throws InvalidNameException, InvalidEntityException {
        Power toReturn = null;
        
        if(name == null || name.trim().length() == 0){
            throw new InvalidEntityException("Invalid Name");
        }

        try {
            toReturn = dao.getPowerByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("The Name entered is invalid");
        }

        return toReturn;
    }

    public Power getPowerById(Integer id) throws InvalidIdException {
        Power toReturn = null;

        try {
            toReturn = dao.getPowerById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("The Id entered is invalid");
        }

        return toReturn;
    }

    public Power addPower(Power toAdd) throws InvalidEntityException {
        validatePower(toAdd);

        return dao.createPower(toAdd);
    }

    public void editPower(Power toEdit) throws InvalidIdException, InvalidEntityException {
        validatePower(toEdit);

        if (dao.editPower(toEdit) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    public void deletePowerById(Integer id) throws InvalidIdException {

        if (dao.removePower(id) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    private void validatePower(Power toAdd) throws InvalidEntityException {
        if (toAdd.getName() == null || toAdd.getName().trim().length() == 0
                || toAdd.getName().trim().length() > 255) {
            throw new InvalidEntityException("Fields are invalid");
        }
    }

}
