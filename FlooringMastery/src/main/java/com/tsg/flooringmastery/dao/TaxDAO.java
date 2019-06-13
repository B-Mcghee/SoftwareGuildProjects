package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.TaxPersistenceException;
import com.tsg.flooringmastery.dto.Tax;

import java.util.List;

public interface TaxDAO {
    Tax addTax(Tax tax) ;
    Tax getTax(String stateAbbreviation);
    List<Tax> getAllTaxes();
    void updateTax(String stateAbbreviation, Tax tax);
    Tax removeTax(String stateAbbrevation);
    void saveTax() throws TaxPersistenceException;
    void readTax() throws TaxPersistenceException;

}
