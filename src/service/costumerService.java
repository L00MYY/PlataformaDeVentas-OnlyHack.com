package service;

import model.customer;

import java.util.List;

/**
 * Alias de compatibilidad para mantener codigo legado.
 * La implementacion principal esta en customerService.
 */
public class costumerService {
    private final customerService delegate;

    public costumerService() {
        this.delegate = new customerService();
    }

    public customerService getDelegate() {
        return delegate;
    }

    public customer crearCliente(String nombre, String dui, String telefono) {
        return delegate.crearCliente(nombre, dui, telefono);
    }

    public List<customer> listarClientes() {
        return delegate.listarClientes();
    }

    public boolean editarCliente(int id, String nombre, String dui, String telefono) {
        return delegate.editarCliente(id, nombre, dui, telefono);
    }

    public boolean eliminarCliente(int id) {
        return delegate.eliminarCliente(id);
    }

    public customer buscarCliente(int id) {
        return delegate.buscarCliente(id);
    }

    public List<customer> buscarPorNombre(String nombre) {
        return delegate.buscarPorNombre(nombre);
    }

    public customer buscarPorDUI(String dui) {
        return delegate.buscarPorDUI(dui);
    }

    public boolean validarDUI(String dui) {
        return delegate.validarDUI(dui);
    }

    public boolean validarTelefono(String telefono) {
        return delegate.validarTelefono(telefono);
    }
}
