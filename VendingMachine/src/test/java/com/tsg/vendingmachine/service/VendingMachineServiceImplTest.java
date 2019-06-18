package com.tsg.vendingmachine.service;

import com.tsg.vendingmachine.dao.*;
import com.tsg.vendingmachine.dto.ChangePurse;
import com.tsg.vendingmachine.dto.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineServiceImplTest {
    VendingMachineServiceImpl testService;
    VendingMachineDAO testDao;
    VendingMachineAuditDAO auditTest;

    public VendingMachineServiceImplTest() {

    }


    @Before

    public void setUp() throws Exception {
        Item item = new Item("A1", "skittles", new BigDecimal(0.75), 5);
        this.testDao = new VendingMachineDAOStubImpl(item);
        this.auditTest = new VendingMachineAuditDAOImplTest();
        this.testService = new VendingMachineServiceImpl(testDao, auditTest);

    }





    @Test
    public void testNotEnoughhMoney() {
        Item itemToPurchase = testDao.getAnItem("A1");

        Assert.assertNotNull("It should not returned null",itemToPurchase);

        BigDecimal money = new BigDecimal("0.60");
        try {
            testService.purchaseItem(itemToPurchase.getVendingSlot(), money);
            Assert.fail("fails it purchase is successful");
        } catch (VendingNoItemInventoryException | VendingPersistenceException e) {
            Assert.fail("Should fail because these exceptions were caught");
        } catch (VendingInsufficientFundsException e) {
            Assert.assertEquals("Funds did not meet the listed price. Please insert more", e.getMessage());
        }

    }

    @Test
    public void testEnoughMoney() {
        Item itemToPurchase = testDao.getAnItem("A1");

        BigDecimal money = new BigDecimal("0.75");
        ChangePurse changePurse = null;
        try {
            changePurse = testService.purchaseItem(itemToPurchase.getVendingSlot(), money);
        }catch (VendingPersistenceException|VendingNoItemInventoryException|VendingInsufficientFundsException e){
            Assert.fail("Should fail because these exceptions were caught");
        }
        Assert.assertTrue("there should be zero change left over", money.compareTo(itemToPurchase.getCost()) == 0);
        ChangePurse returnChange = changePurse.getCoins(money, itemToPurchase.getCost(), changePurse);
        Assert.assertEquals("Quarters should equal 0", 0, returnChange.getNumQuarters());
        Assert.assertEquals("Dimes should equal 0", 0, returnChange.getNumDimes());
        Assert.assertEquals("Nickels should equal 0", 0, returnChange.getNumNickels());
        Assert.assertEquals("Pennies should equal 0", 0, returnChange.getNumPennies());

    }

    @Test
    public void testMoreMoney() {
        Item itemToPurchase = testDao.getAnItem("A1");

        BigDecimal money = new BigDecimal("1.99");
        ChangePurse changePurse = null;
        try {
            changePurse = testService.purchaseItem(itemToPurchase.getVendingSlot(), money);
        }catch (VendingPersistenceException|VendingNoItemInventoryException|VendingInsufficientFundsException e){
            Assert.fail("Should fail because these exceptions were caught");
        }
        Assert.assertTrue("there should be zero change left over", money.compareTo(itemToPurchase.getCost()) == 1);
        ChangePurse returnChange = changePurse.getCoins(money, itemToPurchase.getCost(), changePurse);
        Assert.assertEquals("Quarters should equal 0", 4, returnChange.getNumQuarters());
        Assert.assertEquals("Dimes should equal 0", 2, returnChange.getNumDimes());
        Assert.assertEquals("Nickels should equal 0", 0, returnChange.getNumNickels());
        Assert.assertEquals("Pennies should equal 0", 4, returnChange.getNumPennies());
    }

    //        BigDecimal difference = money.min(cost);
//        ChangePurse changePurse = new ChangePurse();
//
//            Assert.assertTrue("money should be below cost and not able to make the purchase",
//                    money.compareTo(cost) < 0 );
//        ChangePurse returnChange = changePurse.getCoins(money, cost, changePurse);
//        Assert.assertEquals("Quarters should equal 0", 0, returnChange.getNumQuarters());
//
////            throw new VendingInsufficientFundsException("You do not have enough money to make this purchase");
//
//        money = new BigDecimal("0.75");
//        Assert.assertTrue("there should be zero change left over", money.compareTo(cost) == 0);
//        returnChange = changePurse.getCoins(money, cost, changePurse);
//        Assert.assertEquals("Quarters should equal 0", 0, returnChange.getNumQuarters());
//        Assert.assertEquals("Dimes should equal 0", 0, returnChange.getNumDimes());
//        Assert.assertEquals("Nickels should equal 0", 0, returnChange.getNumNickels());
//        Assert.assertEquals("Pennies should equal 0", 0, returnChange.getNumPennies());
//
//        money = new BigDecimal("1.99");
//        Assert.assertTrue("there should be zero change left over", money.compareTo(cost) > 0);
//
//        returnChange = changePurse.getCoins(money, cost, changePurse);
//        Assert.assertEquals("Quarters should equal 4", 4, returnChange.getNumQuarters());
//        Assert.assertEquals("Dimes should equal 2", 2, returnChange.getNumDimes());
//        Assert.assertEquals("Nickels should equal 0", 0, returnChange.getNumNickels());
//        Assert.assertEquals("Pennies should equal 4", 4, returnChange.getNumPennies());







}