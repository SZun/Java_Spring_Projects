/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryOrderDAOPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.service.InvalidAreaException;
import com.sg.flooringmastery.service.InvalidNameException;
import com.sg.flooringmastery.service.NullDateException;
import com.sg.flooringmastery.service.InvalidStateException;
import com.sg.flooringmastery.service.NoOrdersException;
import com.sg.flooringmastery.service.OrderNotFoundException;
import com.sg.flooringmastery.service.DateNotFutureException;
import com.sg.flooringmastery.service.OrderValidationException;
import com.sg.flooringmastery.service.ProductNotFoundException;
import com.sg.flooringmastery.view.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryController {

    FlooringMasteryService service;
    FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean done = false;

        while (!done) {

            int mainMenuChoice = view.getMainMenuChoice();
            
            try {
                switch (mainMenuChoice) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        done = true;
                        break;
                    default:
                        view.displayInvalidSelectionMessage();
                }
            } catch (OrderNotFoundException | OrderValidationException |
                    NoOrdersException | NullDateException | ProductNotFoundException |
                    InvalidStateException | FlooringMasteryOrderDAOPersistenceException |
                    InvalidNameException | InvalidAreaException | DateNotFutureException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }

    }

    private void removeOrder() throws OrderNotFoundException, FlooringMasteryOrderDAOPersistenceException, NullDateException {
        int orderNumber = view.getOrderNumber();
        LocalDate orderDate = view.getOrderDate();

        Order retrieved = service.getOrder(orderNumber, orderDate);

        if (retrieved != null) {
            view.displayOrder(retrieved);
            boolean confirm = view.confirmRemove(retrieved);

            if (confirm) {
                service.removeOrder(orderNumber, orderDate);
            }

        } else {
            view.displayInvalidOrderMessage();
        }
    }

    private void editOrder() throws OrderNotFoundException, OrderValidationException, NullDateException, ProductNotFoundException, InvalidStateException, FlooringMasteryOrderDAOPersistenceException, InvalidNameException, InvalidAreaException {
        int orderNumber = view.getOrderNumber();
        LocalDate orderDate = view.getOrderDate();

        Order retrieved = service.getOrder(orderNumber, orderDate);

        if (retrieved != null) {
            Order updated = view.editOrder(retrieved);

            updated = service.editOrder(updated, orderDate);
            view.displayOrder(updated);
        } else {
            view.displayInvalidOrderMessage();
        }
    }

    private void addOrder() throws OrderValidationException, DateNotFutureException, NullDateException, ProductNotFoundException, InvalidStateException, FlooringMasteryOrderDAOPersistenceException, InvalidNameException, InvalidAreaException {
        LocalDate orderDate = view.getOrderDate();
        Order toAdd = view.getOrder();

        Order added = service.addOrder(toAdd, orderDate);
        view.displayOrder(added);
    }

    private void displayOrders() throws NoOrdersException, FlooringMasteryOrderDAOPersistenceException, NullDateException {
        LocalDate ordersDate = view.getOrderDate();

        List<Order> allOrders = service.getAllOrders(ordersDate);

        view.displayAllOrders(allOrders);
    }
}
