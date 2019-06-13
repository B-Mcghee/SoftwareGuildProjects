package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.OrderPersistenceException;
import com.tsg.flooringmastery.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderImpl implements OrderDAO {
    private final String DELIMITER = ",";
    private String ORDER_FILE;
    private Map<Integer, Order> orderMap;

    public OrderImpl() {
        this.orderMap = new HashMap<>();
    }

    public OrderImpl(String ORDER_FILE) {
        this.ORDER_FILE = ORDER_FILE;
        this.orderMap = new HashMap<>();
    }

    public boolean isTrainingMode() throws OrderPersistenceException{
        File file = new File("Data/");
        if (!file.exists()){
            return false;
        }else{

            Scanner scanner;
            String filePath = file + File.separator + "Mode.txt";
                    try {
                        scanner = new Scanner(new BufferedReader(new FileReader(filePath)));
                    }catch (IOException e){
                        throw new OrderPersistenceException("It is in production mode");
                    }
                    if (scanner.nextLine().contains("Trai")){
                        return true;
                    }else{
                        return false;
                    }

        }
    }


    @Override
    public Order addOrder(Order order){
        int number = order.getOrderNumber();
        Order newOrder = orderMap.put(number, order);
        return newOrder;
    }

    @Override
    public Order getOrder(int orderNumber) {

        return orderMap.get(orderNumber);
    }

    @Override
    public List<Order> getAllOrders(){

        List<Order> allOrders = new ArrayList<>(orderMap.values());
        return allOrders;
    }


    @Override
    public void editOrder(int orderNumber, Order order)  {

        orderMap.replace(orderNumber, order);


    }

    @Override
    public Order removeOrder(int orderNumber) {


        Order order = orderMap.remove(orderNumber);

            return order;
    }



    @Override
    public void saveOrder() throws OrderPersistenceException {
        boolean isTraining = this.isTrainingMode();
        if (!isTraining) {
            List<Order> allOrders = new ArrayList<>(orderMap.values());
            List<LocalDate> getAllDates = new ArrayList<>();
            File dir = new File("Orders/");
            for (File files: dir.listFiles()){
                files.delete();
            }
            for (Order anOrder : allOrders) {
                if (getAllDates.contains(anOrder.getOrderDate())) {
                    continue;
                }
                getAllDates.add(anOrder.getOrderDate());
            }
            PrintWriter print;
            try {

                File file = new File("Orders/");

                for (LocalDate aDate : getAllDates) {
                    ORDER_FILE = makeFileName(aDate);
                    String filepath = file + File.separator + ORDER_FILE;

                    print = new PrintWriter(new FileWriter(filepath));
                    print.println("order number" + DELIMITER + "customer name" + DELIMITER + "state" + DELIMITER + "taxrate" + DELIMITER
                            + "product type" + DELIMITER + "area" + DELIMITER + "cost per sq ft" + DELIMITER + "labor per " +
                            "sq ft" + DELIMITER + "material cost" + DELIMITER + "labor cost" + DELIMITER + "tax" + DELIMITER + "total");
                    for (Order current : allOrders) {
                        if (aDate.equals(current.getOrderDate())) {
                            String txtLine = marshall(current);
                            print.println(txtLine);
                        }
                    }
                    print.flush();
                    print.close();
                }
            } catch (IOException e) {
                throw new OrderPersistenceException("File does exists");
            }
        }

            
        
    }

    @Override
    public void readOrder() throws OrderPersistenceException {
        Scanner textParser;
        File file;


        file = new File("Orders/");
        File[] fileArray = file.listFiles();
        for (File aFile : fileArray) {

            try {
                textParser = new Scanner(new BufferedReader(new FileReader(aFile)));
            } catch (FileNotFoundException e) {

                throw new OrderPersistenceException("File is not found");
            }

            textParser.nextLine();
            LocalDate date = captureDate(aFile);

            while (textParser.hasNextLine()) {
                String line = textParser.nextLine();


                Order order = unmarshall(line, date);
                orderMap.put(order.getOrderNumber(), order);

            }
        }
    }

    private String makeFileName(LocalDate date) throws OrderPersistenceException {
        String oldDateString = date.toString();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate;
        try {
            sDate = sdf.parse(oldDateString);
        } catch (ParseException e) {
            throw new OrderPersistenceException("Parsing is allowed");
        }

        sdf.applyPattern("MMddyyyy");
        String newFormat = sdf.format(sDate);
        String fileName = "Orders_" + newFormat + ".txt";
        return fileName;
    }

    private String marshall(Order order) {
        String emptyString = "";


        emptyString += order.getOrderNumber() + DELIMITER;
        emptyString += order.getCustomerName() + DELIMITER;
        emptyString += order.getState() + DELIMITER;
        emptyString += order.getTaxRate() + DELIMITER;
        emptyString += order.getProductType() + DELIMITER;
        emptyString += order.getArea() + DELIMITER;
        emptyString += order.getCostPerSquareFoot() + DELIMITER;
        emptyString += order.getLaborCostPerSquareFoot() + DELIMITER;
        emptyString += order.getMaterialCost() + DELIMITER;
        emptyString += order.getLaborCost() + DELIMITER;
        emptyString += order.getTax() + DELIMITER;
        emptyString += order.getTotal() + DELIMITER;

        return emptyString;

    }


    private LocalDate captureDate(File file) {
        String date = file.toString().substring(14, 22);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate dt = LocalDate.parse(date, formatter);
        return dt;
    }

    private Order unmarshall(String line, LocalDate date) {
        Order order = new Order();
        order.setOrderDate(date);
        String[] lineArray = line.split(DELIMITER);
        int num = Integer.parseInt(lineArray[0]);
        order.setOrderNumber(num);
        order.setCustomerName(lineArray[1]);
        order.setState(lineArray[2]);
        BigDecimal taxRate = new BigDecimal(lineArray[3]);
        order.setTaxRate(taxRate);
        order.setProductType(lineArray[4]);
        BigDecimal area = new BigDecimal(lineArray[5]);
        order.setArea(area);
        BigDecimal costPerSqFt = new BigDecimal(lineArray[6]);
        order.setCostPerSquareFoot(costPerSqFt);
        BigDecimal laborPerSqFt = new BigDecimal(lineArray[7]);
        order.setLaborCostPerSquareFoot(laborPerSqFt);
        BigDecimal materialCost = new BigDecimal(lineArray[8]);
        order.setMaterialCost(materialCost);
        BigDecimal laborCost = new BigDecimal(lineArray[9]);
        order.setLaborCost(laborCost);
        BigDecimal tax = new BigDecimal(lineArray[10]);
        order.setTax(tax);
        BigDecimal total = new BigDecimal(lineArray[11]);
        order.setTotal(total);

        return order;
    }


}
