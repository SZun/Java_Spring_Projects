/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Power;
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
public class PowerDaoDB implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Power> getAllPowers() {
        final String SELECT_ALL_POWERS = "SELECT * FROM Powers";
        return jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
    }

    @Override
    public Power createPower(Power toAdd) {
        final String INSERT_POWER = "INSERT INTO Powers(Name) VALUES(?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement pStmt = conn.prepareStatement(INSERT_POWER, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, toAdd.getName());

            return pStmt;

        }, keyHolder);

        toAdd.setId(keyHolder.getKey().intValue());
        return toAdd;
    }

    @Override
    public Power getPowerById(Integer id) throws DaoException {
        final String SELECT_POWER_BY_ID = "SELECT * FROM Powers WHERE Id = ?";
        Power toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    @Override
    public int editPower(Power toEdit) {
        final String UPDATE_POWER = "UPDATE Powers SET Name = ? WHERE Id = ?";
        return jdbc.update(UPDATE_POWER, toEdit.getName(), toEdit.getId());
    }

    @Override
    @Transactional
    public int removePower(Integer id) {
        final String DELETE_LOCSUP_BY_POWERID = "DELETE ls FROM LocationsSuperheros ls "
                + "JOIN Superheros s ON s.Id = ls.SuperheroId "
                + "JOIN Powers p ON p.Id = s.PowerId WHERE p.Id = ?";
        final String DELETE_ORGSUP_BY_POWERID = "DELETE os FROM OrganizationsSuperheros os "
                + "JOIN Superheros s ON s.Id = os.SuperheroId "
                + "JOIN Powers p ON p.Id = s.PowerId WHERE p.Id = ?";
        final String DELETE_SUPERHERO_BY_POWERID = "DELETE FROM Superheros where PowerId = ?";
        final String DELETE_POWER = "DELETE FROM Powers where Id = ?";

        return jdbc.update(DELETE_ORGSUP_BY_POWERID, id) + jdbc.update(DELETE_LOCSUP_BY_POWERID, id) 
                + jdbc.update(DELETE_SUPERHERO_BY_POWERID, id) + jdbc.update(DELETE_POWER, id);
    }

    @Override
    public Power getPowerByName(String name) throws DaoException {
        final String SELECT_POWER_BY_NAME = "SELECT * FROM Powers WHERE Name = ?";
        Power toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_POWER_BY_NAME, new PowerMapper(), name);
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            return new Power(rs.getInt("Id"), rs.getString("Name"));
        }
    }

}
