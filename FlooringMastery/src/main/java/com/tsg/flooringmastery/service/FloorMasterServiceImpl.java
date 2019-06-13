package com.tsg.flooringmastery.service;

import com.tsg.flooringmastery.dao.OrderDAO;
import com.tsg.flooringmastery.dao.ProductDAO;
import com.tsg.flooringmastery.dao.TaxDAO;
import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dao.exceptions.ProductPersistenceException;
import com.tsg.flooringmastery.dao.exceptions.TaxPersistenceException;
import com.tsg.flooringmastery.dto.Order;
import com.tsg.flooringmastery.dto.Product;
import com.tsg.flooringmastery.dto.Tax;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class FloorMasterServiceImpl implements FloorMasterService {
    private OrderDAO orderDao;
    private ProductDAO productDao;
    private TaxDAO taxDAO;


    public FloorMasterServiceImpl(OrderDAO orderDao, ProductDAO productDao, TaxDAO taxDAO) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDAO = taxDAO;

    }


    @Override
    public void loadAllSystems() throws OrderPersistenceException, TaxPersistenceException, ProductPersistenceException{

        orderDao.readOrder();
        productDao.readProduct();
        taxDAO.readTax();
    }

    @Override
    public void editOrder( Order order){
        if (order != null) {
            orderDao.editOrder(order.getOrderNumber(), order);
        }
    }

    @Override
    public List<Order> displayOrder(LocalDate date) {

        List<Order> allOrders = orderDao.getAllOrders();
        allOrders = allOrders.stream().filter(d -> d.getOrderDate().equals(date)).collect(Collectors.toList());
        return allOrders;


    }


    @Override
    public int getOrderNumbers(){
        int max = 0;

        List<Order> orderNumbers = orderDao.getAllOrders();
        for (Order iD: orderNumbers){
            if (iD.getOrderNumber() > max){
                max = iD.getOrderNumber();
            }
        }
        return max + 1;
    }
    @Override
    public Order orderAuthorize(Order order) {

            if (order != null) {

                orderDao.addOrder(order);

            }
            return order;
    }


    @Override
    public void removalOfOrder(Order order)  {
            if (order != null) {
                orderDao.removeOrder(order.getOrderNumber());

            }
    }

    @Override
    public Order validateOrderAndDate(int orderNumber, LocalDate date) {
        for (Order iD: orderDao.getAllOrders()) {
            if (iD.getOrderNumber() == orderNumber && iD.getOrderDate().compareTo(date) == 0) {
                return iD;

            }
        }
            return null;

    }


    @Override
    public List<Product> productList(){
        return productDao.getAllProducts();
    }

    @Override
    public Order getSingleOrder(int number) {
        return orderDao.getOrder(number);
    }

    @Override
    public boolean isProductionMode() throws OrderPersistenceException {

            return orderDao.isTrainingMode();


    }

    @Override
    public List<Order> getOrderList() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<Tax> getStateList()  {

        return taxDAO.getAllTaxes();
    }
    @Override
    public void saveWork() throws OrderPersistenceException,TaxPersistenceException, ProductPersistenceException {
        orderDao.saveOrder();
        productDao.saveProduct();
        taxDAO.saveTax();
    }





}
