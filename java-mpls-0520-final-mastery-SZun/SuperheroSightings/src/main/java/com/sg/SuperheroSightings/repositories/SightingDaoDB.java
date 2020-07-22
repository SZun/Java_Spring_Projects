/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.repositories.LocationDaoDB.LocationMapper;
import com.sg.SuperheroSightings.repositories.PowerDaoDB.PowerMapper;
import com.sg.SuperheroSightings.repositories.SuperheroDaoDB.SuperheroMapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author samg.zun
 */
@Repository
@Profile("prod")
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM LocationsSuperheros";
        List<Sighting> allSightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationsAndSuperheroes(allSightings);
        return allSightings;
    }

    @Override
    public Sighting createSighting(Sighting toAdd) {
        final String INSERT_SIGHTING = "INSERT INTO LocationsSuperheros(SuperheroId, LocationId, SightingDate) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement pStmt = conn.prepareStatement(INSERT_SIGHTING, Statement.RETURN_GENERATED_KEYS);
            pStmt.setInt(1, toAdd.getSuperhero().getId());
            pStmt.setInt(2, toAdd.getLocation().getId());
            pStmt.setDate(3, Date.valueOf(toAdd.getSightingDate()));

            return pStmt;

        }, keyHolder);

        toAdd.setId(keyHolder.getKey().intValue());
        return toAdd;
    }

    @Override
    public Sighting getSightingById(Integer id) throws DaoException {
        final String SELECT_SIGHTING_BY_ID = "SELECT * FROM LocationsSuperheros WHERE Id = ?";
        Sighting toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            toReturn.setLocation(getLocationForSighting(id));
            toReturn.setSuperhero(getSuperheroForSighting(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    @Override
    public int editSighting(Sighting toEdit) {
        final String UPDATE_SIGHTING = "UPDATE LocationsSuperheros SET SuperheroId = ?, LocationId = ?, SightingDate = ? WHERE Id = ?";
        return jdbc.update(UPDATE_SIGHTING, toEdit.getSuperhero().getId(), toEdit.getLocation().getId(),
                Date.valueOf(toEdit.getSightingDate()), toEdit.getId());
    }

    @Override
    public int removeSighting(Integer id) {
        final String DELETE_SIGHTING = "DELETE FROM LocationsSuperheros ls WHERE ls.Id = ?";

        return jdbc.update(DELETE_SIGHTING, id);
    }

    private Location getLocationForSighting(int id) throws EmptyResultDataAccessException {
        final String SELECT_LOCATION_BY_ID = "SELECT * FROM Locations l"
                + " JOIN LocationsSuperheros ls ON ls.LocationId = l.Id WHERE ls.Id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
    }

    private Superhero getSuperheroForSighting(int id) throws EmptyResultDataAccessException {
        final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM Superheros s "
                + " JOIN LocationsSuperheros ls ON ls.SuperheroId = s.Id WHERE ls.Id = ?";
        Superhero hero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
        associatePowerSuperhero(hero);
        return hero;
    }

    private void associateLocationsAndSuperheroes(List<Sighting> allSightings) {
        allSightings.stream().forEach(s -> {
            s.setLocation(getLocationForSighting(s.getId()));
            s.setSuperhero(getSuperheroForSighting(s.getId()));
        });
    }

    private void associatePowerSuperhero(Superhero hero) {
        final String GET_POWER_BY_HERO_ID = "SELECT * FROM Powers p "
                + "JOIN Superheros s ON s.PowerId = p.Id WHERE s.Id = ?";
        Power superPower = jdbc.queryForObject(GET_POWER_BY_HERO_ID, new PowerMapper(), hero.getId());
        hero.setPower(superPower);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            return new Sighting(rs.getInt("Id"), rs.getDate("SightingDate").toLocalDate());
        }
    }

}
