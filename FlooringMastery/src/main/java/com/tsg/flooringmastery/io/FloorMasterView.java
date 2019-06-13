package com.tsg.flooringmastery.io;

import com.tsg.flooringmastery.dto.Order;
import com.tsg.flooringmastery.dto.Product;
import com.tsg.flooringmastery.dto.Tax;
import com.tsg.flooringmastery.ui.UserIO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FloorMasterView {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";


    private UserIO io;

    public FloorMasterView(UserIO io) {
        this.io = io;
    }

    public int menuSelection() {

        io.print(ANSI_RED + "<<<< Flooring Program >>>>");
        io.print("============================");
        io.print(ANSI_CYAN + "1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");
        io.print("");

        return io.readInt(ANSI_RED + "Please make a selection", 1, 6);
    }


    //////////////////
    //Order Display methods
    ////////////////////
    public void displayBanner() {
        io.print("===== Display Orders =====");
        io.print("");
    }

    public LocalDate dateDisplayRetrieval() {
        boolean flag = true;
        LocalDate dt = null;
        while (flag) {
            try {

                String dateString = io.readString("Please enter the date(MM/dd/yyyy) of the orders you would like displayed.." +
                        ".");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                dt = LocalDate.parse(dateString, dateFormat);

                flag = false;
            } catch (DateTimeParseException e) {
                io.print("That was an invalid date, please try again");
            }


        }
        return dt;
    }

    public void displayOrders(List<Order> displayOrder, LocalDate date) {
        if (displayOrder.size() != 0) {
            for (Order aOrder : displayOrder) {
                if (aOrder.getOrderDate().equals(date)) {
                    displaySingleOrder(aOrder);

                    io.print("");
                }
            }
        } else {
            io.print("There is no orders matching that date");
            io.print("");
            io.print("===== End =====");
        }
    }


    //////////////////
    //Add Order Methods
    ////////////////////
    public void addBanner() {
        io.print("===== Add an Order =====");
        io.print("");

    }


    public Order dateAddRetrieval() {
        boolean flag = true;
        Order newOrder = null;
        while (flag) {
            try {

                newOrder = new Order();
                String dateString = io.readString("Please enter the date(MM/dd/yyyy) of the order you would like to " +
                        "add" +
                        ".." +
                        ".");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate dt = LocalDate.parse(dateString, dateFormat);

                if (!dt.isAfter(LocalDate.now())) {
                    this.invalidDate();
                    continue;
                } else {
                    newOrder.setOrderDate(dt);
                }
                flag = false;
            } catch (DateTimeParseException e) {
                io.print("That is not a valid date. Please try again");
            }
        }
        return newOrder;
    }

    public void invalidDate() {
        io.print("");
        io.print("You must be a list a date in the future for your order to be processed");
    }

    public String nameAddRetrieval() {
        String name = "";
        do {
            name = io.readString("Enter the name you wish to add for the order");
        } while (name.isEmpty());
        return name;
    }


    public Order stateValidation(List<Tax> allStates, Order order) {
        int counter = 0;

        for (Tax stateNames : allStates) {
            counter++;
            io.print(counter + ") " + stateNames.getStateName());
        }

        int stateChoice = io.readInt("Which state would you like to purchase an order from?", 1, allStates.size());
        Tax tax = allStates.get(stateChoice - 1);
        order.setState(tax.getStateAbbrevation());
        order.setTaxRate(tax.getTaxRate());
        return order;
    }


    public Order productValidation(List<Product> products, Order order) {

        int counter = 0;
        for (Product item : products) {
            counter++;
            io.print(counter + ") " + item.getProductType());
            io.print("SqFoot Cost: $" + item.getCostPerSquareFoot());
            io.print("SqFoot Labor Cost: $" + item.getLaborCostPerSquareFoot());
            io.print("");
        }
        int selection = io.readInt("Enter the number associated with the item you'd like to purchase", 1,
                products.size());

        Product product = products.get(selection - 1);
        order.setProductType(product.getProductType());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        return order;

    }


    public void unknownCommand() {
        io.print("That is an unknown command. Please enter a valid selection.");
    }

    public BigDecimal getArea(String product) {

        BigDecimal area =
                io.readBigDecimal("Enter the square footage of " + product.toLowerCase() + " you would like. Minimum" +
                                " order size" +
                                " is 100 sq ft " + ANSI_RESET,
                        new BigDecimal("100"));

        return area;
    }


    public void displaySingleOrder(Order aOrder) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        io.print(ANSI_RED + "Customer: " + ANSI_CYAN + aOrder.getCustomerName());
        io.print(ANSI_RED + "Order Date: " + ANSI_CYAN + aOrder.getOrderDate().format(format));
        io.print(ANSI_RED + "OrderNumber: " + ANSI_CYAN + "#" + aOrder.getOrderNumber());
        io.print(ANSI_RED + "Product type: " + ANSI_CYAN + aOrder.getProductType());
        io.print(ANSI_RED + "Area needed: " + ANSI_CYAN + aOrder.getArea() + "sq ft");
        io.print(ANSI_RED + "Material Cost: " + ANSI_CYAN + "$" + aOrder.getMaterialCost());
        io.print(ANSI_RED + "Labor Cost: " + ANSI_CYAN + "$" + aOrder.getLaborCost());
        io.print(ANSI_RED + "Added tax: " + ANSI_CYAN + "$" + aOrder.getTax());
        io.print(ANSI_RED + "Total Price: " + ANSI_CYAN + "$" + aOrder.getTotal());

        io.print("");
    }


    public void addOrderSuccess(Order order) {
        io.print("");
        if (order == null) {
            io.print("Order was not added, come visit us again");
        } else {

            io.print("Your Order number will be #" + order.getOrderNumber() + ".");
            io.print("Have a nice Day!");
            io.print("===== THANK YOU =====");
        }
    }


    public void removeBanner() {
        io.print("==== REMOVE AN ORDER =====");
    }

    public LocalDate dateRemoval() {
        boolean flag = true;
        LocalDate date = null;
        while (flag) {
            try {

                String dateString = io.readString("Please enter the date(MM/dd/yyyy) of the order you would like to remove");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = LocalDate.parse(dateString, dateFormat);
                flag = false;
            } catch (DateTimeParseException e) {
                io.print("That was an invalid date, please try again");
            }
        }
        return date;

    }

    public int orderNumberRetrieval(LocalDate date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return io.readInt("What it is the order Number associated with " + date.format(format));
    }

    public int invalidOrderNumber(LocalDate date, int number) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return io.readInt("The #" + number + " is not associated with " + date.format(format));
    }

    public Order validOrder(Order order, LocalDate date, int number) {
        if (order != null) {
            this.displaySingleOrder(order);
            String response = io.readString("Are you sure you would like to delete this order? Y/N?");
            if (response.equalsIgnoreCase("y")) {
                return order;
            }
        }
        invalidOrderNumber(date, number);
        return order;
    }


