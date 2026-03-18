package model;
import enums.OrderStatus;
import model.orderDetail;
import java.util.ArrayList;
import java.util.List;

public class order {
    private int id;
    private int customerId;
    private String date; // Format: dd/MM/yyyy HH:mm
    private OrderStatus status;
    private List<orderDetail> details; // Esta lista guarda los productos agregaedos de un pedido especifico
    //Listado de pedidos
    public order() {
        this.details = new ArrayList<>();
    }

    public order(int id, int customerId, String date, OrderStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.status = status;
        this.details = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public List<orderDetail> getDetails() { return details; }

    public void setDetails(List<orderDetail> details) { this.details = details; }

    // agregar detalle a al pedido
    public void addDetail(orderDetail detail) {
        this.details.add(detail);
    }
    // quitar detalle a al pedido
    public void removeDetail(int detailId) {
        this.details.removeIf(d -> id == detailId);
    }


}
