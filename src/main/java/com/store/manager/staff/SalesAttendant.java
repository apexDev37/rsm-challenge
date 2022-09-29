package com.store.manager.staff;
import org.jetbrains.annotations.NotNull;

public class SalesAttendant extends Employee {

    // Class constant static members
    private static int employeeCount = 0;
    private static final double EMPLOYEE_SALARY = 50000.00;

    // Initializer block for static Class members
    {
        employeeCount++;
        hireEmployee();
        setEmployeeSalary(EMPLOYEE_SALARY);
        generateEmployeeID();
    }


    // Class constructors
    public SalesAttendant(@NotNull Employee employee) {
        super(employee);
    }

    public SalesAttendant(@NotNull BasePerson person, @NotNull Employee employee) {
        super(person, employee);
    }

    public SalesAttendant(
            @NotNull BasePerson person,

            int overtimeHrs,
            double overtimeRate,
            int minWorkHours,
            int maxWorkHours,
            int annualLeaveDays) {

        super(person, overtimeHrs, overtimeRate, minWorkHours, maxWorkHours, annualLeaveDays);
    }

    public SalesAttendant(
            // Personal details
            String firstName,
            String lastName,
            int age,
            String gender,
            String nationalID,
            String educationLevel,

            // Employee details
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
        try {

            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append("ESA-");
            builder.append(String.format("%04d", getEmployeeCount()));
            setEmployeeID(builder.toString());
        } catch (Exception e) {
            System.out.println("Missing fields required to generate Employee ID");
        }
    }
}
