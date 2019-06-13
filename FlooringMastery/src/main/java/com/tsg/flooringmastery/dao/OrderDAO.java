package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dto.Order;

import java.util.List;

public interface OrderDAO {
    boolean isTrainingMode() throws OrderPersistenceException;
    Order addOrder(Order order);
    Order getOrder(int orderNumber) ;
    List<Order> getAllOrders();
    void editOrder(int orderNumber, Order order);
    Order removeOrder(int orderNumber);
    void saveOrder() throws OrderPersistenceException;
    void readOrder() throws OrderPersistenceException;

}
