package service;

import model.product;

import java.util.ArrayList;
import java.util.List;

public class productService {
    private final List<product> productos = new ArrayList<>();
    private int siguienteId = 1;

    public product crearProducto(String nombre, double precio, int stock) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);

        product nuevoProducto = new product(siguienteId++, nombre.trim(), precio, stock);
        productos.add(nuevoProducto);
        return nuevoProducto;
    }

    public product obtenerProducto(int id) {
        for (product producto : productos) {
            if (producto.getIdProducto() == id) {
                return producto;
            }
        }
        return null;
    }

    public List<product> listarProductos() {
        return new ArrayList<>(productos);
    }

    public List<product> buscarPorNombre(String nombre) {
        String nombreBuscado = nombre == null ? "" : nombre.trim().toLowerCase();
        List<product> resultados = new ArrayList<>();

        for (product producto : productos) {
            if (producto.getNombreProducto().toLowerCase().contains(nombreBuscado)) {
                resultados.add(producto);
            }
        }

        return resultados;
    }

    public boolean editarProducto(int id, String nombre, double precio, int stock) {
        product producto = obtenerProducto(id);
        if (producto == null) {
            return false;
        }

        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);

        producto.setNombreProducto(nombre.trim());
        producto.setPrecio(precio);
        producto.setCantidadProducto(stock);
        return true;
    }

    public boolean eliminarProducto(int id) {
        return productos.removeIf(producto -> producto.getIdProducto() == id);
    }

    public boolean hayStock(int idProducto, int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        product producto = obtenerProducto(idProducto);
        return producto != null && producto.getCantidadProducto() >= cantidad;
    }

    public boolean actualizarStock(int idProducto, int cantidadDescontar) {
        product producto = obtenerProducto(idProducto);
        if (producto == null || cantidadDescontar <= 0) {
            return false;
        }
        if (producto.getCantidadProducto() < cantidadDescontar) {
            return false;
        }

        producto.setCantidadProducto(producto.getCantidadProducto() - cantidadDescontar);
        return true;
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }
    }

    private void validarPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    private void validarStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }
}
