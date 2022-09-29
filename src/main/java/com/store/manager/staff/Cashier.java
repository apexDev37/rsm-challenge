package com.store.manager.staff;
import com.store.manager.pos.Customer;
import com.store.manager.pos.Transaction;
import org.jetbrains.annotations.NotNull;

public class Cashier extends Employee {

    // Class constant static members
    private static int employeeCount = 0;
    private static final double EMPLOYEE_SALARY = 80000.00;

    // Initializer block for static Class members
    {
        employeeCount++;
        hireEmployee();
        setEmployeeSalary(EMPLOYEE_SALARY);
        generateEmployeeID();
    }


    // Class constructors
    public Cashier(@NotNull Employee employee) {
        super(employee);
    }

    public Cashier(@NotNull BasePerson person, @NotNull Employee employee) {
        super(person, employee);
    }

    public Cashier(
            @NotNull BasePerson person,
            int overtimeHrs,
            double overtimeRate,
            int minWorkHours,
            int maxWorkHours,
            int annualLeaveDays) {

        super(person, overtimeHrs, overtimeRate, minWorkHours, maxWorkHours, annualLeaveDays);
    }

    public Cashier(
            String firstName,
            String lastName,
            int age,
            String gender,
            String nationalID,
            String educationLevel,

            int overtimeHrs,
            double overtimeRate,
            int minWorkHours,
            int maxWorkHours,
            int annualLeaveDays) {

        super(firstName, lastName, age, gender, nationalID, educationLevel,
                overtimeHrs, overtimeRate, minWorkHours, maxWorkHours, annualLeaveDays);
    }

    // Class methods
    public static int getEmployeeCount() {
        return employeeCount;
    }

    protected void generateEmployeeID() {
        // Process to generate a unique employee ID for each cashier
        // Example: #ECA-3456

        try {

            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append("ECA-");
            builder.append(String.format("%04d", getEmployeeCount()));
            setEmployeeID(builder.toString());
        } catch (Exception e) {
            System.out.println("Missing fields required to generate Employee ID");
        }
    }

    public Transaction startPosTransaction(@NotNull Customer customer) {
        return new Transaction(this,  customer);
    }

    @Override
    public String toString() {
        return "\n\n|| Cashier" +
                "\nName: " + firstName + " " + lastName +
                "\nID: " + employeeID;
    }
}
