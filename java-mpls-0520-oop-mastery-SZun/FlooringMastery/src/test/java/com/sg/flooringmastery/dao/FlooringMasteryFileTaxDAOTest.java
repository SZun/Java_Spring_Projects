/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
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
public class FlooringMasteryFileTaxDAOTest {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringMasteryFileTaxDAO toTest = ctx.getBean("taxFileDAO", FlooringMasteryFileTaxDAO.class);
    
    public FlooringMasteryFileTaxDAOTest() {
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
     * Test of getTax method, of class FlooringMasteryFileTaxDAO.
     */
    @Test
    public void testGetTaxGP() {
        Tax illinois = toTest.getTax("illinois");
        Tax montana = toTest.getTax("montana");
        Tax pennsylvania = toTest.getTax("pennsylvania");
        
        assertEquals("Illinois", illinois.getStateName());
        assertEquals("Montana", montana.getStateName());
        assertEquals("Pennsylvania", pennsylvania.getStateName());
        
        assertEquals(new BigDecimal("6.25"), illinois.getTaxRate());
        assertEquals(new BigDecimal("0.00"), montana.getTaxRate());
        assertEquals(new BigDecimal("6.00"), pennsylvania.getTaxRate());
    }
    
    @Test
    public void testGetTaxNull() {
        assertNull(toTest.getTax("banan"));
    }
    
    @Test
    public void testGetTaxEmpty() {
        assertNull(toTest.getTax(""));
    }
    
    @Test
    public void testGetTaxBlank() {
        assertNull(toTest.getTax(" "));
    }
    
}
