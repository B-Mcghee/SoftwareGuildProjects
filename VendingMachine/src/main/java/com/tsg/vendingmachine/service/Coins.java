package com.tsg.vendingmachine.service;

import java.math.BigDecimal;

public enum Coins {
    QUARTER(new BigDecimal("0.25")),
    DIME(new BigDecimal("0.10")),
    NICKEL(new BigDecimal("0.05")),
    PENNY(new BigDecimal("0.01"));

    public final BigDecimal value;

    Coins(BigDecimal value){
        this.value = value;
    }
}
