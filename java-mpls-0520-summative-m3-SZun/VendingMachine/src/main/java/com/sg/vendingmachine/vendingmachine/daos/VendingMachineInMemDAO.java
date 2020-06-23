/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.daos;

import com.sg.vendingmachine.vendingmachine.dtos.Item;
import com.sg.vendingmachine.vendingmachine.services.ItemNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class VendingMachineInMemDAO implements VendingMachineDAO {
    
    List<Item> allItems = new ArrayList<>(Arrays.asList(
            new Item("Lays Original", new BigDecimal("1.00"), 25),
            new Item("Smartfood Popcorn", new BigDecimal("1.00"), 0),
            new Item("Banan", new BigDecimal("0.50"), 25)
    ));
    
    @Override
    public List<Item> getAllItems() {
        return allItems;
    }

    @Override
    public Item getItemByName(String name) throws IndexOutOfBoundsException, ItemNotFoundException {
        try {
            return allItems.stream().filter(i -> i.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new ItemNotFoundException("The item you entered does not exist");
        }
    }

    @Override
    public void purchaseItem(Item purchasable) throws ItemNotFoundException {
        boolean foundItem = false;
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getName().equalsIgnoreCase(purchasable.getName())) {
                allItems.set(i, purchasable);
                foundItem = true;
            } 
        }
        
        if(!foundItem){
            throw new ItemNotFoundException("The item you entered does not exist");
        }

    }

}
