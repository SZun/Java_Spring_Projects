/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.services;

import com.sg.SuperheroSightings.TestAppConfig;
import com.sg.SuperheroSightings.entities.Location;
import com.sg.SuperheroSightings.exceptions.InvalidEntityException;
import com.sg.SuperheroSightings.exceptions.InvalidIdException;
import com.sg.SuperheroSightings.exceptions.InvalidNameException;
import com.sg.SuperheroSightings.exceptions.NoItemsException;
import com.sg.SuperheroSightings.repositories.LocationDaoInMem;
import java.math.BigDecimal;
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
public class LocationServiceTest {

    @Autowired
    LocationService toTest;

    @Autowired
    LocationDaoInMem dao;
    
    String testString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulu.";

    public LocationServiceTest() {
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
        dao.createLocation(new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000")));
        dao.createLocation(new Location(2,
                "Test Name 2",
                "Test Description 2",
                "Test Address 2",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000")));
        dao.createLocation(new Location(3,
                "Test Name 3",
                "Test Description 3",
                "Test Address 3",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000")));
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class LocationService.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        Location expected2 = new Location(2,
                "Test Name 2",
                "Test Description 2",
                "Test Address 2",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        Location expected3 = new Location(3,
                "Test Name 3",
                "Test Description 3",
                "Test Address 3",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));

        List<Location> fromDao = toTest.getAll();

        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    @Test
    public void testGetAllInvalidId() {
        dao.removeAll();
        try {
            toTest.getAll();
            fail("should hit NoItemsException when there are no items");
        } catch (NoItemsException ex) {

        }
    }

    /**
     * Test of getLocationById method, of class LocationService.
     */
    @Test
    public void testGetLocationById() throws InvalidIdException {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        Location fromDao = toTest.getLocationById(1);
        
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testGetLocationByIdInvalidId() {
       try {
           toTest.getLocationById(4);
           fail("should hit InvalidIdException when Id is invalid");
       } catch(InvalidIdException ex){}
        
    }

    /**
     * Test of addLocation method, of class LocationService.
     */
    @Test
    public void testAddLocation() throws InvalidEntityException, InvalidIdException {
        Location expected = new Location(4,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
           toTest.getLocationById(4);
           fail("should hit InvalidIdException when Id is invalid");
       } catch(InvalidIdException ex){}
        
        Location fromDao = toTest.addLocation(expected);
        assertEquals(expected,fromDao);
        
        fromDao = toTest.getLocationById(4);
        assertEquals(expected,fromDao);
    }
    
    public void testAddLocationNullName(){
        Location testLocation = new Location(
                null,
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationBlankName(){
        Location testLocation = new Location(
                "      ",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationEmptyName(){
        Location testLocation = new Location(
                "",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationNameTooLong(){
        Location testLocation = new Location(
                testString,
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationNullDescription(){
        Location testLocation = new Location(
                "Test Name",
                null,
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationBlankDescription(){
        Location testLocation = new Location(
                "Test Name",
                "      ",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationEmptyDescription(){
        Location testLocation = new Location(
                "Test Name",
                "",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationDescriptionTooLong(){
        Location testLocation = new Location(
                "Test Name",
                testString,
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    

    
    public void testAddLocationAddressTooLong(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                testString,
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationNullAddress(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                null,
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationBlankAddress(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "      ",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationEmptyAddress(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationNullLat(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                null,
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationGreaterThan90Lat() throws InvalidIdException{
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal(91),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationLessThanNeg90Lat() throws InvalidIdException{
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal(-91),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationNullLong(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                null);
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationLessThanNeg180Long(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal(-181));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testAddLocationGreaterThan180Long(){
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal(181));
        
        try {
            toTest.addLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }

    /**
     * Test of editLocation method, of class LocationService.
     */
    @Test
    public void testEditLocation() throws InvalidEntityException, InvalidIdException {
        Location expected = new Location(1,
                "New Test Name",
                "New Test Description",
                "New Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        Location original = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        Location fromDao = toTest.getLocationById(1);
        assertEquals(original,fromDao);
        
        toTest.editLocation(expected);
        
        fromDao = toTest.getLocationById(1);
        assertNotEquals(original,fromDao);
        assertEquals(expected,fromDao);
    }
    
    public void testEditLocationNullName() throws InvalidIdException{
        Location testLocation = new Location(1,
                null,
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationBlankName() throws InvalidIdException{
        Location testLocation = new Location(1,
                "      ",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationEmptyName() throws InvalidIdException{
        Location testLocation = new Location(1,
                "",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationTooLongName() throws InvalidIdException{
        Location testLocation = new Location(1,
                testString,
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationTooLongDescription() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                testString,
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationTooLongAddress() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                testString,
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationBlankDescription() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "      ",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationEmptyDescription() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationNullAddress() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                null,
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationBlankAddress() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                "      ",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationEmptyAddress() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                "",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationNullLat() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                null,
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationLessThanNeg90Lat() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal(-91),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationGreaterThan90Lat() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal(91),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationNullLong() throws InvalidIdException{
        Location testLocation = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                null);
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationLessThanNeg180Long() throws InvalidIdException{
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal(-180));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationGreaterThan180Long() throws InvalidIdException{
        Location testLocation = new Location(
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal(181));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    public void testEditLocationInvalidId() throws InvalidEntityException{
        Location testLocation = new Location(4,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        try {
            toTest.editLocation(testLocation);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }

    /**
     * Test of deleteLocationById method, of class LocationService.
     */
    @Test
    public void testDeleteLocationById() throws InvalidIdException {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        Location fromDao = toTest.getLocationById(1);
        
        assertEquals(expected,fromDao);
        
        toTest.deleteLocationById(1);
        
        try {
           toTest.getLocationById(1);
           fail("should hit InvalidIdException when Id is invalid");
       } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testDeleteLocationByIdInvalidId() {
        try {
           toTest.deleteLocationById(4);
           fail("should hit InvalidIdException when Id is invalid");
       } catch(InvalidIdException ex){}
    }
    
    @Test
    public void testGetLocationByName() throws InvalidNameException, InvalidEntityException {
        Location expected = new Location(1,
                "Test Name",
                "Test Description",
                "Test Address",
                new BigDecimal("1.0000000"),
                new BigDecimal("1.0000000"));
        
        Location fromDao = toTest.getLocationByName("Test Name");
        
        assertEquals(expected,fromDao);
    }
    
    @Test
    public void testGetSuperheroByNameNullName() throws InvalidNameException {
        try {
            toTest.getLocationByName(null);
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameBlankName() throws InvalidNameException {
        try {
            toTest.getLocationByName(" ");
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameEmptyName() throws InvalidNameException {
        try {
            toTest.getLocationByName("");
            fail("should hit InvalidEntityException when name is invalid");
        } catch(InvalidEntityException ex){}
    }
    
    @Test
    public void testGetSuperheroByNameInvalidName() throws InvalidEntityException {
        try {
            toTest.getLocationByName("Z");
            fail("should hit InvalidNameException when name is invalid");
        } catch(InvalidNameException ex){}
    }

}
