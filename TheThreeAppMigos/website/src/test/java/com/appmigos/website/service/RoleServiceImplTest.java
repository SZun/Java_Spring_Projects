/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appmigos.website.service;

import com.appmigos.website.TestAppConfig;
import com.appmigos.website.dtos.Role;
import com.appmigos.website.exceptions.InvalidIdException;
import com.appmigos.website.exceptions.NoItemsException;
import com.appmigos.website.memdaos.RoleDaoInMem;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
public class RoleServiceImplTest {
    
    @Autowired
    RoleService toTest;
    
    @Autowired
    RoleDaoInMem dao;
    
    public RoleServiceImplTest() {
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
     * Test of getAll method, of class RoleServiceImpl.
     */
    @Test
    public void testGetAll() throws NoItemsException {
        Role expected = new Role(1, "Admin");
        Role expected2 = new Role(2, "Author");
        Role expected3 = new Role(3, "Guest");
        
        List<Role> fromService = toTest.getAll();
        
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
            fail("should hit NoItemsException when no items");
        } catch(NoItemsException ex){}
    }

    /**
     * Test of getById method, of class RoleServiceImpl.
     */
    @Test
    public void testGetById() throws InvalidIdException {
        Role expected = new Role(1, "Admin");
        
        Role fromService = toTest.getById(1);
        
        assertEquals(expected, fromService);
    }
    
    @Test
    public void testGetByIdInvalidId() {
        try {
            toTest.getById(-1);
            fail("should hit InvalidIdException when Id is invalid");
        } catch(InvalidIdException ex){}
    }
    
}
