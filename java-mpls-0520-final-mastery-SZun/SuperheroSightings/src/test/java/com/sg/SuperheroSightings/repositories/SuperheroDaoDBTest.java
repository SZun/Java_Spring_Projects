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
public class SuperheroDaoDBTest {
    
    @Autowired
    SuperheroDao toTest;
    
    @Autowired
    JdbcTemplate jdbc;
    
    Power testPower = new Power(1,"A");
    
    List<Organization> orgs = new ArrayList<>();
    
    public SuperheroDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        orgs.clear();
        orgs.add(new Organization(1, "A","A","A"));
        
        jdbc.update("DELETE FROM LocationsSuperheros");
        jdbc.update("DELETE FROM OrganizationsSuperheros");
        jdbc.update("DELETE FROM Organizations");
        jdbc.update("DELETE FROM Superheros");
        jdbc.update("DELETE FROM Powers");
        
        jdbc.update("ALTER TABLE Superheros auto_increment = 1");
        jdbc.update("ALTER TABLE Organizations auto_increment = 1");
        jdbc.update("ALTER TABLE Powers auto_increment = 1");
        
        jdbc.update("INSERT INTO Powers(Name) VALUES('A')");
        jdbc.update("INSERT INTO Superheros(Name, Description, PowerId) VALUES('A','A', 1),('B','B', 1),('C','C', 1)");
        jdbc.update("INSERT INTO Organizations(Name,Address,Email) VALUES('A','A','A')");
        jdbc.update("INSERT INTO OrganizationsSuperheros(SuperheroId, OrganizationId) VALUES(1,1)");
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllSuperheros method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheros() {
        Superhero expected = new Superhero(1,"A","A",testPower, orgs);
        Superhero expected2 = new Superhero(2,"B","B",testPower, new ArrayList<>());
        Superhero expected3 = new Superhero(3,"C","C",testPower, new ArrayList<>());
        
        List<Superhero> fromDao = toTest.getAllSuperheros();
        
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    /**
     * Test of createSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testCreateSuperhero() throws DaoException {
        Superhero expected = new Superhero(4,"A","A",testPower, orgs);
        
        try {
            toTest.getSuperheroById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
        
        Superhero fromDao = toTest.createSuperhero(expected);
        assertEquals(expected, fromDao);
        
        fromDao = toTest.getSuperheroById(4);
        assertEquals(expected, fromDao);
    }

    /**
     * Test of getSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetSuperheroById() throws DaoException {
        Superhero expected = new Superhero(1,"A","A",testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroById(1);
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetSuperheroByIdInvalidId() {
        try {
            toTest.getSuperheroById(4);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }
    
    /**
     * Test of getSuperheroByName method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetSuperheroByName() throws DaoException {
        Superhero expected = new Superhero(1,"A","A",testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroByName("A");
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetSuperheroByIdInvalidName() {
        try {
            toTest.getSuperheroByName("Z");
            fail("should hit DaoException when Name is invalid");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of editSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testEditSuperhero() {
    }
    
    @Test
    public void testEditSuperheroInvalidId() {
        Superhero testSuperhero = new Superhero(4,"A","A",testPower, orgs);
        assertEquals(0, toTest.editSuperhero(testSuperhero));
    }

    /**
     * Test of removeSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testRemoveSuperhero() throws DaoException {
        Superhero toRemove = new Superhero(1,"A","A",testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroById(1);
        assertEquals(toRemove, fromDao);
        
        assertNotEquals(0, toTest.removeSuperhero(1));
        
        try {
            toTest.getSuperheroById(1);
            fail("should hit DaoException when Id is invalid");
        } catch (DaoException ex) {
        }
    }
    
    @Test
    public void testRemoveSuperheroInvalidId() {
        assertEquals(0, toTest.removeSuperhero(4));
    }
    
}
