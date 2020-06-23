/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryFileTaxDAO implements FlooringMasteryTaxDAO {
    String folder;

    public FlooringMasteryFileTaxDAO(String path) {
        this.folder = path;
    }
            
    @Override
    public Tax getTax(String stateName){
        Tax toReturn = null;
        
        if(stateName != null) {
            toReturn = readFile()
                    .stream()
                    .filter(t -> t.getStateName().equalsIgnoreCase(stateName))
                    .findAny()
                    .orElse(null);
        } 
        
        return toReturn;
    }
    
    private List<Tax> readFile(){
        List<Tax> allItems = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(Paths.get(folder, "Taxes.txt").toString())
                    )
            );
            
            fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Tax toAdd = convertLineToTax(row);

                allItems.add(toAdd);

            }
            
            fileScanner.close();

        } catch (FileNotFoundException ex) {

        }

        return allItems;
    }

    private Tax convertLineToTax(String row) {
        String[] cells = row.split(",");
        
        Tax toReturn = new Tax();
        toReturn.setStateName(cells[1]);
        toReturn.setTaxRate(new BigDecimal(cells[2]));
        
        return toReturn;
    }        
}
