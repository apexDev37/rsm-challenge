package com.store.manager.database.inventory;
import com.store.manager.database.DatabaseConnector;
import com.store.manager.database.inventory.queries.ContactQueries;
import com.store.manager.inventory.Contact;
import com.store.manager.inventory.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactDBService {

    // Database service class that enables interactivity with Inventory DB
    // Table: contact

    // Class members
    private static Connection connection;

    // Class methods
    // CRUD service operations for Class Contact

    // C- Create
    static void insertContact(Vendor vendor, List<Contact> contacts) throws SQLException {
        // Process that inserts a Contact or list of Contacts into the DB

        connection = DatabaseConnector.getConnection();
        String insertStatement = ContactQueries.insertStatement();
        insertPrepareStatement(vendor, contacts, insertStatement);
        connection.close();
    }

    private static void insertPrepareStatement(
            Vendor vendor,
            List<Contact> contacts,
            String insertStatement) {

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(insertStatement)) {

            contacts.forEach(contact -> {
                try {
                    preparedStatement.setString(1, vendor.getBusinessName());
                    preparedStatement.setString(2, contact.getFirstName());
                    preparedStatement.setString(3, contact.getLastName());
                    preparedStatement.setLong(4, contact.getMobile());
                    preparedStatement.setString(5, contact.getEmail());
                    preparedStatement.setString(6, contact.getStreetAddress());
                    preparedStatement.setString(7, contact.getCity());
                    preparedStatement.setString(8, contact.getCountry());
                    preparedStatement.addBatch();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    // R -Retrieve
    static List<Contact> retrieveContact(String vendorBusinessName) throws SQLException {
        // Process that retrieves a Contact or list of Contacts
        // associated with a Vendor from the DB

        connection = DatabaseConnector.getConnection();
        String selectStatement = ContactQueries.retrieveStatement();
        List<Contact> contactList = new ArrayList<>();

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(selectStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, vendorBusinessName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Contact contactDB = new Contact();

                contactDB.setFirstName(resultSet.getString(2));
                contactDB.setLastName(resultSet.getString(3));
                contactDB.setMobile(resultSet.getLong(4));
                contactDB.setEmail(resultSet.getString(5));
                contactDB.setStreetAddress(resultSet.getString(6));
                contactDB.setCity(resultSet.getString(7));
                contactDB.setCountry(resultSet.getString(8));

                contactList.add(contactDB);
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }

        connection.close();
        return contactList;
    }

    // U - Update
    static void updateContact(Vendor updatedVendor) throws SQLException {
        // Process that updates a Contact or list of Contacts
        // associated with a Vendor from the DB

        connection = DatabaseConnector.getConnection();
        List<Contact> contactDBList = retrieveContact(updatedVendor.getBusinessName());
        List<Contact> newContactsList = new ArrayList<>();

        if (!contactDBList.isEmpty()) {
            contactDBList.forEach(contactDB ->
                    updatedVendor
                            .getContactsList()
                            .stream()
                            .filter(contact -> contactDB.getMobile() != contact.getMobile())
                            .forEach(newContactsList::add));
            insertContact(updatedVendor, newContactsList);

        } else insertContact(updatedVendor, updatedVendor.getContactsList());
        connection.close();
    }

    // D - Delete
    public static void deleteContact(long mobileContact) throws SQLException {
        // Process to delete a Vendor from the DB

        connection = DatabaseConnector.getConnection();
        String deleteStatement = ContactQueries.deleteStatement();
        deletePrepareStatement(mobileContact, deleteStatement);
        connection.close();
    }

    private static void deletePrepareStatement(long mobileContact, String deleteStatement) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(deleteStatement)) {

            connection.setAutoCommit(false);
            preparedStatement.setLong(1, mobileContact);
            int rowsAffected = preparedStatement.executeUpdate();
            getDeleteContactStatus(mobileContact, rowsAffected);

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (!Objects.isNull(connection))
                rollBackTransaction();
        }
    }

    private static void getDeleteContactStatus(long mobileContact, int rowsAffected) {
        System.out.println("\n|| Status");
        if (rowsAffected > 0) {
            System.out.println(
                    "Successfully deleted Contact \n'" + mobileContact + "' in DB");
        } else {
            System.out.println(
                    "Unsuccessful delete for Vendor \n'" + mobileContact + "' in DB");
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
