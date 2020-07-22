/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.repositories.OrganizationDaoDB.OrganizationMapper;
import com.sg.SuperheroSightings.repositories.PowerDaoDB.PowerMapper;
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
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Superhero> getAllSuperheros() {
        final String SELECT_ALL_SUPERHEROES = "SELECT * FROM Superheros";
        List<Superhero> allSuperheros = jdbc.query(SELECT_ALL_SUPERHEROES, new SuperheroMapper());
        associatePowersSuperheroes(allSuperheros);
        associateOrganizationsSuperheroes(allSuperheros);
        return allSuperheros;
    }

    @Override
    @Transactional
    public Superhero createSuperhero(Superhero toAdd) {
        final String INSERT_SUPERHERO = "INSERT INTO Superheros(Name, Description, PowerId) VALUES(?,?,?)";
        final String INSERT_ORGSUP = "INSERT INTO OrganizationsSuperheros(SuperheroId, OrganizationId) VALUES(?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {

            PreparedStatement pStmt = conn.prepareStatement(INSERT_SUPERHERO, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, toAdd.getName());
            pStmt.setString(2, toAdd.getDescription());
            pStmt.setInt(3, toAdd.getPower().getId());

            return pStmt;

        }, keyHolder);

        toAdd.setId(keyHolder.getKey().intValue());

        if (toAdd.getOrganizations() != null && !toAdd.getOrganizations().isEmpty()) {
            for (Organization org : toAdd.getOrganizations()) {
                jdbc.update(INSERT_ORGSUP, toAdd.getId(), org.getId());
            }
        }

        toAdd.setOrganizations(getOrganizationsForSuperhero(toAdd.getId()));

        return toAdd;
    }

    @Override
    public Superhero getSuperheroById(Integer id) throws DaoException {
        final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM Superheros WHERE Id = ?";
        Superhero toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            toReturn.setPower(getPowerForSuperhero(id));
            toReturn.setOrganizations(getOrganizationsForSuperhero(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    @Override
    @Transactional
    public int editSuperhero(Superhero toEdit) {
        final String UPDATE_SUPERHERO = "UPDATE Superheros SET Name = ?, Description = ?, PowerId = ? WHERE Id = ?";
        final String DELETE_ORGANIZATIONSSUPERHEROES = "DELETE FROM OrganizationsSuperheros os WHERE os.SuperheroId = ?";
        final String INSERT_ORGSUP = "INSERT INTO OrganizationsSuperheros(SuperheroId, OrganizationId) VALUES(?,?)";

        int delete = jdbc.update(DELETE_ORGANIZATIONSSUPERHEROES, toEdit.getId());

        int update = jdbc.update(UPDATE_SUPERHERO, toEdit.getName(), toEdit.getDescription(), toEdit.getPower().getId(), toEdit.getId());

        int count = 0;

        if (update != 0 && toEdit.getOrganizations() != null && !toEdit.getOrganizations().isEmpty()) {
            for (Organization org : toEdit.getOrganizations()) {
                count += jdbc.update(INSERT_ORGSUP, toEdit.getId(), org.getId());
            }
        }

        return delete + update + count;
    }

    @Override
    @Transactional
    public int removeSuperhero(Integer id) {
        final String DELETE_LOCATIONSSUPERHEROES = "DELETE FROM LocationsSuperheros ls WHERE ls.SuperheroId = ?";
        final String DELETE_ORGANIZATIONSSUPERHEROES = "DELETE FROM OrganizationsSuperheros os WHERE os.SuperheroId = ?";
        final String DELETE_SUPERHERO = "DELETE FROM Superheros WHERE Id = ?";

        return jdbc.update(DELETE_LOCATIONSSUPERHEROES, id) + jdbc.update(DELETE_ORGANIZATIONSSUPERHEROES, id)
                + jdbc.update(DELETE_SUPERHERO, id);
    }

    private Power getPowerForSuperhero(Integer id) throws EmptyResultDataAccessException {
        final String SELECT_POWER_BY_SUPERHERO_POWERID = "SELECT * FROM Powers p "
                + " JOIN Superheros s ON p.Id = s.PowerId WHERE s.Id = ?";

        return jdbc.queryForObject(SELECT_POWER_BY_SUPERHERO_POWERID, new PowerMapper(), id);
    }

    private void associatePowersSuperheroes(List<Superhero> allSuperheros) {
        allSuperheros.stream().forEach(s -> s.setPower(getPowerForSuperhero(s.getId())));
    }

    @Override
    public Superhero getSuperheroByName(String name) throws DaoException {
        final String SELECT_SUPERHERO_BY_NAME = "SELECT * FROM Superheros WHERE Name = ?";
        Superhero toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_SUPERHERO_BY_NAME, new SuperheroMapper(), name);
            toReturn.setPower(getPowerForSuperhero(toReturn.getId()));
            toReturn.setOrganizations(getOrganizationsForSuperhero(toReturn.getId()));
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    private void associateOrganizationsSuperheroes(List<Superhero> allSuperheros) {
        allSuperheros.stream().forEach(s -> s.setOrganizations(getOrganizationsForSuperhero(s.getId())));
    }

    private List<Organization> getOrganizationsForSuperhero(int id) {
        final String SELECT_ORGANIZATION_BY_SUPERHERO_ID = "SELECT * FROM Organizations o"
                + " JOIN OrganizationsSuperheros os ON os.OrganizationId = o.Id WHERE os.SuperheroId = ?";
        return jdbc.query(SELECT_ORGANIZATION_BY_SUPERHERO_ID, new OrganizationMapper(), id);
    }

    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int i) throws SQLException {
            return new Superhero(rs.getInt("Id"), rs.getString("Name"), rs.getString("Description"));
        }
    }

}
