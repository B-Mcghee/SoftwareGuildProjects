package com.tsg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Tax {
    private String stateAbbrevation;
    private String stateName;
    private BigDecimal taxRate;

    public Tax() {
    }

    public Tax(String stateAbbrevation, String stateName, BigDecimal taxRate) {
        this.stateAbbrevation = stateAbbrevation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax = (Tax) o;
        return Objects.equals(stateAbbrevation, tax.stateAbbrevation) &&
                Objects.equals(stateName, tax.stateName) &&
                Objects.equals(taxRate, tax.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateAbbrevation, stateName, taxRate);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "stateAbbrevation='" + stateAbbrevation + '\'' +
                ", stateName='" + stateName + '\'' +
                ", taxRate=" + taxRate +
                '}';
    }

    public String getStateAbbrevation() {
        return stateAbbrevation;
    }

    public void setStateAbbrevation(String stateAbbrevation) {
        this.stateAbbrevation = stateAbbrevation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
