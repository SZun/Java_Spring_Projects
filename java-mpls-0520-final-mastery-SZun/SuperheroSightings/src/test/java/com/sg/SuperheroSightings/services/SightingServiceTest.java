/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Sighting;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.SightingDaoInMem;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class SightingServiceTest {
    
    @Autowired
    SightingService toTest;
    
    @Autowired
    SightingDaoInMem dao;
    
    LocalDate sDate = LocalDate.of(2020, 01, 01);

    Location loc = new Location(1,
            "Test Name",
            "Test Description",
            "Test Address",
            new BigDecimal("1.0000000"),
            new BigDecimal("1.0000000"));

    Superhero hero = new Superhero(1, "A", "A", new Power(1, "A"), null);
    
    public SightingServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        dao.removeAll();
        dao.createSighting(new Sighting(1, sDate, loc, hero));
        dao.createSighting(new Sighting(2, sDate, loc, hero));
        dao.createSighting(new Sighting(3, sDate, loc, hero));
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class SightingService.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Sighting expected = new Sighting(1, sDate, loc, hero);
        Sighting expected2 = new Sighting(2, sDate, loc, hero);
        Sighting expected3 = new Sighting(3, sDate, loc, hero);
        
        List<Sighting> fromDao = toTest.getAll();
        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }
    
    @Test
    public void testGetAllNoItems() {
        dao.removeAll();
        try {
            toTest.getAll();
            fail("should hit NoItemsException when not items are found");
        } catch(NoItemsException ex){}
    }

    /**
     * Test of getSightingById method, of class SightingService.
     */
    @Test
    public void testGetSightingById() throws InvalidIdException {
        Sighting expected = new Sighting(1, sDate, loc, hero);
        
        Sighting fromDao = toTest.getSightingById(1);
        
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testGetSightingByIdInvalidId() {
        try {
            toTest.getSightingById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }

    /**
     * Test of addSighting method, of class SightingService.
     */
    @Test
    public void testAddSighting() throws InvalidEntityException, InvalidIdException {
        Sighting expected = new Sighting(4, sDate, loc, hero);
        
        try {
            toTest.getSightingById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
        
        Sighting fromDao = toTest.addSighting(expected);
        assertEquals(expected,fromDao);
        
        fromDao = toTest.getSightingById(4);
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testAddSightingNullDate() {
        Sighting testSighting = new Sighting(4, null, loc, hero);
        
        try {
            toTest.addSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testAddSightingFutureDate() {
        LocalDate tommorrow = LocalDate.now().plusDays(1);
        Sighting testSighting = new Sighting(4, tommorrow, loc, hero);
        
        try {
            toTest.addSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testAddSightingNullLocation() {
        Sighting testSighting = new Sighting(4, sDate, null, hero);
        
        try {
            toTest.addSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }
    
    
    @Test
    public void testAddSightingNullHero() {
        Sighting testSighting = new Sighting(4, sDate, loc, null);
        
        try {
            toTest.addSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }

    /**
     * Test of editSighting method, of class SightingService.
     */
    @Test
    public void testEditSighting() throws InvalidIdException, InvalidEntityException {
        Sighting expected = new Sighting(1, LocalDate.of(2020,02,01), loc, hero);
        Sighting original = new Sighting(1, sDate, loc, hero);
        
        Sighting fromDao = toTest.getSightingById(1);
        assertEquals(original,fromDao);
        
        toTest.editSighting(expected);
        
        fromDao = toTest.getSightingById(1);
        assertNotEquals(original,fromDao);
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testEditSightingNullDate() throws InvalidIdException {
        Sighting testSighting = new Sighting(1, null, loc, hero);
        
        try {
            toTest.editSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditSightingNullLocation() throws InvalidIdException {
        Sighting testSighting = new Sighting(1, sDate, null, hero);
        
        try {
            toTest.editSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditSightingNullHero() throws InvalidIdException {
        Sighting testSighting = new Sighting(1, sDate, loc, null);
        
        try {
            toTest.editSighting(testSighting);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex) {}
    }
    
    @Test
    public void testEditSightingInvalidId() throws InvalidEntityException {
        Sighting testSighting = new Sighting(4, sDate, loc, hero);
        
        try {
            toTest.editSighting(testSighting);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex) {}
    }

    /**
     * Test of deleteSightingById method, of class SightingService.
     */
    @Test
    public void testDeleteSightingById() throws InvalidIdException {
        Sighting toRemove = new Sighting(1, sDate, loc, hero);
        
        Sighting fromDao = toTest.getSightingById(1);
        assertEquals(toRemove,fromDao);
    }
    
    @Test
    public void testDeleteSightingByIdInvalidId() {
        try {
            toTest.deleteSightingById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
}