//    public boolean deleteOrder() {
//        boolean flag = false;
//        String response = io.readString("Are you sure you would like to delete this order?");
//        if (response.equalsIgnoreCase("y")) {
//            flag = true;
//            validOrder(flag);
//        }
//        return flag;
//    }

    public Order editOrderDisplay( Order order, List<Product> productList, List<Tax> allStates) {


        Order copyOrder = this.copyOrder(order);



        if (order != null) {
            boolean flag = true;
            while (flag) {
                editDisplay(order);

                int number =
                        io.readInt("Please select the number associated with the row you would like to edit" + ANSI_RESET, 0
                                , 4);
                switch (number) {
                    case 0:

                        flag = false;
                        break;
                    case 1:
                        String name = editCustomerName(order);
                        order.setCustomerName(name);
                        break;
                    case 2:
                        Tax state = editState(order, allStates);

                        order.setState(state.getStateAbbrevation());
                        order.setTaxRate(state.getTaxRate());
                        order.recalcArea();
                        break;
                    case 3:
                        Product product = editProduct(order, productList);
                        order.setProductType(product.getProductType());
                        order.recalcArea(product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot());
                        break;
                    case 4:
                        BigDecimal area = editArea(order);
                        order.recalcArea(area);
                        break;
                    default:
                        io.print("invalid command");
                }


            }
            return editSave(order,copyOrder );
        }
        io.print("The number given does not associate with the given date");
        return order;
    }

    private Order copyOrder(Order order) {
        Order copyOrder = new Order();
        copyOrder.setOrderDate(order.getOrderDate());
        copyOrder.setOrderNumber(order.getOrderNumber());
        copyOrder.setCustomerName(order.getCustomerName());
        copyOrder.setState(order.getState());
        copyOrder.setTaxRate(order.getTaxRate());
        copyOrder.setProductType(order.getProductType());
        copyOrder.setCostPerSquareFoot(order.getCostPerSquareFoot());
        copyOrder.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
        copyOrder.setArea(order.getArea());
        copyOrder.setMaterialCost(order.getMaterialCost());
        copyOrder.setLaborCost(order.getLaborCost());
        copyOrder.setTax(order.getTax());
        copyOrder.setTotal(order.getTotal());
        return copyOrder;


    }

    public Tax editState(Order aOrder, List<Tax> allStates) {
        int counter = 0;


            io.print("What state abbreviation would you to: (" + aOrder.getState() + ")");
        for (Tax stateNames : allStates) {
            counter++;
            io.print(counter + ") " + stateNames.getStateName());
        }


            Integer stateChoice = io.readInt("Which state would you like to purchase an order from?", 1,
                    allStates.size());
            Tax tax = new Tax();
            if (stateChoice != null) {
                tax = allStates.get(stateChoice - 1);
            }else{
                tax.setTaxRate(aOrder.getTaxRate());
                tax.setStateAbbrevation(aOrder.getState());
            }

        return tax;

    }

    public Product editProduct(Order order , List<Product> productList) {
        int counter = 0;
        for (Product item : productList) {
            counter++;
            io.print(counter + ") " + item.getProductType());
        }
        Integer selection = io.readInt("Enter the number associated with the item you'd like to purchase", 1,
                productList.size());
        Product product = new Product();
        if (selection != null) {
            product = productList.get(selection - 1);
        }else{
            product.setLaborCosterPerSquareFoot(order.getLaborCostPerSquareFoot());
            product.setCostPerSquareFoot(order.getCostPerSquareFoot());
            product.setProductType(order.getProductType());
        }
        return product;
    }

    private BigDecimal editArea(Order order) {
        BigDecimal area = io.readBigDecimal("what would you like the area of your order (" + order.getArea() + " sq " +
                "ft) changed " +
                "to");
        if (area!=null) {
            return area;
        }else{
            return order.getArea();
        }
    }

    public void editDisplay(Order aOrder) {
        io.print(BACKGROUND_YELLOW + ANSI_RED + " 1) Customer:" + ANSI_CYAN + " " + aOrder.getCustomerName() + ANSI_RESET);
        io.print(ANSI_RED + "Order Date: " + ANSI_CYAN + aOrder.getOrderDate());
        if (aOrder.getOrderNumber() != 0) {
            io.print(ANSI_RED + "OrderNumber: " + ANSI_CYAN + "#" + aOrder.getOrderNumber());
        }
        io.print(BACKGROUND_YELLOW + ANSI_RED + " 2) State:" + ANSI_CYAN + " " + aOrder.getState() + ANSI_RESET);
        io.print(BACKGROUND_YELLOW + ANSI_RED + " 3) Product type:" + ANSI_CYAN + " " + aOrder.getProductType() + ANSI_RESET);
        io.print(BACKGROUND_YELLOW + ANSI_RED + " 4) Area needed:" + ANSI_CYAN + " " + aOrder.getArea() +
                " sq ft" + ANSI_RESET);
        io.print(ANSI_RED + "Material Cost: " + ANSI_CYAN + "$" + aOrder.getMaterialCost());
        io.print(ANSI_RED + "Labor Cost: " + ANSI_CYAN + "$" + aOrder.getLaborCost());
        io.print(ANSI_RED + "Added tax: " + ANSI_CYAN + "$" + aOrder.getTax());
        io.print(ANSI_RED + "Total Price: " + ANSI_CYAN + "$" + aOrder.getTotal());
        io.print("");
        io.print(BACKGROUND_YELLOW + ANSI_RED + " 0) Exit" + ANSI_RESET);

        io.print("");
        io.print(ANSI_RED + "The editing process only allows updating to the rows highlighted in " +
                "yellow");
    }


    public LocalDate dateEdit() {
        boolean flag = true;
        LocalDate date = null;
        while (flag) {
            try {

                String dateString = io.readString("Please enter the date(MM/dd/yyyy) of the order you would like to " +
                        "edit");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = LocalDate.parse(dateString, dateFormat);
                flag = false;
            } catch (DateTimeParseException e) {
                io.print("That was an invalid date, please try again");
            }
        }
        return date;
    }

    public String editCustomerName(Order order) {
        String newName = io.readString("What would you like the name changed to: (" + order.getCustomerName() + ")");
        if (!newName.isEmpty()) {
            return newName;
        } else {
            return order.getCustomerName();
        }
    }

    //
    public Order editSave(Order order, Order copyOrder) {

        String request = io.readString("Would you like to save this order? (Y/N)");
        if (request.equalsIgnoreCase("Y")) {
            return order;

        }
        return copyOrder;
    }
    public Order requestSave(Order order) {

        String request = io.readString("Would you like to save this order? (Y/N)");
        if (request.equalsIgnoreCase("Y")) {
            return order;

        }
        return null;
    }


    public Order getNewOrder(List<Tax> stateList, List<Product> productList, List<Order> orderList) {
        this.addBanner();
        int max = 0;
        for (Order aOrder: orderList) {
            if (max < aOrder.getOrderNumber() ){
                max = aOrder.getOrderNumber();
            }
        }
        Order order = this.dateAddRetrieval();
        order.setCustomerName(this.nameAddRetrieval());

        order = this.stateValidation(stateList, order);
        order = this.productValidation(productList, order);
        order.recalcArea(this.getArea(order.getProductType()));
        order.setOrderNumber(max + 1);



        this.displaySingleOrder(order);

        return requestSave(order);

    }

    public void permissions() {
        io.print("");
        io.print("You do not have authorization to be able to write to files while in training mode");
        io.print("");
    }

    public boolean saveWork() {
        String request = io.readString("Would you like to save your work before exiting? (Y/N)");
        if (request.equalsIgnoreCase("Y")) {
            return true;

        }
        return false;
    }
}
