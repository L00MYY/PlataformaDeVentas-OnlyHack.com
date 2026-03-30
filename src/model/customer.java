package model;

public class customer {
    private int id;
    private String nombre;
    private String dui; // Format: 00000000-0
    private String telefono;


    public customer(int id, String nombre, String dui, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.dui = dui;
        this.telefono = telefono;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | DUI: " + dui + " | Tel: " + telefono;
    }
}
