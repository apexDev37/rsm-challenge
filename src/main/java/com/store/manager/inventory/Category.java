package com.store.manager.inventory;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Category {

    // Class members
    private String categoryName;
    private String categoryCode;
    private int categoryLocationNo;
    private String categoryDesc;

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Class constructors
    public Category() {}

    public Category(String categoryName, String categoryCode, int categoryLocationNo, String categoryDesc) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.categoryLocationNo = categoryLocationNo;
        this.categoryDesc = categoryDesc;
    }

    // Class Getters
    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public int getCategoryLocationNo() {
        return categoryLocationNo;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    // Class setters
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setCategoryLocationNo(int categoryLocationNo) {
        this.categoryLocationNo = categoryLocationNo;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    // Class methods
    public void recordCategoryInfo() {
        System.out.println("\n|| Record Category Details");
        System.out.print("Category Name: ");
        setCategoryName(scan.nextLine());
        System.out.print("Category Code: ");
        setCategoryCode(scan.nextLine());
        System.out.print("Category Location Number (###): ");
        setCategoryLocationNo(Integer.parseInt(scan.nextLine()));
        System.out.print("Category Description: ");
        setCategoryDesc(scan.nextLine());
    }

    public void getCategoryInfo() {
        if(!nonNullFields()){
            System.out.println("\nRecord Category information first!");
            recordCategoryInfo();
        }

        System.out.println("\n|| Category Details");
        System.out.println("Category Name: " + getCategoryName());
        System.out.println("Category Code: " + getCategoryCode());
        System.out.println("Category Location Number: #" + getCategoryLocationNo());
        System.out.println("Category Description: " + getCategoryDesc());
    }

    public boolean nonNullFields() {
        try {
            return Stream.of(this.categoryName, this.categoryCode, this.categoryLocationNo, this.categoryDesc)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
