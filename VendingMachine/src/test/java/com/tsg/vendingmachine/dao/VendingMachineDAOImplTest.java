package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;

public class VendingMachineDAOImplTest {

    private VendingMachineDAO testDao;

    @Before
    public void setUp() throws Exception {
        testDao = new VendingMachineDAOImpl("blank.txt");
        FileWriter write = new FileWriter("blank.txt");
        FileReader read = new FileReader("blank.txt");

    }

    @Test
    public void testingAddItem() throws Exception {
        Item item = new Item("A1", "Snickers", new BigDecimal("0.75"), 5);
        testDao.addItem(item);


        Item retrievedItem = testDao.getAnItem("A1");
        Assert.assertEquals("Checking if both items equal each other", item, retrievedItem);
        Assert.assertEquals("Checking slot", item.getVendingSlot(), retrievedItem.getVendingSlot());
        Assert.assertEquals("Checking name", item.getName(), retrievedItem.getName());
        Assert.assertEquals("Checking cost", item.getCost(), retrievedItem.getCost());
        Assert.assertEquals("Checking quantity", item.getQuantityOfItems(), retrievedItem.getQuantityOfItems());


    }

    @Test
    public void testGetAllItems() throws Exception {
        Item firstItem = new Item("A1", "Snickers", new BigDecimal("0.75"), 5);
        testDao.addItem(firstItem);

        Item secondItem = new Item("B1", "Sour Patches", new BigDecimal("1.25"), 5);
        testDao.addItem(secondItem);

        testDao.addItem(firstItem);
        testDao.addItem(secondItem);

        List<Item> allItems = testDao.getAllItems();

        Assert.assertNotNull("The list of items must not null ", allItems);
        Assert.assertEquals("List of items.", 2, allItems.size());

        Assert.assertTrue("The list of Items should include  Snickers", testDao.getAllItems().contains(firstItem));
        Assert.assertTrue("The list of Items should include  sour patches", testDao.getAllItems().contains(secondItem));
    }

    @Test
    public void testUpdateItem() throws Exception {
        Item firstItem = new Item("A1", "Snickers", new BigDecimal("0.75"), 5);
        testDao.addItem(firstItem);

        List<Item> allItems = testDao.getAllItems();
        Assert.assertTrue("All items should contain Snickers", allItems.contains(firstItem));
        Item secondItem = new Item("B1", "Sour Patches", new BigDecimal("1.25"), 5);


        testDao.updateAnItem(firstItem.getVendingSlot(), secondItem);

        allItems = testDao.getAllItems();

        Assert.assertEquals("all items should only contain 1 item", 1, allItems.size());
        Assert.assertTrue("All items should contain Sour Patches", allItems.contains(secondItem));

        Item retrievedItem = testDao.getAnItem("B1");

        Assert.assertEquals("All items should not equal first item", secondItem, retrievedItem);


    }

    @Test
    public void testRemoveItem() throws Exception {
        Item firstItem = new Item("A1", "Snickers", new BigDecimal("0.75"), 5);
        testDao.addItem(firstItem);

        List<Item> allItems = testDao.getAllItems();
        Assert.assertTrue("All items should contain Snickers", allItems.contains(firstItem));

        Item secondItem = new Item("B1", "Sour Patches", new BigDecimal("1.25"), 5);
        testDao.addItem(secondItem);

        testDao.removeAnItem("A1");
        Item retrievedItem = testDao.getAnItem("B1");

        allItems = testDao.getAllItems();
        Assert.assertEquals("All items should be cleared",1,  allItems.size());
        Assert.assertEquals("Checking if both items equal each other", secondItem, retrievedItem);

        testDao.removeAnItem("B1");
        allItems = testDao.getAllItems();
        Assert.assertTrue("All items should be cleared", allItems.isEmpty());

        retrievedItem = testDao.getAnItem("B1");
        Assert.assertNull("No item should have been retrieved " , retrievedItem);

        retrievedItem = testDao.getAnItem("A1");
        Assert.assertNull("No item should have been retrieved " , retrievedItem);










    }
}