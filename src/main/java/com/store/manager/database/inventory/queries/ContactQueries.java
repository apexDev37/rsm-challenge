package com.store.manager.database.inventory.queries;

public interface ContactQueries {

    // Interface that contains the SQL queries for the Contact class
    // Implemented by the ContactDBService class

    static String insertStatement() {
        return "INSERT INTO inventory.contact" +
                "(vendor," +
                "first_name," +
                "last_name," +
                "mobile," +
                "email," +
                "street_address," +
                "city," +
                "country)" +
                "VALUES(" +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?, " +
                "?)";
    }

    static String retrieveStatement() {
        return "SELECT  " +
                "vendor, " +
                "first_name, " +
                "last_name, " +
                "mobile, " +
                "email, " +
                "street_address, " +
                "city, " +
                "country " +
                "FROM inventory.contact " +
                "WHERE vendor = ?";
    }

    static String updateStatement() {
        return "UPDATE inventory.contact " +
                "SET " +
                "vendor = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "mobile = ?," +
                "email = ?," +
                "street_address = ?," +
                "city = ?, " +
                "country = ? " +
                "WHERE vendor = ?";
    }

    static String deleteStatement() {
        return "DELETE " +
                "FROM inventory.contact " +
                "WHERE mobile = ?";
    }
}
