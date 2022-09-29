package com.store.manager.pos;
import com.store.manager.staff.Cashier;

public class Main {

    // Implementation for the Point of Sale (POS) Package
    public static void main(String[] args) {

        System.out.println("Point of Sale | STORE MANAGER\n");

        // Implementation for Transaction Class
        // Create an instance of Cashier Class
        Cashier cashier =
                new Cashier(
                        "Eugene",
                        "Mwangi",
                        24,
                        "Male",
                        "36545357",
                        "Bachelors in Information & Technology",

                        15,
                        1500,
                        170,
                        220,
                        10
                );

        // Create an instance of Customer Class
        Customer customer =
                new Customer(
                        "Bakhita",
                        "Kabingu",
                        "backs@gmail.com",
                        719844725);

        // Test
        Customer customer1 = new Customer();
        System.out.println(customer1);

        // Create an instance of the Transaction class
        Transaction transaction = new Transaction(cashier, customer);
        transaction.startTransaction();

    }
}
