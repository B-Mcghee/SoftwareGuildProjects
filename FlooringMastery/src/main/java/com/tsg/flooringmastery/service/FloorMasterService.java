package com.tsg.flooringmastery.service;

import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dao.exceptions.ProductPersistenceException;
import com.tsg.flooringmastery.dao.exceptions.TaxPersistenceException;
import com.tsg.flooringmastery.dto.Order;
import com.tsg.flooringmastery.dto.Product;
import com.tsg.flooringmastery.dto.Tax;

import java.time.LocalDate;
import java.util.List;

public interface FloorMasterService {

    void loadAllSystems() throws OrderPersistenceException, TaxPersistenceException, ProductPersistenceException;
    void editOrder( Order order);
    List<Order> displayOrder(LocalDate date);
    void saveWork() throws OrderPersistenceException,TaxPersistenceException, ProductPersistenceException;
    boolean isProductionMode() throws OrderPersistenceException;
    List<Order> getOrderList() ;
    List<Tax> getStateList() ;
    int getOrderNumbers();
    Order orderAuthorize(Order orderCheck);
    List<Product> productList();
    Order getSingleOrder(int orderNumber);
    void removalOfOrder(Order order);

    Order validateOrderAndDate(int orderNumber, LocalDate date);
}
