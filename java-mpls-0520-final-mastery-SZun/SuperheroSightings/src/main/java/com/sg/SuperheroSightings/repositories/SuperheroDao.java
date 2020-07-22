/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface SuperheroDao {

    public List<Superhero> getAllSuperheros();

    public Superhero createSuperhero(Superhero toAdd);

    public Superhero getSuperheroById(Integer id) throws DaoException;
    
    public Superhero getSuperheroByName(String name) throws DaoException;

    public int editSuperhero(Superhero toEdit);

    public int removeSuperhero(Integer id);

}
