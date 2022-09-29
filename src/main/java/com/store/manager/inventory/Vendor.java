package com.store.manager.inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Vendor {

    // Class members
    private String businessName;
    private String vendorCode;
    private boolean vendorStatus;
    private String vendorAccNo;
    private int supplyDelay;
    private int reorderDelay;

    // Associated Class members
    private Contact vendorContact;

    // List Class members for Associated Classes
    private final List<Contact> contactsList = new ArrayList<>();

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Initializer block default for class Vendor
    { deactivateVendor(); }

    // Class constructors
    public Vendor() {}

    public Vendor(Contact vendorContact) {
        this.vendorContact = vendorContact;
        contactsList.add(vendorContact);
    }

    public Vendor(
            String businessName,
            String vendorCode,
            String vendorAccNo,
            int supplyDelay,
            int reorderDelay) {

        this.businessName = businessName;
        this.vendorCode = vendorCode;
        this.vendorAccNo = vendorAccNo;
        this.supplyDelay = supplyDelay;
        this.reorderDelay = reorderDelay;
    }


    // Class getters
    public String getBusinessName() {
        return businessName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getVendorStatus() {
        return this.vendorStatus? "active" : "deactivated";
    }

    public String getVendorAccNo() {
        return vendorAccNo;
    }

    public int getSupplyDelay() {
        return supplyDelay;
    }

    public int getReorderDelay() {
        return reorderDelay;
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public int getNumberOfVendorContacts() {
        return contactsList.size();
    }

    // Class setters
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public void setVendorAccNo(String vendorAccNo) {
        this.vendorAccNo = vendorAccNo;
    }

    public void setSupplyDelay(int supplyDelay) {
        this.supplyDelay = supplyDelay;
    }

    public void setReorderDelay(int reorderDelay) {
        this.reorderDelay = reorderDelay;
    }

    // Class methods
    public void activateVendor() { this.vendorStatus = true; }

    public void deactivateVendor() { this.vendorStatus = false; }

    public void recordVendorInfo() {
        System.out.println("\n|| Record Vendor Details");
        System.out.print("Business Name: ");
        setBusinessName(scan.nextLine());
        System.out.print("Vendor Code (ABC-###): ");
        setVendorCode(scan.nextLine());
        System.out.print("Vendor Account Number: ");
        setVendorAccNo(scan.nextLine());
        System.out.print("Reorder Delay: ");
        setReorderDelay(scan.nextInt());
        System.out.print("Supply Delay: ");
        setSupplyDelay(scan.nextInt());
        addVendorContact();

        activateVendor();
    }

    public void getVendorInfo() {
        if(!nonNullFields()){
            System.out.println("\nRecord Vendor information first!");
            recordVendorInfo();
        }

        System.out.println("\n|| Vendor Details");
        System.out.println("Business Name: " + getBusinessName());
        System.out.println("Vendor Code: " + getVendorCode());
        System.out.println("Vendor Status: " + getVendorStatus());
        System.out.println("Vendor Account Number: " + getVendorAccNo());
        System.out.println("Reorder Delay: " + getReorderDelay());
        System.out.println("Supply Delay: " + getSupplyDelay());
        System.out.println("Contacts: " + getNumberOfVendorContacts());
    }

    public void addVendorContact() {
        vendorContact = new Contact();
        vendorContact.recordContactInfo();
        contactsList.add(vendorContact);
    }

    public void addVendorContact(@NotNull Contact vendorContact) {
        contactsList.add(vendorContact);
    }


    public void getVendorContactsInfo() {
        contactsList.forEach(Contact::getContactInfo);
    }

    public boolean nonNullFields() {
        try {
            return Stream.of(this.businessName, this.vendorCode, this.vendorStatus,
                            this.vendorAccNo, this.reorderDelay, this.supplyDelay)
                    .allMatch(Objects::nonNull) && contactsList.size() > 0;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
