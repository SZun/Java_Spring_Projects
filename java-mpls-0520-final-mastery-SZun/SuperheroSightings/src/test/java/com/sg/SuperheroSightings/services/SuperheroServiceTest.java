/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Organization;
import com.sg.SuperheroSightings.entities.Power;
import com.sg.SuperheroSightings.entities.Superhero;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.SuperheroDaoInMem;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class SuperheroServiceTest {
    
    @Autowired
    SuperheroService toTest;
    
    @Autowired
    SuperheroDaoInMem dao;
    
    Power testPower = new Power(1, "A");
    
    String testString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulu.";
    
    List<Organization> orgs = new ArrayList<>();
    
    public SuperheroServiceTest() {
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
        orgs.clear();
        orgs.add(new Organization(1, "A","A","A"));
        dao.createSuperhero(new Superhero(1, "A", "A", testPower, orgs));
        dao.createSuperhero(new Superhero(2, "B", "B", testPower, orgs));
        dao.createSuperhero(new Superhero(3, "C", "C", testPower, orgs));
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class SuperheroService.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Superhero expected = new Superhero(1, "A", "A", testPower, orgs);
        Superhero expected2 = new Superhero(2, "B", "B", testPower, orgs);
        Superhero expected3 = new Superhero(3, "C", "C", testPower, orgs);
        
        List<Superhero> fromDao = toTest.getAll();
        
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
     * Test of getSuperheroById method, of class SuperheroService.
     */
    @Test
    public void testGetSuperheroById() throws InvalidIdException {
        Superhero expected = new Superhero(1, "A", "A", testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroById(1);
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetSuperheroByIdInvalidId() throws InvalidIdException {
        try {
            toTest.getSuperheroById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testGetSuperheroByName() throws InvalidNameException, InvalidEntityException {
        Superhero expected = new Superhero(1, "A", "A", testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroByName("A");
        
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testGetSuperheroByNameNullName() throws InvalidNameException {
        try {
            toTest.getSuperheroByName(null);
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameBlankName() throws InvalidNameException {
        try {
            toTest.getSuperheroByName(" ");
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameEmptyName() throws InvalidNameException {
        try {
            toTest.getSuperheroByName("");
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameInvalidName() throws InvalidEntityException {
        try {
            toTest.getSuperheroByName("Z");
            fail("should hit InvalidNameException when name is invalid");
        } catch(InvalidNameException ex){}
    }

    /**
     * Test of addSuperhero method, of class SuperheroService.
     */
    @Test
    public void testAddSuperhero() throws InvalidEntityException, InvalidIdException {
        Superhero expected = new Superhero(4, "D", "D", testPower, orgs);
        
        try {
            toTest.getSuperheroById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
        
        Superhero fromDao = toTest.addSuperhero(expected);
        assertEquals(expected, fromDao);
        
        fromDao = toTest.getSuperheroById(4);
        assertEquals(expected, fromDao);
    }
    
    @Test
    public void testAddSuperheroNullName() {
        Superhero testHero = new Superhero(4, null, "D", testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroBlankName() {
        Superhero testHero = new Superhero(4, "   ", "D", testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroEmptyName() { 
        Superhero testHero = new Superhero(4, "", "D", testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroTooLongName() { 
        Superhero testHero = new Superhero(4, testString, "D", testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroNullDescription() {
        Superhero testHero = new Superhero(4, "D", null, testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroTooLongDescription() {
        Superhero testHero = new Superhero(4, "D", testString, testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroBlankDescription() {
        Superhero testHero = new Superhero(4, "D", "   ", testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroEmptyDescription() { 
        Superhero testHero = new Superhero(4, "D", "", testPower, orgs);
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testAddSuperheroNullPower() {
        Superhero testHero = new Superhero(4, "D", "D");
        try {
            toTest.addSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }

    /**
     * Test of editSuperhero method, of class SuperheroService.
     */
    @Test
    public void testEditSuperhero() throws InvalidEntityException, InvalidIdException {
        Superhero expected = new Superhero(1, "D", "D", testPower, orgs);
        Superhero original = new Superhero(1, "A", "A", testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroById(1);
        assertEquals(original, fromDao);
        
        toTest.editSuperhero(expected);
        
        fromDao = toTest.getSuperheroById(1);
        assertNotEquals(original, fromDao);
        assertEquals(expected, fromDao);
    }
    
        @Test
    public void testEditSuperheroNullName() throws InvalidIdException {
        Superhero testHero = new Superhero(1, null, "D", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroBlankName() throws InvalidIdException {
        Superhero testHero = new Superhero(1, "   ", "D", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroEmptyName() throws InvalidIdException { 
        Superhero testHero = new Superhero(1, "", "D", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroTooLongName() throws InvalidIdException { 
        Superhero testHero = new Superhero(1, testString, "D", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroNullDescription() throws InvalidIdException {
        Superhero testHero = new Superhero(1, "D", null, testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroTooLongDescription() throws InvalidIdException {
        Superhero testHero = new Superhero(1, "D", testString, testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroBlankDescription() throws InvalidIdException {
        Superhero testHero = new Superhero(1, "D", "   ", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroEmptyDescription() throws InvalidIdException { 
        Superhero testHero = new Superhero(1, "D", "", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroNullPower() throws InvalidIdException {
        Superhero testHero = new Superhero(1, "D", "D", null, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testEditSuperheroInvalidId() throws InvalidEntityException {
        Superhero testHero = new Superhero(4, "D", "D", testPower, orgs);
        try {
            toTest.editSuperhero(testHero);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }

    /**
     * Test of deleteSuperheroById method, of class SuperheroService.
     */
    @Test
    public void testDeleteSuperheroById() throws Exception {
        Superhero toRemove = new Superhero(1, "A", "A", testPower, orgs);
        
        Superhero fromDao = toTest.getSuperheroById(1);
        assertEquals(toRemove,fromDao);
        
        toTest.deleteSuperheroById(1);
        
        try {
            toTest.getSuperheroById(1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testDeleteSuperheroByIdInvalidId() throws Exception {
        try {
            toTest.deleteSuperheroById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
}
