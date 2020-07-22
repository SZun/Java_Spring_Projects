/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface PowerDao {

    public List<Power> getAllPowers();

    public Power createPower(Power toAdd);

    public Power getPowerById(Integer id) throws DaoException;
    
    public Power getPowerByName(String name) throws DaoException;

    public int editPower(Power toEdit);

    public int removePower(Integer id);

}
