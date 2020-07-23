/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.daos;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.BadUpdateException;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.RoleDaoException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
public class RoleDBImplTest {

    @Autowired
    RoleDao rDao;

    @Autowired
    JdbcTemplate template;

    @BeforeEach
    public void setUp() {
        template.update("DELETE FROM UserRole");
        template.update("DELETE FROM Roles");
        template.update("ALTER TABLE Roles auto_increment = 1");
        template.update("INSERT INTO Roles(name) VALUES"
                + "('ROLE_ADMIN'),"
                + "('ROLE_AUTHOR')");
    }

    /**
     * Test of getRoleById method, of class RoleDBImpl.
     */
    @Test
    public void testGetRoleByIdGoldenPath() throws RoleDaoException {
        Role toCheck = rDao.getRoleById(2);
        assertEquals(2, toCheck.getId());
        assertEquals("ROLE_AUTHOR", toCheck.getRole());
    }

    @Test
    public void testGetRoleByBadId() {
        try {
            rDao.getRoleById(-1);
            fail("Should have hit RoleDaoException");
        } catch (RoleDaoException ex) {

        }
    }

    /**
     * Test of getRoleByName method, of class RoleDBImpl.
     */
    @Test
    public void testGetRoleByName() throws RoleDaoException {
        Role toCheck = rDao.getRoleByName("ROLE_ADMIN");
        assertEquals(1, toCheck.getId());
        assertEquals("ROLE_ADMIN", toCheck.getRole());
    }

    @Test
    public void testGetRoleByBadName() {
        try {
            rDao.getRoleByName("ROLE_FAKE");
            fail("Should have hit RoleDaoException");
        } catch (RoleDaoException ex) {

        }
    }

    /**
     * Test of getAllRoles method, of class RoleDBImpl.
     */
    @Test
    public void testGetAllRolesGoldenPath() throws RoleDaoException {
        List<Role> allRoles = rDao.getAllRoles();
        Role first = allRoles.get(0);
        Role last = allRoles.get(allRoles.size() - 1);
        assertEquals(2, allRoles.size());
        assertEquals(1, first.getId());
        assertEquals("ROLE_ADMIN", first.getRole());
        assertEquals(2, last.getId());
        assertEquals("ROLE_AUTHOR", last.getRole());
    }

    @Test
    public void testGetAllRolesEmptyResult() {
        try {
            template.update("DELETE FROM Roles");
            rDao.getAllRoles();
        } catch (RoleDaoException ex) {

        }

    }

    /**
     * Test of updateRole method, of class RoleDBImpl.
     */
    @Test
    public void testUpdateRoleGoldenPath() throws RoleDaoException, BadUpdateException, InvalidEntityException {
        Role preEdit = rDao.getRoleById(2);
        assertEquals(2, preEdit.getId());
        assertEquals("ROLE_AUTHOR", preEdit.getRole());
        preEdit.setRole("ROLE_EMPLOYEE");
        rDao.updateRole(preEdit);
        Role postEdit = rDao.getRoleById(2);
        assertEquals(2, postEdit.getId());
        assertEquals("ROLE_EMPLOYEE", postEdit.getRole());
    }

    @Test
    public void testUpdateRoleNullObject() throws RoleDaoException, BadUpdateException {
        try {
            Role nullRole = null;
            rDao.updateRole(nullRole);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testUpdateRoleBadId() throws RoleDaoException, InvalidEntityException {
        try {
            Role preEdit = rDao.getRoleById(2);
            assertEquals(2, preEdit.getId());
            assertEquals("ROLE_AUTHOR", preEdit.getRole());
            preEdit.setRole("ROLE_EMPLOYEE");
            preEdit.setId(-1);
            rDao.updateRole(preEdit);
            fail("Should have thrown BadUpdateException");
        } catch (BadUpdateException ex) {

        }
    }
    
    @Test
    public void testUpdateRoleNullRoleName() throws RoleDaoException, BadUpdateException {
        try {
            Role invalidRole = new Role(null);
            rDao.updateRole(invalidRole);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testUpdateRoleBadRoleName() throws RoleDaoException, BadUpdateException {
        try {
            Role invalidRole = new Role(createBadRoleName());
            rDao.updateRole(invalidRole);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of removeRole method, of class RoleDBImpl.
     */
    @Test
    public void testRemoveRole() throws RoleDaoException, BadUpdateException {
        List<Role> preRemove = rDao.getAllRoles();
        assertEquals(2, preRemove.size());
        Role author = preRemove.get(preRemove.size() - 1);
        rDao.removeRole(author.getId());
        List<Role> postRemove = rDao.getAllRoles();
        Role first = postRemove.get(0);
        Role last = postRemove.get(postRemove.size() - 1);
        assertEquals(1, postRemove.size());
        assertEquals(1, first.getId());
        assertEquals("ROLE_ADMIN", first.getRole());
        assertEquals(last, first);

    }

    @Test
    public void testRemoveRoleBadId() throws RoleDaoException {
        try {
            rDao.removeRole(-1);
            fail("Should have hit BadUpdateException");
        } catch (BadUpdateException ex) {

        }
    }

    /**
     * Test of createRole method, of class RoleDBImpl.
     */
    @Test
    public void testCreateRoleGoldenPath() throws RoleDaoException, InvalidEntityException {
        Role toCheck = rDao.createRole(new Role("ROLE_GUEST"));
        assertEquals(3, toCheck.getId());
        assertEquals("ROLE_GUEST", toCheck.getRole());
    }

    @Test
    public void testCreateRoleNullObject() throws RoleDaoException {
        try {
            Role nullRole = null;
            rDao.createRole(nullRole);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testCreateRoleNullRoleName() throws RoleDaoException {
        try {
            Role invalidRole = new Role(null);
            rDao.createRole(invalidRole);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }
    
    @Test
    public void testCreateRoleBadRoleName() throws RoleDaoException {
        try {
            Role invalidRole = new Role(createBadRoleName());
            rDao.createRole(invalidRole);
            fail("Should have hit InvalidEntityException");
        } catch (InvalidEntityException ex) {

        }
    }

    private String createBadRoleName() {
        char[] chars = new char[31];
        Arrays.fill(chars, 'a');
        return new String(chars);
    }
    
    
    
}
