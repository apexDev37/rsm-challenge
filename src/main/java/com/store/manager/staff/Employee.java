package com.store.manager.staff;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

public class Employee extends BasePerson {

    // A derived class of BasePerson to provide a base collection of characteristics for type Employee
    // Class constant string literals
    private static final String STATUS_EMPLOYED = "Employed";
    private static final String STATUS_PROBATION = "Probation";
    private static final String STATUS_RELIEVED = "Relieved";

    // Class members
    protected String employeeID;
    protected String employeeStatus;
    protected double employeeSalary;
    protected LocalDate dateHired;
    protected int overtimeHrs;
    protected double overtimeRate;
    protected int minWorkHours;
    protected int maxWorkHours;
    protected int annualLeaveDays;

    // Class Constructors
    public Employee(@NotNull BasePerson person) {
        super(person);
    }

    public Employee(@NotNull Employee employee) {
        super(employee);
        this.overtimeHrs = employee.getOvertimeHrs();
        this.overtimeRate = employee.getOvertimeRate();
        this.minWorkHours = employee.getMinWorkHours();
        this.maxWorkHours = employee.getMaxWorkHours();
        this.annualLeaveDays = employee.getAnnualLeaveDays();
    }

    public Employee(@NotNull BasePerson person, @NotNull Employee employee) {
        super(person);
        this.overtimeHrs = employee.getOvertimeHrs();
        this.overtimeRate = employee.getOvertimeRate();
        this.minWorkHours = employee.getMinWorkHours();
        this.maxWorkHours = employee.getMaxWorkHours();
        this.annualLeaveDays = employee.getAnnualLeaveDays();
    }

    public Employee(
            @NotNull BasePerson person,

            int overtimeHrs,
            double overtimeRate,
            int minWorkHours,
            int maxWorkHours,
            int annualLeaveDays) {

        super(person);
        this.overtimeHrs = overtimeHrs;
        this.overtimeRate = overtimeRate;
        this.minWorkHours = minWorkHours;
        this.maxWorkHours = maxWorkHours;
        this.annualLeaveDays = annualLeaveDays;
    }

    public Employee(
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

        super(firstName, lastName, age, gender, nationalID, educationLevel);
        this.overtimeHrs = overtimeHrs;
        this.overtimeRate = overtimeRate;
        this.minWorkHours = minWorkHours;
        this.maxWorkHours = maxWorkHours;
        this.annualLeaveDays = annualLeaveDays;
    }


    // Class getters
    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public String getDateHired() {
        if (Objects.isNull(dateHired))
            return "--- Date hired not set! Employee not hired ---";

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateHired.format(dateFormat);
    }

    public int getOvertimeHrs() {
        return overtimeHrs;
    }

    public double getOvertimeRate() {
        return overtimeRate;
    }

    public int getMinWorkHours() {
        return minWorkHours;
    }

    public int getMaxWorkHours() {
        return maxWorkHours;
    }

    public int getAnnualLeaveDays() {
        return annualLeaveDays;
    }

    // Class setters
    protected void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    private void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    protected void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public void setOvertimeHrs(int overtimeHrs) {
        this.overtimeHrs = overtimeHrs;
    }

    public void setOvertimeRate(double overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public void setMinWorkHours(int minWorkHours) {
        this.minWorkHours = minWorkHours;
    }

    public void setMaxWorkHours(int maxWorkHours) {
        this.maxWorkHours = maxWorkHours;
    }

    public void setAnnualLeaveDays(int annualLeaveDays) {
        this.annualLeaveDays = annualLeaveDays;
    }

    // Class methods
    public void hireEmployee() {
        setEmployeeStatus(STATUS_EMPLOYED);
        dateHired = LocalDate.now();
        System.out.println(
                "\n|| Employee Hired"
                        + "\nName: " + getFirstName() + " " + getLastName()
                        + "\nDate Hired: " + getDateHired()
        );
    }

    public void probateEmployee() {
        setEmployeeStatus(STATUS_PROBATION);
        System.out.println(
                "\n|| Employee On Probation"
                        + "\nName: " + getFirstName() + " " + getLastName()
                        + "\nDate Probated: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );

    }

    public void relieveEmployee() {
        employeeStatus = STATUS_RELIEVED;
        System.out.println(
                "\n|| Employee Relieved"
                        + "\nName: " + getFirstName() + " " + getLastName()
                        + "\nDate Relieved: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }

    public void getEmployeeInfo() {
        if (!nonNullEmployeeFields()) {
            System.out.println("\n--- Update Employee Information first! ---");
            recordEmployeeInfo();
            return;
        }

        System.out.println("\n|| Employee Details");
        if (employeeID != null
            && employeeStatus != null
            && employeeSalary != 0.0) {

            System.out.println("Employee ID: " + getEmployeeID());
            System.out.println("Employee Status: " + getEmployeeStatus());
            System.out.println("Employee Salary: " + getEmployeeSalary());
        }

        System.out.println("Date Hired: " + getDateHired());
        System.out.println("Overtime Hours (Monthly): " + getOvertimeHrs());
        System.out.println("Overtime Rate: " + getOvertimeRate());
        System.out.println("Minimum Work Hours (Monthly): " + getMinWorkHours());
        System.out.println("Maximum Work Hours (Monthly): " + getMaxWorkHours());
        System.out.println("Annual Leave days: " + getAnnualLeaveDays());
    }

    public void getAllInfo() {
        getPersonalInfo();
        getEmployeeInfo();
        getSalaryInfo();
    }

    public void recordEmployeeInfo() {
        System.out.println("\n|| Record Employee Details");
        System.out.print("Overtime Hours (Monthly): ");
        setOvertimeHrs(Integer.parseInt(scan.nextLine()));
        System.out.print("Overtime Rate: ");
        setOvertimeRate(Double.parseDouble(scan.nextLine()));
        System.out.print("Minimum Work Hours (Monthly): ");
        setMinWorkHours(Integer.parseInt(scan.nextLine()));
        System.out.print("Maximum Work Hours (Monthly): ");
        setMaxWorkHours(Integer.parseInt(scan.nextLine()));
        System.out.print("Annual Leave days: ");
        setAnnualLeaveDays(Integer.parseInt(scan.nextLine()));
    }

    protected void generateEmployeeID() {}

    private double calcAnnualSalary() {
        return this.getEmployeeSalary() * 12;
    }

    private double calcOvertimeSalary() {
        return this.getOvertimeRate() * this.getOvertimeHrs();
    }

    private double calcTotalSalary() {
        return this.getEmployeeSalary() + calcOvertimeSalary();
    }

    public void getSalaryInfo() {
        System.out.println("\n|| Employee Salary Details");
        System.out.println("Salary (Monthly): " + getEmployeeSalary());
        System.out.println("Overtime Salary (Monthly): " + calcOvertimeSalary());
        System.out.println("Total Salary (Monthly): " + calcTotalSalary());
        System.out.println("Annual Salary: " + calcAnnualSalary());
    }

    public boolean nonNullEmployeeFields() {
        try {
            return Stream.of(this.dateHired, this.overtimeHrs, this.overtimeRate,
                            this.minWorkHours, this.maxWorkHours, this.annualLeaveDays)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
