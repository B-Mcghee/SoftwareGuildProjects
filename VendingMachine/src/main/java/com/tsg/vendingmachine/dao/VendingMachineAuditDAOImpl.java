package com.tsg.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VendingMachineAuditDAOImpl implements VendingMachineAuditDAO {

    public static final String AUDIT_FILE = "audit.txt";


    @Override
    public void writeAuditEntry(String entry) throws VendingPersistenceException{
        PrintWriter out;

        try {
            out = new PrintWriter( new FileWriter(AUDIT_FILE, true));
        }catch (IOException e){
            throw new VendingPersistenceException("Could not persist audit information", e);

        }



        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formatDate = now.format(formatter);


        out.println(formatDate + " : " + entry);
        out.flush();
    }
}
