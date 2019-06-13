package com.tsg.flooringmastery.service;

import com.tsg.flooringmastery.dao.*;
import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dto.Order;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FloorMasterServiceImplTest {

    private FloorMasterService service;

    public FloorMasterServiceImplTest( ) {
        OrderDAO dao = new OrderImplTest();
        ProductDAO productDAO = new ProductDAOImplTest();
        TaxDAO tax = new TaxDAOImplTest();

        service = new FloorMasterServiceImpl(dao, productDAO, tax);
    }

    @Test
    public void orderAuthorizeTest() throws Exception {
        Order onlyOrder = new Order();
        onlyOrder.setOrderDate(LocalDate.of(2020, 10, 20));
        onlyOrder.setOrderNumber(30);
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



        onlyOrder = service.orderAuthorize(onlyOrder);
        Assert.assertNotNull(onlyOrder);

        Order retrieved = service.getSingleOrder(onlyOrder.getOrderNumber());
        Assert.assertEquals("Retrieved should equal to the third order object", onlyOrder, retrieved);


    }

    @Test
    public void testGetOrderNumbers() throws Exception{
        Order thirdOrder = new Order();
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

        Order onlyOrder = new Order();
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

        service.orderAuthorize(thirdOrder);
        Order retrieved = service.getSingleOrder(thirdOrder.getOrderNumber());
        service.orderAuthorize(onlyOrder);

        int number = service.getOrderNumbers();

        Assert.assertEquals("The order number should be one more than the highest number", 100, number);
        Assert.assertEquals("Retrieved should equal to the third order object", thirdOrder, retrieved);
    }

    @Test
    public void testValidateOrderAndDate() throws OrderPersistenceException {
        Order thirdOrder = new Order();
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

        Order onlyOrder = new Order();
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


        Order order = service.validateOrderAndDate(5, LocalDate.of(2019,5, 9));
        Assert.assertNull("this should return back null for no such record", order);

        Order retrieved = service.validateOrderAndDate(50, LocalDate.of(2020,10, 20));
        Assert.assertEquals("These two should equal after validating the correcdt order", onlyOrder, retrieved);

    }

    @Test
    public void testOrderMethods() {

        Order onlyOrder = new Order();
        onlyOrder.setOrderDate(LocalDate.of(2020, 10, 20));
        onlyOrder.setOrderNumber(50);
        onlyOrder.setCustomerName("Brandon Smith");
        onlyOrder.setState("CA");
        onlyOrder.setProductType("Linoleum");
        onlyOrder.setArea(new BigDecimal("200"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("5.00"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("7.50"));
        onlyOrder.setTaxRate(new BigDecimal("25.00"));


        onlyOrder.setLaborCost(onlyOrder.calcLaborCost());
        onlyOrder.setCostPerSquareFoot(onlyOrder.calcMaterialCost());
        onlyOrder.setTax(onlyOrder.calcTax());
        onlyOrder.setTotal(onlyOrder.calcTotalCost());

        Assert.assertEquals("The calculated take method is calucates the tax for order",
                new BigDecimal(
                "625.00"), onlyOrder.calcTax());

        onlyOrder.setMaterialCost(new BigDecimal("100.00"));
        Assert.assertEquals("recalculates the tax after changing material cost the tax for " +
                        "order",
                new BigDecimal(
                        "400.00"), onlyOrder.calcTax());

        onlyOrder.setLaborCost(new BigDecimal("100.00"));
        Assert.assertEquals("recalculates the tax after changing labor the tax for " +
                        "order",
                new BigDecimal(
                        "50.00"), onlyOrder.calcTax());







    }
}
