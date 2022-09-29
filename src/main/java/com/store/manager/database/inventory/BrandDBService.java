package com.store.manager.database.inventory;
import com.store.manager.database.DatabaseConnector;
import com.store.manager.database.inventory.queries.BrandQueries;
import com.store.manager.inventory.Brand;
import com.store.manager.inventory.Category;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Objects;

public class BrandDBService implements BrandQueries {

    // Database service class that enables interactivity with Inventory DB
    // Table: brand

    // Class members
    private static Connection connection;

    // Class methods
    // CRUD service operations for Class Brand

    // C- Create
    public static void insertBrand(@NotNull Brand brand) throws SQLException {
        // Process that inserts a Brand into the DB

        if (!brand.nonNullFields()) {
            System.out.println(
                    "Brand has null fields!" +
                    "\nUpdate fields before inserting into DB");
            return;
        }
        String insertStatement = BrandQueries.insertStatement();

        connection = DatabaseConnector.getConnection();
        insertPrepareStatement(brand, insertStatement);
        connection.close();
    }

    private static void insertPrepareStatement(@NotNull Brand brand, String insertStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, brand.getBrandName());
            preparedStatement.setString(2, brand.getBrandCode());
            preparedStatement.setString(3, brand.getBrandCategory().getCategoryName());
            preparedStatement.setString(4, brand.getBrandStatus());
            preparedStatement.setString(5, brand.getBrandDesc());

            int rowsAffected = preparedStatement.executeUpdate();
            if (validInsertOperation(rowsAffected, brand))
                connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static boolean validInsertOperation(int rowsAffected, @NotNull Brand brand)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully inserted Brand \n'" + brand.getBrandName() + "' into DB");
            return true;
        } else {
            System.out.println(
                    "Unsuccessful insert for Brand \n'" + brand.getBrandName() + "' into DB");
            return false;
        }
    }


    // R - Retrieve
    public static Brand retrieveBrand(String brandName) throws SQLException {
        // Process that retrieves a Brand from the DB

        Brand brandDB = new Brand();
        String selectStatement = BrandQueries.retrieveStatement();

        connection = DatabaseConnector.getConnection();
        retrieveByNamePrepareStatement(brandName, brandDB, selectStatement);
        connection.close();
        return brandDB;
    }

    private static void retrieveByNamePrepareStatement(String brandName, Brand brandDB, String selectStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(selectStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, brandName);
            ResultSet resultSet = preparedStatement.executeQuery();
            assignQueryValues(brandDB, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void assignQueryValues(Brand brandDB, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            connection.commit();

            brandDB.setBrandName(resultSet.getString(1));
            brandDB.setBrandCode(resultSet.getString(2));
            brandDB.setBrandCategory(retrieveBrandCategory(resultSet.getString(3)));
            if ((resultSet.getString(4).equals("active"))) {
                brandDB.activateBrand();
            } else {
                brandDB.deactivateBrand();
            }
            brandDB.setBrandDesc(resultSet.getString(5));
        }
        resultSet.close();
    }

    @NotNull
    private static Category retrieveBrandCategory(String brandCategoryName) throws SQLException {
        return CategoryDBService
                .retrieveCategory(brandCategoryName);
    }


    // U - Update
    public static void updateBrand(Brand brand) throws SQLException {
        // Process that updates a Category in the DB

        connection = DatabaseConnector.getConnection();
        String updateStatement = BrandQueries.updateStatement();
        updatePrepareStatement(brand, updateStatement);
        connection.close();
    }

    private static void updatePrepareStatement(Brand brand, String updateStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(updateStatement)) {

            preparedStatement.setString(6, brand.getBrandName());
            preparedStatement.setString(1, brand.getBrandName());
            preparedStatement.setString(2, brand.getBrandCode());
            preparedStatement.setString(3, brand.getBrandCategory().getCategoryName());
            preparedStatement.setString(4, brand.getBrandStatus());
            preparedStatement.setString(5, brand.getBrandDesc());

            int rowsAffected = preparedStatement.executeUpdate();
            getUpdateBrandStatus(brand, rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getUpdateBrandStatus(Brand brand, int rowsAffected)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully updated Brand \n'" + brand.getBrandName() + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful update for Brand \n'" + brand.getBrandName() + "' in DB");
        }
    }


    // D - Delete
    public static void deleteBrand(String brandName) throws SQLException {
        // Process to delete a Brand from the DB

        connection = DatabaseConnector.getConnection();
        String deleteStatement = BrandQueries.deleteStatement();
        deletePrepareStatement(brandName, deleteStatement);
        connection.close();
    }

    private static void deletePrepareStatement(String brandName, String deleteStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setString(1, brandName);
            int rowsAffected = preparedStatement.executeUpdate();
            getDeleteBrandStatus(brandName, rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getDeleteBrandStatus(String brandName, int rowsAffected)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully deleted Brand \n'" + brandName + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful delete for Category \n'" + brandName + "' in DB");
        }
    }

    private static void rollBackTransaction() {
        // Method to roll back a transaction

        try {
            System.err.println("Transaction is being rolled back...");
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
