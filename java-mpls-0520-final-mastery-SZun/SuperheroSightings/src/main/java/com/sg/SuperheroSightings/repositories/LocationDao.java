/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface LocationDao {

    public List<Location> getAllLocations();

    public Location createLocation(Location toAdd);

    public Location getLocationById(Integer id) throws DaoException;

    public int editLocation(Location toEdit);

    public int removeLocation(Integer id);

    public Location getLocationByName(String name) throws DaoException;

}
