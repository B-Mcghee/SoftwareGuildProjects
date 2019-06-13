package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.TaxPersistenceException;
import com.tsg.flooringmastery.dto.Tax;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class TaxDAOImpl implements TaxDAO {

    private final String DELIMITER = ",";
    File file = new File("Data/");
    private String TAX_FILE = file + File.separator + "Taxes.txt";
    private Map<String, Tax> taxMap;

    public TaxDAOImpl() {
        taxMap = new TreeMap<>();

    }

    public TaxDAOImpl(String newFile) {
        taxMap = new TreeMap<>();
        this.TAX_FILE = newFile;
    }

    @Override
    public Tax addTax(Tax tax) {

        Tax newTax = taxMap.put(tax.getStateAbbrevation(), tax);
        
        return newTax;
    }

    @Override
    public Tax getTax(String state)  {
        
        return taxMap.get(state);
    }

    @Override
    public List<Tax> getAllTaxes(){

        return new ArrayList<>(taxMap.values());
    }

    @Override
    public void updateTax(String stateAbbreviation, Tax tax) {
        
        taxMap.replace(stateAbbreviation, tax);
        
    }

    @Override
    public Tax removeTax(String stateAbbrevation) {
        Tax removeTax = taxMap.remove(stateAbbrevation);
        return removeTax;
    }

    @Override
    public void saveTax() throws TaxPersistenceException {
        List<Tax> allTaxes = getAllTaxes();

        PrintWriter printer;
        try {
            printer = new PrintWriter(new FileWriter(TAX_FILE));
        } catch (IOException e) {
            throw new TaxPersistenceException("The file is there");
        }
        printer.println("State" + DELIMITER + "TaxRate");
        for (Tax line: allTaxes ){
            String lineString = marshallTax(line);
            printer.println(lineString);
        }
        printer.flush();
        printer.close();

    }

    @Override
    public void readTax() throws TaxPersistenceException {
        Scanner textParser;

        try {
            textParser = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {

            throw new TaxPersistenceException("File is not found");
        }

        textParser.nextLine();
        while (textParser.hasNextLine()) {
            String line = textParser.nextLine();

            Tax tax = unmarshallTax(line);
            taxMap.put(tax.getStateAbbrevation(), tax);

        }
    }


    private String marshallTax(Tax tax) {
        String emptyString =
                tax.getStateAbbrevation() + DELIMITER +
                        tax.getStateName() + DELIMITER +
                        tax.getTaxRate() + DELIMITER;

        return emptyString;
    }

    private Tax unmarshallTax(String line) {
        Tax emptyTax = new Tax();
        String[] linePieces = line.split(DELIMITER);
        emptyTax.setStateAbbrevation(linePieces[0]);
        emptyTax.setStateName(linePieces[1]);
        emptyTax.setTaxRate(new BigDecimal(linePieces[2]));

        return emptyTax;
    }
}
