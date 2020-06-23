/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author samg.zun
 */
public class Tax {
    private String stateName;
    private String stateAbbreviation;
    private BigDecimal taxRate;

    public Tax() {
    }
    

    public Tax(String stateName, BigDecimal taxRate) {
        this.stateName = stateName;
        this.taxRate = taxRate;
    }
    
    
    /**
     * @param stateAbbreviation the stateAbbreviation to set
     */
    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    /**
     * @return the stateAbbreviation
     */
    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

}
