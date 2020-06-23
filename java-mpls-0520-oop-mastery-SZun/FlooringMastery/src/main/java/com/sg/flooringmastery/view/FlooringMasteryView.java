/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.view;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryView {
    UserIO io = new UserIO();

    public void displayErrorMessage(String message){
        io.print("\n"+ message +"\n");
    }

    public int getMainMenuChoice() {
        io.print("\n <<Flooring Program>> \n");
        io.print("1. Display Orders\n");
        io.print("2. Add an Order\n");
        io.print("3. Edit an Order\n");
        io.print("4. Remove an Order\n");
        io.print("5. Quit\n");
        
        return io.readInt("Please enter your selection: ", 1, 6);
    }

    public void displayInvalidSelectionMessage() {
        io.print("\n You've entered an invalid selection. Please enter (1-6): \n");
    }

    public void displayAllOrders(List<Order> allOrders) {
        for(Order o : allOrders){
            displayOrder(o);
        }
    }

    public Order getOrder() {
        Order toReturn = new Order();
        
        toReturn.setCustomerName(io.readString("\nPlease enter your name\n"));
        toReturn.setState(io.readString("Please enter your state\n"));
        toReturn.setProductType(io.readString("Please enter your product type (Carpet, Laminate, Tile, Wood)\n"));
        toReturn.setArea(io.readBigDecimal("Please enter the area\n"));
        
        
        return toReturn;
    }

    public Order editOrder(Order retrieved) {
        Order updatedOrder = new Order();

        updatedOrder.setOrderNumber(retrieved.getOrderNumber());
        updatedOrder.setCustomerName(io.editString("\nEnter a new customer name or press enter to keep [" +
                retrieved.getCustomerName()+ "]\n", retrieved.getCustomerName()));
        updatedOrder.setState(io.editString("\nEnter a new state or press enter to keep [" + 
                retrieved.getState() + "]\n", retrieved.getState()));
        updatedOrder.setProductType(io.editString("\nEnter a new product type (Carpet, Laminate, Tile, Wood)"
                + " or press enter to keep [" + 
                retrieved.getProductType() + "]\n", retrieved.getProductType()));
        updatedOrder.setArea(io.editBigDecimal("\nEnter a new product area or press enter to keep [" + 
                retrieved.getArea() + "]\n", retrieved.getArea()));;

        return updatedOrder;
    }

    public int getOrderNumber() {
        return io.readInt("\nPlease enter your order number: \n");
    }
    
    public LocalDate getOrderDate(){
        return io.readDate("\nPlease enter the date of your order (\"MM/dd/yyyy\"): \n");
    }

    public void displayInvalidOrderMessage() {
        io.print("\nThe order you have entered does not exist\n");
    }

    public boolean confirmRemove(Order retrieved) {
        return io.readString("\nEnter \"yes\" to continue: \n").equalsIgnoreCase("yes");
    }

    public void displayOrder(Order o) {
        io.print("\n"+ "Order Number: " + o.getOrderNumber() + "\n");
        io.print("Customer Name: " + o.getCustomerName() + "\n");
        io.print("State: " + o.getState() + "\n");
        io.print("Tax Rate: " + o.getTaxRate() + "%\n");
        io.print("Product Type: " + o.getProductType() + "\n");
        io.print("Area: " + o.getArea() + "ft^2\n");
        io.print("Cost Per Square Foot: $" + o.getCostPerSquareFoot() + "\n");
        io.print("Labor Cost Per Square Foot: $" + o.getLaborCostPerSquareFoot() + "\n");
        io.print("Material Cost: $" + o.getMaterialCost() + "\n");
        io.print("Labor Cost: $" + o.getLaborCost() + "\n");
        io.print("Tax: $" + o.getTax() + "\n");
        io.print("Total: $" + o.getTotal() + "\n");
    }
}
