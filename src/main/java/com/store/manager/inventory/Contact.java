package com.store.manager.inventory;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Contact {

    // Contact - Class members
    private String firstName;
    private String lastName;
    private long mobile;
    private String email;

    // Address - Class members
    private String streetAddress;
    private String city;
    private String country;

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Class constructors
    public Contact() {}

    public Contact(
            String firstName,
            String lastName,
            long mobile,
            String email,
            String streetAddress,
            String city,
            String country) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    // Class getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    // Class setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Class methods
    public void recordContactInfo() {
        System.out.println("\n|| Record Contact Details");
        System.out.print("First Name: ");
        setFirstName(scan.nextLine());
        System.out.print("Last Name: ");
        setLastName(scan.nextLine());
        System.out.print("Email: ");
        setEmail(scan.nextLine());
        System.out.print("Mobile: ");
        setMobile(Long.parseLong(scan.nextLine()));
        System.out.print("Street Address: ");
        setStreetAddress(scan.nextLine());
        System.out.print("City: ");
        setCity(scan.nextLine());
        System.out.print("Country: ");
        setCountry(scan.nextLine());
    }

    public void getContactInfo() {
        if(!nonNullFields()){
            System.out.println("\nRecord Contact information first!");
            recordContactInfo();
        }

        System.out.println("\n|| Contact Details");
        System.out.println("First Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Mobile: " + getMobile());
        System.out.println("Email: "+ getEmail());
        System.out.println("Street Address: " + getStreetAddress());
        System.out.println("City: " + getCity());
        System.out.println("Country: " + getCountry());
    }

    public boolean nonNullFields() {
        try {
            return Stream.of(this.firstName, this.lastName, this.mobile,
                            this.email, this.streetAddress, this.city,
                            this.country)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
