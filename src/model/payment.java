package model;

public abstract class payment {
    private int id;
    private int idPedido;
    private double monto;
    private String fecha; // Format: dd/MM/yyyy HH:mm

    public payment() {}

    public payment(int id, int idPedido, double monto, String fecha) {
        this.id = id;
        this.idPedido = idPedido;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public abstract String getMetodoPago();
}
