/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryFileProductDAOTest {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringMasteryFileProductDAO toTest = ctx.getBean("productFileDAO", FlooringMasteryFileProductDAO.class);

    
    public FlooringMasteryFileProductDAOTest() {
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
     * Test of getProduct method, of class FlooringMasteryFileProductDAO.
     */
    @Test
    public void testGetProductGP() {
        
        Product wood = toTest.getProduct("wood");
        Product laminate = toTest.getProduct("laminate");
        Product tile = toTest.getProduct("tile");
        
        assertEquals("Wood", wood.getProductType());
        assertEquals("Laminate", laminate.getProductType());
        assertEquals("Tile", tile.getProductType());
        
        assertEquals(new BigDecimal("5.15"), wood.getCostPerSquareFoot());
        assertEquals(new BigDecimal("1.75"), laminate.getCostPerSquareFoot());
        assertEquals(new BigDecimal("3.50"), tile.getCostPerSquareFoot());
        
        assertEquals(new BigDecimal("4.75"), wood.getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("2.10"), laminate.getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("4.15"), tile.getLaborCostPerSquareFoot());
        
    }
    
    @Test
    public void testGetProductNull() {
        assertNull(toTest.getProduct("banan"));
    }
    
    @Test
    public void testGetProductEmpty() {
        assertNull(toTest.getProduct(""));
    }
    
    @Test
    public void testGetProductBlank() {
        assertNull(toTest.getProduct(" "));
    }
}
