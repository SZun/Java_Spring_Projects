/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryInMemOrderDAO implements FlooringMasteryOrderDAO {

    List<Order> allOrders = new ArrayList<>();

    public FlooringMasteryInMemOrderDAO() {
        allOrders.add(new Order(LocalDate.of(2050,6,2), 1, "Sam", "Illinois", new BigDecimal("6.25"), "Wood", new BigDecimal(500), new BigDecimal("5.15"), new BigDecimal("4.75")));
        allOrders.add(new Order(LocalDate.of(2050,6,2), 2, "Samuel", "Michigan", new BigDecimal("6.00"), "Tile", new BigDecimal(600), new BigDecimal("3.50"), new BigDecimal("4.15")));
        allOrders.add(new Order(LocalDate.of(2050,6,2), 3, "Sammy", "Colorado", new BigDecimal("2.90"), "Laminate", new BigDecimal(400), new BigDecimal("1.75"), new BigDecimal("2.10")));
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) {
        return allOrders.stream().filter(o -> o.getOrderDate().equals(date)).collect(Collectors.toList());
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) {
        return allOrders
                .stream()
                .filter(o -> o.getOrderNumber() == orderNumber && date.equals(o.getOrderDate()))
                .findAny()
                .orElse(null);
    }

    @Override
    public Order editOrder(Order updated) throws FlooringMasteryOrderDAOPersistenceException {
        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == updated.getOrderNumber() &&
                    updated.getOrderDate().equals(toCheck.getOrderDate())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new FlooringMasteryOrderDAOPersistenceException("Can't be edited because the order number can't be found for this date");
        }

        allOrders.set(index, updated);
        return allOrders.get(index);
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryOrderDAOPersistenceException {
        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == orderNumber && date.equals(toCheck.getOrderDate())) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            throw new FlooringMasteryOrderDAOPersistenceException("Can't be removed because the order number can't be found for this date");
        }
        
        allOrders.remove(index);
    }

    @Override
    public Order addOrder(Order toAdd) {
        int orderNumber = 0;

        for (Order o : getAllOrders(toAdd.getOrderDate())) {
            if (o.getOrderNumber() > orderNumber) {
                orderNumber = o.getOrderNumber();
            }
        }

        toAdd.setOrderNumber(orderNumber + 1);

        allOrders.add(toAdd);

        return toAdd;
    }

}
