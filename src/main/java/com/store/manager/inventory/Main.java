package com.store.manager.inventory;
import com.store.manager.database.inventory.BrandDBService;
import com.store.manager.database.inventory.CategoryDBService;
import com.store.manager.database.inventory.ContactDBService;
import com.store.manager.database.inventory.VendorDBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {

        // Implementation for class Category
        System.out.println("|| STORE MANAGER\n");


//        // --- Implementation for class Vendor ---
//        Vendor vendor =
//                new Vendor(
//                        "LumiCharge",
//                        "LMC-001",
//                        "157382948345",
//                        2,
//                        4);
//
//        Contact contact =
//                new Contact("Alan",
//                            "Watts",
//                            798237283,
//                            "allan@gmail.com",
//                            "123 Ave Street",
//                            "Nairobi",
//                            "Kenya");
//
//        Contact contact2 =
//                new Contact("Jordan",
//                        "Peterson",
//                        790382847,
//                        "peterson@gmail.com",
//                        "254 Wall Street",
//                        "LA",
//                        "California");
//
//        Vendor vendor1 =
//                new Vendor("A-spark Suppliers",
//                            "ASP-001",
//                            "199283748283",
//                            2,
//                            3);
//
//        Contact contact3 =
//                new Contact("Mali",
//                        "Vogue",
//                        723345647,
//                        "mali@gmail.com",
//                        "254 Sunrise",
//                        "Nairobi",
//                        "Kenya");


//        vendor.activateVendor();
//        vendor.addVendorContact(contact);
//        vendor.addVendorContact(contact2);
//        vendor.getVendorInfo();
//        vendor.getVendorContactsInfo();

//        vendor1.activateVendor();
//        vendor1.addVendorContact(contact3);
//        vendor1.getVendorInfo();
//        vendor1.getVendorContactsInfo();
//
//        VendorDBService.insertVendor(vendor1);

//        // Vendor DB retrieve implementation
//        Vendor retrievedVendor = VendorDBService.retrieveVendor("LumiCharge");
//        retrievedVendor.getVendorInfo();
//        retrievedVendor.getVendorContactsInfo();

//        // Vendor DB update implementation
//        Vendor retrievedVendor = VendorDBService.retrieveVendor("Arrow");
//        retrievedVendor.setSupplyDelay(5);
//        retrievedVendor.addVendorContact();
//        VendorDBService.updateVendor("Arrow", retrievedVendor);

//        // Contact DB delete implementation
//        ContactDBService.deleteContact(711111111);

        // Implementation for class Product

        Category category = CategoryDBService.retrieveCategory("Electronics");
        Brand brand = BrandDBService.retrieveBrand("Samsung");
        Vendor vendor = VendorDBService.retrieveVendor("Avnet");

        Product product = new Product(category, brand);
        product.setProductName("Samsung S21");
        product.setProductWeight(1.2);
        product.setProductCode("210");
        product.setProductDesc("Smartphone electronic device S21 by brand Samsung");

        product.setCostPrice(40000.00);
        product.setWholesalePrice(70000.00);
        product.setProfitMargin(50.0);
        product.setRetailPrice(140000.00);

        product.setProductVendor(vendor);
        product.activateProduct();
        product.getProductInfo();
        System.out.println("Product has non null fields? " + product.nonNullFields());



//        // Implementation for class PurchaseOrder
//        PurchaseOrder myPurchase = new PurchaseOrder(product);
//        myPurchase.setOrderID("SML-543");
//        myPurchase.setOrderStore("AIR Retail Store");
//        myPurchase.setUnitsOrdered(50);
//        myPurchase.setOrderDesc("Purchase order for 50 units of the smartphone, Samsung S21 by the brand Samsung");
//        myPurchase.placeOrder();
//        myPurchase.getOrderInfo();
//        myPurchase.receiveOrder();
//        myPurchase.getOrderInfo();
//
    }
}
