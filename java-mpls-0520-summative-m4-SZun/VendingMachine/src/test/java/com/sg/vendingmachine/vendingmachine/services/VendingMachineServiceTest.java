/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.services;

import com.sg.vendingmachine.vendingmachine.daos.VendingMachineFileDAO;
import com.sg.vendingmachine.vendingmachine.daos.VendingMachineInMemDAO;
import com.sg.vendingmachine.vendingmachine.dtos.Change;
import com.sg.vendingmachine.vendingmachine.dtos.Item;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
public class VendingMachineServiceTest {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public VendingMachineServiceTest() {
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
     * Test of getAllItems method, of class VendingMachineService.
     */
    @Test
    public void testGetAllItemsGP() {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);


        List<Item> res = toTest.getAllItems();
        System.out.println(res);

        assertEquals("Lays Original", res.get(0).getName());
        assertEquals(new BigDecimal("1.00"), res.get(0).getCost());
        assertEquals(25, res.get(0).getTotal());
        
        assertEquals("Banan", res.get(2).getName());
        assertEquals(new BigDecimal("0.50"), res.get(2).getCost());
        assertEquals(25, res.get(2).getTotal());
    }

    /**
     * Test of buyItem method, of class VendingMachineService.
     */
    @Test
    public void testBuyItemGP() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        toTest.addMoney(new BigDecimal("1.41"));
        
        Change res = toTest.buyItem("Lays Original");
        
        assertEquals(1, res.getQuarters());
        assertEquals(1, res.getDimes());
        assertEquals(1, res.getNickels());
        assertEquals(1, res.getPennies());
        
        Change moneyInMachine = toTest.returnChange();
        
        assertEquals(0, moneyInMachine.getQuarters());
        assertEquals(0, moneyInMachine.getDimes());
        assertEquals(0, moneyInMachine.getNickels());
        assertEquals(0, moneyInMachine.getPennies());
    }
    
    @Test
    public void testReturnChangeGP(){
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        Change c = toTest.returnChange();
        
        assertEquals(0, c.getQuarters());
        assertEquals(0, c.getDimes());
        assertEquals(0, c.getNickels());
        assertEquals(0, c.getPennies());
    }
    
    @Test
    public void testReturnChangeWithMoney() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        toTest.addMoney(new BigDecimal("2.16"));
        
        
        Change c = toTest.returnChange();
        
        assertEquals(8, c.getQuarters());
        assertEquals(1, c.getDimes());
        assertEquals(1, c.getNickels());
        assertEquals(1, c.getPennies());
    }
    
    @Test
    public void testBuyItemNotEnoughMoney() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        try {
            toTest.buyItem("Lays Original");
            fail("should hit NotEnoughMoneyException");
        } catch(NotEnoughMoneyException ex){
            
        }
        
    }
    
    @Test
    public void testBuyItemOutOfStock() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        try {
            toTest.buyItem("Smartfood Popcorn");
            fail("should hit ItemOutOfStockException");
        } catch(ItemOutOfStockException ex){
            
        }
        
    }
    
    @Test 
    public void testBuyItemInvalidItem() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        try {
            toTest.buyItem(" ");
            fail("should hit ItemNotFoundException");
        }
        catch(ItemNotFoundException ex){
            
        }
    }
    
    @Test
    public void testAddMoneyNegativeMoney() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        try {
            toTest.addMoney(new BigDecimal(-1));
            fail("should hit NegativeMoneyException");
        } catch(NegativeMoneyException ex){
            
        }
    }
    
    @Test
    public void testAddMoneyGP() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        toTest.addMoney(BigDecimal.TEN);
        
        Change c = toTest.returnChange();
        
        assertEquals(40, c.getQuarters());
        assertEquals(0, c.getDimes());
        assertEquals(0, c.getNickels());
        assertEquals(0, c.getPennies());
    }
    
    @Test
    public void testAddMoneyNoMoney() throws Exception {
        VendingMachineService toTest = ctx.getBean("service", VendingMachineService.class);
        
        toTest.addMoney(BigDecimal.ZERO);
        
        Change c = toTest.returnChange();
        
        assertEquals(0, c.getQuarters());
        assertEquals(0, c.getDimes());
        assertEquals(0, c.getNickels());
        assertEquals(0, c.getPennies());
    }

}
