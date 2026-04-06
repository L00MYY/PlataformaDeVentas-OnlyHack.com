package model;

public class orderDetail {
    private int id;
    private int idPedido;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;

    // Sobrecarga de constructores

    // Creams uno vacio
    public orderDetail() {}
    //Cuando ya tenemos todos los parametros, cuando ya lo tenemos completo se usa este
    public orderDetail(int id, int idPedido, int idProducto, int cantidad, double precioUnitario) {
        this.id = id;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}
