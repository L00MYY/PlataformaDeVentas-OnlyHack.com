package service;

import model.customer;

import java.util.ArrayList;
import java.util.List;

public class customerService {
    private final List<customer> clientes = new ArrayList<>();
    private int siguienteId = 1;

    public customer crearCliente(String nombre, String dui, String telefono) {
        validarNombre(nombre);
        if (!validarDUI(dui)) {
            throw new IllegalArgumentException("El DUI no tiene un formato valido.");
        }
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("El telefono debe tener 8 digitos.");
        }
        if (buscarPorDUI(dui) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese DUI.");
        }

        customer nuevoCliente = new customer(siguienteId++, nombre.trim(), dui.trim(), telefono.trim());
        clientes.add(nuevoCliente);
        return nuevoCliente;
    }

    public List<customer> listarClientes() {
        return new ArrayList<>(clientes);
    }

    public boolean editarCliente(int id, String nombre, String dui, String telefono) {
        customer cliente = buscarCliente(id);
        if (cliente == null) {
            return false;
        }

        validarNombre(nombre);
        if (!validarDUI(dui)) {
            throw new IllegalArgumentException("El DUI no tiene un formato valido.");
        }
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("El telefono debe tener 8 digitos.");
        }

        customer clienteConMismoDui = buscarPorDUI(dui);
        if (clienteConMismoDui != null && clienteConMismoDui.getId() != id) {
            throw new IllegalArgumentException("Ya existe otro cliente con ese DUI.");
        }

        cliente.setNombre(nombre.trim());
        cliente.setDui(dui.trim());
        cliente.setTelefono(telefono.trim());
        return true;
    }

    public boolean eliminarCliente(int id) {
        return clientes.removeIf(cliente -> cliente.getId() == id);
    }

    public customer buscarCliente(int id) {
        for (customer cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<customer> buscarPorNombre(String nombre) {
        String nombreBuscado = nombre == null ? "" : nombre.trim().toLowerCase();
        List<customer> resultados = new ArrayList<>();

        for (customer cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(nombreBuscado)) {
                resultados.add(cliente);
            }
        }

        return resultados;
    }

    public customer buscarPorDUI(String dui) {
        if (dui == null) {
            return null;
        }

        String duiBuscado = dui.trim();
        for (customer cliente : clientes) {
            if (cliente.getDui().equals(duiBuscado)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean existeCliente(int id) {
        return buscarCliente(id) != null;
    }

    public boolean validarDUI(String dui) {
        return dui != null && dui.trim().matches("\\d{8}-\\d");
    }

    public boolean validarTelefono(String telefono) {
        return telefono != null && telefono.trim().matches("\\d{8}");
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
    }
}
