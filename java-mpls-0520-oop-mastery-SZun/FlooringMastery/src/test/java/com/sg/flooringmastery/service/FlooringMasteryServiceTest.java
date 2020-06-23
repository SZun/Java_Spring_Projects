/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDAOPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
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
public class FlooringMasteryServiceTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringMasteryService toTest = ctx.getBean("service", FlooringMasteryService.class);
//    LocalDate orderDate = LocalDate.parse("06/02/2050", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    LocalDate orderDate = LocalDate.of(2050, 6, 2);

    public FlooringMasteryServiceTest() {
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
     * Test of getAllOrders method, of class FlooringMasteryService.
     */
    @Test
    public void testGetAllOrdersGP() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order expected1 = new Order(orderDate, 1, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        Order expected2 = new Order(orderDate, 2, "Samuel", "Michigan", new BigDecimal("6.00"), "Tile", new BigDecimal(600), new BigDecimal("3.50"), new BigDecimal("4.15"));
        Order expected3 = new Order(orderDate, 3, "Sammy", "Colorado", new BigDecimal("2.90"), "Laminate", new BigDecimal(400), new BigDecimal("1.75"), new BigDecimal("2.10"));

        List<Order> res = toTest.getAllOrders(orderDate);

        assertEquals(expected1.getOrderDate(), res.get(0).getOrderDate());
        assertEquals(expected1.getOrderNumber(), res.get(0).getOrderNumber());
        assertEquals(expected1.getCustomerName(), res.get(0).getCustomerName());
        assertEquals(expected1.getState(), res.get(0).getState());
        assertEquals(expected1.getTaxRate(), res.get(0).getTaxRate());
        assertEquals(expected1.getProductType(), res.get(0).getProductType());
        assertEquals(expected1.getArea(), res.get(0).getArea());
        assertEquals(expected1.getCostPerSquareFoot(), res.get(0).getCostPerSquareFoot());
        assertEquals(expected1.getLaborCostPerSquareFoot(), res.get(0).getLaborCostPerSquareFoot());
        assertEquals(expected1.getMaterialCost(), res.get(0).getMaterialCost());
        assertEquals(expected1.getLaborCost(), res.get(0).getLaborCost());
        assertEquals(expected1.getTax(), res.get(0).getTax());
        assertEquals(expected1.getTotal(), res.get(0).getTotal());

        assertEquals(expected2.getOrderDate(), res.get(0).getOrderDate());
        assertEquals(expected2.getOrderNumber(), res.get(1).getOrderNumber());
        assertEquals(expected2.getCustomerName(), res.get(1).getCustomerName());
        assertEquals(expected2.getState(), res.get(1).getState());
        assertEquals(expected2.getTaxRate(), res.get(1).getTaxRate());
        assertEquals(expected2.getProductType(), res.get(1).getProductType());
        assertEquals(expected2.getArea(), res.get(1).getArea());
        assertEquals(expected2.getCostPerSquareFoot(), res.get(1).getCostPerSquareFoot());
        assertEquals(expected2.getLaborCostPerSquareFoot(), res.get(1).getLaborCostPerSquareFoot());
        assertEquals(expected2.getMaterialCost(), res.get(1).getMaterialCost());
        assertEquals(expected2.getLaborCost(), res.get(1).getLaborCost());
        assertEquals(expected2.getTax(), res.get(1).getTax());
        assertEquals(expected2.getTotal(), res.get(1).getTotal());

        assertEquals(expected3.getOrderDate(), res.get(0).getOrderDate());
        assertEquals(expected3.getOrderNumber(), res.get(2).getOrderNumber());
        assertEquals(expected3.getCustomerName(), res.get(2).getCustomerName());
        assertEquals(expected3.getState(), res.get(2).getState());
        assertEquals(expected3.getTaxRate(), res.get(2).getTaxRate());
        assertEquals(expected3.getProductType(), res.get(2).getProductType());
        assertEquals(expected3.getArea(), res.get(2).getArea());
        assertEquals(expected3.getCostPerSquareFoot(), res.get(2).getCostPerSquareFoot());
        assertEquals(expected3.getLaborCostPerSquareFoot(), res.get(2).getLaborCostPerSquareFoot());
        assertEquals(expected3.getMaterialCost(), res.get(2).getMaterialCost());
        assertEquals(expected3.getLaborCost(), res.get(2).getLaborCost());
        assertEquals(expected3.getTax(), res.get(2).getTax());
        assertEquals(expected3.getTotal(), res.get(2).getTotal());

    }

    @Test
    public void testGetAllOrdersNoOrders() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        toTest.removeOrder(1, orderDate);
        toTest.removeOrder(2, orderDate);
        toTest.removeOrder(3, orderDate);
        try {
            toTest.getAllOrders(orderDate);
            fail("should hit NoOrdersException when res is empty");
        } catch (NoOrdersException ex) {
        }
    }

    @Test
    public void testGetAllOrdersNullDate() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.getAllOrders(null);
            fail("should hit NullDateException when date is null");
        } catch (NullDateException ex) {
        }
    }

    /**
     * Test of addOrder method, of class FlooringMasteryService.
     */
    @Test
    public void testAddOrderGP() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order expected = new Order(orderDate, 0, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));

        Order res = toTest.addOrder(expected, orderDate);

        assertEquals(expected.getOrderDate(), res.getOrderDate());
        assertEquals(4, res.getOrderNumber());
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

        res = toTest.getOrder(4, orderDate);

        assertEquals(expected.getOrderDate(), res.getOrderDate());
        assertEquals(4, res.getOrderNumber());
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
    public void testAddOrderNullDate() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));

        try {
            toTest.addOrder(testOrder, null);
            fail("should hit NullDateException when date is null");
        } catch (NullDateException ex) {

        }

    }

    @Test
    public void testAddOrderValidationExceptionNullName() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, null, "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionEmptyName() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionBlankName() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "   ", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionNullState() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", null, new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionEmptyState() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionBlankState() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "   ", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionNullProductType() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), null, new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionEmptyProductType() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionBlankProductType() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "   ", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderValidationExceptionNullArea() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", null, new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testAddOrderDateNotFutureException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, LocalDate.now());
            fail("should hit DateNotFutureException when order date is not in future");
        } catch (DateNotFutureException ex) {
        }
    }

    @Test
    public void testAddOrderProductNotFoundException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Banan", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit ProductNotFoundException when product is invalid");
        } catch (ProductNotFoundException ex) {
        }
    }

    @Test
    public void testAddOrderInvalidStateException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Alabanana", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit InvalidStateException when state is invalid");
        } catch (InvalidStateException ex) {
        }
    }

    @Test
    public void testAddOrderInvalidNameException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "???", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit InvalidNameException when customer name is invalid");
        } catch (InvalidNameException ex) {
        }
    }

    @Test
    public void testAddOrderInvalidAreaException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(99), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.addOrder(testOrder, orderDate);
            fail("should hit InvalidAreaException when area is less than 100");
        } catch (InvalidAreaException ex) {
        }
    }

    /**
     * Test of editOrder method, of class FlooringMasteryService.
     */
    @Test
    public void testEditOrderGP() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order expected = new Order(orderDate, 1, "Samuel", "Michigan", new BigDecimal("6.00"), "Tile", new BigDecimal(600), new BigDecimal("3.50"), new BigDecimal("4.15"));

        Order res = toTest.editOrder(expected, orderDate);

        assertEquals(expected.getOrderDate(), res.getOrderDate());
        assertEquals(1, res.getOrderNumber());
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

        res = toTest.getOrder(1, orderDate);

        assertEquals(expected.getOrderDate(), res.getOrderDate());
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
    public void testEditOrderValidationExceptionNullName() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, null, "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderNullDate() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, null);
            fail("should hit NullDateException when date is null");
        } catch (NullDateException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionEmptyName() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionBlankName() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "   ", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionNullState() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", null, new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionEmptyState() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionBlankState() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "   ", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionNullProductType() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), null, new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionEmptyProductType() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionBlankProductType() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "   ", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderValidationExceptionNullArea() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 4, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", null, new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderValidationException when customer name is null");
        } catch (OrderValidationException ex) {
        }
    }

    @Test
    public void testEditOrderOrderNotFoundException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 10, "Samuel", "Michigan", new BigDecimal("6.00"), "Tile", new BigDecimal(600), new BigDecimal("3.50"), new BigDecimal("4.15"));

        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit OrderNotFoundException when orderNumber is invalid");
        } catch (OrderNotFoundException ex) {
        }
    }

    @Test
    public void testEditOrderProductNotFoundException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 1, "Sam", "Illinois", new BigDecimal("6.25"), "Banan", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit ProductNotFoundException when product is invalid");
        } catch (ProductNotFoundException ex) {
        }
    }

    @Test
    public void testEditOrderInvalidStateException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 1, "Sam", "Alabanana", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit InvalidStateException when state is invalid");
        } catch (InvalidStateException ex) {
        }
    }

    @Test
    public void testEditOrderInvalidNameException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 1, "???", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit InvalidNameException when customer name is invalid");
        } catch (InvalidNameException ex) {
        }
    }

    @Test
    public void testEditOrderInvalidAreaException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 1, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(99), new BigDecimal("5.15"), new BigDecimal("4.75"));
        try {
            toTest.editOrder(testOrder, orderDate);
            fail("should hit InvalidAreaException when area is less than 100");
        } catch (InvalidAreaException ex) {
        }
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryService.
     */
    @Test
    public void testRemoveOrderGP() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order testOrder = new Order(orderDate, 1, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));

        Order res = toTest.getOrder(testOrder.getOrderNumber(), orderDate);

        assertEquals(testOrder.getOrderNumber(), res.getOrderNumber());
        assertEquals(testOrder.getCustomerName(), res.getCustomerName());
        assertEquals(testOrder.getState(), res.getState());
        assertEquals(testOrder.getTaxRate(), res.getTaxRate());
        assertEquals(testOrder.getProductType(), res.getProductType());
        assertEquals(testOrder.getArea(), res.getArea());
        assertEquals(testOrder.getCostPerSquareFoot(), res.getCostPerSquareFoot());
        assertEquals(testOrder.getLaborCostPerSquareFoot(), res.getLaborCostPerSquareFoot());
        assertEquals(testOrder.getMaterialCost(), res.getMaterialCost());
        assertEquals(testOrder.getLaborCost(), res.getLaborCost());
        assertEquals(testOrder.getTax(), res.getTax());
        assertEquals(testOrder.getTotal(), res.getTotal());

        toTest.removeOrder(testOrder.getOrderNumber(), orderDate);

        try {
            res = toTest.getOrder(testOrder.getOrderNumber(), orderDate);
            fail("should hit OrderNotFoundException when orderNumber is invalid");
        } catch (OrderNotFoundException ex) {
        }

    }

    @Test
    public void testRemoveOrderOrderNotFoundException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.removeOrder(10, orderDate);
            fail("should hit OrderNotFoundException when orderNumber is invalid");
        } catch (OrderNotFoundException ex) {
        }
    }

    @Test
    public void testRemoveOrderNullDate() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.removeOrder(1, null);
            fail("should hit NullDateException when date is null");
        } catch (NullDateException ex) {
        }
    }

    /**
     * Test of getOrder method, of class FlooringMasteryService.
     */
    @Test
    public void testGetOrderGP() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        Order expected = new Order(orderDate, 1, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75"));

        Order res = toTest.getOrder(expected.getOrderNumber(), orderDate);

        assertEquals(expected.getOrderDate(), res.getOrderDate());
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
    public void testGetOrderOrderNullDate() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.getOrder(1, null);
            fail("should hit NullDateException when date is null");
        } catch (NullDateException ex) {
        }
    }

    @Test
    public void testGetOrderOrderNotFoundException() throws FlooringServiceException, FlooringMasteryOrderDAOPersistenceException {
        try {
            toTest.getOrder(10, orderDate);
            fail("should hit OrderNotFoundException when orderNumber is invalid");
        } catch (OrderNotFoundException ex) {
        }
    }

}
