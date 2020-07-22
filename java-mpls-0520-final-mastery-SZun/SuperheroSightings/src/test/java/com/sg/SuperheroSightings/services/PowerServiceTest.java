/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.PowerDaoInMem;
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
public class PowerServiceTest {
    
    @Autowired
    PowerService toTest;
    
    @Autowired
    PowerDaoInMem dao;
    
    String testString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulu.";
    
    public PowerServiceTest() {
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
        dao.createPower(new Power(1,"A"));
        dao.createPower(new Power(2,"B"));
        dao.createPower(new Power(3,"C"));
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class PowerService.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Power expected = new Power(1,"A");
        Power expected2 = new Power(2,"B");
        Power expected3 = new Power(3,"C");
        
        List<Power> fromDao = toTest.getAll();
        
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
     * Test of getPowerById method, of class PowerService.
     */
    @Test
    public void testGetPowerById() throws InvalidIdException {
        Power expected = new Power(1,"A");
        
        Power fromDao = toTest.getPowerById(1);
        
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testGetPowerByIdInvalidId()  {
        try {
            toTest.getPowerById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testGetPowerByName() throws InvalidNameException, InvalidEntityException {
        Power expected = new Power(1,"A");
        
        Power fromDao = toTest.getPowerByName("A");
        
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testGetSuperheroByNameNullName() throws InvalidNameException {
        try {
            toTest.getPowerByName(null);
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameBlankName() throws InvalidNameException {
        try {
            toTest.getPowerByName(" ");
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameEmptyName() throws InvalidNameException {
        try {
            toTest.getPowerByName("");
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameInvalidName() throws InvalidEntityException {
        try {
            toTest.getPowerByName("Z");
            fail("should hit InvalidNameException when name is invalid");
        } catch(InvalidNameException ex){}
    }

    /**
     * Test of addPower method, of class PowerService.
     */
    @Test
    public void testAddPower() throws InvalidEntityException, InvalidIdException {
        Power expected = new Power(4,"D");
        
        try {
            toTest.getPowerById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
        
        Power fromDao = toTest.addPower(expected);
        assertEquals(expected,fromDao);
        
        fromDao = toTest.getPowerById(4);
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testAddPowerNullName() {
        Power testPower = new Power(4, null);
        
        try {
            toTest.addPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddPowerBlankName() {
        Power testPower = new Power(4, "   ");
        
        try {
            toTest.addPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddPowerEmptyName() throws InvalidEntityException {
        Power testPower = new Power(4, "");
        
        try {
            toTest.addPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddPowerTooLongName() throws InvalidEntityException {
        Power testPower = new Power(4, testString);
        
        try {
            toTest.addPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }

    /**
     * Test of editPower method, of class PowerService.
     */
    @Test
    public void testEditPower() throws InvalidIdException, InvalidEntityException {
        Power expected = new Power(1,"D");
        Power original = new Power(1, "A");
        
        Power fromDao = toTest.getPowerById(1);
        assertEquals(original, fromDao);
        
        toTest.editPower(expected);
        
        fromDao = toTest.getPowerById(1);
        assertNotEquals(original, fromDao);
        assertEquals(expected, fromDao);
        
    }
    
    @Test
    public void testEditPowerNullName() throws InvalidIdException {
        Power testPower = new Power(1, null);
        
        try {
            toTest.editPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditPowerBlankName() throws InvalidIdException {
        Power testPower = new Power(1, "   ");
        
        try {
            toTest.editPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditPowerEmptyName() throws InvalidIdException {
        Power testPower = new Power(1, "");
        
        try {
            toTest.editPower(testPower);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditPowerInvalidId() throws InvalidEntityException {
        Power testPower = new Power(4, "A");
        
        try {
            toTest.editPower(testPower);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }

    /**
     * Test of deletePowerById method, of class PowerService.
     */
    @Test
    public void testDeletePowerById() throws InvalidIdException {
        Power toRemove = new Power(1, "A");
        
        Power fromDao = toTest.getPowerById(1);
        assertEquals(toRemove, fromDao);
        
        toTest.deletePowerById(1);
        
        try {
            toTest.getPowerById(1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testDeletePowerByIdInvalidId() {
        try {
            toTest.deletePowerById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
}
