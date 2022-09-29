package com.store.manager.pos;
import com.store.manager.inventory.Product;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Receipt {

    // Class to represent a single Receipt for a given POS transaction
    // Class static members
    private static int receiptCount = 0;

    // Class members
    private String receiptID;
    private String receiptDate;
    private String receiptTime;

    // Class associated members
    private final Transaction transaction;

    // Initializer block for static Class member
    { receiptCount++; initializeDateTime(); }


    // Class constructors
    public Receipt(@NotNull Transaction transaction) {
        this.transaction = transaction;
        generateReceiptID();
    }

    // Class getters
    public static int getReceiptCount() {
        return receiptCount;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    // Class setters
    private void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

    private void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    private void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    // Class methods
    private void initializeDateTime() {
        // Method that initializes the date and time fields to the current date-time
        // of the creation of the receipt

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        setReceiptDate(date.format(dateFormat));

        LocalTime time = LocalTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        setReceiptTime(time.format(timeFormat));
    }

    private void generateReceiptID() {
        // Method that auto generates a unique receipt ID for a given transaction

        try {

            StringBuilder builder = new StringBuilder();
            builder.append("RCP-");
            builder.append(transaction.getCashier().getEmployeeID().substring(6));
            builder.append("-");
            builder.append(transaction.getCustomer().getCustomerID().substring(8));
            builder.append("-");
            builder.append(String.format("-%04d", getReceiptCount()));
            setReceiptID(builder.toString());
        } catch (Exception e) {
            System.out.println("Missing fields required to generate Customer ID");
        }
    }

    private StringBuilder generateReceipt() {
        // Method that generates a receipt template for a complete transaction

        StringBuilder receipt = new StringBuilder();
        getReceiptDetails(receipt);
        getCashierDetails(receipt);
        getCustomerDetails(receipt);
        getPurchaseItemDetails(receipt);
        getPaymentDetails(receipt);

        return receipt;
    }

    public void printReceipt() {
        System.out.println(generateReceipt());
    }

    private double calcItemAmount(@NotNull Product product, Integer quantity) {
        return product.getRetailPrice() * quantity;
    }

    private void getPaymentDetails(@NotNull StringBuilder receipt) {
        receipt.append("\n\n--- Payment ---");
        receipt.append(
                String.format("\nTOTAL: %.2f",
                        transaction.getTransactionTotal()));
        receipt.append(
                String.format("\nMODE OF PAYMENT: %s",
                        transaction.getCustomer().getModeOfPayment()));
        receipt.append(
                String.format("\nBALANCE: %.0f",
                        transaction.getTransactionBalance()));
        receipt.append("\n\n >>> THANK YOU, DEAR VALUED CUSTOMER <<<");
    }

    private void getPurchaseItemDetails(@NotNull StringBuilder receipt) {
        receipt.append("\n\n|| Purchased Products");
        transaction.getCustomerProducts()
                .forEach((product, quantity)
                        -> receipt.append(
                                String.format("\nProduct: %s \n[Items: %d]\t\t\t\t\t\t%.2f",
                                        product.getProductName(),
                                        quantity,
                                        calcItemAmount(product, quantity))));
    }

    private void getCustomerDetails(@NotNull StringBuilder receipt) {
        receipt.append(transaction.getCustomer());
    }

    private void getCashierDetails(@NotNull StringBuilder receipt) {
        receipt.append(transaction.getCashier());
    }

    private void getReceiptDetails(@NotNull StringBuilder receipt) {
        receipt.append(this);
    }

    @Override
    public String toString() {
        return "\n|| RECEIPT"
                + "\nID: " + receiptID
                + "\nDate: " + receiptDate
                + "\nTime: " + receiptTime;
    }
}
