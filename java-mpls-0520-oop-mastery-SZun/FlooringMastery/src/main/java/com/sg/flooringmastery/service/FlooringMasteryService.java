/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDAOPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryOrderDAO;
import com.sg.flooringmastery.dao.FlooringMasteryProductDAO;
import com.sg.flooringmastery.dao.FlooringMasteryTaxDAO;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryService {

    FlooringMasteryOrderDAO orderDAO;
    FlooringMasteryProductDAO productDAO;
    FlooringMasteryTaxDAO taxDAO;

    public FlooringMasteryService(FlooringMasteryOrderDAO dao, FlooringMasteryProductDAO productDAO, FlooringMasteryTaxDAO taxDAO) {
        this.orderDAO = dao;
        this.productDAO = productDAO;
        this.taxDAO = taxDAO;
    }

    public List<Order> getAllOrders(LocalDate ordersDate) throws NoOrdersException, FlooringMasteryOrderDAOPersistenceException, NullDateException {
        validateDate(ordersDate);
        
        List<Order> allOrders = orderDAO.getAllOrders(ordersDate);

        if (allOrders.isEmpty()) {
            throw new NoOrdersException("There are no orders for this day.");
        }

        return allOrders;
    }

    public Order addOrder(Order toAdd, LocalDate orderDate) throws OrderValidationException, DateNotFutureException, NullDateException, ProductNotFoundException, InvalidStateException, FlooringMasteryOrderDAOPersistenceException, InvalidNameException, InvalidAreaException {
        validateDate(orderDate);
        validateOrderData(toAdd);
        validateOrderDateInFuture(orderDate);
        
        toAdd.setOrderDate(orderDate);

        Product prod = productDAO.getProduct(toAdd.getProductType());
        Tax taxObj = taxDAO.getTax(toAdd.getState());

        validateFullOrder(toAdd, prod, taxObj);

        toAdd.setCostPerSquareFoot(prod.getCostPerSquareFoot());
        toAdd.setLaborCostPerSquareFoot(prod.getLaborCostPerSquareFoot());

        toAdd.setTaxRate(taxObj.getTaxRate());

        return orderDAO.addOrder(toAdd);
    }

    public Order editOrder(Order updated, LocalDate orderDate) throws OrderValidationException, NullDateException, OrderNotFoundException, ProductNotFoundException, InvalidStateException, FlooringMasteryOrderDAOPersistenceException, InvalidNameException, InvalidAreaException {
        validateDate(orderDate);
        validateOrderData(updated);
        
        updated.setOrderDate(orderDate);

        Product prod = productDAO.getProduct(updated.getProductType());
        Tax taxObj = taxDAO.getTax(updated.getState());

        validateFullOrder(updated, prod, taxObj);

        updated.setCostPerSquareFoot(prod.getCostPerSquareFoot());
        updated.setLaborCostPerSquareFoot(prod.getLaborCostPerSquareFoot());

        updated.setTaxRate(taxObj.getTaxRate());

        try {
            return orderDAO.editOrder(updated);
        } catch (FlooringMasteryOrderDAOPersistenceException ex) {
            throw new OrderNotFoundException("\nThe order you are looking for can not be found.\n");
        }
    }

    public void removeOrder(int orderNumber, LocalDate date) throws OrderNotFoundException, FlooringMasteryOrderDAOPersistenceException, NullDateException {
       validateDate(date);
        
        try {
            orderDAO.removeOrder(orderNumber, date);
        } catch (FlooringMasteryOrderDAOPersistenceException ex) {
            throw new OrderNotFoundException("\nThe order you are looking for can not be found.\n");
        }
    }

    public Order getOrder(int orderNumber, LocalDate date) throws OrderNotFoundException, FlooringMasteryOrderDAOPersistenceException, NullDateException {
        validateDate(date);
        
        Order toReturn = orderDAO.getOrder(orderNumber, date);

        if (toReturn == null) {
            throw new OrderNotFoundException("\nThe order you are looking for can not be found.\n");
        }

        return toReturn;
    }

    private void validateOrderData(Order o) throws OrderValidationException {
        if (o.getCustomerName() == null || o.getCustomerName().trim().length() == 0
                || o.getState() == null || o.getState().trim().length() == 0
                || o.getProductType() == null || o.getProductType().trim().length() == 0
                || o.getArea() == null) {
            throw new OrderValidationException("\nThe order you have entered has invalid fields.\n");
        }
    }

    private void validateOrderDateInFuture(LocalDate orderDate) throws DateNotFutureException {
        if (!LocalDate.now().isBefore(orderDate)) {
            throw new DateNotFutureException("\nThe order date specified is not in the future.\n");
        }
    }

    private void validateDate(LocalDate orderDate)throws NullDateException {
        if(orderDate == null){
            throw new NullDateException("\nThe date you have entere is invalid\n");
        }
    }
    
    private void validateFullOrder(Order order, Product prod, Tax taxObj) throws InvalidNameException, InvalidAreaException, ProductNotFoundException, InvalidStateException{
        if (!order.getCustomerName().matches("^[-a-zA-Z0-9.,]+")) {
            throw new InvalidNameException("\nThe name you have entered is invalid\n");
        } else if (!(order.getArea().compareTo(new BigDecimal(100)) >= 0)) {
            throw new InvalidAreaException("\nThe area you have entered is too small\n");
        } else if (prod == null) {
            throw new ProductNotFoundException("\nThe product you have entered can not be found.\n");
        } else if (taxObj == null) {
            throw new InvalidStateException("\nThe state you have entered is invalid.\n");
        }
    }

}
