/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Sighting;
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
public class SightingDaoInMem implements SightingDao {

    List<Sighting> allSightings = new ArrayList<>();

    @Override
    public List<Sighting> getAllSightings() {
        return allSightings;
    }

    @Override
    public Sighting createSighting(Sighting toAdd) {
        int nextId = allSightings.stream()
                .mapToInt(s -> s.getId())
                .max()
                .orElse(0) + 1;
        toAdd.setId(nextId);
        allSightings.add(toAdd);
        return toAdd;
    }

    @Override
    public Sighting getSightingById(Integer id) throws DaoException {
        return allSightings.stream().filter(s -> s.getId() == id).findFirst().orElseThrow(() -> new DaoException("Invalid Id"));
    }

    @Override
    public int editSighting(Sighting toEdit) {
        int toReturn = 0;

        for (int i = 0; i < allSightings.size(); i++) {
            if (allSightings.get(i).getId() == toEdit.getId()) {
                allSightings.set(i, toEdit);
                toReturn = 1;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public int removeSighting(Integer id) {
        int toReturn = 0;

        for (Sighting s : allSightings) {
            if (s.getId() == id) {
                allSightings.remove(s);
                toReturn = 1;
            }
        }

        return toReturn;
    }

    public void removeAll() {
        allSightings = new ArrayList<>();
    }

}
