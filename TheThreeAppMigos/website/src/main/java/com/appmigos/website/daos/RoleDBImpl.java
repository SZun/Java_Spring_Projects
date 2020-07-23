/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.RoleDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class RoleDBImpl implements RoleDao{
    
    @Autowired
    JdbcTemplate template;

    @Override
    public Role getRoleById(int id) throws RoleDaoException {
        try{
            return template.queryForObject("SELECT * FROM Roles WHERE id = ?", new RoleMapper(), id);
        } catch(DataAccessException ex){
            throw new RoleDaoException("Failed to reach database in getRoleById");
        }
    }

    @Override
    public Role getRoleByName(String name) throws RoleDaoException {
        try{
            return template.queryForObject("SELECT * FROM Roles WHERE name = ?", new RoleMapper(), name);
        } catch(DataAccessException ex){
            throw new RoleDaoException("Failed to reach database in getRoleByName");
        }
    }

    @Override
    public List<Role> getAllRoles() throws RoleDaoException {
        try{
            List<Role> allRoles = template.query("SELECT * FROM Roles", new RoleMapper());
            if(allRoles.isEmpty()) throw new RoleDaoException("No roles found");
            return allRoles;
        } catch(DataAccessException ex){
            throw new RoleDaoException("Failed to reach database in getAllRoles");
        }
    }

    @Override
    public void updateRole(Role toEdit) throws RoleDaoException, BadUpdateException, InvalidEntityException {
        validateRoleData(toEdit);
        try{
        int affectedRows = template.update("UPDATE Roles SET name = ? WHERE id = ?",
                toEdit.getRole(), toEdit.getId());
        if(affectedRows < 1) throw new BadUpdateException("No rows affected in updateRole");
        if(affectedRows > 1) throw new BadUpdateException("More than one row affected int updateRole");
        } catch(DataAccessException ex){
            throw new RoleDaoException("Failed to reach database in updateRole");
        }
    }

    @Override
    public void removeRole(int id) throws RoleDaoException, BadUpdateException {
        try{
        template.update("DELETE FROM UserRole WHERE roleId = ?", id);
        int affectedRows = template.update("DELETE FROM Roles WHERE id = ?", id);
        if(affectedRows < 1) throw new BadUpdateException("No rows affected in removeRole");
        if(affectedRows > 1) throw new BadUpdateException("More than one row affected int removeRole");
        } catch(DataAccessException ex){
            throw new RoleDaoException("Failed to reach database in removeRole");
        }
    }

    @Override
    public Role createRole(Role toAdd) throws RoleDaoException, InvalidEntityException {
        validateRoleData(toAdd);
        try{
            template.update("INSERT INTO Roles(name) VALUES(?)", toAdd.getRole());
            int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setId(newId);
            return toAdd;
        } catch(DataAccessException ex){
            throw new RoleDaoException("Failed to reach database in createRole");
        }
    }
    
    private void validateRoleData(Role toCheck) throws InvalidEntityException{
        if(toCheck == null) throw new InvalidEntityException("Role Object cannot be null");
        if(toCheck.getRole() == null) throw new InvalidEntityException("Role Object properties cannot be null");
        if(toCheck.getRole().length() > 30) throw new InvalidEntityException("Role title must be 30 characters or less");
    }
    
    private static class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role toReturn = new Role();
            toReturn.setId(rs.getInt("id"));
            toReturn.setRole(rs.getString("name"));
            return toReturn;
        }
    }
    
}
