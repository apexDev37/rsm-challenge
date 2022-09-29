package com.store.manager.inventory;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Brand {

    // Class members
    private String brandName;
    private String brandCode;
    private Category brandCategory;
    private Boolean brandStatus;
    private String brandDesc;

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);


    // Constructors
    public Brand() {}

    public Brand(Category brandCategory) {
        this.brandCategory = brandCategory;
    }

    public Brand(
            String brandName,
            String brandCode,
            Category brandCategory,
            Boolean brandStatus) {

        this.brandName = brandName;
        this.brandCode = brandCode;
        this.brandCategory = brandCategory;
        this.brandStatus = brandStatus;
    }

    // Class Getters
    public String getBrandName() {
        return brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public String getBrandStatus() {
        return this.brandStatus ? "active" : "deactivated";
    }

    public Category getBrandCategory() {
        return brandCategory;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    // Class setters
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public void setBrandCategory(Category brandCategory) {
        this.brandCategory = brandCategory;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    // Class methods
    public void activateBrand() { this.brandStatus = true; }

    public void deactivateBrand() { this.brandStatus = false; }


    public void recordBrandInfo() {
        System.out.println("\n|| Record Brand Details");
        System.out.print("Brand Name: ");
        setBrandName(scan.nextLine());
        System.out.print("Brand Code (ABC): ");
        setBrandCode(scan.nextLine());
        System.out.print("Brand Description: ");
        setBrandDesc(scan.nextLine());

        activateBrand();
    }

    public void getBrandInfo() {
        if(!nonNullFields()){
            System.out.println("\nRecord Brand information first!");
            recordBrandInfo();
        }

        System.out.println("\n|| Brand Details");
        System.out.println("Brand Name: " + getBrandName());
        System.out.println("Brand Code: " + getBrandCode());
        System.out.println("Brand Category: " + getBrandCategory().getCategoryName());
        System.out.println("Brand Status: " + getBrandStatus());
        System.out.println("Brand Description: " + getBrandDesc());
    }

    public boolean nonNullFields() {
        try {

            return Stream.of(
                            this.brandName,
                            this.brandCode,
                            this.brandCategory,
                            this.brandStatus,
                            this.brandDesc)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
