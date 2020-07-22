/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface OrganizationDao {

    public List<Organization> getAllOrganizations();

    public Organization createOrganization(Organization toAdd);

    public Organization getOrganizationById(Integer id) throws DaoException;

    public int editOrganization(Organization toEdit);

    public int removeOrganization(Integer id);

    public int addMember(List<Organization> orgs, int superId);

    public List<Organization> getOrganizationsBySuperheroId(int id);

    public Organization getOrganizationByName(String name) throws DaoException;
}
