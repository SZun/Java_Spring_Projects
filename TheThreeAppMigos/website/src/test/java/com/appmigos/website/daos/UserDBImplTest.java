/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.UserDaoException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Isaia
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("database")
public class UserDBImplTest {

    @Autowired
    UserDao uDao;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM UserRole");
        template.update("DELETE FROM Roles");
        template.update("DELETE FROM Users");
        template.update("ALTER TABLE Roles auto_increment = 1");
        template.update("INSERT INTO Roles(name) VALUES"
                + "('ROLE_ADMIN'),"
                + "('ROLE_Author')");
        template.update("ALTER TABLE Users auto_increment = 1");
        template.update("INSERT INTO Users(userName, password, enabled) VALUES"
                + "('Admin', 'password', 1),"
                + "('Author', 'password', 1)");
        template.update("INSERT INTO UserRole(userId, roleId) VALUES"
                + "(1, 1),"
                + "(2, 2)");
    }

    /**
     * Test of getUserById method, of class UserDBImpl.
     */
    @Test
    public void testGetUserByIdGoldenPath() throws UserDaoException {
        User toCheck = uDao.getUserById(1);
        assertEquals(1, toCheck.getId());
        assertEquals("Admin", toCheck.getUserName());
        assertEquals("password", toCheck.getPassword());
        assertEquals(1, toCheck.getRole().size());
        assertTrue(toCheck.isEnabled());
    }

    @Test
    public void testGetUserByBadId() {
        try {
            uDao.getUserById(-1);
            fail("Should have hit UserDaoException");
        } catch (UserDaoException ex) {

        }
    }

    /**
     * Test of getUserByName method, of class UserDBImpl.
     */
    @Test
    public void testGetUserByNameGoldenPath() throws UserDaoException {
        User toCheck = uDao.getUserByName("Author");
        assertEquals(2, toCheck.getId());
        assertEquals("Author", toCheck.getUserName());
        assertEquals("password", toCheck.getPassword());
        assertEquals(1, toCheck.getRole().size());
        assertTrue(toCheck.isEnabled());
    }

    @Test
    public void testGetUserByBadName() {
        try {
            uDao.getUserByName("Fake Name");
            fail("Should have hit UserDaoException");
        } catch (UserDaoException ex) {

        }
    }

    /**
     * Test of getAllUsers method, of class UserDBImpl.
     */
    @Test
    public void testGetAllUsersGoldenPath() throws UserDaoException {
        List<User> allUsers = uDao.getAllUsers();
        User first = allUsers.get(0);
        User last = allUsers.get(allUsers.size() - 1);
        assertEquals(2, allUsers.size());
        assertEquals(1, first.getId());
        assertEquals("Admin", first.getUserName());
        assertEquals("password", first.getPassword());
        assertTrue(first.isEnabled());
        assertEquals(2, last.getId());
        assertEquals("Author", last.getUserName());
        assertEquals("password", last.getPassword());
        assertTrue(last.isEnabled());
    }

    @Test
    public void testGetAllUsersEmptyResult() {
        try {
            template.update("DELETE FROM UserRole");
            template.update("DELETE FROM Users");
            uDao.getAllUsers();
            fail("Should have hit UserDaoException");
        } catch (UserDaoException ex) {

        }
    }

    /**
     * Test of updateUser method, of class UserDBImpl.
     */
    @Test
    public void testUpdateUserGoldenPath() throws UserDaoException, BadUpdateException, InvalidEntityException {
        User preEdit = uDao.getUserById(2);
        assertEquals(2, preEdit.getId());
        assertEquals("Author", preEdit.getUserName());
        assertEquals("password", preEdit.getPassword());
        preEdit.setUserName("Jim");
        preEdit.setPassword("new");
        uDao.updateUser(preEdit);
        User postEdit = uDao.getUserById(2);
        assertEquals(2, postEdit.getId());
        assertEquals("Jim", postEdit.getUserName());
        assertEquals("new", postEdit.getPassword());
    }

    @Test
    public void testUpdateUserNullObject() throws UserDaoException, BadUpdateException {
        try {
            User nullUser = null;
            uDao.updateUser(nullUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testUpdateUserNullUserName() throws UserDaoException, BadUpdateException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User(null, "new", true, roleSet);
            uDao.updateUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testUpdateUserNullPassword() throws UserDaoException, BadUpdateException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User("Jim", null, true, roleSet);
            uDao.updateUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testUpdateUserNullRoles() throws UserDaoException, BadUpdateException {
        try {
            User invalidUser = new User("Jim", "new", true, null);
            uDao.updateUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testUpdateUserBadUserName() throws UserDaoException, BadUpdateException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User(createBadUserName(), "new", true, roleSet);
            uDao.updateUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testUpdateUserBadPassword() throws UserDaoException, BadUpdateException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User("Jim", createBadPassword(), true, roleSet);
            uDao.updateUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of deleteUser method, of class UserDBImpl.
     */
    @Test
    public void testDeleteUser() throws UserDaoException, BadUpdateException {
        uDao.deleteUser(2);
        List<User> allUsers = uDao.getAllUsers();
        User first = allUsers.get(0);
        User last = allUsers.get(allUsers.size() - 1);
        assertEquals(1, allUsers.size());
        assertEquals(1, first.getId());
        assertEquals("Admin", first.getUserName());
        assertEquals("password", first.getPassword());
        assertTrue(first.isEnabled());
        assertEquals(first, last);
    }

    @Test
    public void testDeleteUserBadId() throws UserDaoException {
        try {
            uDao.deleteUser(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }
    }

    /**
     * Test of createUser method, of class UserDBImpl.
     */
    @Test
    public void testCreateUserGoldenPath() throws UserDaoException, InvalidEntityException {
        Set<Role> roleSet = uDao.getUserById(2).getRole();
        User toAdd = new User("Barb", "password", true, roleSet);
        User toTest = uDao.createUser(toAdd);
        assertEquals(3, toTest.getId());
        assertEquals("Barb", toTest.getUserName());
        assertEquals("password", toTest.getPassword());
        assertEquals(toAdd.getRole(), toTest.getRole());
        assertTrue(toTest.isEnabled());
    }

    @Test
    public void testCreateUserNullObject() throws UserDaoException {
        try {
            User nullUser = null;
            uDao.createUser(nullUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    @Test
    public void testCreateUserNullUserName() throws UserDaoException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User(null, "password", true, roleSet);
            uDao.createUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    @Test
    public void testCreateUserNullPassword() throws UserDaoException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User("Barb", null, true, roleSet);
            uDao.createUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    @Test
    public void testCreateUserNullRoles() throws UserDaoException {
        try {
            User invalidUser = new User("Barb", "password", true, null);
            uDao.createUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    @Test
    public void testCreateUserBadUserName() throws UserDaoException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User(createBadUserName(), "password", true, roleSet);
            uDao.createUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    @Test
    public void testCreateUserBadPassword() throws UserDaoException {
        try {
            Set<Role> roleSet = uDao.getUserById(2).getRole();
            User invalidUser = new User("Barb", createBadPassword(), true, roleSet);
            uDao.createUser(invalidUser);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }

    }

    private String createBadPassword() {
        char[] chars = new char[101];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

    private String createBadUserName() {
        char[] chars = new char[31];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }

}
