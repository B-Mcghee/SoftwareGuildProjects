package com.tsg.flooringmastery.dao;

import com.tsg.flooringmastery.dao.exceptions.ProductPersistenceException;
import com.tsg.flooringmastery.dto.Product;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ProductDAOImplTest implements ProductDAO{
    private final String DELIMITER = ",";
    File file = new File("Data/");
    private String PRODUCT_FILE = file + File.separator + "testProducts.txt";

    private Map<String, Product> productMap;

    public ProductDAOImplTest() {
        this.productMap = new HashMap<>();
    }

    public ProductDAOImplTest(String productFile) {
        this.PRODUCT_FILE = productFile;
        this.productMap = new HashMap<>();
    }

    @Override
    public Product addProduct(Product product){

        Product newProduct = productMap.put(product.getProductType(), product);

        return newProduct;
    }

    @Override
    public Product getProduct(String productType){

        return productMap.get(productType);

    }

    @Override
    public List<Product> getAllProducts()  {

        return new ArrayList<>(productMap.values());
    }

    @Override
    public void editProduct(String productType, Product product) {
        productMap.replace(productType, product);
    }

    @Override
    public Product removeProduct(String productType){

        return productMap.remove(productType);


    }

    @Override
    public void saveProduct() throws ProductPersistenceException {
        List<Product> allProducts = getAllProducts();
        PrintWriter printer;

        try {
            printer = new PrintWriter(new FileWriter(PRODUCT_FILE));
        } catch (IOException e) {
            throw new ProductPersistenceException("The file is there");
        }
        printer.println("ProductType" + DELIMITER
                + "CostPerSquareFoot" + DELIMITER
                + "LaborCostPerSquareFoot" +DELIMITER);
        for (Product aProduct : allProducts) {
            String line = marshallProduct(aProduct);

            printer.println(line);
        }
        printer.close();
        printer.flush();

    }

    @Override
    public void readProduct() throws ProductPersistenceException {
        Scanner textParser;

        try {
            textParser = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {

            throw new ProductPersistenceException("File is not found");
        }

        textParser.nextLine();
        while (textParser.hasNextLine()) {
            String line = textParser.nextLine();

            Product product = unmarshallProduct(line);
            productMap.put(product.getProductType(), product);

        }
    }



    private Product unmarshallProduct(String line) {


        String[] linePieces = line.split(DELIMITER);

        Product emptyProduct = new Product();
        emptyProduct.setProductType(linePieces[0]);
        emptyProduct.setCostPerSquareFoot(new BigDecimal(linePieces[1]));
        emptyProduct.setLaborCosterPerSquareFoot(new BigDecimal(linePieces[2]));
        return emptyProduct;
    }

    private String marshallProduct(Product product) {

        String emptyString = product.getProductType() + DELIMITER +
                product.getCostPerSquareFoot() + DELIMITER +
                product.getLaborCostPerSquareFoot() + DELIMITER;

        return emptyString;

    }
}