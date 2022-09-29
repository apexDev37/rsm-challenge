package com.store.manager.pos;
import com.store.manager.inventory.Product;
import com.store.manager.staff.Cashier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class Transaction implements CommandTerminal {

    // Class to represent a single POS transaction between a Cashier and Customer
    // Class constant string literals
    private static final String STATUS_OPEN = "open";
    private static final String STATUS_REVOKED = "revoked";
    private static final String STATUS_PAID = "paid";
    private static final String STATUS_COMPLETE = "complete";

    // Class static members
    private static int transactionCount = 0;

    // Class members
    private String transactionID;
    private String transactionStatus;
    private double transactionTotal;
    private double transactionBalance;
    private double customerPayment;
    private final Map<Product, Integer> customerProducts =  new HashMap<>();

    // Class associated members
    private final Cashier transactionCashier;
    private final Customer transactionCustomer;

    // Initializer block for static Class member
    { transactionCount++; }


    // Class constructors
    public Transaction(Cashier transactionCashier, Customer transactionCustomer) {
        this.transactionCashier = transactionCashier;
        this.transactionCustomer = transactionCustomer;
        generateTransactionID();
    }

    // Class getters
    public static int getTransactionCount() {
        return transactionCount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public double getTransactionTotal() {
        return transactionTotal;
    }

    public double getTransactionBalance() {
        return transactionBalance;
    }

    public Cashier getCashier() {
        return transactionCashier;
    }

    public Customer getCustomer() {
        return transactionCustomer;
    }

    public double getCustomerPayment() {
        return customerPayment;
    }

    public Map<Product, Integer> getCustomerProducts() {
        return customerProducts;
    }

    // Class setters
    private void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    private void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    private void setTransactionTotal(double transactionTotal) {
        this.transactionTotal = transactionTotal;
    }

    private void setTransactionBalance(double transactionBalance) {
        this.transactionBalance = transactionBalance;
    }

    public void setCustomerPayment(double customerPayment) {
        this.customerPayment = customerPayment;
    }

    // Class methods
    public void startTransaction() {
        // Process that launches the Cashier terminal for interactivity

        setTransactionStatus(STATUS_OPEN);
        defaultCashierTerminal();
    }

    private void terminateTransaction(Transaction transaction) {
        // Process that terminates the Cashier terminal interactive session
        if (!transactionStatus.equals(STATUS_PAID)) {
            System.out.println(
                    "[Alert]" +
                    "\nCannot complete POS transaction" +
                    "\nwithout payment first being made!" +
                    "\nUse the 'pay' command to receive Customer payment");
            return;
        }

        setTransactionStatus(STATUS_COMPLETE);
        Receipt receipt = new Receipt(transaction);
        receipt.printReceipt();
        transactionCustomer.receiveReceipt();   // Add receipt as first param for method
    }

    private void addItem() {
        // Process that accounts for a Customer's product on the Transaction

        System.out.println("\n|| Add Customer Product");
        String productSKU;
        int productQuantity;

        do {

            productSKU = CommandTerminal.getProductSKU();
            if (productSKU.equals(CLOSE)) break;
            productQuantity = CommandTerminal.getProductQuantity();
            customerProducts.put(productsByCode.get(productSKU), productQuantity);
        } while (true);

        printCustomerProducts();
        defaultCashierTerminal();
    }

    private void removeItem() {
        // Process that removes an accounted Customer's product on the Transaction

        System.out.println("\n|| Remove Customer Product");
        String productSKU;

        do {

            productSKU = CommandTerminal.getProductSKU();
            if (productSKU.equals(CLOSE)) break;
            removeProduct(productSKU);
        } while (true);

        printCustomerProducts();
        defaultCashierTerminal();
    }

    private void removeProduct(String productSKU) {
        customerProducts.entrySet()
                .removeIf(productQuantityEntry ->
                        productQuantityEntry.getKey()
                                .getProductCode().equals(productSKU));
    }

    private void  receiveCustomerPayment() {
        // Process that accounts for the total products balance and customer payment
        if (getCustomerPayment() != 0) {
            System.out.println(
                    "Customer payment has already been made!" +
                    "\nProceed to complete the transaction");
            return;
        }
        if (customerProducts.isEmpty()) {
            System.out.println(
                    "Customer products purchased is zero!" +
                    "\nPlease account for Customer products purchased" +
                    "\nbefore using the 'pay' command");
            return;
        }

        calcTransactionTotal();
        System.out.println(
                "\n|| PAYMENT" +
                "\nTotal: " + transactionTotal);

        double payment = transactionCustomer.makePayment(transactionTotal);
        setCustomerPayment(
                (payment == 0)?
                        transactionTotal : payment);

        calcTransactionBalance(payment, transactionTotal);
        System.out.println("Amount Paid: " + getCustomerPayment());
        setTransactionStatus(STATUS_PAID);
    }

    public void revokeTransaction() {
        // Process that revokes and cancels an ongoing transaction

        setTransactionStatus(STATUS_REVOKED);
        System.out.println(
                this +
                "\nhas been revoked!");
    }

    private void defaultCashierTerminal() {
        // Process that keeps the Cashier Terminal active and listening for commands

        do { processCommand(CommandTerminal.retrieveCommand());
        } while (!(transactionStatus.equals(COMPLETE)
                || transactionStatus.equals(STATUS_REVOKED)));
    }

    private void calcTransactionTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : customerProducts.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            total += product.getRetailPrice() * quantity;
        }
        setTransactionTotal(total);
    }

    private void calcTransactionBalance(double customerPayment, double transactionTotal) {
        if ((transactionCustomer
                        .getModeOfPayment().name()
                        .equalsIgnoreCase("cash"))) {
            setTransactionBalance(customerPayment - transactionTotal);
        } else setTransactionBalance(0);
    }

    private void processCommand(@NotNull String command) {
        // Method that matches a valid command argument to its process logic

        switch (command) {

            case ADD:
                // Process to call add method in the Transaction Class
                addItem();
                break;
            case REMOVE:
                // Process to call remove method in the Transaction Class
                removeItem();
                break;
            case PAY:
                // Process to call pay method in the Transaction Class
                receiveCustomerPayment();
                break;
            case COMPLETE:
                // Process to call complete method in the Transaction Class
                terminateTransaction(this);
                break;
            case REVOKE:
                // Process to call revoke method in the Transaction Class
                revokeTransaction();
                break;
            default:
                System.out.println("An error occurred processing the transaction");
        }
    }

    private void printCustomerProducts() {
        System.out.println();
        customerProducts.forEach(((product, quantity) -> System.out.println(
                "Product: " + product.getProductName() +
                ", Quantity: " + quantity
        )));
    }

    private void generateTransactionID() {
        // Process to generate a unique Transaction ID for each Transaction
        // Example: TSC-2550-0028

        try {

            StringBuilder builder = new StringBuilder();
            builder.append("TSC-");
            builder.append(transactionCashier.getEmployeeID().substring(5));
            builder.append("-");
            builder.append(String.format("%04d", getTransactionCount()));
            setTransactionID(builder.toString());
        } catch (Exception e) {
            System.out.println("Missing fields required to generate Employee ID");
        }
    }

    public void getTransactionDetails() {
        if (!transactionStatus.equals(STATUS_COMPLETE)) {
            System.out.println(
                    "Transaction Status must be complete" +
                    "in order to retrieve Transaction details!");
            return;
        }

        System.out.println("\n|| Transaction Details");
        System.out.println(getCashier());
        System.out.println(getCustomer());
        System.out.println("\nID: " + getTransactionID());
        System.out.println("Count: " + getTransactionCount());
        System.out.println("Status: " + getTransactionStatus());
        System.out.println("Items: " + customerProducts.size());
        System.out.println("Total: " + getTransactionTotal());
        System.out.println("Payment: " + getCustomerPayment());
        System.out.println("Balance: " + getTransactionBalance());
    }

    @Override
    public String toString() {
        return "Transaction: " + getTransactionID();
    }
}
