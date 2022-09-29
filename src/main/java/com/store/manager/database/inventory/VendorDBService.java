package com.store.manager.database.inventory;
import com.store.manager.database.DatabaseConnector;
import com.store.manager.database.inventory.queries.VendorQueries;
import com.store.manager.inventory.Contact;
import com.store.manager.inventory.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class VendorDBService implements VendorQueries {

    // Database service class that enables interactivity with Inventory DB
    // Table: vendor

    // Class members
    private static Connection connection;

    // Class methods
    // CRUD service operations for Class Vendor

    // C- Create
    public static void insertVendor(Vendor vendor) throws SQLException {
        // Process that inserts a Vendor into the DB
        // and Vendor contacts into the Contact relation

        if (!vendor.nonNullFields()) {
            System.out.println(
                    "Vendor has null fields!" +
                            "\nUpdate fields before inserting into DB");
            return;
        }
        connection = DatabaseConnector.getConnection();
        String insertStatement = VendorQueries.insertStatement();
        insertPrepareStatement(vendor, insertStatement);
        ContactDBService.insertContact(vendor, vendor.getContactsList());
        connection.close();
    }

    private static void insertPrepareStatement(Vendor vendor, String insertStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, vendor.getBusinessName());
            preparedStatement.setString(2, vendor.getVendorCode());
            preparedStatement.setString(3, vendor.getVendorStatus());
            preparedStatement.setString(4, vendor.getVendorAccNo());
            preparedStatement.setInt(5, vendor.getReorderDelay());
            preparedStatement.setInt(6, vendor.getSupplyDelay());
            preparedStatement.setInt(7, vendor.getNumberOfVendorContacts());

            int rowsAffected = preparedStatement.executeUpdate();
            if (validInsertOperation(rowsAffected, vendor))
                connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static boolean validInsertOperation(int rowsAffected, Vendor vendor)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully inserted Vendor \n'" + vendor.getBusinessName() + "' into DB");
            return true;
        }

        System.out.println("Unsuccessful insert for Vendor \n'" + vendor.getBusinessName() + "' into DB");
        return false;
    }

    // R - Retrieve
    public static Vendor retrieveVendor(String vendorBusinessName) throws SQLException {
        // Process that retrieves a Vendor from the DB

        Vendor vendorDB = new Vendor();
        String selectStatement = VendorQueries.retrieveStatement();

        connection = DatabaseConnector.getConnection();
        retrievePrepareStatement(vendorBusinessName, vendorDB, selectStatement);
        retrieveVendorContacts(vendorBusinessName, vendorDB);
        connection.close();
        return vendorDB;
    }

    private static void retrieveVendorContacts(String vendorBusinessName, Vendor vendorDB) throws SQLException {
        List<Contact> contactListFromDB = ContactDBService.retrieveContact(vendorBusinessName);
        contactListFromDB.forEach(vendorDB::addVendorContact);
    }

    private static void retrievePrepareStatement(
            String vendorBusinessName,
            Vendor vendorDB,
            String selectStatement) throws SQLException {

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(selectStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, vendorBusinessName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                connection.commit();

                vendorDB.setBusinessName(resultSet.getString(1));
                vendorDB.setVendorCode(resultSet.getString(2));
                if (resultSet.getString(3).equals("active")) vendorDB.activateVendor();
                vendorDB.setVendorAccNo(resultSet.getString(4));
                vendorDB.setReorderDelay(resultSet.getInt(5));
                vendorDB.setSupplyDelay(resultSet.getInt(6));
                // Add functionality to retrieve all contacts and update them to the contacts list
            }
        }
    }

    // U - Update
    public static void updateVendor(String vendorBusinessName, Vendor updatedVendor) throws SQLException {
        // Process that updates a Vendor in the DB

        connection = DatabaseConnector.getConnection();
        String updateStatement = VendorQueries.updateStatement();
        updatePrepareStatement(vendorBusinessName, updatedVendor, updateStatement);
        ContactDBService.updateContact(updatedVendor);
        connection.close();
    }

    private static void updatePrepareStatement(
            String vendorBusinessName,
            Vendor updatedVendor,
            String updateStatement) {

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(updateStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, updatedVendor.getBusinessName());
            preparedStatement.setString(2, updatedVendor.getVendorCode());
            preparedStatement.setString(3, updatedVendor.getVendorStatus());
            preparedStatement.setString(4, updatedVendor.getVendorAccNo());
            preparedStatement.setInt(5, updatedVendor.getReorderDelay());
            preparedStatement.setInt(6, updatedVendor.getSupplyDelay());
            preparedStatement.setInt(7, updatedVendor.getNumberOfVendorContacts());
            preparedStatement.setString(8, vendorBusinessName);

            int rowsAffected = preparedStatement.executeUpdate();
            getUpdateVendorStatus(vendorBusinessName, rowsAffected);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getUpdateVendorStatus(String vendorBusinessName, int rowsAffected)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully updated Vendor \n'" + vendorBusinessName + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful update for Vendor \n'" + vendorBusinessName + "' in DB");
        }
    }


    // D - Delete
    public static void deleteVendor(String vendorBusinessName) throws SQLException {
        // Process to delete a Vendor from the DB

        connection = DatabaseConnector.getConnection();
        String deleteStatement = VendorQueries.deleteStatement();
        deletePrepareStatement(vendorBusinessName, deleteStatement);
        connection.close();
    }

    private static void deletePrepareStatement(String vendorBusinessName, String deleteStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(deleteStatement)) {

            preparedStatement.setString(1, vendorBusinessName);
            int rowsAffected = preparedStatement.executeUpdate();
            getDeleteVendorStatus(vendorBusinessName, rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getDeleteVendorStatus(String vendorBusinessName, int rowsAffected)
            throws SQLException {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully deleted Vendor \n'" + vendorBusinessName + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful delete for Vendor \n'" + vendorBusinessName + "' in DB");
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
