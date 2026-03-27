package service;

import model.product;

import java.util.ArrayList;
import java.util.List;

public class productService {
    private final List<product> products = new ArrayList<>();
    private int nextId = 1;

    public product createProduct(String name, double price, int stock) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        product newProduct = new product(nextId++, name.trim(), price, stock);
        products.add(newProduct);
        return newProduct;
    }

    public product getProductById(int id) {
        for (product p : products) {
            if (p.getIdProducto() == id) {
                return p;
            }
        }
        return null;
    }

    public List<product> listProducts() {
        return new ArrayList<>(products);
    }

    public boolean hasStock(int productId, int quantity) {
        if (quantity <= 0) {
            return false;
        }
        product p = getProductById(productId);
        return p != null && p.getCantidadProducto() >= quantity;
    }

    /**
     * Descuenta stock si hay disponibilidad suficiente.
     */
    public boolean updateStock(int productId, int quantityToDiscount) {
        product p = getProductById(productId);
        if (p == null || quantityToDiscount <= 0) {
            return false;
        }
        if (p.getCantidadProducto() < quantityToDiscount) {
            return false;
        }

        p.setCantidadProducto(p.getCantidadProducto() - quantityToDiscount);
        return true;
    }

    // Metodos de compatibilidad con nombres previos
    public boolean hayStock(int idProducto, int cantidad) {
        return hasStock(idProducto, cantidad);
    }

    public product obtenerProducto(int idProducto) {
        return getProductById(idProducto);
    }

    public boolean actualizarStock(int idProducto, int cantidadDescontar) {
        return updateStock(idProducto, cantidadDescontar);
    }
}
