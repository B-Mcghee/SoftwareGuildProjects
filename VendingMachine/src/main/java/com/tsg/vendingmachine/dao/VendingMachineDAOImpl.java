package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineDAOImpl implements VendingMachineDAO {
    private String TEXT_FILE = "vending.txt";
    private final String DELIMITER = "::";
    private Map<String, Item> vendingItems;

    public VendingMachineDAOImpl() {
        vendingItems = new TreeMap<>();
        this.TEXT_FILE = TEXT_FILE;
    }

    public VendingMachineDAOImpl(String file) {
        vendingItems = new TreeMap<>();
        this.TEXT_FILE = file;
    }






    @Override
    public Item addItem(Item item){
        String slot = item.getVendingSlot();
        Item newItem = vendingItems.put(slot, item);

        return newItem;

    }

    @Override
    public List<Item> getAllItems(){

        return new ArrayList<>(vendingItems.values());
    }

    @Override
    public Item getAnItem(String itemId){

        return vendingItems.get(itemId);

    }

    @Override
    public void updateAnItem(String uniqueId, Item item) {


        vendingItems.replace(uniqueId, item);

    }

    @Override
    public void removeAnItem(String uniqueId) {

        vendingItems.remove(uniqueId);


    }

    //PERSISTENCE

    @Override
    public void loadAllItems() throws VendingPersistenceException{
        Scanner textParser;

        try {

            textParser = new Scanner(new BufferedReader(new FileReader(TEXT_FILE)));
        } catch (FileNotFoundException ex) {
            throw new VendingPersistenceException(ex);
        }

        while (textParser.hasNextLine()) {
            String line = textParser.nextLine();
            Item readFile = this.unmarshallItems(line);
            vendingItems.put(readFile.getVendingSlot(), readFile);

        }

        textParser.close();

    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException{
        PrintWriter printer;

        try {
            printer = new PrintWriter(new FileWriter(TEXT_FILE));
        } catch (IOException ex) {
            throw new VendingPersistenceException(ex);
        }
        for (Item eachItem : this.getAllItems()) {
            String ItemString = this.marshallItems(eachItem);

            printer.println(ItemString);
        }

        printer.flush();
        printer.close();

    }



    private String marshallItems(Item aItem) {
        String itemString = " ";

        itemString = aItem.getVendingSlot() + this.DELIMITER;
        itemString += aItem.getName() + this.DELIMITER;
        itemString += aItem.getCost() + this.DELIMITER;
        itemString += aItem.getQuantityOfItems() + this.DELIMITER;


        return itemString;

    }

    private Item unmarshallItems(String fileLine) {

        Item emptyLine = new Item();

        String[] lineBits = fileLine.split(this.DELIMITER);

        emptyLine.setVendingSlot(lineBits[0]);
        emptyLine.setName(lineBits[1]);
        BigDecimal cost = new BigDecimal(lineBits[2]);
        emptyLine.setCost(cost);
        int quantity = Integer.parseInt(lineBits[3]);
        emptyLine.setQuantityOfItems(quantity);


        return emptyLine;
    }
}
