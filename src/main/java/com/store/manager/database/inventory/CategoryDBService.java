package com.store.manager.database.inventory;
import com.store.manager.database.DatabaseConnector;
import com.store.manager.database.inventory.queries.CategoryQueries;
import com.store.manager.inventory.Category;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CategoryDBService implements CategoryQueries {

    // Database service class that enables interactivity with Inventory DB
    // Table: category

    // Class members
    private static Connection connection;

    // Class methods
    // CRUD service operations for Class Category

    // C- Create
    public static void insertCategory(@NotNull Category category) throws SQLException {
        // Process that inserts a Category into the DB

        if (!category.nonNullFields()) {
            System.out.println(
                    "Category has null fields!" +
                    "\nUpdate fields before inserting into DB");
            return;
        }
        String insertStatement = CategoryQueries.insertStatement();

        connection = DatabaseConnector.getConnection();
        insertPrepareStatement(category, insertStatement);
        connection.close();
    }

    private static void insertPrepareStatement(@NotNull Category category, String insertStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertStatement)) {

            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getCategoryCode());
            preparedStatement.setInt(3, category.getCategoryLocationNo());
            preparedStatement.setString(4, category.getCategoryDesc());
            int rowsAffected = preparedStatement.executeUpdate();
            getInsertCategoryStatus(rowsAffected, category);

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getInsertCategoryStatus(int rowsAffected, @NotNull Category category)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully inserted Category \n'" + category.getCategoryName() + "' into DB");
        } else {
            System.out.println(
                    "Unsuccessful insert for Category \n'" + category.getCategoryName() + "' into DB");
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

    // R- Retrieve
    public static Category retrieveCategory(String categoryName) throws SQLException {
        // Process that retrieves a Category from the DB

        Category categoryDB = new Category();
        String selectStatement = CategoryQueries.retrieveStatement();

        connection = DatabaseConnector.getConnection();
        retrieveByNamePrepareStatement(categoryName, categoryDB, selectStatement);
        connection.close();
        return categoryDB;
    }

    private static void retrieveByNamePrepareStatement(
            String categoryName,
            Category categoryDB,
            String selectStatement) {

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(selectStatement)) {

            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                categoryDB.setCategoryName(resultSet.getString(1));
                categoryDB.setCategoryCode(resultSet.getString(2));
                categoryDB.setCategoryLocationNo(resultSet.getInt(3));
                categoryDB.setCategoryDesc(resultSet.getString(4));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    // U - Update
    public static void updateCategory(Category category) throws SQLException {
        // Process that updates a Category in the DB

        connection = DatabaseConnector.getConnection();
        String updateStatement = CategoryQueries.updateStatement();
        updatePrepareStatement(category, updateStatement);
        connection.close();
    }

    private static void updatePrepareStatement(Category category, String updateStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(updateStatement) ) {

            preparedStatement.setString(5, category.getCategoryName());
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getCategoryCode());
            preparedStatement.setInt(3, category.getCategoryLocationNo());
            preparedStatement.setString(4, category.getCategoryDesc());

            int rowsAffected = preparedStatement.executeUpdate();
            getUpdateCategoryStatus(category, rowsAffected);


        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getUpdateCategoryStatus(Category category, int rowsAffected)
            throws SQLException {

        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully updated Category \n'" + category.getCategoryName() + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful update for Category \n'" + category.getCategoryName() + "' in DB");
        }
    }

    // D - Delete
    public static void deleteCategory(String categoryName) throws SQLException {
        // Process to delete a Category from the DB

        String deleteStatement = CategoryQueries.deleteStatement();
        connection = DatabaseConnector.getConnection();
        deletePrepareStatement(categoryName, deleteStatement);
        connection.close();
    }

    private static void deletePrepareStatement(String categoryName, String deleteStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setString(1, categoryName);
            int rowsAffected = preparedStatement.executeUpdate();
            getDeleteCategoryStatus(categoryName, rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getDeleteCategoryStatus(String categoryName, int rowsAffected)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully deleted Category \n'" + categoryName + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful delete for Category \n'" + categoryName + "' in DB");
        }
    }
}
