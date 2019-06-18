package com.tsg.vendingmachine.service;

import com.tsg.vendingmachine.dao.VendingMachineAuditDAO;
import com.tsg.vendingmachine.dao.VendingMachineDAO;
import com.tsg.vendingmachine.dao.VendingPersistenceException;
import com.tsg.vendingmachine.dto.ChangePurse;
import com.tsg.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceImpl implements VendingMachineServiceDAO {
    private VendingMachineDAO itemStorage;
    private VendingMachineAuditDAO auditDAO;

    public VendingMachineServiceImpl(VendingMachineDAO itemStorage, VendingMachineAuditDAO auditDAO) {
        this.itemStorage = itemStorage;
        this.auditDAO = auditDAO;
    }

    @Override
    public void loadMachine() throws VendingPersistenceException {
        itemStorage.loadAllItems();



    }

    @Override
    public List<Item> getAllItemsInMachine() {


        List<Item> allItems = itemStorage.getAllItems();

        return allItems;
    }

    @Override
    public Item getOneItem(String vendingSlot) {
        List<Item> allItems = itemStorage.getAllItems();

        for (Item aItem : allItems) {
            if (aItem.getVendingSlot().equalsIgnoreCase(vendingSlot)) {
                return aItem;
            }
        }
        return null;

    }

    @Override
    public ChangePurse purchaseItem(String vendingSlot, BigDecimal money) throws VendingPersistenceException,
            VendingNoItemInventoryException,VendingInsufficientFundsException {

        this.loadMachine();
        Item item = itemStorage.getAnItem(vendingSlot);
        if (item == null) {
            throw new VendingNoItemInventoryException("There is no current item matching to that vending slot");
        }
        BigDecimal itemPrice = item.getCost();
        ChangePurse changePurse = new ChangePurse();
        int quantity = item.getQuantityOfItems();


        if (item.getQuantityOfItems() <= 0) {
            auditDAO.writeAuditEntry("Attempted to buy " + item.getName() + " but it is sold out");

            throw new VendingNoItemInventoryException("This item is sold out, please try another selection");


        } else if (itemPrice.compareTo(money) == 0) {

            item.setQuantityOfItems(quantity - 1);
            itemStorage.saveAllChanges();
            return changePurse;
        } else if (itemPrice.compareTo(money) == -1) {

            auditDAO.writeAuditEntry("Purchased " + item.getName());

            changePurse.getCoins(money, itemPrice, changePurse);


            item.setQuantityOfItems(quantity - 1);
            itemStorage.updateAnItem(item.getVendingSlot(), item);
            itemStorage.saveAllChanges();

            return changePurse;

        } else if (itemPrice.compareTo(money) == 1) {


            auditDAO.writeAuditEntry("Attempted to buy " + item.getName() + ", but did not have enough money");


            throw new VendingInsufficientFundsException("Funds did not meet the listed price. Please insert more");
        }
        itemStorage.saveAllChanges();
        return changePurse;


    }
}
