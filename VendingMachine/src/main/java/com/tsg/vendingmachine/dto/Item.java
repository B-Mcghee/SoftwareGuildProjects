package com.tsg.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private String vendingSlot;
    private String name;
    private BigDecimal cost;
    private int quantityOfItems;

    public Item() {
    }

    public Item(String vendingSlot, String name, BigDecimal cost, int quantityOfItems) {
        this.vendingSlot = vendingSlot;
        this.name = name;
        this.cost = cost;
        this.quantityOfItems = quantityOfItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantityOfItems == item.quantityOfItems &&
                Objects.equals(vendingSlot, item.vendingSlot) &&
                Objects.equals(name, item.name) &&
                Objects.equals(cost, item.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendingSlot, name, cost, quantityOfItems);
    }

    @Override
    public String toString() {
        return "Item{" +
                "vendingSlot='" + vendingSlot + '\'' +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                ", quantityOfItems=" + quantityOfItems +
                '}';
    }

    public String getVendingSlot() {
        return vendingSlot;
    }

    public void setVendingSlot(String vendingSlot) {
        this.vendingSlot = vendingSlot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getQuantityOfItems() {
        return quantityOfItems;
    }

    public void setQuantityOfItems(int quantityOfItems) {
        this.quantityOfItems = quantityOfItems;
    }
}
