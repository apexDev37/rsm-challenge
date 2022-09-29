package com.store.manager.database.inventory.queries;

public interface VendorQueries {

    // Interface that contains the SQL queries for the Vendor class
    // Implemented by the VendorDBService class

    static String insertStatement() {
        return "INSERT INTO inventory.vendor" +
                "(business_name," +
                "vendor_code," +
                "vendor_status," +
                "account_number," +
                "reorder_delay," +
                "supply_delay," +
                "contacts)" +
                "VALUES(" +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?)";
    }

    static String retrieveStatement() {
        return "SELECT " +
                "business_name," +
                "vendor_code," +
                "vendor_status," +
                "account_number," +
                "reorder_delay," +
                "supply_delay," +
                "contacts " +
                "FROM inventory.vendor " +
                "WHERE business_name = ?";
    }

    static String updateStatement() {
        return "UPDATE inventory.vendor " +
                "SET " +
                "business_name = ?, " +
                "vendor_code = ?, " +
                "vendor_status = ?, " +
                "account_number = ?," +
                "reorder_delay = ?," +
                "supply_delay = ?," +
                "contacts = ? " +
                "WHERE business_name = ?";
    }

    static String deleteStatement() {
        return "DELETE " +
                "FROM inventory.vendor " +
                "WHERE business_name = ?";
    }
}
