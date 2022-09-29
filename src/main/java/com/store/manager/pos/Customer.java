package com.store.manager.pos;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Customer {

    // Class to represent a customer involved in a retail POS transaction
    // Class static members
    private static int customerCount = 0;

    // Class constant string literals for modes of payment
    private static final String CASH = "cash";
    private static final String CARD = "card";
    private static final String M_PESA = "m_pesa";
    private static final String PAYPAL = "paypal";

    // Class members
    private String firstName;
    private String lastName;
    private String emailAddress;
    private long mobileNumber;
    private String customerID;

    private paymentMethod modeOfPayment;
    public enum paymentMethod {CASH, CARD, M_PESA, PAYPAL}

    // Scanner member to get user input
    static Scanner scan = new Scanner(System.in);

    // Initializer block to count number of customers registered
    { customerCount++; initializeCustomerFields(); }

    // Class constructors
    public Customer() {}

    public Customer(
            String firstName,
            String lastName,
            String emailAddress,
            long mobileNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        generateCustomerID();
    }

    // Class getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public String getCustomerID() {
        return customerID;
    }

    public static int getCustomerCount() {
        return customerCount;
    }

    public paymentMethod getModeOfPayment() {
        return modeOfPayment;
    }

    // Class setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setModeOfPayment(paymentMethod modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    // Class methods
    private void choosePaymentMethod() {
        // Get user choice
        System.out.println(
                "\n|| Modes of Payment" +
                "\n[Cash, Card, M-Pesa, Paypal]");

        String customerChoice;
        do {
            System.out.print("Customer Mode of Payment: ");
            customerChoice = scan.nextLine().toLowerCase(Locale.ROOT);
        } while (!validModeOfPayment(customerChoice));

        switch (customerChoice) {
            // Mode of selection to return constant string literal
            case CASH:
                setModeOfPayment(paymentMethod.CASH);

                break;
            case CARD:
                setModeOfPayment(paymentMethod.CARD);
                break;
            case M_PESA:
                setModeOfPayment(paymentMethod.M_PESA);
                break;
            case PAYPAL:
                setModeOfPayment(paymentMethod.PAYPAL);
                break;
            default:
                System.out.println("Error! Could not identify valid mode of payment");
        }
    }

    private boolean validModeOfPayment(String customerChoice) {
        return Arrays.stream(paymentMethod.values())
                .anyMatch(method -> method.name().equalsIgnoreCase(customerChoice))
                || invalidModeOfPaymentMsg();
    }

    private boolean invalidModeOfPaymentMsg() {
        System.out.println(
                "Invalid mode of payment chosen" +
                "\nPlease select a valid mode from the list!");
        return false;
    }

    public double makePayment(double transactionTotal) {
        choosePaymentMethod();
        if (modeOfPayment.name().equalsIgnoreCase(CASH)) {
            double cashPayment;

            do {
                System.out.print("Enter Amount Paid: ");
                cashPayment = scan.nextDouble();
            } while (!validCashPayment(cashPayment, transactionTotal));

            return cashPayment;
        } return 0;
    }

    private boolean validCashPayment(double cashPayment, double transactionTotal) {
        return cashPayment >= transactionTotal || invalidCashPaymentMsg();
    }

    private boolean invalidCashPaymentMsg() {
        System.out.println(
                "\nCustomer cash payment cannot be less" +
                "\nthan the total purchase amount!");
        return false;
    }

    public void receiveReceipt() {
        // Action for a customer to receive a receipt
        // Last step of action for a given transaction
    }

    private void generateCustomerID() {
        // Process to generate a unique customer ID for each registered customer
        // Example: CSM-725-0057

        try {

            StringBuilder builder = new StringBuilder();
            builder.append("CSM-");
            builder.append(String.valueOf(getMobileNumber()).substring(6));
            builder.append(String.format("-%04d", getCustomerCount()));
            customerID = builder.toString();
        } catch (Exception e) {
            System.out.println("Missing fields required to generate Customer ID");
        }
    }

    public void getCustomerInfo() {
        generateCustomerID();
        if (!nonNullCustomerFields()) {
            System.out.println("\nCustomer details are empty..." +
                                "\nRecord Customer details first!");
        }

        System.out.println("\n|| Customer Details");
        System.out.println("First Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Email Address: " + getEmailAddress());
        System.out.println("Mobile: " + getMobileNumber());
        System.out.println("Customer ID: " + getCustomerID());
    }

    public void recordCustomerInfo() {
        // If the fields are already populated, inform user and return

        System.out.println("\n|| Record Customer Details");
        System.out.print("First Name: ");
        setFirstName(scan.nextLine());
        System.out.print("Last Name: ");
        setLastName(scan.nextLine());
        System.out.print("Email Address: ");
        setEmailAddress(scan.nextLine());
        System.out.print("Mobile: ");
        setMobileNumber(Long.parseLong(scan.nextLine()));

        generateCustomerID();
    }

    private void initializeCustomerFields() {
        this.firstName = "unknown";
        this.lastName = "unknown";
        this.emailAddress = "unknown";
        this.customerID = "unknown";
    }

    public boolean nonNullCustomerFields() {
        try {
            return Stream.of(
                            this.firstName,
                            this.lastName,
                            this.emailAddress,
                            this.mobileNumber,
                            this.customerID)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "\n\n|| Customer" +
                "\nName: " + firstName + " " + lastName +
                "\nID: " + customerID;
    }
}
