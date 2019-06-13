package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.ProductPersistenceException;
import com.tsg.flooringmastery.dto.Product;

import java.util.List;

public interface ProductDAO {
    Product addProduct(Product product);
    Product getProduct(String productType) ;
    List<Product>getAllProducts() ;
    void editProduct(String productType, Product product);
    Product removeProduct(String productType);
    void saveProduct() throws ProductPersistenceException;
    void readProduct() throws ProductPersistenceException;



}
