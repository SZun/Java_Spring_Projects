/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.repositories;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.DaoException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("prod")
public class OrganizationDaoDBTest {
    
    @Autowired
    OrganizationDao toTest;
    
    @Autowired
    JdbcTemplate jdbc;
    
    List<Superhero> testMembers = new ArrayList();
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        testMembers = new ArrayList();
        testMembers.add(new Superhero(1, "A","A", new Power(1,"A"), null));
        
        jdbc.update("DELETE FROM LocationsSuperheros");
        jdbc.update("DELETE FROM OrganizationsSuperheros");
        jdbc.update("DELETE FROM Organizations");
        jdbc.update("DELETE FROM Superheros");
        jdbc.update("DELETE FROM Powers");
        
        jdbc.update("ALTER TABLE Organizations auto_increment = 1");
        jdbc.update("ALTER TABLE Superheros auto_increment = 1");
        jdbc.update("ALTER TABLE Powers auto_increment = 1");
        
        jdbc.update("INSERT INTO Organizations(Name,Address,Email) VALUES('A','A','A'),('B','B','B'),('C','C','C')");
        jdbc.update("INSERT INTO Powers(Name) VALUES('A')");
        jdbc.update("INSERT INTO Superheros(Name, Description, PowerId) VALUES('A','A', 1)");
        jdbc.update("INSERT INTO OrganizationsSuperheros(SuperheroId, OrganizationId) VALUES(1,1),(1,2),(1,3)");
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    /**
     * Test of addMember method, of class OrganizationDaoDB.
     */
    @Test
    public void addMember() {
        List<Organization> allOrgs = toTest.getAllOrganizations();
        Superhero testSup = new Superhero(1, "A","A", new Power(1,"A"), null);
        
        int res = toTest.addMember(allOrgs, 1);
        assertNotEquals(0, res);
        
        allOrgs = toTest.getAllOrganizations();
        assertEquals(3, allOrgs.size());
        assertTrue(allOrgs.get(0).getMembers().contains(testSup));
        assertTrue(allOrgs.get(1).getMembers().contains(testSup));
        assertTrue(allOrgs.get(2).getMembers().contains(testSup));
    }
    
    /**
     * Test of getOrganizationsBySuperheroId method, of class OrganizationDaoDB.
     */
    @Test
    public void getOrganizationsBySuperheroId() {
        Organization expected = new Organization(1, "A","A","A");
        expected.setMembers(testMembers);
        
        Organization expected2 = new Organization(2, "B","B","B");
        expected2.setMembers(testMembers);
        
        Organization expected3 = new Organization(3, "C","C","C");
        expected3.setMembers(testMembers);
        
        List<Organization> fromDao = toTest.getOrganizationsBySuperheroId(1);
        
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }
    
    
    /**
     * Test of getOrganizationByName method, of class OrganizationDaoDB.
     */
    @Test
    public void getOrganizationByName() throws DaoException {
        Organization expected = new Organization(1, "A","A","A");
        expected.setMembers(testMembers);
        
        Organization fromDao = toTest.getOrganizationByName("A");
        assertEquals(expected, fromDao);
    }
    
    /**
     * Test of getOrganizationByName method, of class OrganizationDaoDB.
     */
    @Test
    public void getOrganizationByNameInvalidName() {
        try {
            toTest.getOrganizationByName("z");
            fail("should hit DaoException when name is invalid");
        } catch(DaoException ex) {}
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization expected = new Organization(1, "A","A","A");
        expected.setMembers(testMembers);
        
        Organization expected2 = new Organization(2, "B","B","B");
        expected2.setMembers(testMembers);
        
        Organization expected3 = new Organization(3, "C","C","C");
        expected3.setMembers(testMembers);
        
        List<Organization> fromDao = toTest.getAllOrganizations();
        
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    /**
     * Test of createOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testCreateOrganization() throws DaoException {
        Organization expected = new Organization(4,"D","D","D");
        expected.setMembers(testMembers);
        
        try {
            toTest.getOrganizationById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
        
        Organization added = toTest.createOrganization(expected);
        assertEquals(expected, added);
        
        added = toTest.getOrganizationById(4);
        assertEquals(expected, added);
    }

    /**
     * Test of getOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationById() throws DaoException {
        Organization expected = new Organization(1, "A","A","A");
        expected.setMembers(testMembers);
        
        Organization fromDao = toTest.getOrganizationById(1);
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetOrganizationByIdInvalidId() {
        try {
            toTest.getOrganizationById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of editOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testEditOrganization() throws DaoException {
        Organization expected = new Organization(1, "Z","Z","Z");
        expected.setMembers(testMembers);
        
        Organization original = new Organization(1, "A","A","A");
        original.setMembers(testMembers);
        
        Organization fromDao = toTest.getOrganizationById(1);
        
        assertEquals(original,fromDao);
        
        assertNotEquals(0, toTest.editOrganization(expected));
        
        fromDao = toTest.getOrganizationById(1);
        
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testEditOrganizationInvalidId() {
        Organization testEdit = new Organization(4, "A","A","A");
        testEdit.setMembers(testMembers);
        
        assertEquals(0, toTest.editOrganization(testEdit));
    }

    /**
     * Test of removeOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testRemoveOrganization() throws DaoException {
        Organization toRemove = new Organization(1, "A","A","A");
        toRemove.setMembers(testMembers);
        
        Organization fromDao = toTest.getOrganizationById(1);
        assertEquals(toRemove, fromDao);
        
        assertNotEquals(0, toTest.removeOrganization(1));
        
        try {
            toTest.getOrganizationById(1);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }
    
    @Test
    public void testRemoveOrganizationInvalidId() {
        assertEquals(0, toTest.removeOrganization(4));
    }
    
    
    
}
