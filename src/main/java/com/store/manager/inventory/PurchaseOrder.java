package com.store.manager.inventory;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

// A purchase order is a form used to request products or services from your supplier.
public class PurchaseOrder {

    // Constant string literals
    private static final String NOT_ORDERED = "Not Ordered";
    private static final String ORDERED = "Ordered";
    private static final String PENDING = "Pending";
    private static final String COMPLETED = "Completed";

    // Class members
    // Order-related members
    private String orderID;
    private boolean orderActive;
    private String orderStatus;
    private String orderStore;
    private String dateOrdered;
    private String dateExpected;
    private String dateReceived;
    private int unitsOrdered;
    private int unitsReceived;
    private double orderTotal;
    private String orderDesc;

    // Associated Class members
    private Vendor orderVendor;
    private Product orderProduct;

    // Scanner member to get user input
    Scanner scan = new Scanner(System.in);

    // Class Constructors
    public PurchaseOrder(Product orderProduct) {
        this.orderProduct = orderProduct;
        this.orderVendor = orderProduct.getProductVendor();
        orderStatus = NOT_ORDERED;
    }

    public PurchaseOrder(Vendor orderVendor, Product orderProduct) {
        this.orderVendor = orderVendor;
        this.orderProduct = orderProduct;
        orderStatus = NOT_ORDERED;
    }

    // Class getters
    public String getOrderID() {
        return orderID;
    }

    public boolean isOrderActive() {
        return orderActive;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderStore() {
        return orderStore;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public String getDateExpected() {
        return dateExpected;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public int getUnitsOrdered() {
        return unitsOrdered;
    }

    public int getUnitsReceived() {
        return unitsReceived;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public Vendor getOrderVendor() {
        return orderVendor;
    }

    public Product getOrderProduct() {
        return orderProduct;
    }

    // Class setters
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    private void setOrderActive(boolean orderActive) {
        this.orderActive = orderActive;
    }

    private void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderStore(String orderStore) {
        this.orderStore = orderStore;
    }

    private void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    private void setDateExpected(String dateExpected) {
        this.dateExpected = dateExpected;
    }

    private void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setUnitsOrdered(int unitsOrdered) {
        this.unitsOrdered = unitsOrdered;
    }

    private void setUnitsReceived(int unitsReceived) {
        this.unitsReceived = unitsReceived;
    }

    private void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public void setOrderVendor(Vendor orderVendor) {
        this.orderVendor = orderVendor;
    }

    public void setOrderProduct(Product orderProduct) {
        this.orderProduct = orderProduct;
    }

    // Class methods
    public void placeOrder() {
        // Method changes the status of the order to 'ordered'
        // Sets the date ordered to the current date of when the order was placed
        if (!hasRequiredInfo()) {
            System.out.println("Record Purchase info before placing order");
            return;
        }

        setOrderActive(true);
        setOrderStatus(ORDERED);
        setDateOrdered(currentDate());
        setDateExpected(calcDateExpected());
        setOrderTotal(calcTotalCost());
    }

    public void monitorOrder() {
        // Method changes the status of the order to 'pending'
        // if the date expected is exceeded
        if (!isOrderActive()) {
            System.out.println("Record Purchase info and place order before monitoring order");
            return;
        }

        if (Date.valueOf(currentDate())
                .after(Date.valueOf(getDateExpected()))) {
                    setOrderStatus(PENDING);
        }
    }

    public void receiveOrder() {
        // Method changes the status of the order to 'complete'
        // Sets the date received and the units received
        if (!isOrderActive()) {
            System.out.println("Record Purchase info and place order before receiving order");
            return;
        }

        setOrderStatus(COMPLETED);
        setDateReceived(currentDate());

        System.out.print("Number of Units Received: ");
        setUnitsReceived(scan.nextInt());
    }

    private String currentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return currentDate.format(dateFormat);
    }

    public void recordOrderInfo() {
        System.out.println("\n|| Record Purchase Order Details");
        System.out.print("Order ID (ABC-###): ");
        setOrderID(scan.nextLine());
        System.out.print("Order Store Location: ");
        setOrderStore(scan.nextLine());
        System.out.print("Units Ordered: ");
        setUnitsOrdered(Integer.parseInt(scan.nextLine()));
        System.out.print("Order Description: ");
        setOrderDesc(scan.nextLine());
        setOrderTotal(calcTotalCost());
    }

    public void getOrderInfo() {
        if (!nonNullFields()) {
            System.out.println("\nRecord Purchase information first!");
            recordOrderInfo();
        }

        System.out.println("\n|| Purchase Order Details");
        System.out.println("Order ID (ABC-###): " + getOrderID());
        System.out.println("Order Store Location: " + getOrderStore());
        System.out.println("Order Vendor: " + getOrderVendor().getBusinessName());
        System.out.println("Product Ordered: " + getOrderProduct().getProductName());
        System.out.println("Order Status: " + getOrderStatus());
        System.out.println("Date Ordered (dd/mm/yyyy): " + getDateOrdered());
        System.out.println("Date Expected (dd/mm/yyyy): " + getDateExpected());
        System.out.println("Units Ordered: " + getUnitsOrdered());
        System.out.println("Order Description: " + getOrderDesc());
        System.out.println("Order Total Cost: " + getOrderTotal());
    }

    public void addNewProduct() {
        if (orderProduct != null) {
            System.out.println("Purchase Order already has an assigned Product!");
            return;
        }

        Product product = new Product();
        product.recordProductInfo();
        setOrderVendor(product.getProductVendor());
        setOrderProduct(product);
        setOrderTotal(calcTotalCost());
    }

    private double calcTotalCost() {
        return (orderProduct.getWholesalePrice() != null)? orderProduct.getWholesalePrice() * unitsOrdered : 0;
    }

    private String calcDateExpected() {
        try {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expectedDate = currentDate.plusDays(orderProduct.getLeadTime());
            return expectedDate.format(dateFormat);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean hasRequiredInfo() {
        // Checks to see if the required fields are initialized before a place order

        try {
            return Stream.of(this.orderID, this.orderStore,
                            this.unitsOrdered,
                            this.orderDesc,
                            this.orderProduct, this.orderVendor)
                    .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean nonNullFields() {
        // Fields date received and units received are permitted to be null

        try {
            return Stream.of(this.orderID, this.orderStore,
                            this.orderStatus, this.dateOrdered,
                            this.dateExpected, this.unitsOrdered,
                            this.orderTotal, this.orderDesc,
                            this.orderProduct, this.orderVendor)
                        .allMatch(Objects::nonNull);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
