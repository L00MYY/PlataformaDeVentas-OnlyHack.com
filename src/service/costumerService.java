package service;

import model.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class costumerService {
    private static final Pattern DUI_PATTERN = Pattern.compile("^\\d{8}-\\d$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{8}$");

    private final List<customer> clientes;
    private int nextId;

    public costumerService() {
        this.clientes = new ArrayList<>();
        this.nextId = 1;
    }

    /**
     * Crea y registra un nuevo cliente en memoria.
     */
    public customer crearCliente(String nombre, String dui, String telefono) {
        validarDatosCliente(nombre, dui, telefono, -1);

        customer nuevoCliente = new customer(nextId++, nombre.trim(), dui.trim(), telefono.trim());
        clientes.add(nuevoCliente);
        return nuevoCliente;
    }

    /**
     * Retorna una copia de la lista actual de clientes.
     */
    public List<customer> listarClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Edita los datos de un cliente existente por ID.
     */
    public boolean editarCliente(int id, String nombre, String dui, String telefono) {
        customer cliente = buscarCliente(id);
        if (cliente == null) {
            return false;
        }

        validarDatosCliente(nombre, dui, telefono, id);

        cliente.setcostumerName(nombre.trim());
        cliente.setDui(dui.trim());
        cliente.setPhone(telefono.trim());
        return true;
    }

    /**
     * Elimina un cliente por ID.
     */
    public boolean eliminarCliente(int id) {
        return clientes.removeIf(cliente -> cliente.getId() == id);
    }

    /**
     * Busca cliente por ID exacto.
     */
    public customer buscarCliente(int id) {
        for (customer cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Busca clientes por nombre (contiene, sin importar mayúsculas/minúsculas).
     */
    public List<customer> buscarPorNombre(String nombre) {
        List<customer> resultados = new ArrayList<>();
        if (nombre == null || nombre.trim().isEmpty()) {
            return resultados;
        }

        String nombreBuscado = nombre.trim().toLowerCase(Locale.ROOT);
        for (customer cliente : clientes) {
            String nombreCliente = cliente.getcostumerName();
            if (nombreCliente != null && nombreCliente.toLowerCase(Locale.ROOT).contains(nombreBuscado)) {
                resultados.add(cliente);
            }
        }
        return resultados;
    }

    /**
     * Busca cliente por DUI exacto.
     */
    public customer buscarPorDUI(String dui) {
        if (dui == null) {
            return null;
        }

        String duiNormalizado = dui.trim();
        for (customer cliente : clientes) {
            if (duiNormalizado.equals(cliente.getDui())) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Valida formato exacto de DUI salvadoreño: 00000000-0
     */
    public boolean validarDUI(String dui) {
        return dui != null && DUI_PATTERN.matcher(dui.trim()).matches();
    }

    /**
     * Valida formato de teléfono: exactamente 8 dígitos.
     */
    public boolean validarTelefono(String telefono) {
        return telefono != null && PHONE_PATTERN.matcher(telefono.trim()).matches();
    }

    private void validarDatosCliente(String nombre, String dui, String telefono, int idActual) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (!validarDUI(dui)) {
            throw new IllegalArgumentException("DUI inválido. Formato requerido: 00000000-0.");
        }

        customer clienteConDui = buscarPorDUI(dui);
        if (clienteConDui != null && clienteConDui.getId() != idActual) {
            throw new IllegalArgumentException("El DUI ya está registrado.");
        }

        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("Teléfono inválido. Debe tener exactamente 8 dígitos.");
        }
    }
}
