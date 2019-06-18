package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineDAOStubImpl implements VendingMachineDAO{
    private Item testItem;
    private VendingMachineAuditDAO testAudit;

    public VendingMachineDAOStubImpl(Item testItem ) {
        this.testItem = testItem;
        this.testAudit = new VendingMachineAuditDAOImplTest();
    }







    @Override
    public void loadAllItems() throws VendingPersistenceException {

    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException {

    }

    @Override
    public Item addItem(Item item){
      return null;

    }

    @Override
    public List<Item> getAllItems(){
        List<Item> allItems = new ArrayList<>();
        allItems.add(testItem);
        return allItems;
    }

    @Override
    public Item getAnItem(String itemId){

        if(testItem.getVendingSlot().equals(itemId)){
            return this.testItem;
        } else{
            return null;
        }

    }

    @Override
    public void updateAnItem(String uniqueId, Item item) {
//        List<Item> allItems = new ArrayList<>();
//        allItems.add(testItem);
//
//
//        allItems.

    }

    @Override
    public void removeAnItem(String uniqueId) {




    }
}