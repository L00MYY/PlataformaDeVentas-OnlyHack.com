package model;
import enums.PaymentMethods;
public class payment {
    private int id;
    private int orderId;
    private double amount;
    private PaymentMethods paymentMethod;
    private String date; // Format: dd/MM/yyyy HH:mm

    public payment() {}

    public payment(int id, int orderId, double amount, PaymentMethods paymentMethod, String date) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public PaymentMethods getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethods paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

}
