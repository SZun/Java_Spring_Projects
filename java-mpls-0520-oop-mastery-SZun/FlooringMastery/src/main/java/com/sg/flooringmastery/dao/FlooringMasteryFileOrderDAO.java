/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryFileOrderDAO implements FlooringMasteryOrderDAO {

    String folder;

    public FlooringMasteryFileOrderDAO(String path) {
        this.folder = path;
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringMasteryOrderDAOPersistenceException {
        List<Order> allItems = new ArrayList<>();
        
        if(date == null){
            throw new FlooringMasteryOrderDAOPersistenceException("\nThe date you have entered is not valid\n");
        }

        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(Paths.get(folder, "Order_"
                                    + date.format(DateTimeFormatter.ofPattern("MMddyyyy"))
                                    + ".txt")
                                    .toString())
                    )
            );

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Order toAdd = convertLineToOrder(row);

                allItems.add(toAdd);
            }

            fileScanner.close();

        } catch (FileNotFoundException ex) {
            throw new FlooringMasteryOrderDAOPersistenceException("\nThe date you have entered does not correspond to any orders\n");
        }

        return allItems;
    }

    private Order convertLineToOrder(String row) {
        String[] cells = row.split(",");

        Order toReturn = new Order();
        toReturn.setOrderNumber(Integer.parseInt(cells[0]));
        toReturn.setCustomerName(cells[1]);
        toReturn.setState(cells[2]);
        toReturn.setTaxRate(new BigDecimal(cells[3]));
        toReturn.setProductType(cells[4]);
        toReturn.setArea(new BigDecimal(cells[5]));
        toReturn.setCostPerSquareFoot(new BigDecimal(cells[6]));
        toReturn.setLaborCostPerSquareFoot(new BigDecimal(cells[7]));

        return toReturn;
    }

    private void writeFile(List<Order> orders, LocalDate orderDate) {
        try {
            PrintWriter writer = new PrintWriter(
                    new FileWriter(
                            Paths.get(folder, "Order_"
                                    + orderDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))
                                    + ".txt")
                                    .toString()));
            writer.println(
                    "Order Number,Customer Name,State,Tax Rate,Product Type,"
                    + "Area,Cost Per Square Foot,Labor Cost Per Square Foot,Material Cost,"
                    + "Labor Cost,Tax,Total"
            );
            for (Order o : orders) {
                String line = convertOrdertoLine(o);
                writer.println(line);
            }

            writer.flush();
            writer.close();

        } catch (IOException ex) {

        }

    }

    private String convertOrdertoLine(Order o) {
        return o.getOrderNumber() + ","
                + o.getCustomerName() + ","
                + o.getState() + ","
                + o.getTaxRate() + ","
                + o.getProductType() + ","
                + o.getArea() + ","
                + o.getCostPerSquareFoot() + ","
                + o.getLaborCostPerSquareFoot() + ","
                + o.getMaterialCost() + ","
                + o.getLaborCost() + ","
                + o.getTax() + ","
                + o.getTotal();

    }

    @Override
    public Order addOrder(Order toAdd) throws FlooringMasteryOrderDAOPersistenceException {
        List<Order> allOrders = new ArrayList<>();
        
        if(toAdd == null || toAdd.getOrderDate() == null){
            throw new FlooringMasteryOrderDAOPersistenceException("\nThe order or date you have entered is invalid\n");
        }

        try {
            allOrders = getAllOrders(toAdd.getOrderDate());
        } catch (FlooringMasteryOrderDAOPersistenceException ex) {

        }

        int orderNumber = 0;

        for (Order o : allOrders) {
            if (o.getOrderNumber() > orderNumber) {
                orderNumber = o.getOrderNumber();
            }
        }

        toAdd.setOrderNumber(orderNumber + 1);

        allOrders.add(toAdd);
        writeFile(allOrders, toAdd.getOrderDate());

        return toAdd;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate date) throws FlooringMasteryOrderDAOPersistenceException {

        return getAllOrders(date)
                .stream()
                .filter(o -> o.getOrderNumber() == orderNumber)
                .findAny()
                .orElse(null);
    }

    @Override
    public Order editOrder(Order updated) throws FlooringMasteryOrderDAOPersistenceException {
        if(updated == null){
            throw new FlooringMasteryOrderDAOPersistenceException("\nThe order you have entered is invalid\n");
        }
        
        List<Order> allOrders = getAllOrders(updated.getOrderDate());

        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == updated.getOrderNumber()) {
                index = i;
                break;
            }
        }
        
        if (index == -1){
            throw new FlooringMasteryOrderDAOPersistenceException("\nCould not edit due to order number not being found\n");
        }

        allOrders.set(index, updated);
        writeFile(allOrders, updated.getOrderDate());

        return allOrders.get(index);
    }

    @Override
    public void removeOrder(int orderNumber, LocalDate date) throws FlooringMasteryOrderDAOPersistenceException {
        List<Order> allOrders = getAllOrders(date);
        
        int index = -1;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);

            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }
        
        if (index == -1){
            throw new FlooringMasteryOrderDAOPersistenceException("\nCould not remove due to order number not being found\n");
        }
        
        allOrders.remove(index);

        writeFile(allOrders, date);
    }

}
