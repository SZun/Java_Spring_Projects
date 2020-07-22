/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.OrganizationDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author samg.zun
 */
@Service
public class OrganizationService {

    @Autowired
    OrganizationDao dao;

    public List<Organization> getAll() throws NoItemsException {
        List<Organization> allOrganizations = dao.getAllOrganizations();

        if (allOrganizations.isEmpty()) {
            throw new NoItemsException("No organizations found");
        }

        return allOrganizations;
    }

    public Organization getOrganizationById(Integer id) throws InvalidIdException {
        Organization toReturn = null;

        try {
            toReturn = dao.getOrganizationById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("The Id entered is invalid");
        }

        return toReturn;
    }

    public Organization addOrganization(Organization toAdd) throws InvalidEntityException {
        validateOrganization(toAdd);

        return dao.createOrganization(toAdd);
    }

    public void editOrganization(Organization toEdit) throws InvalidIdException, InvalidEntityException {
        validateOrganization(toEdit);

        if (dao.editOrganization(toEdit) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    public void deleteOrganizationById(Integer id) throws InvalidIdException {
        if (dao.removeOrganization(id) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    private void validateOrganization(Organization toAdd) throws InvalidEntityException {
        if (toAdd.getName() == null || toAdd.getName().trim().length() == 0 || toAdd.getName().trim().length() > 50
                || toAdd.getAddress() == null || toAdd.getAddress().trim().length() == 0
                || toAdd.getAddress().trim().length() > 200
                || toAdd.getEmail() == null || toAdd.getEmail().trim().length() == 0
                || toAdd.getEmail().trim().length() > 255
                || !toAdd.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidEntityException("Fields are invalid");
        }
    }

    public void addMember(List<Organization> orgs, int superId) throws InvalidIdException {
        if (dao.addMember(orgs, superId) == 0) {
            throw new InvalidIdException("The Id entered is invalid");
        }
    }

    public List<Organization> getOrganizationsBySuper(int id) throws NoItemsException {
        List<Organization> allOrganizations = dao.getOrganizationsBySuperheroId(id);

        if (allOrganizations.isEmpty()) {
            throw new NoItemsException("No organizations found");
        }

        return allOrganizations;
    }

    public Organization getOrganizationByName(String name) throws InvalidNameException, InvalidEntityException {
        Organization toReturn = null;
        
        if(name == null || name.trim().length() == 0){
            throw new InvalidEntityException("Invalid Name");
        }

        try {
            toReturn = dao.getOrganizationByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("The Name entered is invalid");
        }

        return toReturn;
    }

}
