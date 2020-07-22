/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface SightingDao {

    public List<Sighting> getAllSightings();

    public Sighting createSighting(Sighting toAdd);

    public Sighting getSightingById(Integer id) throws DaoException;

    public int editSighting(Sighting toEdit);

    public int removeSighting(Integer id);

}
