/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.exceptions.DaoException;
import java.sql.Connection;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samg.zun
 */
@Repository
@Profile("prod")
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Locations";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location createLocation(Location toAdd) {
        final String INSERT_LOCATION = "INSERT INTO Locations(Name, Description, Address, Latitude, Longitude) VALUES(?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement pStmt = conn.prepareStatement(INSERT_LOCATION, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, toAdd.getName());
            pStmt.setString(2, toAdd.getDescription());
            pStmt.setString(3, toAdd.getAddress());
            pStmt.setBigDecimal(4, toAdd.getLatitude());
            pStmt.setBigDecimal(5, toAdd.getLongitude());

            return pStmt;
        }, keyHolder);

        toAdd.setId(keyHolder.getKey().intValue());

        return toAdd;
    }

    @Override
    public Location getLocationById(Integer id) throws DaoException {
        final String SELECT_LOCATION_BY_ID = "SELECT * FROM Locations WHERE Id = ?";
        Location toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    @Override
    public int editLocation(Location toEdit) {
        final String UPDATE_LOCATION = "UPDATE Locations SET Name = ?, Description = ?, Address = ?, Latitude = ?, Longitude = ?"
                + " WHERE Id = ?";
        return jdbc.update(UPDATE_LOCATION, toEdit.getName(), toEdit.getDescription(), toEdit.getAddress(),
                toEdit.getLatitude(), toEdit.getLongitude(), toEdit.getId());
    }

    @Override
    @Transactional
    public int removeLocation(Integer id) {
        final String DELETE_LOCATIONSSUPERHEROES = "DELETE FROM LocationsSuperheros ls WHERE ls.LocationId = ?";
        final String DELETE_LOCATION = "DELETE FROM Locations where id = ?";

        return jdbc.update(DELETE_LOCATIONSSUPERHEROES, id) + jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public Location getLocationByName(String name) throws DaoException {
        final String SELECT_LOCATION_BY_NAME = "SELECT * FROM Locations WHERE Name = ?";
        Location toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_LOCATION_BY_NAME, new LocationMapper(), name);
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            return new Location(
                    rs.getInt("Id"), rs.getString("Name"), rs.getString("Description"),
                    rs.getString("Address"), rs.getBigDecimal("Latitude"), rs.getBigDecimal("Longitude")
            );
        }
    }

}
