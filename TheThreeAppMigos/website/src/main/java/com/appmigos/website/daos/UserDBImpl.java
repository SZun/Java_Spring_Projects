/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.dtos.Role;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.UserDaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Isaia
 */
@Repository
@Profile("database")
public class UserDBImpl implements UserDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public User getUserById(int id) throws UserDaoException { // takes int id and searches sql database for match, returns User object
        try {
            User toReturn = template.queryForObject("SELECT * FROM Users WHERE id = ?", new UserMapper(), id);
            Set<Role> userRoles = getRolesByUserId(toReturn.getId());
            toReturn.setRole(userRoles);
            return toReturn;
        } catch (DataAccessException ex) {
            throw new UserDaoException("SQL error has occured in getUserById");
        }
    }

    @Override
    public User getUserByName(String name) throws UserDaoException { // takes a username, searches database, returns User object
        try {
            User toGet = template.queryForObject("SELECT * FROM Users WHERE username = ?", new UserMapper(), name);
            Set<Role> userRoles = getRolesByUserId(toGet.getId());
            toGet.setRole(userRoles);
            return toGet;
        } catch (DataAccessException ex) {
            throw new UserDaoException("SQL error has occured in getAllUsers");
        }
    }

    @Override
    public List<User> getAllUsers() throws UserDaoException { // returns a list of all User objects currently stored in database
        try {
            List<User> allUsers = template.query("SELECT * FROM Users", new UserMapper());
            if (allUsers.isEmpty()) {
                throw new UserDaoException("No users found");
            }
            associateRolesToUser(allUsers);
            return allUsers;
        } catch (DataAccessException ex) {
            throw new UserDaoException("SQL error has occured in getAllUsers");
        }
    }

    @Override
    public void updateUser(User toEdit) throws UserDaoException, BadUpdateException, InvalidEntityException { // takes in User object and updates username and password of the associated id
        validateUserData(toEdit);
        try {
            int affectedRows = template.update("UPDATE Users SET userName = ?, password = ? WHERE id = ?",
                    toEdit.getUserName(), toEdit.getPassword(), toEdit.getId());
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in updateUser");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affected in updateUser");
            }
            template.update("DELETE FROM UserRole WHERE userId = ?", toEdit.getId());
            for (Role r : toEdit.getRole()) {
                template.update("INSERT INTO UserRole(userId, roleId) VALUES(?, ?)", toEdit.getId(), r.getId());
            }
        } catch (DataAccessException ex) {
            throw new UserDaoException("Failed to reach database in updateUser");
        }
    }

    @Override
    @Transactional
    public void deleteUser(int id) throws UserDaoException, BadUpdateException { // searches database for associated id and removes User object
        try {
            template.update("DELETE FROM UserRole WHERE userId = ?", id);
            int affectedRows = template.update("DELETE FROM Users WHERE id = ?", id);
            if (affectedRows < 1) {
                throw new BadUpdateException("No rows affected in deleteUser");
            }
            if (affectedRows > 1) {
                throw new BadUpdateException("More than one row affected in deleteUser");
            }
        } catch (DataAccessException ex) {
            throw new UserDaoException("Failed to reach database in deleteUser");
        }
    }

    // May need to come back to this one. Currently not setting the Set<Role> field
    // thinking it might be a problem. Happy to deal with it later
    @Override
    public User createUser(User toAdd) throws UserDaoException, InvalidEntityException { // takes in a User object with no id, adds it to the db, and returns the same User object with an id
        validateUserData(toAdd);
        try {
            template.update("INSERT INTO Users(userName, `password`, enabled) VALUES (?, ?, ?)",
                    toAdd.getUserName(), toAdd.getPassword(), toAdd.isEnabled());
            int newId = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            toAdd.setId(newId);
            for (Role r : toAdd.getRole()) {
                template.update("INSERT INTO UserRole(userId, roleId) VALUES(?, ?)", toAdd.getId(), r.getId());
            }
            return toAdd;
        } catch (DataAccessException ex) {
            throw new UserDaoException("Failed to reach database.", ex);
        }
    }

    private Set<Role> getRolesByUserId(int id) { //

        return new HashSet<>(template.query("SELECT * FROM Roles ro INNER JOIN UserRole ur on ro.id = ur.roleId WHERE ur.userId = ?", new RoleMapper(), id));

    }

    private void associateRolesToUser(List<User> users) {
        for (User u : users) {
            u.setRole(getRolesByUserId(u.getId()));
        }
    }

    private void validateUserData(User toCheck) throws InvalidEntityException {
        if (toCheck == null) {
            throw new InvalidEntityException("User Object cannot be null");
        }
        if (toCheck.getUserName() == null || toCheck.getPassword() == null || toCheck.getRole() == null) {
            throw new InvalidEntityException("User Object properties cannot be null");
        }
        if (toCheck.getUserName().length() > 30) {
            throw new InvalidEntityException("User name must be 30 characters or less");
        }
        if (toCheck.getPassword().length() > 100) {
            throw new InvalidEntityException("User password must be 100 characters or less");
        }
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User toReturn = new User();
            toReturn.setId(rs.getInt("id"));
            toReturn.setUserName(rs.getString("userName"));
            toReturn.setPassword(rs.getString("password"));
            toReturn.setEnabled(rs.getBoolean("enabled"));
            return toReturn;
        }
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
