/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryInMemProductDAO implements FlooringMasteryProductDAO {

    List<Product> allProducts = Arrays.asList(
            new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10")),
            new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10")),
            new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15")),
            new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75"))
    );

    @Override
    public Product getProduct(String productType) {
        Product toReturn = null;

        if(productType != null){
            toReturn = allProducts.stream()
                    .filter(p -> p.getProductType().equalsIgnoreCase(productType))
                    .findAny()
                    .orElse(null);
        }
           

        return toReturn;
    }

}
