package com.tsg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCosterPerSquareFoot;

    public Product() {
    }

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCosterPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCosterPerSquareFoot = laborCosterPerSquareFoot;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) &&
                Objects.equals(costPerSquareFoot, product.costPerSquareFoot) &&
                Objects.equals(laborCosterPerSquareFoot, product.laborCosterPerSquareFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, laborCosterPerSquareFoot);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCosterPerSquareFoot=" + laborCosterPerSquareFoot +
                '}';
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCosterPerSquareFoot;
    }

    public void setLaborCosterPerSquareFoot(BigDecimal laborCosterPerSquareFoot) {
        this.laborCosterPerSquareFoot = laborCosterPerSquareFoot;
    }
}
