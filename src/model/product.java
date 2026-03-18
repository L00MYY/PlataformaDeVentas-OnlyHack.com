package model;

public class product {
    private int idProducto;
    private String nombreProducto;
    private double precio;
    private int cantidadProducto; // stock

    public product() {}

    public product(int idProducto, String nombreProducto, double precio, int cantidadProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidadProducto = cantidadProducto;
    }

    // Getters y Setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidadProducto() { return cantidadProducto; }
    public void setCantidadProducto(int cantidadProducto) { this.cantidadProducto = cantidadProducto; }

    @Override
    public String toString() {
        return idProducto + " | " + nombreProducto + " | $" + precio + " | Stock: " + cantidadProducto;
    }

}
