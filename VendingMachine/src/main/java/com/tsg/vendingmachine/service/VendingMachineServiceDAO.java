package com.tsg.vendingmachine.service;

import com.tsg.vendingmachine.dao.VendingPersistenceException;
import com.tsg.vendingmachine.dto.ChangePurse;
import com.tsg.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceDAO {

    void loadMachine() throws VendingPersistenceException;

    List<Item> getAllItemsInMachine();

    Item getOneItem(String vendingSlot);

    ChangePurse purchaseItem(String vendingSlot, BigDecimal money)
            throws VendingInsufficientFundsException,
            VendingNoItemInventoryException,
            VendingPersistenceException;


}
