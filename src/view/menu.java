package view;

import model.customer;
import service.customerService;

import java.util.List;
import java.util.Scanner;

public class menu {
    private final customerService customerService;
    private final Scanner scanner;

    public menu() {
        this.customerService = new customerService();
        this.scanner = new Scanner(System.in);
    }

    public menu(customerService customerService, Scanner scanner) {
        this.customerService = customerService;
        this.scanner = scanner;
    }

    public void mostrarMenuClientes() {
        int opcion;

        do {
            System.out.println("\n=== MENU DE CLIENTES ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Editar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Buscar cliente");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    editarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    buscarCliente();
                    break;
                case 6:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 6);
    }

    private void registrarCliente() {
        System.out.println("\n--- Registrar Cliente ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("DUI (00000000-0): ");
        String dui = scanner.nextLine();
        System.out.print("Telefono (8 digitos): ");
        String telefono = scanner.nextLine();

        try {
            customer nuevo = customerService.createCustomer(nombre, dui, telefono);
            System.out.println("Cliente registrado con exito. ID: " + nuevo.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        List<customer> clientes = customerService.listCustomers();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (customer cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private void editarCliente() {
        System.out.println("\n--- Editar Cliente ---");
        System.out.print("ID del cliente: ");
        int id = leerEntero();

        if (customerService.findCustomerById(id) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo DUI (00000000-0): ");
        String dui = scanner.nextLine();
        System.out.print("Nuevo telefono (8 digitos): ");
        String telefono = scanner.nextLine();

        try {
            boolean editado = customerService.updateCustomer(id, nombre, dui, telefono);
            if (editado) {
                System.out.println("Cliente editado con exito.");
            } else {
                System.out.println("No se pudo editar el cliente.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al editar cliente: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        System.out.println("\n--- Eliminar Cliente ---");
        System.out.print("ID del cliente a eliminar: ");
        int id = leerEntero();

        boolean eliminado = customerService.deleteCustomer(id);
        if (eliminado) {
            System.out.println("Cliente eliminado con exito.");
        } else {
            System.out.println("No existe un cliente con ese ID.");
        }
    }

    private void buscarCliente() {
        System.out.println("\n--- Buscar Cliente ---");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por nombre");
        System.out.println("3. Buscar por DUI");
        System.out.print("Seleccione una opcion: ");
        int opcion = leerEntero();

        switch (opcion) {
            case 1:
                buscarPorId();
                break;
            case 2:
                buscarPorNombre();
                break;
            case 3:
                buscarPorDui();
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    private void buscarPorId() {
        System.out.print("Ingrese ID: ");
        int id = leerEntero();

        customer cliente = customerService.findCustomerById(id);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println(cliente);
    }

    private void buscarPorNombre() {
        System.out.print("Ingrese nombre a buscar: ");
        String nombre = scanner.nextLine();
        List<customer> resultados = customerService.findCustomersByName(nombre);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron clientes.");
            return;
        }

        for (customer cliente : resultados) {
            System.out.println(cliente);
        }
    }

    private void buscarPorDui() {
        System.out.print("Ingrese DUI (00000000-0): ");
        String dui = scanner.nextLine();

        customer cliente = customerService.findCustomerByDui(dui);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println(cliente);
    }

    private int leerEntero() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
}
