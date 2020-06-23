/*
 * To toReturn this license header, choose License Headers in Project Properties.
 * To toReturn this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.services;

import com.sg.vendingmachine.vendingmachine.daos.VendingMachineDAO;
import com.sg.vendingmachine.vendingmachine.dtos.Change;
import com.sg.vendingmachine.vendingmachine.dtos.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public class VendingMachineService {

    VendingMachineDAO dao;
    private BigDecimal enteredMoney = BigDecimal.ZERO;

    public VendingMachineService(VendingMachineDAO dao) {
        this.dao = dao;
    }

    public Change returnChange() {
        Change toReturn = calculateChange();
        setEnteredMoneyToDefault();
        return toReturn;
    }
    
    public BigDecimal addMoney(BigDecimal enteredMoney) throws NegativeMoneyException {
        if(enteredMoney.signum() == -1){
            throw new NegativeMoneyException("You've entered negative money");
        } else {
            this.enteredMoney = this.enteredMoney.add(enteredMoney);
        }
        return this.enteredMoney;

    }

    private void setEnteredMoneyToDefault() {
        this.enteredMoney = BigDecimal.ZERO;
    }

    public List<Item> getAllItems() {
        return dao.getAllItems();
    }

    public Change buyItem(String name) throws ItemNotFoundException, NotEnoughMoneyException, ItemOutOfStockException {
        Item savedItem = dao.getItemByName(name);
        Change c = null;

        if (savedItem.getTotal() == 0) {
            throw new ItemOutOfStockException("\n Sorry we are currently out of stock of " + savedItem.getName() + "\n");
        }

        if (enteredMoney.compareTo(savedItem.getCost()) < 0) {
            throw new NotEnoughMoneyException("You have entered too little money. The item costs " + savedItem.getCost().toString() + "\n");
        } else if (enteredMoney.compareTo(savedItem.getCost()) >= 0) {
            savedItem.setTotal(savedItem.getTotal() - 1);
            dao.purchaseItem(savedItem);
            enteredMoney = enteredMoney.subtract(savedItem.getCost());
            c = returnChange();
        }

        return c;
    }

    private Change calculateChange() {
        Change toReturn = new Change();
        
        int totalPennies = enteredMoney.multiply(new BigDecimal(100)).intValue();
        
        int quarters = totalPennies / 25;
//        totalPennies -= quarters * 25;
        totalPennies %= 25;
        int dimes = totalPennies / 10;
        totalPennies %= 10;
        int nickels = totalPennies / 5;
        totalPennies %= 5;
        

        toReturn.setQuarters(quarters);
        toReturn.setDimes(dimes);
        toReturn.setNickels(nickels);
        toReturn.setPennies(totalPennies);
        
        return toReturn;
    }

}
