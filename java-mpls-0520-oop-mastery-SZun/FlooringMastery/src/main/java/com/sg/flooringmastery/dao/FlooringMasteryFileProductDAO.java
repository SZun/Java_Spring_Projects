/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
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
public class FlooringMasteryFileProductDAO implements FlooringMasteryProductDAO {

    String folder;

    public FlooringMasteryFileProductDAO(String path) {
        this.folder = path;
    }

    @Override
    public Product getProduct(String productType) {
        Product toReturn = null;

        if (productType != null) {
            toReturn = readFile()
                    .stream()
                    .filter(p -> p.getProductType().equalsIgnoreCase(productType))
                    .findAny()
                    .orElse(null);
        }

        return toReturn;
    }

    private List<Product> readFile() {
        List<Product> allItems = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(new FileReader(
                            Paths.get(folder, "Products.txt").toString()))
            );

            fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Product toAdd = convertLineToProduct(row);

                allItems.add(toAdd);
            }

            fileScanner.close();

        } catch (FileNotFoundException ex) {

        }

        return allItems;
    }

    private Product convertLineToProduct(String row) {
        String[] cells = row.split(",");

        Product toReturn = new Product();
        toReturn.setProductType(cells[0]);
        toReturn.setCostPerSquareFoot(new BigDecimal(cells[1]));
        toReturn.setLaborCostPerSquareFoot(new BigDecimal(cells[2]));

        return toReturn;
    }

}
