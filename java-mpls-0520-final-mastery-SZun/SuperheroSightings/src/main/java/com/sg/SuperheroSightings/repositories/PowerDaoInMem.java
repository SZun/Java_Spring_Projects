/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Power;
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
public class PowerDaoInMem implements PowerDao {

    List<Power> allPowers = new ArrayList<>();

    @Override
    public List<Power> getAllPowers() {
        return allPowers;
    }

    @Override
    public Power createPower(Power toAdd) {
        int nextId = allPowers.stream()
                .mapToInt(p -> p.getId())
                .max()
                .orElse(0) + 1;
        toAdd.setId(nextId);
        allPowers.add(toAdd);
        return toAdd;
    }

    @Override
    public Power getPowerById(Integer id) throws DaoException {
        return allPowers.stream().filter(p -> p.getId() == id).findFirst().orElseThrow(() -> new DaoException("Invalid Id"));
    }

    @Override
    public int editPower(Power toEdit) {
        int toReturn = 0;

        for (int i = 0; i < allPowers.size(); i++) {
            if (allPowers.get(i).getId() == toEdit.getId()) {
                allPowers.set(i, toEdit);
                toReturn = 1;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public int removePower(Integer id) {
        int toReturn = 0;
        Power toRemove = null;

        for (Power p : allPowers) {
            if (p.getId() == id) {
                toRemove = p;
                toReturn = 1;
            }
        }

        if (toReturn == 1) {
            allPowers.remove(toRemove);
        }

        return toReturn;
    }

    public void removeAll() {
        allPowers = new ArrayList<>();
    }

    @Override
    public Power getPowerByName(String name) throws DaoException {
        return allPowers.stream().filter(p -> p.getName() == name).findFirst().orElseThrow(() -> new DaoException("Invalid Name"));
    }

}
