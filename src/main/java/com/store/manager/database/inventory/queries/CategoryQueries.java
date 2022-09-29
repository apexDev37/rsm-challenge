package com.store.manager.database.inventory.queries;

public interface CategoryQueries {

    // Interface that contains the SQL queries for the Category class
    // Implemented by the CategoryDBService class

    static String insertStatement() {
        return "INSERT INTO inventory.category" +
                "(category_name," +
                "category_code," +
                "location_no," +
                "category_desc)" +
                "VALUES(" +
                "?, " +
                "?, " +
                "?, " +
                "?)";
    }

    static String retrieveStatement() {
        return "SELECT " +
                "category_name, " +
                "category_code, " +
                "location_no, " +
                "category_desc " +
                "FROM inventory.category " +
                "WHERE category_name = ?";
    }

    static String updateStatement() {
        return "UPDATE inventory.category " +
                "SET " +
                "category_name = ?, " +
                "category_code = ?, " +
                "location_no = ?, " +
                "category_desc = ? " +
                "WHERE category_name = ?";
    }

    static String deleteStatement() {
        return "DELETE " +
                "FROM inventory.category " +
                "WHERE category_name = ?";
    }
}
