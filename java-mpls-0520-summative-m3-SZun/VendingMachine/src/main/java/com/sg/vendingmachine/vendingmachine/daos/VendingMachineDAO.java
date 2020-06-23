/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.daos;

import com.sg.vendingmachine.vendingmachine.dtos.Item;
import com.sg.vendingmachine.vendingmachine.services.ItemNotFoundException;
import java.util.List;

/**
 *
 * @author samg.zun
 */
public interface VendingMachineDAO {

    public List<Item> getAllItems();

    public Item getItemByName(String name) throws IndexOutOfBoundsException, ItemNotFoundException;

    public void purchaseItem(Item toReturn) throws ItemNotFoundException;
    
}
