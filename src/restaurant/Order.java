package restaurant;

public class Order {
    private int orderId;
    private int customerId;
    private String orderDetails;
    private double amount;

    public Order(int orderId, int customerId, String orderDetails, double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDetails = orderDetails;
        this.amount = amount;
    }

    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public String getOrderDetails() { return orderDetails; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return "Order ID: " + orderId + "\nCustomer ID: " + customerId + "\nDetails: " + orderDetails + "\nAmount: " + amount;

    }
}