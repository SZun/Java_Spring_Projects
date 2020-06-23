/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.daos;

import com.sg.vendingmachine.vendingmachine.dtos.Item;
import com.sg.vendingmachine.vendingmachine.services.ItemNotFoundException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class VendingMachineFileDAO implements VendingMachineDAO {

    String path;

    public VendingMachineFileDAO(String path) {
        this.path = path;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(path)
                    )
            );

            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Item toAdd = convertLineToItem(row);

                allItems.add(toAdd);

            }

        } catch (FileNotFoundException ex) {

        }

        return allItems;
    }

    @Override
    public Item getItemByName(String name) throws IndexOutOfBoundsException, ItemNotFoundException {
        try {
            return getAllItems().stream().filter(i -> i.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new ItemNotFoundException("The item you entered does not exist");
        }
    }

    @Override
    public void purchaseItem(Item purchasable) throws ItemNotFoundException {
            
        boolean foundItem = false;
        List<Item> allItems = getAllItems();

        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getName().equalsIgnoreCase(purchasable.getName())) {
                allItems.set(i, purchasable);
                foundItem = true;
            }
        }

        if(!foundItem){
            throw new ItemNotFoundException("The item you entered does not exist");
        }
        
            writeFile(allItems);


    }

    private Item convertLineToItem(String row) {
        String[] cells = row.split("::");

        String name = cells[0];
        BigDecimal cost = new BigDecimal(cells[1]);
        int total = Integer.parseInt(cells[2]);

        Item toReturn = new Item();
        toReturn.setName(name);
        toReturn.setCost(cost);
        toReturn.setTotal(total);

        return toReturn;
    }

    private void writeFile(List<Item> updatedItems) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));
            
            for( Item i : updatedItems ){
                
                String line = convertItemtoLine( i );
                writer.println(line);
            }
            
            writer.flush();
            writer.close();
            
        } catch(IOException ex){
            
        }
        
    }

    private String convertItemtoLine(Item i) {
        return i.getName() + "::" +
               i.getCost() + "::" +
               i.getTotal();
    }

}
