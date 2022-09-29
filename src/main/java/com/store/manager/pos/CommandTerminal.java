package com.store.manager.pos;
import com.store.manager.inventory.Product;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public interface CommandTerminal {

    // Class constant string literals
    // Cashier valid commands
    String ADD = "add";
    String REMOVE = "remove";
    String CLOSE = "close";
    String PAY = "pay";
    String COMPLETE = "complete";
    String REVOKE = "revoke";

    // Key value pair list of products
    Map<String, Product> productsByCode =  new HashMap<>();

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Class methods
    static @NotNull String retrieveCommand() {
        System.out.print("\n(Cashier Terminal) ~ ");
        String command = scan.nextLine();
        return validCommand(command)? command : retrieveCommand();
    }

    static String getProductSKU() {
        populateProductsList();
        String productSKU;
        do {

            System.out.print("\n[Enter Product SKU]: ");
            productSKU = scan.nextLine();
        } while (!validProductSKU(productSKU));
        return productSKU;
    }

    private static boolean validProductSKU(String productSKU) {
        // Method that checks a list of product SKU's to determine if it maps to an existing product
        if (productSKU.equals(CLOSE))
            return true;

        return productsByCode.containsKey(productSKU) || invalidProductSkuMsg(productSKU);
    }

    private static boolean invalidProductSkuMsg(String productSKU) {
        System.out.println(
                "The Product SKU '" + productSKU +
                "'\ndoes not exist or their is not product" +
                "\nwith that requested SKU" +
                "\nTry again!");
        return false;
    }

    static int getProductQuantity() {
        try {

            System.out.print("[Enter Product Quantity]: ");
            return Integer.parseInt(scan.nextLine());
        } catch (Exception e) {
            System.out.println(
                    "Invalid quantity input!" +
                    "\nPlease use valid whole numeric values");
            return getProductQuantity();
        }
    }

    private static boolean validCommand(String command) {
        // Method that checks if Cashier input a valid command argument

        boolean validCommand
                = Stream.of(
                        ADD,
                        REMOVE,
                        CLOSE,
                        PAY,
                        COMPLETE,
                        REVOKE
                    ).anyMatch(s -> s.contains(command));
        return validCommand || invalidCommandMsg(command);
    }

    private static boolean invalidCommandMsg(String command) {
        System.out.println(
                "The command '" + command +
                "'\nis not recognized as a valid system argument" +
                "\nTry again!");
        return false;
    }

    private static void populateProductsList() {
        // Method that populates a list with 3 products

        Product product1 = new Product();
        product1.setProductName("Samsung S21");
        product1.setProductCode("SMG-0001");
        product1.setRetailPrice(140000.00);

        Product product2 = new Product();
        product2.setProductName("HP Envy 15");
        product2.setProductCode("HPE-0001");
        product2.setRetailPrice(96000.00);

        Product product3 = new Product();
        product3.setProductName("Lenovo ThinkPad");
        product3.setProductCode("LTP-0001");
        product3.setRetailPrice(45000.00);

        productsByCode.put(product1.getProductCode(), product1);
        productsByCode.put(product2.getProductCode(), product2);
        productsByCode.put(product3.getProductCode(), product3);
    }
}
