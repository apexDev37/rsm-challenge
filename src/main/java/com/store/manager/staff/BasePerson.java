package com.store.manager.staff;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class BasePerson {

    // Base class to provide a collection of common characteristics for staff members
    // Class members
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String gender;
    protected String nationalID;
    protected String educationLevel;

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Class constructors
    public BasePerson() {}

    public BasePerson(@NotNull BasePerson person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
        this.gender = person.getGender();
        this.nationalID = person.getNationalID();
        this.educationLevel = person.getEducationLevel();
    }

    public BasePerson(@NotNull Employee employee) {
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.age = employee.getAge();
        this.gender = employee.getGender();
        this.nationalID = employee.getNationalID();
        this.educationLevel = employee.getEducationLevel();
    }

    public BasePerson(
            String firstName,
            String lastName,
            int age,
            String gender,
            String nationalID,
            String educationLevel) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.nationalID = nationalID;
        this.educationLevel = educationLevel;
    }

    // Class getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getNationalID() {
        return nationalID;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    // Class setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    // Class methods
    public void recordPersonalInfo() {
        System.out.println("\n|| Record Personal Details");
        System.out.print("First Name: ");
        setFirstName(scan.nextLine());
        System.out.print("Last Name: ");
        setLastName(scan.nextLine());
        System.out.print("Age: ");
        setAge(Integer.parseInt(scan.nextLine()));
        System.out.print("Gender: ");
        setGender(scan.nextLine());
        System.out.print("National ID: ");
        setNationalID(scan.nextLine());
        System.out.print("Educational Level (Highest): ");
        setEducationLevel(scan.nextLine());
    }

    public void getPersonalInfo() {
        if (!nonNullPersonalFields()) {
            System.out.println("Record Personal Information first!");
            recordPersonalInfo();
            return;
        }

        System.out.println("\n|| Personal Details");
        System.out.println("First Name: " + getFirstName());
        System.out.println("Last Name: " + getLastName());
        System.out.println("Age: " + getAge());
        System.out.println("Gender: " + getGender());
        System.out.println("National ID: " + getNationalID());
        System.out.println("Educational Level: " + getEducationLevel());
    }

    public boolean nonNullPersonalFields() {
        try {
            return Stream.of(this.firstName, this.lastName, this.age,
                            this.gender, this.nationalID, this.educationLevel)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }

}
