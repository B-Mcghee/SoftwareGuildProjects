package com.tsg.vendingmachine.dao;

public interface VendingMachineAuditDAO {

    public void writeAuditEntry(String entry)throws VendingPersistenceException;


}
