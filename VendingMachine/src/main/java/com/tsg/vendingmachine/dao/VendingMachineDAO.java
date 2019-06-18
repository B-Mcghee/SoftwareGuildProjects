package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;

import java.util.List;

public interface VendingMachineDAO {
    void loadAllItems() throws VendingPersistenceException;

    void saveAllChanges() throws VendingPersistenceException;

    Item addItem(Item item);

    List<Item> getAllItems();

    Item getAnItem(String itemId);

    void updateAnItem(String uniqueId, Item item);

    void removeAnItem(String uniqueId);

}
