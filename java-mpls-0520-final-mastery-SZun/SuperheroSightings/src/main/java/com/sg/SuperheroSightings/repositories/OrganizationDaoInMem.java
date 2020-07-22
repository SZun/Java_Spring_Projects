/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samg.zun
 */
@Repository
@Profile("test")
public class OrganizationDaoInMem implements OrganizationDao {

    List<Organization> allOrganizations = new ArrayList<>();
    
    Superhero testSup = new Superhero(1, "A", "A", new Power(1, "A"), null);

    @Override
    public List<Organization> getAllOrganizations() {
        return allOrganizations;
    }

    @Override
    public Organization createOrganization(Organization toAdd) {
        int nextId = allOrganizations.stream()
                .mapToInt(o -> o.getId())
                .max()
                .orElse(0) + 1;
        toAdd.setId(nextId);
        allOrganizations.add(toAdd);
        return toAdd;
    }

    @Override
    public Organization getOrganizationById(Integer id) throws DaoException {
        return allOrganizations.stream().filter(o -> o.getId() == id).findFirst().orElseThrow(() -> new DaoException("Invalid Id"));
    }

    @Override
    public int editOrganization(Organization toEdit) {
        int toReturn = 0;

        for (int i = 0; i < allOrganizations.size(); i++) {
            if (allOrganizations.get(i).getId() == toEdit.getId()) {
                allOrganizations.set(i, toEdit);
                toReturn = 1;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public int removeOrganization(Integer id) {
        int toReturn = 0;
        Organization toRemove = null;

        for (Organization o : allOrganizations) {
            if (o.getId() == id) {
                toRemove = o;
                toReturn = 1;
            }
        }

        if (toReturn == 1) {
            allOrganizations.remove(toRemove);
        }

        return toReturn;
    }

    public void removeAll() {
        allOrganizations = new ArrayList<>();
    }

    @Override
    public int addMember(List<Organization> orgs, int superId) {
        Superhero sup = null;
        
        int res = 0;
        
        if(superId == testSup.getId()){
            sup = testSup;
        }
        
        for(Organization org: orgs){
            if(org.getMembers().contains(sup)){
                org.getMembers().remove(sup);
                org.getMembers().add(sup);
                res = 1;
            }
        }
        
        return res;
    }

    @Override
    public List<Organization> getOrganizationsBySuperheroId(int id) {
        Superhero sup = null;
        List<Organization> superOrgs = new ArrayList<>();
        
        if(id == testSup.getId()){
            sup = testSup;
        }
        
        for(Organization org: allOrganizations){
            if(org.getMembers().contains(sup)){
                superOrgs.add(org);
            }
        }
        return superOrgs;
    }

    @Override
    public Organization getOrganizationByName(String name) throws DaoException {
        return allOrganizations.stream().filter(o -> o.getName() == name).findFirst().orElseThrow(() -> new DaoException("Invalid Name"));
    }

}
