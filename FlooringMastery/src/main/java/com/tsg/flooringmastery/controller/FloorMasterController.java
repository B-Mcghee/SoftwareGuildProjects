package com.tsg.flooringmastery.controller;

import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dao.exceptions.ProductPersistenceException;
import com.tsg.flooringmastery.dao.exceptions.TaxPersistenceException;
import com.tsg.flooringmastery.dto.Order;
import com.tsg.flooringmastery.dto.Product;
import com.tsg.flooringmastery.dto.Tax;
import com.tsg.flooringmastery.io.FloorMasterView;
import com.tsg.flooringmastery.service.FloorMasterService;

import java.time.LocalDate;
import java.util.List;

public class FloorMasterController {
    private FloorMasterView view;
    private FloorMasterService service;


    public FloorMasterController(FloorMasterView view, FloorMasterService service) {
        this.view = view;
        this.service = service;

    }

    public void run()  {
        boolean isProduction = false;
        try{
        service.loadAllSystems();
            isProduction = service.isProductionMode();
    }catch (OrderPersistenceException | TaxPersistenceException | ProductPersistenceException e) {
        view.displayBanner();
    }


        List<Order> allOrder =  service.getOrderList();
        List<Tax> stateList = service.getStateList();
        List<Product> productList = service.productList();


        boolean flag = true;
        while (flag) {


                int menuSelection = view.menuSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders(allOrder);
                        break;
                    case 2:
                        addOrder(allOrder, productList, stateList);
                        break;
                    case 3:
                        editOrder(productList, stateList);
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        try {
                            saveOrder(isProduction);

                        } catch (TaxPersistenceException | ProductPersistenceException | OrderPersistenceException e) {
                            view.displayBanner();
                        }
                        break;
                    case 6:
                try{
                        saveOrder();
                }catch (TaxPersistenceException | ProductPersistenceException | OrderPersistenceException e) {
                view.displayBanner();
            }
                    flag = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }


        }
    }

    private void unknownCommand() {
        view.unknownCommand();
    }


    //////////////////
    //Order Display methods
    ////////////////////
    private void displayOrders(List<Order> orders){
        view.displayBanner();
        LocalDate date = view.dateDisplayRetrieval();

        view.displayOrders(orders,date);


    }


    //////////////////
    //Add Order Methods
    ////////////////////
    private void addOrder(List<Order> orderList, List<Product> productList, List<Tax>stateList) {

        Order order = view.getNewOrder(stateList,productList, orderList);

        service.orderAuthorize(order);

        view.addOrderSuccess(order);


    }


    //////////////////
    //Edit Order Methods
    ////////////////////
    private void editOrder( List<Product> productList, List<Tax> stateList){
        LocalDate date = view.dateEdit();
        int orderNumber = view.orderNumberRetrieval(date);

        Order editOrder = service.validateOrderAndDate(orderNumber, date);

//        service.getSingleOrder(editOrder);

        Order order = view.editOrderDisplay(editOrder, productList,
                stateList);
        service.editOrder(order);

    }

    //////////////////
    //Remove Order methods
    ////////////////////
    private void removeOrder(){
        view.removeBanner();

        LocalDate retrievedDate = view.dateRemoval();
        int orderNumber = view.orderNumberRetrieval(retrievedDate);
        Order order = service.validateOrderAndDate(orderNumber, retrievedDate);
        order = view.validOrder(order, retrievedDate, orderNumber);
        service.removalOfOrder(order);


//        Order order = service.getSingleOrder(orderNumber);
//        view.displaySingleOrder(order);
//        boolean confirmDelete = view.deleteOrder();
//        service.removalOfOrder(retrievedDate, orderNumber, confirmDelete);



    }


    //////////////////
    //Save Order Methods
    ////////////////////
    private void saveOrder()throws OrderPersistenceException,TaxPersistenceException, ProductPersistenceException{
        boolean save = view.saveWork();
        if (save){
            service.saveWork();
        }
    }
    private void saveOrder(boolean production) throws OrderPersistenceException,TaxPersistenceException, ProductPersistenceException {
        if (!production) {
            service.saveWork();
        }else{
            view.permissions();
        }
    }


}
