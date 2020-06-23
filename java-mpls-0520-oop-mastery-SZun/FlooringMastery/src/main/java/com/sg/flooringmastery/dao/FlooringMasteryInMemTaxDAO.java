/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author samg.zun
 */
public class FlooringMasteryInMemTaxDAO implements FlooringMasteryTaxDAO {

    List<Tax> allTaxes = Arrays.asList(
            new Tax("Alabama", new BigDecimal(4)),
            new Tax("Alaska", BigDecimal.ZERO),
            new Tax("Arizona", new BigDecimal("5.60")),
            new Tax("Arkansas", new BigDecimal("6.50")),
            new Tax("Illinois", new BigDecimal("6.25")),
            new Tax("Michigan", new BigDecimal("6.00"))
    );

    @Override
    public Tax getTax(String stateName) {
        Tax toReturn = null;

        if(stateName != null) {
            toReturn = allTaxes.stream()
                    .filter(t -> t.getStateName().equalsIgnoreCase(stateName))
                    .findAny()
                    .orElse(null);
        }

        return toReturn;
    }

}
