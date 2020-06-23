/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dvdlibrary.service;

import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryDAOException;
import com.sg.dvdlibrary.dvdlibrary.dao.DVDLibraryInMemDAO;
import com.sg.dvdlibrary.dvdlibrary.dto.DVD;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samg.zun
 */
public class DVDLibraryServiceLayerTest {

    public DVDLibraryServiceLayerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createDVD method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testCreateDVDGoldenPath() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        DVD toAdd = new DVD();
        toAdd.setTitle("a");
        toAdd.setReleaseDate("a");
        toAdd.setMpaaRating("a");
        toAdd.setDirectorsName("a");
        toAdd.setStudio("a");
        toAdd.setNote("a");

        DVD toAdd2 = new DVD();
        toAdd2.setTitle("b");
        toAdd2.setReleaseDate("b");
        toAdd2.setMpaaRating("b");
        toAdd2.setDirectorsName("b");
        toAdd2.setStudio("b");
        toAdd2.setNote("b");

        try {
            DVD returned = toTest.createDVD(toAdd);

            assertEquals(1, returned.getId());
            assertEquals("a", returned.getTitle());
            assertEquals("a", returned.getReleaseDate());
            assertEquals("a", returned.getMpaaRating());
            assertEquals("a", returned.getDirectorsName());
            assertEquals("a", returned.getStudio());
            assertEquals("a", returned.getNote());

            DVD saveValidation = toTest.getDVD(1);

            assertEquals(1, saveValidation.getId());
            assertEquals("a", saveValidation.getTitle());
            assertEquals("a", saveValidation.getReleaseDate());
            assertEquals("a", saveValidation.getMpaaRating());
            assertEquals("a", saveValidation.getDirectorsName());
            assertEquals("a", saveValidation.getStudio());
            assertEquals("a", saveValidation.getNote());

            DVD returned2 = toTest.createDVD(toAdd2);

            assertEquals(2, returned2.getId());
            assertEquals("b", returned2.getTitle());
            assertEquals("b", returned2.getReleaseDate());
            assertEquals("b", returned2.getMpaaRating());
            assertEquals("b", returned2.getDirectorsName());
            assertEquals("b", returned2.getStudio());
            assertEquals("b", returned2.getNote());

            DVD saveValidation2 = toTest.getDVD(2);

            assertEquals(2, saveValidation2.getId());
            assertEquals("b", saveValidation2.getTitle());
            assertEquals("b", saveValidation2.getReleaseDate());
            assertEquals("b", saveValidation2.getMpaaRating());
            assertEquals("b", saveValidation2.getDirectorsName());
            assertEquals("b", saveValidation2.getStudio());
            assertEquals("b", saveValidation2.getNote());

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        } catch (DVDLibraryDataValidationException ex) {
            fail("should not hit DVDLibraryDataValidationException during gp test");
        } catch (DVDLibraryDuplicateIdException ex) {
            fail("should not hit DVDLibraryDuplicateIdException during gp test");
        }

    }

    @Test
    public void testCreateDVDBlankTitle() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        DVD toAdd = new DVD();
        toAdd.setTitle("");
        toAdd.setReleaseDate("a");
        toAdd.setMpaaRating("a");
        toAdd.setDirectorsName("a");
        toAdd.setStudio("a");
        toAdd.setNote("a");

        try {
            DVD returned = toTest.createDVD(toAdd);

            fail("Should have hit exception in DVDLibraryDataValidationException");

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        } catch (DVDLibraryDataValidationException ex) {

        } catch (DVDLibraryDuplicateIdException ex) {
            fail("should not hit DVDLibraryDuplicateIdException during gp test");
        }
    }

    /**
     * Test of getAllDVDs method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testGetAllDVDsGP() throws Exception {
        
    }
    
    /**
     * Test of getAllDVDs method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testGetAllDVDsEmpty() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        DVD toAdd = new DVD();
        toAdd.setTitle("a");
        toAdd.setReleaseDate("a");
        toAdd.setMpaaRating("a");
        toAdd.setDirectorsName("a");
        toAdd.setStudio("a");
        toAdd.setNote("a");

        DVD toAdd2 = new DVD();
        toAdd2.setTitle("b");
        toAdd2.setReleaseDate("b");
        toAdd2.setMpaaRating("b");
        toAdd2.setDirectorsName("b");
        toAdd2.setStudio("b");
        toAdd2.setNote("b");

        try {
            toTest.createDVD(toAdd);
            toTest.createDVD(toAdd2);
            
            List<DVD> returned = toTest.getAllDVDs();

            assertEquals(1, returned.get(0).getId());
            assertEquals("a", returned.get(0).getTitle());
            assertEquals("a", returned.get(0).getReleaseDate());
            assertEquals("a", returned.get(0).getMpaaRating());
            assertEquals("a", returned.get(0).getDirectorsName());
            assertEquals("a", returned.get(0).getStudio());
            assertEquals("a", returned.get(0).getNote());

            assertEquals(2, returned.get(1).getId());
            assertEquals("b", returned.get(1).getTitle());
            assertEquals("b", returned.get(1).getReleaseDate());
            assertEquals("b", returned.get(1).getMpaaRating());
            assertEquals("b", returned.get(1).getDirectorsName());
            assertEquals("b", returned.get(1).getStudio());
            assertEquals("b", returned.get(1).getNote());


        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        }
    }

    /**
     * Test of getDVD method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testGetDVD() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());


        try {
            List<DVD> expected = new ArrayList<>();
            List<DVD> res = toTest.getAllDVDs();
            
            assertEquals(expected, res);

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        }
    }

    @Test
    public void testGetDVDException() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        try {
            DVD saveValidation = toTest.getDVD(1);

            fail("should hit DVDLibraryInvalidIdException during gp test");
        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during test");
        } catch (DVDLibraryInvalidIdException ex) {

        }
    }

    /**
     * Test of removeDVD method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testRemoveDVDGP() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        DVD toAdd = new DVD();
        toAdd.setTitle("a");
        toAdd.setReleaseDate("a");
        toAdd.setMpaaRating("a");
        toAdd.setDirectorsName("a");
        toAdd.setStudio("a");
        toAdd.setNote("a");

        try {

            toTest.createDVD(toAdd);

            toTest.removeDVD(1);

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        }
    }

    @Test
    public void testRemoveDVDException() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        try {

            toTest.removeDVD(1);
            
            fail("should hit IndexOutOfBoundsException during test");
            
        } catch (IndexOutOfBoundsException ex) {

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during  test");
        }
    }

    /**
     * Test of editDVD method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testEditDVDGP() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());
        
        DVD toEdit = new DVD();
        toEdit.setId(1);
        toEdit.setTitle("b");
        toEdit.setReleaseDate("b");
        toEdit.setMpaaRating("b");
        toEdit.setDirectorsName("b");
        toEdit.setStudio("b");
        toEdit.setNote("b");
        
        DVD toAdd = new DVD();
        toAdd.setTitle("a");
        toAdd.setReleaseDate("a");
        toAdd.setMpaaRating("a");
        toAdd.setDirectorsName("a");
        toAdd.setStudio("a");
        toAdd.setNote("a");
        
        try {
            
            toTest.createDVD(toAdd);
            toTest.editDVD(toEdit);
            
            DVD retrieved = toTest.getDVD(1);
            
            assertEquals(1, retrieved.getId());
            assertEquals("b", retrieved.getTitle());
            assertEquals("b", retrieved.getReleaseDate());
            assertEquals("b", retrieved.getMpaaRating());
            assertEquals("b", retrieved.getDirectorsName());
            assertEquals("b", retrieved.getStudio());
            assertEquals("b", retrieved.getNote());
            
            
        } catch (DVDLibraryInvalidIdException ex) {
            fail("should hit DVDLibraryInvalidIdException during GP test");
        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during GP test");
        }
    }
    
    @Test
    public void testEditDVDException() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());
        
        DVD toEdit = new DVD();
        toEdit.setTitle("a");
        toEdit.setReleaseDate("a");
        toEdit.setMpaaRating("a");
        toEdit.setDirectorsName("a");
        toEdit.setStudio("a");
        toEdit.setNote("a");
        
        try {

            toTest.editDVD(toEdit);
            
            fail("should hit DVDLibraryInvalidIdException during test");
            
        } catch (DVDLibraryInvalidIdException ex) {

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during  test");
        }
    }

    /**
     * Test of getDVDbyTitle method, of class DVDLibraryServiceLayer.
     */
    @Test
    public void testGetDVDbyTitleGP() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        DVD toAdd = new DVD();
        toAdd.setTitle("a");
        toAdd.setReleaseDate("a");
        toAdd.setMpaaRating("a");
        toAdd.setDirectorsName("a");
        toAdd.setStudio("a");
        toAdd.setNote("a");

        try {
            toTest.createDVD(toAdd);

            DVD retrieved = toTest.getDVDbyTitle("a");

            assertEquals("a", retrieved.getTitle()); //Is this redundent?

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        }

    }

    @Test
    public void testGetDVDbyTitleException() throws Exception {
        DVDLibraryServiceLayer toTest = new DVDLibraryServiceLayer(new DVDLibraryInMemDAO());

        try {

            DVD retrieved = toTest.getDVDbyTitle(" ");

            fail("Should have hit DVDLibraryBlankTitleException");

        } catch (DVDLibraryDAOException ex) {
            fail("should not hit DVDLibraryDAOException during gp test");
        } catch (DVDLibraryBlankTitleException ex) {

        }

    }

}
