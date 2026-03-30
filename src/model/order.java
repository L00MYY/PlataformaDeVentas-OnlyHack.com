package model;

import enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class order {
    private int id;
    private int idCliente;
    private String fecha; // Format: dd/MM/yyyy HH:mm
    private OrderStatus estado;
    private List<orderDetail> detalles;

    public order() {
        this.detalles = new ArrayList<>();
    }

    public order(int id, int idCliente, String fecha, OrderStatus estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
        this.detalles = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public OrderStatus getEstado() { return estado; }
    public void setEstado(OrderStatus estado) { this.estado = estado; }

    public List<orderDetail> getDetalles() { return detalles; }
    public void setDetalles(List<orderDetail> detalles) { this.detalles = detalles; }

    public void agregarDetalle(orderDetail detalle) {
        this.detalles.add(detalle);
    }

    public void quitarDetalle(int idDetalle) {
        this.detalles.removeIf(detalle -> detalle.getId() == idDetalle);
    }
}
