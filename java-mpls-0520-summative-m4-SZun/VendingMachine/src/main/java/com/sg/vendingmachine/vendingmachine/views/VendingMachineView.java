/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.views;

import com.sg.vendingmachine.vendingmachine.dtos.Change;
import com.sg.vendingmachine.vendingmachine.dtos.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class VendingMachineView {
    
    UserIO io = new UserIO();

    public int getMainMenuChoice() {
        io.print("____Main Menu___\n");
        io.print("1. Purchase Item\n");
        io.print("2. Enter money\n");
        io.print("3. Return change\n");
        io.print("4. Exit\n");
        
        return io.readInt("Please enter your selection (1-4): ");
    }

    public void displayInvalidSelectionMessage() {
        io.print("Please enter a valid selection");
    }

    public void displayAllItems(List<Item> allItems) {
        for(Item i : allItems){
            io.print("Name: " + i.getName() + "\n");
            io.print("Cost: $" + i.getCost().toString() + "\n");
            io.print("Total: " + i.getTotal() + "\n");
            io.print("\n");
        }
    }

    public String getItemName() {
        return io.readString("Please enter the name of the item: ");
    }

    public void displayBoughtItemMessage(String name, Change change) {
        System.out.println("\n You've just bought a " + name + " & " + stringifyChange(change));
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public BigDecimal getMoney() {
        return new BigDecimal(io.readString("Please enter your money: "));
    }

    public String stringifyChange(Change change) {
        return "your change is " + change.getQuarters() + " quarters " + change.getDimes() + " dimes " + 
                change.getNickels() + " nickels " + change.getPennies() + " pennies." + "\n";
    }
    
    public void displayChange(Change change){
        io.print(stringifyChange(change));
    }

    public void displayMoney(BigDecimal totalMoney) {
        io.print("You have entered $" + totalMoney + "\n");
    }
    
}
