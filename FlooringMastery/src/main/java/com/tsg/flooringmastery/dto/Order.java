package com.tsg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Order {
    private LocalDate orderDate;
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order() {
    }




    public Order(LocalDate orderDate, int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total) {
        this.orderDate = orderDate;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }

    public BigDecimal calcMaterialCost(){
        materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        return materialCost;
    }
    public BigDecimal calcMaterialCost(BigDecimal costPerSquareFoot){
        this.costPerSquareFoot = costPerSquareFoot;
        materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        return materialCost;
    }
    public BigDecimal calcLaborCost(){
        laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        return laborCost;
    }
    public BigDecimal calcLaborCost(BigDecimal laborCostPerSquareFoot){
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;

        laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        return laborCost;
    }
    public BigDecimal calcTax(){

        BigDecimal newTaxRate = taxRate.divide(new BigDecimal("100"), 3, RoundingMode.HALF_UP);
        tax = materialCost.add(laborCost);
        tax = tax.multiply(newTaxRate).setScale(2, RoundingMode.HALF_UP);
        return tax;
    }


    public BigDecimal calcTotalCost(){
        total = materialCost.add(laborCost).add(tax);
        return total;
    }

    public String getOrderDateAsString(){
        return this.orderDate.format(DateTimeFormatter.ISO_DATE);
    }

    public void setOrderDateAsString(String isoDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate theDate = LocalDate.parse(isoDate, formatter);
        this.orderDate = theDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(customerName, order.customerName) &&
                Objects.equals(state, order.state) &&
                Objects.equals(taxRate, order.taxRate) &&
                Objects.equals(productType, order.productType) &&
                Objects.equals(area, order.area) &&
                Objects.equals(costPerSquareFoot, order.costPerSquareFoot) &&
                Objects.equals(laborCostPerSquareFoot, order.laborCostPerSquareFoot) &&
                Objects.equals(materialCost, order.materialCost) &&
                Objects.equals(laborCost, order.laborCost) &&
                Objects.equals(tax, order.tax) &&
                Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDate=" + orderDate +
                ", orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", calcMaterialCost=" + materialCost +
                ", calcLaborCost=" + laborCost +
                ", tax=" + tax +
                ", total=" + total +
                '}';
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }
    public void recalcArea(){

        this.calcLaborCost();
        this.calcMaterialCost();
        this.calcTax();
        this.calcTotalCost();

    }
    public void recalcArea(BigDecimal area){
        this.setArea(area);
        this.calcLaborCost();
        this.calcMaterialCost();
        this.calcTax();
        this.calcTotalCost();

    }
    public void recalcArea(BigDecimal labor, BigDecimal cost){

        this.calcLaborCost(labor);
        this.calcMaterialCost(cost);
        this.calcTax();
        this.calcTotalCost();

    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


}
