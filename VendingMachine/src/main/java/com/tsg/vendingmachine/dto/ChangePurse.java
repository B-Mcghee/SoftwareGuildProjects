package com.tsg.vendingmachine.dto;

import com.tsg.vendingmachine.service.Coins;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ChangePurse {
    private int numPennies;
    private int numNickels;
    private int numDimes;
    private int numQuarters;


    public ChangePurse() {
    }

    public ChangePurse(int numPennies, int numNickels, int numDimes, int numQuarters) {
        this.numPennies = numPennies;
        this.numNickels = numNickels;
        this.numDimes = numDimes;
        this.numQuarters = numQuarters;
    }

    public BigDecimal getTotal(ChangePurse changePurse){
        int quarter = changePurse.getNumQuarters();
        int dime = changePurse.getNumDimes();
        int nickels = changePurse.getNumNickels();
        int penny = changePurse.getNumPennies();
        BigDecimal p = new BigDecimal(penny) ;
        BigDecimal n = new BigDecimal(nickels);
        BigDecimal d = new BigDecimal(dime);
        BigDecimal q = new BigDecimal(quarter);

        BigDecimal total = Coins.QUARTER.value.multiply(q);
        total = total.add(Coins.NICKEL.value.multiply(n));
        total = total.add(Coins.DIME.value.multiply(d));
        total = total.add(Coins.PENNY.value.multiply(p));
        return total;
    }
    public ChangePurse getCoins(BigDecimal money, BigDecimal itemPrice, ChangePurse changePurse){
        money = money.subtract(itemPrice).setScale(2, RoundingMode.HALF_UP);

        BigDecimal change = money;
        BigDecimal coinCounter = money;


//                changePurse.setTotal(change.toPlainString());

        change = coinCounter.divide(Coins.QUARTER.value, 0, RoundingMode.DOWN);
        //convert change to integer
        changePurse.setNumQuarters(Integer.parseInt(change.toPlainString()));

        coinCounter = coinCounter.remainder(Coins.QUARTER.value);
        change = coinCounter.divide(Coins.DIME.value, 0, RoundingMode.DOWN);
        changePurse.setNumDimes(Integer.parseInt(change.toPlainString()));
        coinCounter = coinCounter.remainder(Coins.DIME.value);
        change = coinCounter.divide(Coins.NICKEL.value, 0, RoundingMode.DOWN);
        changePurse.setNumNickels(Integer.parseInt(change.toPlainString()));
        coinCounter = coinCounter.remainder(Coins.NICKEL.value);
        change = coinCounter.divide(Coins.PENNY.value, 0, RoundingMode.DOWN);
        changePurse.setNumPennies(Integer.parseInt(change.toPlainString()));

        getTotal(changePurse);
        return changePurse;
    }

    @Override
    public String toString() {
        return "ChangePurse{" +
                "numPennies='" + numPennies + '\'' +
                ", numNickels=" + numNickels +
                ", numDimes=" + numDimes +
                ", numQuarters=" + numQuarters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangePurse that = (ChangePurse) o;
        return numNickels == that.numNickels &&
                numDimes == that.numDimes &&
                numQuarters == that.numQuarters &&
                Objects.equals(numPennies, that.numPennies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numPennies, numNickels, numDimes, numQuarters);
    }

    public int getNumPennies() {
        return numPennies;
    }

    public void setNumPennies(int numPennies) {
        this.numPennies = numPennies;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public void setNumNickels(int numNickels) {
        this.numNickels = numNickels;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public void setNumDimes(int numDimes) {
        this.numDimes = numDimes;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }




}
