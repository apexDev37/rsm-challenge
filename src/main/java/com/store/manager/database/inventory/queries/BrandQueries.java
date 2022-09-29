package com.store.manager.database.inventory.queries;

public interface BrandQueries {

    // Interface that contains the SQL queries for the Brand class
    // Implemented by the BrandDBService class

    static String insertStatement() {
        return "INSERT INTO inventory.brand" +
                "(brand_name," +
                "brand_code," +
                "category," +
                "brand_status," +
                "brand_desc)" +
                "VALUES(" +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?)";
    }

    static String retrieveStatement() {
        return "SELECT " +
                "brand_name, " +
                "brand_code, " +
                "category, " +
                "brand_status, " +
                "brand_desc " +
                "FROM inventory.brand " +
                "WHERE brand_name = ?";
    }

    static String updateStatement() {
        return "UPDATE inventory.brand " +
                "SET " +
                "brand_name = ?, " +
                "brand_code = ?, " +
                "category = ?, " +
                "brand_status = ?, " +
                "brand_desc = ? " +
                "WHERE brand_name = ?";
    }

    static String deleteStatement() {
        return "DELETE " +
                "FROM inventory.brand " +
                "WHERE brand_name = ?";
    }
}
