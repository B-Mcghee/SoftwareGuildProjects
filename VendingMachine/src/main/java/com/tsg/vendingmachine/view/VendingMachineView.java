package com.tsg.vendingmachine.view;

import com.tsg.vendingmachine.dto.ChangePurse;
import com.tsg.vendingmachine.dto.Item;

import java.math.BigDecimal;

public class VendingMachineView {


    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }


    public BigDecimal moneyInsert() {
        io.print("");
        io.print("$~~$$$~~ INSERT MONEY ~~$$$~$");


           return io.readBigDecimal("Enter the amount of money you wish " +
                   "to put in, or press 0 to exit");




    }

    public String vendingSelection() {
        io.print("*~~**~~ SELECTION MENU ~~**~~*");

        return io.readString("You can now enter your selection or press 0 to exit");
    }


    public void changePurseReturn(ChangePurse changePurse, BigDecimal change, BigDecimal startMoney) {
        io.print("");
        if(change.compareTo(BigDecimal.ZERO) <= 0){
            io.print("No change returned");
        }else if (change.compareTo(startMoney) == 0) {
            io.print("You do not have enough money. Your $" + startMoney + " has been returned in:");
        } else {
            io.print("Your change of $" + change + " has been returned in:");
        }
        if (changePurse.getNumQuarters() != 0) {
            io.print(changePurse.getNumQuarters() + " quarters");
        }
        if (changePurse.getNumDimes() != 0) {
            io.print(changePurse.getNumDimes() + " dimes");
        }
        if (changePurse.getNumNickels() != 0) {
            io.print(changePurse.getNumNickels() + " nickels");
        }
        if (changePurse.getNumPennies() != 0) {
            io.print(changePurse.getNumPennies() + " pennies");
        }


    }


//


    public void goodByeMessage() {
        io.print("");
        io.print("%%% ~Have a Nice Day~ %%%");
    }

    public void returnMoneyMessage(String money) {
        io.print("Your $" + money + " has been returned");

    }
    public void errorBanner() {
        io.print("You have problems with either writing onto or reading from your text file. ");
    }

    public void credit(BigDecimal money) {
        io.print("");
        io.print(" Credit $" + money);
    }
    public void welcomeMessage() {
        io.print("");
        io.print("$$$|$$$ ~ WELCOME TO THE WORLD OF SPORTS VENDING ~ $$$|$$$ ");
        io.print("");
    }


    public void vendingDisplay(Item item) {
        if (item.getQuantityOfItems() == 0) {
            io.print(item.getVendingSlot() + " " + item.getName() + " - SOLD OUT");

        } else {
            io.print(item.getVendingSlot() + " " + item.getName() + " - $" + item.getCost());
        }
    }

    public void errorBanner(Item item, BigDecimal money) {
        io.print("");
        if(item == null){
            io.print("There is currently no item matching that vending slot selection. Your $"
                    + money + " has been " +
                    "returned");
        }else if (item.getCost().compareTo(money)>0){
            io.print("You currently do not have enough funds to complete that purchase. Your $"
                    + money +" has been " +
                    "returned");
        }else {
            io.print(item.getName() + " is currently sold out. Sorry");
        }
    }

    public boolean insertMoreMoney() {
        io.print("");
        String keepGoing = io.readString("Would you like to insert " +
                "more money? Y/N?");
        if (keepGoing.equalsIgnoreCase("n")) {
            return false;
        } else {
            return true;
        }

    }

    public void itemPurchase(Item item) {
        io.print("Enjoy your " + item.getName() + "!!");
    }


}
