/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.controllers;

import com.sg.vendingmachine.vendingmachine.dtos.Change;
import com.sg.vendingmachine.vendingmachine.dtos.Item;
import com.sg.vendingmachine.vendingmachine.services.ItemNotFoundException;
import com.sg.vendingmachine.vendingmachine.services.ItemOutOfStockException;
import com.sg.vendingmachine.vendingmachine.services.NegativeMoneyException;
import com.sg.vendingmachine.vendingmachine.services.NotEnoughMoneyException;
import com.sg.vendingmachine.vendingmachine.services.VendingMachineService;
import com.sg.vendingmachine.vendingmachine.views.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class VendingMachineController {

    VendingMachineView view;
    VendingMachineService service;

    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean done = false;

        while (!done) {
            getAllItems();

            int mainMenuChoice = view.getMainMenuChoice();

            try {
                switch (mainMenuChoice) {
                    case 1:
                        buyItem();
                        break;
                    case 2:
                        enterMoney();
                        break;
                    case 3:
                        getChange();
                        break;
                    case 4:
                        done = true;
                        break;
                    default:
                        view.displayInvalidSelectionMessage();
                        break;
                }
            } catch (ItemNotFoundException | NotEnoughMoneyException 
                    | ItemOutOfStockException | NegativeMoneyException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

    private void getAllItems() {
        List<Item> allItems = service.getAllItems();

        view.displayAllItems(allItems);
    }

    private void buyItem() throws ItemNotFoundException, NotEnoughMoneyException, ItemOutOfStockException {
        String itemName = view.getItemName();

        Change c = service.buyItem(itemName);

        view.displayBoughtItemMessage(itemName, c);
    }

    private void enterMoney() throws NegativeMoneyException {
        BigDecimal money = view.getMoney();
        
        BigDecimal totalMoney = service.addMoney(money);
        
        view.displayMoney(totalMoney);
    }

    private void getChange() {
        Change change = service.returnChange();
        
        view.displayChange(change);
    }
}
