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
import com.sg.SuperheroSightings.repositories.OrganizationDaoInMem;
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
public class OrganizationServiceTest {

    @Autowired
    OrganizationService toTest;

    @Autowired
    OrganizationDaoInMem dao;

    List<Superhero> testMembers = new ArrayList();

    String testString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulu.";

    public OrganizationServiceTest() {

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
        testMembers.add(new Superhero(1, "A", "A", new Power(1, "A"), null));
        Organization org1 = new Organization(1, "A", "A", "email@domain.com");
        org1.setMembers(testMembers);
        dao.createOrganization(org1);
        Organization org2 = new Organization(2, "B", "B", "email@domain.com");
        org2.setMembers(testMembers);
        dao.createOrganization(org2);
        Organization org3 = new Organization(3, "C", "C", "email@domain.com");
        org3.setMembers(testMembers);
        dao.createOrganization(org3);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getOrganizationsBySuper method, of class OrganizationDaoDB.
     */
    @Test
    public void getOrganizationsBySuper() throws NoItemsException {
        Organization expected = new Organization(1, "A", "A", "email@domain.com");
        expected.setMembers(testMembers);

        Organization expected2 = new Organization(2, "B", "B", "email@domain.com");
        expected2.setMembers(testMembers);

        Organization expected3 = new Organization(3, "C", "C", "email@domain.com");
        expected3.setMembers(testMembers);

        List<Organization> fromDao = toTest.getOrganizationsBySuper(1);

        assertEquals(3, fromDao.size());
        assertTrue(fromDao.contains(expected));
        assertTrue(fromDao.contains(expected2));
        assertTrue(fromDao.contains(expected3));
    }

    @Test
    public void getOrganizationsBySuperNoItems() throws NoItemsException {
        try {
            toTest.getOrganizationsBySuper(10);
            fail("should throw NoItemsException when No Item are returned");
        } catch (NoItemsException ex) {
        }
    }

    /**
     * Test of getAll method, of class OrganizationService.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Organization expected = new Organization(1, "A", "A", "email@domain.com");
        expected.setMembers(testMembers);

        Organization expected2 = new Organization(2, "B", "B", "email@domain.com");
        expected2.setMembers(testMembers);

        Organization expected3 = new Organization(3, "C", "C", "email@domain.com");
        expected3.setMembers(testMembers);

        List<Organization> fromDao = toTest.getAll();

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
            fail("should hit NoItemsException when there are no items");
        } catch (NoItemsException ex) {

        }
    }

    /**
     * Test of addMember method, of class OrganizationService.
     */
    @Test
    public void addMember() throws InvalidIdException, NoItemsException {
        List<Organization> allOrgs = toTest.getAll();
        Superhero testSup = new Superhero(1, "A", "A", new Power(1, "A"), null);

        toTest.addMember(allOrgs, 1);

        allOrgs = toTest.getAll();
        assertEquals(3, allOrgs.size());
        assertTrue(allOrgs.get(0).getMembers().contains(testSup));
        assertTrue(allOrgs.get(1).getMembers().contains(testSup));
        assertTrue(allOrgs.get(2).getMembers().contains(testSup));
    }

    @Test
    public void addMemberInvalidId() throws NoItemsException {
        List<Organization> allOrgs = toTest.getAll();

        try {
            toTest.addMember(allOrgs, 10);
            fail("should throw InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }

    }

    /**
     * Test of getOrganizationByName method, of class OrganizationService.
     */
    @Test
    public void testGetOrganizationByName() throws InvalidNameException, InvalidEntityException {
        Organization expected = new Organization(1, "A", "A", "email@domain.com");
        expected.setMembers(testMembers);

        Organization fromDao = toTest.getOrganizationByName("A");

        assertEquals(expected, fromDao);
    }

    @Test
    public void testGetOrganizationByInvalidName() throws InvalidEntityException {

        try {
            toTest.getOrganizationByName("Z");
            fail("should hit InvalidNameException when name is invalid");
        } catch (InvalidNameException ex) {
        }
    }
    
    @Test
    public void testGetOrganizationByBlankName() throws InvalidNameException  {

        try {
            toTest.getOrganizationByName(" ");
            fail("should hit InvalidEntityException when name is invalid");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testGetOrganizationByNullName() throws InvalidNameException {

        try {
            toTest.getOrganizationByName(null);
           fail("should hit InvalidEntityException when name is invalid");
        } catch (InvalidEntityException ex) {
        }
    }
    
    @Test
    public void testGetOrganizationByEmptyName() throws InvalidNameException {

        try {
            toTest.getOrganizationByName("");
             fail("should hit InvalidEntityException when name is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of getOrganizationById method, of class OrganizationService.
     */
    @Test
    public void testGetOrganizationById() throws InvalidIdException {
        Organization expected = new Organization(1, "A", "A", "email@domain.com");
        expected.setMembers(testMembers);

        Organization fromDao = toTest.getOrganizationById(1);

        assertEquals(expected, fromDao);
    }

    @Test
    public void testGetOrganizationByIdInvalidId() {
        try {
            toTest.getOrganizationById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addOrganization method, of class OrganizationService.
     */
    @Test
    public void testAddOrganization() throws InvalidEntityException, InvalidIdException {
        Organization expected = new Organization(4, "D", "D", "email@domain.com");
        expected.setMembers(testMembers);

        try {
            toTest.getOrganizationById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }

        Organization fromDao = toTest.addOrganization(expected);
        assertEquals(expected, fromDao);

        fromDao = toTest.getOrganizationById(4);
        assertEquals(expected, fromDao);
    }

    @Test
    public void testAddOrganizationNullAddress() {
        Organization testOrg = new Organization(4, "A", null, "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankAddress() {
        Organization testOrg = new Organization(4, "A", "    ", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyAddress() {
        Organization testOrg = new Organization(4, "A", "", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationAddressTooLong() {
        Organization testOrg = new Organization(4, "A", testString, "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationNullEmail() {
        Organization testOrg = new Organization(4, "A", "A", null);
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankEmail() {
        Organization testOrg = new Organization(4, "A", "A", "   ");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyEmail() {
        Organization testOrg = new Organization(4, "A", "A", "");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationTooLongEmail() {
        Organization testOrg = new Organization(4, "A", "A", testString);
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationInvalidEmail() {
        Organization testOrg = new Organization(4, "A", "A", "@yahoo.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationNullName() {
        Organization testOrg = new Organization(4, null, "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankName() {
        Organization testOrg = new Organization(4, "   ", "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyName() {
        Organization testOrg = new Organization(4, "", "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationTooLongName() {
        Organization testOrg = new Organization(4, testString, "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.addOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    /**
     * Test of editOrganization method, of class OrganizationService.
     */
    @Test
    public void testEditOrganization() throws InvalidIdException, InvalidEntityException {
        Organization expected = new Organization(1, "D", "A", "email@domain.com");
        expected.setMembers(testMembers);
        Organization original = new Organization(1, "A", "A", "email@domain.com");
        original.setMembers(testMembers);

        Organization fromDao = toTest.getOrganizationById(1);
        assertEquals(original, fromDao);

        toTest.editOrganization(expected);

        fromDao = toTest.getOrganizationById(1);
        assertNotEquals(original, fromDao);
        assertEquals(expected, fromDao);
    }

    @Test
    public void testEditOrganizationNullAddress() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", null, "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditOrganizationBlankAddress() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "   ", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditOrganizationEmptyAddress() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testEditOrganizationTooLongAddress() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", testString, "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testEditOrganizationNullEmail() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "A", null);
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditOrganizationBlankEmail() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "A", "   ");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditOrganizationEmptyEmail() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "A", "");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testEditOrganizationTooLongEmail() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "A", testString);
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testEditOrganizationInvalidEmail() throws InvalidIdException {
        Organization testOrg = new Organization(1, "A", "A", "@yahoo.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testEditOrganizationNullName() throws InvalidIdException {
        Organization testOrg = new Organization(1, null, "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditOrganizationBlankName() throws InvalidIdException {
        Organization testOrg = new Organization(1, "   ", "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testEditOrganizationEmptyName() throws InvalidIdException {
        Organization testOrg = new Organization(1, "", "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testEditOrganizationTooLongName() throws InvalidIdException {
        Organization testOrg = new Organization(1, testString, "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidEntityException when entity is invalid");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddOrganizationInvalidId() throws InvalidEntityException {
        Organization testOrg = new Organization(4, "D", "A", "email@domain.com");
        testOrg.setMembers(testMembers);
        try {
            toTest.editOrganization(testOrg);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationService.
     */
    @Test
    public void testDeleteOrganizationById() throws InvalidIdException {
        Organization toRemove = new Organization(1, "A", "A", "email@domain.com");
        toRemove.setMembers(testMembers);

        Organization fromDao = toTest.getOrganizationById(1);
        assertEquals(toRemove, fromDao);

        toTest.deleteOrganizationById(1);

        try {
            toTest.getOrganizationById(1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeleteOrganizationByIdInvalidId() {
        try {
            toTest.deleteOrganizationById(4);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

}
