/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.repositories.PowerDaoDB.PowerMapper;
import com.sg.SuperheroSightings.repositories.SuperheroDaoDB.SuperheroMapper;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM Organizations";
        List<Organization> allOrganizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateOrgnizationsSuperheroes(allOrganizations);
        return allOrganizations;
    }

    @Override
    public Organization createOrganization(Organization toAdd) {
        final String INSERT_ORGANIZATION = "INSERT INTO Organizations(Name,Address,Email) VALUES(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement pStmt = conn.prepareStatement(INSERT_ORGANIZATION, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, toAdd.getName());
            pStmt.setString(2, toAdd.getAddress());
            pStmt.setString(3, toAdd.getEmail());
            return pStmt;
        }, keyHolder);

        toAdd.setId(keyHolder.getKey().intValue());
        insertOrganizationSuperheroes(toAdd);

        return toAdd;
    }

    @Override
    public Organization getOrganizationById(Integer id) throws DaoException {
        final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM Organizations WHERE id = ?";
        Organization toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            toReturn.setMembers(getSuperheroesForOrganization(id));
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Id");
        }

        return toReturn;
    }

    @Override
    @Transactional
    public int editOrganization(Organization toEdit) {
        final String UPDATE_ORGANIZATION = "UPDATE Organizations SET Name = ?, Address = ?, Email = ? WHERE Id = ?";
        int updated = jdbc.update(UPDATE_ORGANIZATION, toEdit.getName(), toEdit.getAddress(), toEdit.getEmail(), toEdit.getId());

        final String DELETE_ORGANIZATIONSSUPERHEROES = "DELETE FROM OrganizationsSuperheros os WHERE os.OrganizationId = ?";
        int deleted = jdbc.update(DELETE_ORGANIZATIONSSUPERHEROES, toEdit.getId());

        if (updated != 0 && toEdit.getMembers() != null && !toEdit.getMembers().isEmpty()) {
            insertOrganizationSuperheroes(toEdit);
        }

        return updated + deleted;
    }

    @Override
    @Transactional
    public int removeOrganization(Integer id) {
        final String DELETE_ORGANIZATIONSSUPERHEROES = "DELETE FROM OrganizationsSuperheros os WHERE os.OrganizationId = ?";
        final String DELETE_ORGANIZATION = "DELETE FROM Organizations where Id = ?";

        return jdbc.update(DELETE_ORGANIZATIONSSUPERHEROES, id) + jdbc.update(DELETE_ORGANIZATION, id);
    }

    private void insertOrganizationSuperheroes(Organization toAdd) {
        if (toAdd.getMembers() != null && !toAdd.getMembers().isEmpty()) {
            final String INSERT_ORGANIZATIONSSUPERHEROES = "INSERT INTO OrganizationsSuperheros(SuperheroId, OrganizationId) VALUES(?,?)";
            toAdd.getMembers().stream().forEach(s -> jdbc.update(INSERT_ORGANIZATIONSSUPERHEROES, s.getId(), toAdd.getId()));
        }
    }

    private List<Superhero> getSuperheroesForOrganization(Integer id) {
        final String SELECT_SUPERHEROES_BY_ORGANIZATION_ID = "SELECT * FROM Superheros s"
                + " JOIN OrganizationsSuperheros os ON os.SuperheroId = s.Id WHERE os.OrganizationId = ?";
        List<Superhero> superheros = jdbc.query(SELECT_SUPERHEROES_BY_ORGANIZATION_ID, new SuperheroMapper(), id);
        associatePowersSuperheros(superheros);
        return superheros;
    }

    private void associateOrgnizationsSuperheroes(List<Organization> allOrganizations) {
        allOrganizations.stream().forEach(o -> o.setMembers(getSuperheroesForOrganization(o.getId())));
    }

    private void associatePowersSuperheros(List<Superhero> superheros) {
        final String SELECT_POWER_BY_SUPER_ID = "SELECT * FROM Powers p "
                + "JOIN Superheros s ON p.Id = s.PowerId WHERE s.Id = ?";
        superheros.stream().forEach(s -> s.setPower(jdbc.queryForObject(SELECT_POWER_BY_SUPER_ID, new PowerMapper(), s.getId())));
    }

    @Override
    @Transactional
    public int addMember(List<Organization> orgs, int superId) {
        final String DELETE_ORGSUP_BY_SUPID = "DELETE FROM OrganizationsSuperheros os WHERE os.SuperheroId = ?";
        final String INSERT_ORG_SUP = "INSERT INTO OrganizationsSuperheros(SuperheroId, OrganizationId) VALUES(?,?)";

        int count = jdbc.update(DELETE_ORGSUP_BY_SUPID, superId);

        for (Organization org : orgs) {
            count += jdbc.update(INSERT_ORG_SUP, superId, org.getId());
        }

        return count;
    }

    @Override
    public List<Organization> getOrganizationsBySuperheroId(int id) {
        final String SELECT_ORGANIZATIONS_BY_SUPERHERO = "SELECT * FROM Organizations o "
                + "JOIN OrganizationsSuperheros os ON os.OrganizationId = o.Id "
                + "JOIN Superheros s ON os.SuperheroId = s.Id WHERE s.Id = ?";
        List<Organization> allOrganizations = jdbc.query(SELECT_ORGANIZATIONS_BY_SUPERHERO, new OrganizationMapper(), id);
        associateOrgnizationsSuperheroes(allOrganizations);
        return allOrganizations;
    }

    @Override
    public Organization getOrganizationByName(String name) throws DaoException {
        final String SELECT_ORGANIZATION_BY_Name = "SELECT * FROM Organizations WHERE Name = ?";
        Organization toReturn = null;

        try {
            toReturn = jdbc.queryForObject(SELECT_ORGANIZATION_BY_Name, new OrganizationMapper(), name);
            toReturn.setMembers(getSuperheroesForOrganization(toReturn.getId()));
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("Invalid Name");
        }

        return toReturn;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            return new Organization(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address"), rs.getString("Email"));
        }
    }
}
