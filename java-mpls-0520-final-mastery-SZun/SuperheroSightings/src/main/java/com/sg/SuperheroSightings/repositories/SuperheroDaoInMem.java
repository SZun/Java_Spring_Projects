/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

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
public class SuperheroDaoInMem implements SuperheroDao {

    List<Superhero> allSuperheros = new ArrayList<>();

    @Override
    public List<Superhero> getAllSuperheros() {
        return allSuperheros;
    }

    @Override
    public Superhero createSuperhero(Superhero toAdd) {
        int nextId = allSuperheros.stream()
                .mapToInt(s -> s.getId())
                .max()
                .orElse(0) + 1;
        toAdd.setId(nextId);
        allSuperheros.add(toAdd);
        return toAdd;
    }

    @Override
    public Superhero getSuperheroById(Integer id) throws DaoException {
        return allSuperheros.stream().filter(s -> s.getId() == id).findFirst().orElseThrow(() -> new DaoException("Invalid Id"));
    }

    @Override
    public int editSuperhero(Superhero toEdit) {
        int toReturn = 0;

        for (int i = 0; i < allSuperheros.size(); i++) {
            if (allSuperheros.get(i).getId() == toEdit.getId()) {
                allSuperheros.set(i, toEdit);
                toReturn = 1;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public int removeSuperhero(Integer id) {
        int toReturn = 0;
        Superhero toRemove = null;

        for (Superhero s : allSuperheros) {
            if (s.getId() == id) {
                toRemove = s;
                toReturn = 1;
            }
        }

        if (toReturn == 1) {
            allSuperheros.remove(toRemove);
        }

        return toReturn;
    }

    public void removeAll() {
        allSuperheros = new ArrayList<>();
    }

    @Override
    public Superhero getSuperheroByName(String name) throws DaoException {
        return allSuperheros.stream().filter(s -> s.getName() == name).findFirst().orElseThrow(() -> new DaoException("Invalid Name"));
    }

}
