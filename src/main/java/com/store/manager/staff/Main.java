package com.store.manager.staff;

public class Main {
    public static void main(String[] args) {
        System.out.println("Staff Management | STORE MANAGER\n");

        // Implementation for BasePerson class
        BasePerson personOne =
                new BasePerson(
                        "Sean",
                        "Mwangi",
                        20,
                        "Male",
                        "365121212",
                        "Bachelors in Business Management & IT"
                );

        // Implementation for Employee Class
        Employee employeeOne =
                new Employee(
                        personOne,
                        12,
                        1000.00,
                        180,
                        240,
                        12
                );


        // Implementation for Cashier Class
        Cashier cashierOne = new Cashier(employeeOne);
        cashierOne.getPersonalInfo();
        cashierOne.getEmployeeInfo();
        cashierOne.getSalaryInfo();

        Cashier cashierTwo =
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

        cashierTwo.getPersonalInfo();
        cashierTwo.getEmployeeInfo();
        cashierTwo.getSalaryInfo();
    }
}
