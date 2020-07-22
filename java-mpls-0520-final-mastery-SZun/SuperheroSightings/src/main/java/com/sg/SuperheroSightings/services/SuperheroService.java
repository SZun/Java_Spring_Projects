/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.SuperheroDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class SuperheroService {

    @Autowired
    SuperheroDao dao;

    public List<Superhero> getAll() throws NoItemsException {
        List<Superhero> allSuperheros = dao.getAllSuperheros();

        if (allSuperheros.isEmpty()) {
            throw new NoItemsException("No superheroes found");
        }

        return allSuperheros;
    }

    public Superhero getSuperheroById(Integer id) throws InvalidIdException {
        Superhero toReturn = null;

        try {
            toReturn = dao.getSuperheroById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("The Id entered is invalid");
        }

        return toReturn;
    }

    public Superhero getSuperheroByName(String name) throws InvalidNameException, InvalidEntityException {
        Superhero toReturn = null;
        
        if(name == null || name.trim().length() == 0){
            throw new InvalidEntityException("Invalid Name");
        }

        try {
            toReturn = dao.getSuperheroByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("The Name entered is invalid");
        }

        return toReturn;
    }

    public Superhero addSuperhero(Superhero toAdd) throws InvalidEntityException {
        validateSuperhero(toAdd);

        return dao.createSuperhero(toAdd);
    }

    public void editSuperhero(Superhero toEdit) throws InvalidIdException, InvalidEntityException {
        validateSuperhero(toEdit);

        if (dao.editSuperhero(toEdit) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    public void deleteSuperheroById(Integer id) throws InvalidIdException {
        if (dao.removeSuperhero(id) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }

    }

    private void validateSuperhero(Superhero toAdd) throws InvalidEntityException {
        if (toAdd.getName() == null || toAdd.getName().trim().length() == 0 || toAdd.getName().trim().length() > 30
                || toAdd.getDescription() == null || toAdd.getDescription().trim().length() == 0
                || toAdd.getDescription().trim().length() > 255 || toAdd.getPower() == null) {
            throw new InvalidEntityException("Fields are invalid");
        }
    }

}
