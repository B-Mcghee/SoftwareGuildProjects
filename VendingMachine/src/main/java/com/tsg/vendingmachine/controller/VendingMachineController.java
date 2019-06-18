package com.tsg.vendingmachine.controller;


import com.tsg.vendingmachine.dao.VendingPersistenceException;
import com.tsg.vendingmachine.dto.ChangePurse;
import com.tsg.vendingmachine.dto.Item;
import com.tsg.vendingmachine.service.VendingInsufficientFundsException;
import com.tsg.vendingmachine.service.VendingMachineServiceDAO;
import com.tsg.vendingmachine.service.VendingNoItemInventoryException;
import com.tsg.vendingmachine.view.VendingMachineView;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceDAO serviceDao;


    public VendingMachineController(VendingMachineView view, VendingMachineServiceDAO serviceDao) {
        this.view = view;
        this.serviceDao = serviceDao;

    }

    public void run() {

        try {
            loadMachine();
            boolean flag = true;
            while (flag) {
                vendingDisplay();

                BigDecimal moneyEntered = view.moneyInsert();
                if (moneyEntered.compareTo(BigDecimal.ZERO) <= 0) {
                    flag = false;
                    view.goodByeMessage();
                    continue;
                }
                credit(moneyEntered);
                String selection = view.vendingSelection().toUpperCase();
                if (!selection.contains("0")) {

                    Item item =serviceDao.getOneItem(selection);

                    try {

                        ChangePurse changePurse;

                        changePurse = serviceDao.purchaseItem(selection, moneyEntered);
                        view.itemPurchase(item);

                        BigDecimal change = changePurse.getTotal(changePurse);
                        view.changePurseReturn(changePurse,change,moneyEntered);
                    } catch (VendingInsufficientFundsException | VendingNoItemInventoryException e) {
                        view.errorBanner(item, moneyEntered);
                    }

                } else {
                    view.returnMoneyMessage(moneyEntered.toString());
                    view.goodByeMessage();
                    flag = false;
                    continue;
                }

                flag = view.insertMoreMoney();
            }
        } catch (VendingPersistenceException ex) {
            view.errorBanner();
        }

    }


    public void loadMachine() {
        try {
            serviceDao.loadMachine();
        } catch (VendingPersistenceException e) {
            view.errorBanner();
        }
    }


    public void credit(BigDecimal money) {
        view.credit(money);
    }


    public void vendingDisplay() {
        view.welcomeMessage();
        List<Item> allItems = serviceDao.getAllItemsInMachine();
        for (Item aItem : allItems) {
            view.vendingDisplay(aItem);
        }

    }
}