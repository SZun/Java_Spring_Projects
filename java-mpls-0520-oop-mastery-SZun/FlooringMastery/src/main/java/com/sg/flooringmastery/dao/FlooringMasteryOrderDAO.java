/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface FlooringMasteryOrderDAO {

    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryOrderDAOPersistenceException;

    public Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryOrderDAOPersistenceException;

    public Order editOrder(Order updated) throws FlooringMasteryOrderDAOPersistenceException;

    public void removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryOrderDAOPersistenceException;

    public Order addOrder(Order toAdd) throws FlooringMasteryOrderDAOPersistenceException;
    
}
