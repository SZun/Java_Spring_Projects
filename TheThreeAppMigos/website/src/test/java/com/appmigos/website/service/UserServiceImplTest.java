/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.dtos.User;
import com.appmigos.website.exceptions.InvalidEntityException;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.memdaos.UserDaoInMem;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samg.zun
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
public class UserServiceImplTest {

    @Autowired
    UserService toTest;

    @Autowired
    UserDaoInMem dao;
    
    String testString = "Cf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6zCf8QIYBy8cT4Kr1l1H5hybcqk4ctHE2iazVCjKtCTO3RV2PaTyuMm05KkkU6z";

    public UserServiceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        dao.setUp();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAll method, of class UserServiceImpl.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Set<Role> roleSet1 = new HashSet<>();
        Set<Role> roleSet2 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        Role role2 = new Role(2, "Author");

        roleSet1.add(role1);
        roleSet2.add(role2);

        User expected = new User(1, "Admin", "password", true, roleSet1);
        User expected2 = new User(2, "Jerry", "password", true, roleSet2);
        User expected3 = new User(3, "Bill", "password", true, roleSet2);

        List<User> fromService = toTest.getAll();
        assertEquals(3, fromService.size());
        assertTrue(fromService.contains(expected));
        assertTrue(fromService.contains(expected2));
        assertTrue(fromService.contains(expected3));
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
     * Test of getById method, of class UserServiceImpl.
     */
    @Test
    public void testGetById() throws InvalidIdException {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User expected = new User(1, "Admin", "password", true, roleSet1);

        User fromService = toTest.getById(1);

        assertEquals(expected, fromService);
    }

    @Test
    public void testGetByIdInvalidId() {
        try {
            toTest.getById(-1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of deleteById method, of class UserServiceImpl.
     */
    @Test
    public void testDeleteById() throws InvalidIdException {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User expected = new User(1, "Admin", "password", true, roleSet1);

        User fromService = toTest.getById(1);
        assertEquals(expected, fromService);
        
        toTest.deleteById(1);

        try {
            toTest.getById(1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeleteByIdInvalidId() {
        try {
            toTest.deleteById(-1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of editUser method, of class UserServiceImpl.
     */
    @Test
    public void testEditUser() throws InvalidIdException, InvalidEntityException {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User expected = new User(1, "Author", "banan", false, roleSet1);
        User original = new User(1, "Admin", "password", true, roleSet1);

        User fromService = toTest.getById(1);
        assertEquals(original, fromService);
        
        toTest.editUser(expected);
        
        fromService = toTest.getById(1);
        assertNotEquals(original, fromService);
        assertEquals(expected, fromService);
    }
    
    @Test
    public void testEditUserInvalidId() throws InvalidEntityException {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(-1, "Author", "banan", false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidIdException when Id is Invalid");
        } catch(InvalidIdException ex){}
        
    }
    
    @Test
    public void testEditUserNullName() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, null, "banan", false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserBlankName() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, null, "   ", false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserEmptyName() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, null, "", false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserTooLongName() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, null, testString, false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserNullPassword() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, "Admin", null, false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserEmptyPassword() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, "Admin", "", false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserBlankPassword() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, "Admin", "  ", false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserTooLongPassword() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, "Admin", testString, false, roleSet1);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserNullRoles() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, "Admin", "password", false, null);

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }
    
    @Test
    public void testEditUserEmptyRoles() throws InvalidIdException  {
        Set<Role> roleSet1 = new HashSet<>();
        Role role1 = new Role(1, "Admin");
        roleSet1.add(role1);
        User testUser = new User(1, "Admin", "password", false, new HashSet<>());

        
        try {
            toTest.editUser(testUser);
            fail("should hit InvalidEntityException when entity is Invalid");
        } catch(InvalidEntityException ex){}
        
    }

}
