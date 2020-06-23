/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class FlooringMasteryFileOrderDAOTest {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringMasteryFileOrderDAO toTest = ctx.getBean("orderFileDAO", FlooringMasteryFileOrderDAO.class);
    LocalDate orderDate = LocalDate.of(2050,6,2);
    
    public FlooringMasteryFileOrderDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        Path testPath = Paths.get("testData", "Order_"+orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))+".txt");

        File testFile = testPath.toFile();

        testFile.delete();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryFileOrderDAO.
     */
    @Test
    public void testGetAllOrdersGP() throws FlooringMasteryOrderDAOPersistenceException {
        Order expected1 = new Order(LocalDate.of(2050,6,2), 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        Order expected2 = new Order(LocalDate.of(2050,6,2), 2,"Samuel","Michigan",new BigDecimal("6.00"),"Tile",new BigDecimal(600),new BigDecimal("3.50"),new BigDecimal("4.15"));
        Order expected3 = new Order(LocalDate.of(2050,6,2), 3,"Sammy","Colorado",new BigDecimal("2.90"),"Laminate",new BigDecimal(400),new BigDecimal("1.75"),new BigDecimal("2.10"));
        
        toTest.addOrder(expected1);
        toTest.addOrder(expected2);
        toTest.addOrder(expected3);
        
        List<Order> allOrders = toTest.getAllOrders(orderDate);
        
        assertEquals(expected1.getOrderNumber(), allOrders.get(0).getOrderNumber());
        assertEquals(expected1.getCustomerName(), allOrders.get(0).getCustomerName());
        assertEquals(expected1.getState(), allOrders.get(0).getState());
        assertEquals(expected1.getTaxRate(), allOrders.get(0).getTaxRate());
        assertEquals(expected1.getProductType(), allOrders.get(0).getProductType());
        assertEquals(expected1.getArea(), allOrders.get(0).getArea());
        assertEquals(expected1.getCostPerSquareFoot(), allOrders.get(0).getCostPerSquareFoot());
        assertEquals(expected1.getLaborCostPerSquareFoot(), allOrders.get(0).getLaborCostPerSquareFoot());
        assertEquals(expected1.getMaterialCost(), allOrders.get(0).getMaterialCost());
        assertEquals(expected1.getLaborCost(), allOrders.get(0).getLaborCost());
        assertEquals(expected1.getTax(), allOrders.get(0).getTax());
        assertEquals(expected1.getTotal(), allOrders.get(0).getTotal());
        
        assertEquals(expected2.getOrderNumber(), allOrders.get(1).getOrderNumber());
        assertEquals(expected2.getCustomerName(), allOrders.get(1).getCustomerName());
        assertEquals(expected2.getState(), allOrders.get(1).getState());
        assertEquals(expected2.getTaxRate(), allOrders.get(1).getTaxRate());
        assertEquals(expected2.getProductType(), allOrders.get(1).getProductType());
        assertEquals(expected2.getArea(), allOrders.get(1).getArea());
        assertEquals(expected2.getCostPerSquareFoot(), allOrders.get(1).getCostPerSquareFoot());
        assertEquals(expected2.getLaborCostPerSquareFoot(), allOrders.get(1).getLaborCostPerSquareFoot());
        assertEquals(expected2.getMaterialCost(), allOrders.get(1).getMaterialCost());
        assertEquals(expected2.getLaborCost(), allOrders.get(1).getLaborCost());
        assertEquals(expected2.getTax(), allOrders.get(1).getTax());
        assertEquals(expected2.getTotal(), allOrders.get(1).getTotal());
        
        assertEquals(expected3.getOrderNumber(), allOrders.get(2).getOrderNumber());
        assertEquals(expected3.getCustomerName(), allOrders.get(2).getCustomerName());
        assertEquals(expected3.getState(), allOrders.get(2).getState());
        assertEquals(expected3.getTaxRate(), allOrders.get(2).getTaxRate());
        assertEquals(expected3.getProductType(), allOrders.get(2).getProductType());
        assertEquals(expected3.getArea(), allOrders.get(2).getArea());
        assertEquals(expected3.getCostPerSquareFoot(), allOrders.get(2).getCostPerSquareFoot());
        assertEquals(expected3.getLaborCostPerSquareFoot(), allOrders.get(2).getLaborCostPerSquareFoot());
        assertEquals(expected3.getMaterialCost(), allOrders.get(2).getMaterialCost());
        assertEquals(expected3.getLaborCost(), allOrders.get(2).getLaborCost());
        assertEquals(expected3.getTax(), allOrders.get(2).getTax());
        assertEquals(expected3.getTotal(), allOrders.get(2).getTotal());
    }
    
    @Test
    public void testGetAllOrdersException() throws FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.getAllOrders(LocalDate.now());
            fail("should hit FlooringMasterFileOrderDAOPersistenseException when date can't be found in file name");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){
            
        }
    }
    
    @Test
    public void testGetAllOrdersNullException() throws FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.getAllOrders(null);
            fail("should hit FlooringMasterFileOrderDAOPersistenseException when date can't be found in file name");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){
            
        }
    }

    /**
     * Test of addOrder method, of class FlooringMasteryFileOrderDAO.
     */
    @Test
    public void testAddOrderGP() throws FlooringMasteryOrderDAOPersistenceException {
        Order expected1 = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        Order expected2 = new Order(orderDate, 2,"Samuel","Michigan",new BigDecimal("6.00"),"Tile",new BigDecimal(600),new BigDecimal("3.50"),new BigDecimal("4.15"));
        Order expected3 = new Order(orderDate, 3,"Sammy","Colorado",new BigDecimal("2.90"),"Laminate",new BigDecimal(400),new BigDecimal("1.75"),new BigDecimal("2.10"));
        
        toTest.addOrder(expected1);
        toTest.addOrder(expected2);
        toTest.addOrder(expected3);
        
        List<Order> allOrders = toTest.getAllOrders(orderDate);
        
        assertEquals(expected1.getOrderNumber(), allOrders.get(0).getOrderNumber());
        assertEquals(expected1.getCustomerName(), allOrders.get(0).getCustomerName());
        assertEquals(expected1.getState(), allOrders.get(0).getState());
        assertEquals(expected1.getTaxRate(), allOrders.get(0).getTaxRate());
        assertEquals(expected1.getProductType(), allOrders.get(0).getProductType());
        assertEquals(expected1.getArea(), allOrders.get(0).getArea());
        assertEquals(expected1.getCostPerSquareFoot(), allOrders.get(0).getCostPerSquareFoot());
        assertEquals(expected1.getLaborCostPerSquareFoot(), allOrders.get(0).getLaborCostPerSquareFoot());
        assertEquals(expected1.getMaterialCost(), allOrders.get(0).getMaterialCost());
        assertEquals(expected1.getLaborCost(), allOrders.get(0).getLaborCost());
        assertEquals(expected1.getTax(), allOrders.get(0).getTax());
        assertEquals(expected1.getTotal(), allOrders.get(0).getTotal());
        
        assertEquals(expected2.getOrderNumber(), allOrders.get(1).getOrderNumber());
        assertEquals(expected2.getCustomerName(), allOrders.get(1).getCustomerName());
        assertEquals(expected2.getState(), allOrders.get(1).getState());
        assertEquals(expected2.getTaxRate(), allOrders.get(1).getTaxRate());
        assertEquals(expected2.getProductType(), allOrders.get(1).getProductType());
        assertEquals(expected2.getArea(), allOrders.get(1).getArea());
        assertEquals(expected2.getCostPerSquareFoot(), allOrders.get(1).getCostPerSquareFoot());
        assertEquals(expected2.getLaborCostPerSquareFoot(), allOrders.get(1).getLaborCostPerSquareFoot());
        assertEquals(expected2.getMaterialCost(), allOrders.get(1).getMaterialCost());
        assertEquals(expected2.getLaborCost(), allOrders.get(1).getLaborCost());
        assertEquals(expected2.getTax(), allOrders.get(1).getTax());
        assertEquals(expected2.getTotal(), allOrders.get(1).getTotal());
        
        assertEquals(expected3.getOrderNumber(), allOrders.get(2).getOrderNumber());
        assertEquals(expected3.getCustomerName(), allOrders.get(2).getCustomerName());
        assertEquals(expected3.getState(), allOrders.get(2).getState());
        assertEquals(expected3.getTaxRate(), allOrders.get(2).getTaxRate());
        assertEquals(expected3.getProductType(), allOrders.get(2).getProductType());
        assertEquals(expected3.getArea(), allOrders.get(2).getArea());
        assertEquals(expected3.getCostPerSquareFoot(), allOrders.get(2).getCostPerSquareFoot());
        assertEquals(expected3.getLaborCostPerSquareFoot(), allOrders.get(2).getLaborCostPerSquareFoot());
        assertEquals(expected3.getMaterialCost(), allOrders.get(2).getMaterialCost());
        assertEquals(expected3.getLaborCost(), allOrders.get(2).getLaborCost());
        assertEquals(expected3.getTax(), allOrders.get(2).getTax());
        assertEquals(expected3.getTotal(), allOrders.get(2).getTotal());
    }
    
    @Test
    public void testAddOrderNullOrder() throws FlooringMasteryOrderDAOPersistenceException{
        try {
            toTest.addOrder(null);
            fail("should hit FlooringMasterOrderDAOPersistenceException when order is null");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }
    
    @Test
    public void testAddOrderNullDate() throws FlooringMasteryOrderDAOPersistenceException{
        Order testOrder = new Order(null, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        
        try {
            toTest.addOrder(testOrder);
            fail("should hit FlooringMasterOrderDAOPersistenceException when orderDate is null");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }
    
    /**
     * Test of getOrder method, of class FlooringMasteryFileOrderDAO.
     */
    @Test
    public void testGetOrderGP() throws FlooringMasteryOrderDAOPersistenceException {
        Order expected = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        
        toTest.addOrder(expected);
        
        Order res = toTest.getOrder(expected.getOrderNumber(), orderDate);
        
        assertEquals(expected.getOrderNumber(), res.getOrderNumber());
        assertEquals(expected.getCustomerName(), res.getCustomerName());
        assertEquals(expected.getState(), res.getState());
        assertEquals(expected.getTaxRate(), res.getTaxRate());
        assertEquals(expected.getProductType(), res.getProductType());
        assertEquals(expected.getArea(), res.getArea());
        assertEquals(expected.getCostPerSquareFoot(), res.getCostPerSquareFoot());
        assertEquals(expected.getLaborCostPerSquareFoot(), res.getLaborCostPerSquareFoot());
        assertEquals(expected.getMaterialCost(), res.getMaterialCost());
        assertEquals(expected.getLaborCost(), res.getLaborCost());
        assertEquals(expected.getTax(), res.getTax());
        assertEquals(expected.getTotal(), res.getTotal());
    }
    
    @Test
    public void testGetOrderNull() throws FlooringMasteryOrderDAOPersistenceException {
        Order expected = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        toTest.addOrder(expected);
        
        assertNull(toTest.getOrder(2, orderDate));
    }
    
    @Test
    public void testGetOrderDateNotFound() throws FlooringMasteryOrderDAOPersistenceException {
        try {
            assertNull(toTest.getOrder(2, LocalDate.now()));
            fail("should hit FlooringMasteryOrderDAOPersistenceException when date can't be found");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }
    
    @Test
    public void testGetOrderDateNull() throws FlooringMasteryOrderDAOPersistenceException {
        try {
            assertNull(toTest.getOrder(2, null));
            fail("should hit FlooringMasteryOrderDAOPersistenceException when date is null");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }

    /**
     * Test of editOrder method, of class FlooringMasteryFileOrderDAO.
     */
    @Test
    public void testEditOrderGP() throws FlooringMasteryOrderDAOPersistenceException {
        Order original = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        Order expected = new Order(orderDate, 1,"Samuel","Michigan",new BigDecimal("6.00"),"Tile",new BigDecimal(600),new BigDecimal("3.50"),new BigDecimal("4.15"));
        
        toTest.addOrder(original);
        
        Order res = toTest.getOrder(original.getOrderNumber(), orderDate);
        
        assertEquals(original.getOrderNumber(), res.getOrderNumber());
        assertEquals(original.getCustomerName(), res.getCustomerName());
        assertEquals(original.getState(), res.getState());
        assertEquals(original.getTaxRate(), res.getTaxRate());
        assertEquals(original.getProductType(), res.getProductType());
        assertEquals(original.getArea(), res.getArea());
        assertEquals(original.getCostPerSquareFoot(), res.getCostPerSquareFoot());
        assertEquals(original.getLaborCostPerSquareFoot(), res.getLaborCostPerSquareFoot());
        assertEquals(original.getMaterialCost(), res.getMaterialCost());
        assertEquals(original.getLaborCost(), res.getLaborCost());
        assertEquals(original.getTax(), res.getTax());
        assertEquals(original.getTotal(), res.getTotal());
        
        res = toTest.editOrder(expected);
        
        assertEquals(expected.getOrderNumber(), res.getOrderNumber());
        assertEquals(expected.getCustomerName(), res.getCustomerName());
        assertEquals(expected.getState(), res.getState());
        assertEquals(expected.getTaxRate(), res.getTaxRate());
        assertEquals(expected.getProductType(), res.getProductType());
        assertEquals(expected.getArea(), res.getArea());
        assertEquals(expected.getCostPerSquareFoot(), res.getCostPerSquareFoot());
        assertEquals(expected.getLaborCostPerSquareFoot(), res.getLaborCostPerSquareFoot());
        assertEquals(expected.getMaterialCost(), res.getMaterialCost());
        assertEquals(expected.getLaborCost(), res.getLaborCost());
        assertEquals(expected.getTax(), res.getTax());
        assertEquals(expected.getTotal(), res.getTotal());
    }
    
    @Test
    public void testEditOrderOrderNotFound() throws FlooringMasteryOrderDAOPersistenceException{
        Order testOrder = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        Order editedOrder = new Order(orderDate, 2,"Samuel","Michigan",new BigDecimal("6.00"),"Tile",new BigDecimal(600),new BigDecimal("3.50"),new BigDecimal("4.15"));
        toTest.addOrder(testOrder);
        
        try {
            toTest.editOrder(editedOrder);
            fail("should hit FlooringMasterOrderDAOPersistenceException when order number is incorrect");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }
    
    @Test
    public void testEditOrderNullOrder() throws FlooringMasteryOrderDAOPersistenceException{
        Order testOrder = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        toTest.addOrder(testOrder);
        
        try {
            toTest.editOrder(null);
            fail("should hit FlooringMasterOrderDAOPersistenceException when order number is null");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }
    
    @Test
    public void testEditOrderNullDate() throws FlooringMasteryOrderDAOPersistenceException{
        Order testOrder = new Order(null, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        
        try {
            toTest.editOrder(testOrder);
            fail("should hit FlooringMasterOrderDAOPersistenceException when order date is null");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }
    
    @Test
    public void testEditOrderDateNotFound() throws FlooringMasteryOrderDAOPersistenceException{
        Order testOrder = new Order(LocalDate.now(), 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        
        try {
            toTest.editOrder(testOrder);
            fail("should hit FlooringMasterOrderDAOPersistenceException when order date can't be found");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){}
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryFileOrderDAO.
     */
    @Test
    public void testRemoveOrderGP() throws FlooringMasteryOrderDAOPersistenceException {
        Order toRemove = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        
        toTest.addOrder(toRemove);
        
        Order res = toTest.getOrder(toRemove.getOrderNumber(), orderDate);
        
        assertEquals(toRemove.getOrderNumber(), res.getOrderNumber());
        assertEquals(toRemove.getCustomerName(), res.getCustomerName());
        assertEquals(toRemove.getState(), res.getState());
        assertEquals(toRemove.getTaxRate(), res.getTaxRate());
        assertEquals(toRemove.getProductType(), res.getProductType());
        assertEquals(toRemove.getArea(), res.getArea());
        assertEquals(toRemove.getCostPerSquareFoot(), res.getCostPerSquareFoot());
        assertEquals(toRemove.getLaborCostPerSquareFoot(), res.getLaborCostPerSquareFoot());
        assertEquals(toRemove.getMaterialCost(), res.getMaterialCost());
        assertEquals(toRemove.getLaborCost(), res.getLaborCost());
        assertEquals(toRemove.getTax(), res.getTax());
        assertEquals(toRemove.getTotal(), res.getTotal());
        
        toTest.removeOrder(toRemove.getOrderNumber(), orderDate);
        
        assertNull(toTest.getOrder(toRemove.getOrderNumber(), orderDate));
    }
    
    @Test
    public void testRemoveOrderOrderNumberException() throws FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.removeOrder(1, orderDate);
            fail("should hit FlooringMasteryOrderDAOPersistenceException when order number doesn't exist");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){
            
        }
    }
    
    @Test
    public void testRemoveOrderDateNotFoundException() throws FlooringMasteryOrderDAOPersistenceException {
        Order toRemove = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        toTest.addOrder(toRemove);
        
        try {
            toTest.removeOrder(1, LocalDate.now());
            fail("should hit FlooringMasteryOrderDAOPersistenceException when order number doesn't exist");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){
            
        }
    }
    
    @Test
    public void testRemoveOrderNullDateException() throws FlooringMasteryOrderDAOPersistenceException {
        Order toRemove = new Order(orderDate, 1,"Sam","Illinois",new BigDecimal("6.25"),"Wood",new BigDecimal(500),new BigDecimal("5.15"),new BigDecimal("4.75"));
        toTest.addOrder(toRemove);
        
        try {
            toTest.removeOrder(1, null);
            fail("should hit FlooringMasteryOrderDAOPersistenceException when order number doesn't exist");
        } catch(FlooringMasteryOrderDAOPersistenceException ex){
            
        }
    }
    
}
