package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dto.Order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


public class OrderImplTest implements OrderDAO{
    private final String DELIMITER = ",";
    private String ORDER_FILE;

    public Order onlyOrder;
    public Order thirdOrder;

    public OrderImplTest() {
        onlyOrder = new Order();
        onlyOrder.setOrderDate(LocalDate.of(2020, 10, 20));
        onlyOrder.setOrderNumber(50);
        onlyOrder.setCustomerName("Brandon Smith");
        onlyOrder.setState("CA");
        onlyOrder.setProductType("Linoleum");
        onlyOrder.setArea(new BigDecimal("200"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("5.00"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("7.50"));
        onlyOrder.setMaterialCost(new BigDecimal("1000.00"));
        onlyOrder.setLaborCost(new BigDecimal("1500.00"));
        onlyOrder.setTaxRate(new BigDecimal("25.00"));
        onlyOrder.setTax(new BigDecimal("625.00"));
        onlyOrder.setTotal(new BigDecimal("3125.00"));

        thirdOrder = new Order();
        thirdOrder.setOrderDate(LocalDate.of(2020, 10, 20));
        thirdOrder.setOrderNumber(99);
        thirdOrder.setCustomerName("Brandon Smith");
        thirdOrder.setState("CA");
        thirdOrder.setProductType("Linoleum");
        thirdOrder.setArea(new BigDecimal("200"));
        thirdOrder.setCostPerSquareFoot(new BigDecimal("5.00"));
        thirdOrder.setLaborCostPerSquareFoot(new BigDecimal("7.50"));
        thirdOrder.setMaterialCost(new BigDecimal("1000.00"));
        thirdOrder.setLaborCost(new BigDecimal("1500.00"));
        thirdOrder.setTaxRate(new BigDecimal("25.00"));
        thirdOrder.setTax(new BigDecimal("625.00"));
        thirdOrder.setTotal(new BigDecimal("3125.00"));


    }

    public OrderImplTest(Order testOrder, Order otherTest){
        this.onlyOrder = testOrder;
        this.thirdOrder = otherTest;
    }





    public boolean isTrainingMode() throws OrderPersistenceException{
        File file = new File("Data/");
        if (!file.exists()){
            return false;
        }else{

            Scanner scanner;
            String filePath = file + File.separator + ORDER_FILE;
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
            }catch (IOException e){
                throw new OrderPersistenceException("It is in production mode");
            }
            if (scanner.nextLine().contains("Trai")){
                return true;
            }else{
                return false;
            }

        }
    }


    @Override
    public Order addOrder(Order order) {
        if (order.getOrderNumber() == onlyOrder.getOrderNumber()){
            return onlyOrder;
        }else {
            return null;
        }
    }

    @Override
    public Order getOrder(int orderNumber) {
        if (orderNumber == onlyOrder.getOrderNumber()){
            return onlyOrder;
        }else if(orderNumber == thirdOrder.getOrderNumber()){
                return thirdOrder;
        }
        else {
            return null;
        }

    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> allOrders = new ArrayList<>();
        allOrders.add(onlyOrder);
        allOrders.add(thirdOrder);
        return allOrders;
    }

    @Override
    public void editOrder(int orderNumber, Order order) {
        Map<Integer, Order> map = new HashMap<>();

        for (Order orders: getAllOrders()) {
            map.put(orders.getOrderNumber(), orders);
        }
        map.replace(orderNumber, order);



    }

    @Override
    public Order removeOrder(int orderNumber){

        return null;
    }



    @Override
    public void saveOrder()  {




    }

    @Override
    public void readOrder() throws OrderPersistenceException {

    }

    private void deleteOrder() {

    }
}