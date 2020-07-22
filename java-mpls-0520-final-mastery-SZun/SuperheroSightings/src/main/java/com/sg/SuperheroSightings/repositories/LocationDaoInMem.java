/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Location;
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
public class LocationDaoInMem implements LocationDao {

    List<Location> allLocations = new ArrayList<>();

    @Override
    public List<Location> getAllLocations() {
        return allLocations;
    }

    @Override
    public Location createLocation(Location toAdd) {
        int nextId = allLocations.stream()
                .mapToInt(l -> l.getId())
                .max()
                .orElse(0) + 1;
        toAdd.setId(nextId);
        allLocations.add(toAdd);
        return toAdd;
    }

    @Override
    public Location getLocationById(Integer id) throws DaoException {
        return allLocations.stream().filter(l -> l.getId() == id).findFirst().orElseThrow(() -> new DaoException("Invalid Id"));
    }

    @Override
    public int editLocation(Location toEdit) {
        int toReturn = 0;

        for (int i = 0; i < allLocations.size(); i++) {
            if (allLocations.get(i).getId() == toEdit.getId()) {
                allLocations.set(i, toEdit);
                toReturn = 1;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public int removeLocation(Integer id) {
        int toReturn = 0;
        Location toRemove = null;

        for (Location l : allLocations) {
            if (l.getId() == id) {
                toRemove = l;
                toReturn = 1;
            }
        }

        if (toReturn == 1) {
            allLocations.remove(toRemove);
        }

        return toReturn;
    }

    public void removeAll() {
        allLocations = new ArrayList<>();
    }

    @Override
    public Location getLocationByName(String name) throws DaoException {
        return allLocations.stream().filter(l -> l.getName() == name).findFirst().orElseThrow(() -> new DaoException("Invalid Name"));
    }

}
