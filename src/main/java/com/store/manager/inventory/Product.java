package com.store.manager.inventory;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Product {

    // Class members
    // Identification-related members
    private String productName;
    private Double productWeight;
    private String productCode;
    private String productSKU;
    private Boolean productStatus;
    private String productDesc;

    // Price-related members
    private Double costPrice;
    private Double wholesalePrice;
    private Double retailPrice;
    private Double profitMargin;

    // Inventory-related members
    private int leadTime;

    // Associated Class members
    private Category productCategory;
    private Brand productBrand;
    private Vendor productVendor;

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Initializer for default product status
    { deactivateProduct(); }

    // Class constructors
    public Product() {}

    public Product(Category productCategory, Brand productBrand) {
        this.productCategory = productCategory;
        this.productBrand = productBrand;
    }

    public Product(
            Category productCategory,
            Brand productBrand,
            Vendor productVendor) {

        this.productCategory = productCategory;
        this.productBrand = productBrand;
        this.productVendor = productVendor;
    }

    public Product(
            String productName,
            Double productWeight,
            String productCode,
            String productDesc,
            Double costPrice,
            Double wholesalePrice,
            Double retailPrice,
            Double profitMargin,
            int leadTime,
            Category productCategory,
            Brand productBrand,
            Vendor productVendor) {

        this.productName = productName;
        this.productWeight = productWeight;
        this.productCode = productCode;
        this.productDesc = productDesc;
        this.costPrice = costPrice;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.profitMargin = profitMargin;
        this.leadTime = leadTime;
        this.productCategory = productCategory;
        this.productBrand = productBrand;
        this.productVendor = productVendor;
    }

    // Class getters
    public String getProductName() {
        return productName;
    }

    public Double getProductWeight() {
        return productWeight;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getProductStatus() {
        return this.productStatus? "Active" : "Not active";
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public Brand getProductBrand() {
        return productBrand;
    }

    public Vendor getProductVendor() {
        return productVendor;
    }

    // Class setters
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductWeight(Double productWeight) {
        this.productWeight = productWeight;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    private void setLeadTime(int leadTime) {
        this.leadTime = leadTime;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductBrand(Brand productBrand) {
        this.productBrand = productBrand;
    }

    public void setProductVendor(Vendor productVendor) {
        this.productVendor = productVendor;
    }

    // Class methods
    public void activateProduct() {
        setLeadTime(calcLeadTime());
        generateProductSKU();
        this.productStatus = nonNullFields();
        System.out.println("Product Status: " + getProductStatus());
    }

    public void deactivateProduct() { this.productStatus = false; }

    public void recordProductInfo() {
        System.out.println("\n|| Record Product Details");
        System.out.print("Product Name: ");
        setProductName(scan.nextLine());
        System.out.print("Product Weight [kgs]: ");
        setProductWeight(Double.parseDouble(scan.nextLine()));
        System.out.print("Product Code [ABC-###]: ");
        setProductCode(scan.nextLine());
        System.out.print("Product Description: ");
        setProductDesc(scan.nextLine());
        System.out.print("\nProduct Cost Price: ");
        setCostPrice(scan.nextDouble());
        System.out.print("Product Wholesale Price: ");
        setWholesalePrice(scan.nextDouble());
        System.out.print("Product Profit Margin [%]: ");
        setProfitMargin(scan.nextDouble());
        System.out.print("[Recommended Retail Price: " + calcRetailPrice() + "] \nProduct Retail Price: ");
        setRetailPrice(scan.nextDouble());
        addProductVendor();
        setLeadTime(calcLeadTime());

        activateProduct();
    }

    public void getProductInfo() {
        if (!nonNullFields()) {
            System.out.println("\nRecord Product information first!");
            recordProductInfo();
        }

        System.out.println("\n|| Product Details");
        System.out.println("Product Name: " + getProductName());
        System.out.println("Product Code: " + getProductCode());
        System.out.println("Product Weight: " + getProductWeight());
        System.out.println("Product Description: " + getProductDesc());
        System.out.println("Product Category: " + getProductCategory().getCategoryName());
        System.out.println("Product Brand: " + getProductBrand().getBrandName());
        System.out.println("Product Vendor: " + getProductVendor().getBusinessName());
        System.out.println("Product SKU: " + getProductSKU());
        System.out.println("Product Status: " + getProductStatus());

        System.out.println("Product Cost Price: " + getCostPrice());
        System.out.println("Product Wholesale Price: " + getWholesalePrice());
        System.out.println("Product Retail Price: " + getRetailPrice());

        System.out.println("Product Profit Margin: " + getProfitMargin() + "%");
        System.out.println("Product Lead Time: " + getLeadTime());
    }

    private void addProductVendor() {
        if (this.productVendor == null) {
            productVendor = new Vendor();
            productVendor.recordVendorInfo();
        }
    }

    private int calcLeadTime() {
        return productVendor.getSupplyDelay() + productVendor.getReorderDelay();
    }

    private double calcRetailPrice() {
        if (wholesalePrice == null) {
            System.out.println("Cannot calculate retail price, without wholesale price");
            return 0;
        }

        return wholesalePrice / (1 - (profitMargin / 100));
    }

    private void generateProductSKU() {
        try {

            StringBuilder builder = new StringBuilder();
            builder.append(this.productCategory.getCategoryCode());
            builder.append("#");
            builder.append(this.productCategory.getCategoryLocationNo());
            builder.append("-");
            builder.append(this.productBrand.getBrandCode());
            builder.append("-");
            builder.append(this.productCode);
            this.productSKU = builder.toString();
        } catch (Exception e) {
            System.out.println("Missing fields required to generate Product SKU! \nRecord product info!");
        }
    }

    public boolean nonNullFields() {

        try {
            return Stream.of(this.productName, this.productWeight, this.productCode,
                            this.productStatus, this.productSKU, this.productDesc,
                            this.costPrice, this.wholesalePrice, this.retailPrice,
                            this.profitMargin, this.leadTime,
                            this.productCategory, this.productBrand, this.productVendor)
                        .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
