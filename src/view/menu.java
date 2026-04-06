package view;

import enums.PaymentMethods;
import model.customer;
import model.order;
import model.orderDetail;
import model.payment;
import model.product;
import service.customerService;
import service.orderService;
import service.paymentService;
import service.productService;

import java.util.List;
import java.util.Scanner;

public class menu {
    private final Scanner scanner;
    private final customerService servicioClientes;
    private final productService servicioProductos;
    private final orderService servicioPedidos;
    private final paymentService servicioPagos;

    public menu() {
        this.scanner = new Scanner(System.in);
        this.servicioClientes = new customerService();
        this.servicioProductos = new productService();
        this.servicioPedidos = new orderService(servicioClientes, servicioProductos);
        this.servicioPagos = new paymentService(servicioPedidos);
    }
//MENU PRINCIPAL DEL CUAL SE DESGLOSAN LOS DEMAS MENUS
    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    menuProductos();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 3:
                    menuPedidos();
                    break;
                case 4:
                    menuPagos();
                    break;
                case 5:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 5);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE VENTAS ===");
        System.out.println("1. Gestion de productos");
        System.out.println("2. Gestion de clientes");
        System.out.println("3. Gestion de pedidos");
        System.out.println("4. Gestion de pagos");
        System.out.println("5. Salir");
    }



// AQUI INICIA MENU PRODUCTOS
    private void menuProductos() {
        int opcion;

        do {
            System.out.println("\n--- GESTION DE PRODUCTOS ---");
            System.out.println("1. Crear producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Buscar producto");
            System.out.println("6. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    crearProducto();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    editarProducto();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    buscarProducto();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 6);
    }

    private void crearProducto() {
        System.out.println("\n--- NUEVO PRODUCTO ---");
        String nombre = leerTexto("Nombre: ");
        double precio = leerDouble("Precio: ");
        int stock = leerEntero("Stock: ");

        try {
            product nuevoProducto = servicioProductos.crearProducto(nombre, precio, stock);
            System.out.println("Producto creado con exito. ID: " + nuevoProducto.getIdProducto());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }
    }

    private void listarProductos() {
        List<product> productos = servicioProductos.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        for (product producto : productos) {
            System.out.println(producto);
        }
    }

    private void editarProducto() {
        System.out.println("\n--- EDITAR PRODUCTO ---");
        int id = leerEntero("ID del producto: ");
        product producto = servicioProductos.obtenerProducto(id);

        if (producto == null) {
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        double precio = leerDouble("Nuevo precio: ");
        int stock = leerEntero("Nuevo stock: ");

        try {
            boolean editado = servicioProductos.editarProducto(id, nombre, precio, stock);
            if (editado) {
                System.out.println("Producto editado con exito.");
            } else {
                System.out.println("No se pudo editar el producto.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al editar producto: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        int id = leerEntero("ID del producto: ");
        boolean eliminado = servicioProductos.eliminarProducto(id);

        if (eliminado) {
            System.out.println("Producto eliminado con exito.");
        } else {
            System.out.println("No existe un producto con ese ID.");
        }
    }

    private void buscarProducto() {
        System.out.println("\n--- BUSCAR PRODUCTO ---");
        String nombre = leerTexto("Nombre a buscar: ");
        List<product> resultados = servicioProductos.buscarPorNombre(nombre);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos.");
            return;
        }

        for (product producto : resultados) {
            System.out.println(producto);
        }
    }


// MENU CLIENTES
    private void menuClientes() {
        int opcion;

        do {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Editar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Buscar cliente");
            System.out.println("6. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

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
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 6);
    }

    private void registrarCliente() {
        System.out.println("\n--- NUEVO CLIENTE ---");
        String nombre = leerTexto("Nombre: ");
        String dui = leerTexto("DUI (00000000-0): ");
        String telefono = leerTexto("Telefono 8 números sin guión: ");

        try {
            customer nuevoCliente = servicioClientes.crearCliente(nombre, dui, telefono);
            System.out.println("Cliente registrado con exito. ID: " + nuevoCliente.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    private void listarClientes() {
        List<customer> clientes = servicioClientes.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n--- LISTA DE CLIENTES ---");
        for (customer cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private void editarCliente() {
        System.out.println("\n--- EDITAR CLIENTE ---");
        int id = leerEntero("ID del cliente: ");

        if (servicioClientes.buscarCliente(id) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        String nombre = leerTexto("Nuevo nombre: ");
        String dui = leerTexto("Nuevo DUI (00000000-0): ");
        String telefono = leerTexto("Nuevo telefono (8 digitos): ");

        try {
            boolean editado = servicioClientes.editarCliente(id, nombre, dui, telefono);
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
        System.out.println("\n--- ELIMINAR CLIENTE ---");
        int id = leerEntero("ID del cliente: ");
        boolean eliminado = servicioClientes.eliminarCliente(id);

        if (eliminado) {
            System.out.println("Cliente eliminado con exito.");
        } else {
            System.out.println("No existe un cliente con ese ID.");
        }
    }

    private void buscarCliente() {
        int opcion;

        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por nombre");
        System.out.println("3. Buscar por DUI");
        opcion = leerEntero("Seleccione una opcion: ");

        switch (opcion) {
            case 1:
                buscarClientePorId();
                break;
            case 2:
                buscarClientePorNombre();
                break;
            case 3:
                buscarClientePorDui();
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    private void buscarClientePorId() {
        int id = leerEntero("ID: ");
        customer cliente = servicioClientes.buscarCliente(id);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println(cliente);
    }

    private void buscarClientePorNombre() {
        String nombre = leerTexto("Nombre: ");
        List<customer> resultados = servicioClientes.buscarPorNombre(nombre);

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron clientes.");
            return;
        }

        for (customer cliente : resultados) {
            System.out.println(cliente);
        }
    }

    private void buscarClientePorDui() {
        String dui = leerTexto("DUI: ");
        customer cliente = servicioClientes.buscarPorDUI(dui);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println(cliente);
    }



// AQUI INICIA MENU PEDIDOS
    private void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n--- GESTION DE PEDIDOS ---");
            System.out.println("1. Crear pedido");
            System.out.println("2. Agregar producto a pedido");
            System.out.println("3. Ver pedido");
            System.out.println("4. Calcular total del pedido");
            System.out.println("5. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    crearPedido();
                    break;
                case 2:
                    agregarProductoAPedido();
                    break;
                case 3:
                    verPedido();
                    break;
                case 4:
                    calcularTotalPedido();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 5);
    }

    private void crearPedido() {
        System.out.println("\n--- CREAR PEDIDO ---");
        listarClientes();
        int idCliente = leerEntero("ID del cliente: ");

        order nuevoPedido = servicioPedidos.crearPedido(idCliente);
        if (nuevoPedido == null) {
            System.out.println("No se pudo crear el pedido. El cliente no existe.");
            return;
        }

        System.out.println("Pedido creado con exito. ID: " + nuevoPedido.getId());
    }

    private void agregarProductoAPedido() {
        System.out.println("\n--- AGREGAR PRODUCTO A PEDIDO ---");
        int idPedido = leerEntero("ID del pedido: ");

        if (!servicioPedidos.existePedido(idPedido)) {
            System.out.println("No existe un pedido con ese ID.");
            return;
        }

        listarProductos();
        int idProducto = leerEntero("ID del producto: ");
        int cantidad = leerEntero("Cantidad: ");

        boolean agregado = servicioPedidos.agregarProducto(idPedido, idProducto, cantidad);
        if (agregado) {
            System.out.println("Producto agregado con exito.");
        } else {
            System.out.println("No se pudo agregar el producto. Verifique el stock y el estado del pedido.");
        }
    }

    private void verPedido() {
        System.out.println("\n--- VER PEDIDO ---");
        int idPedido = leerEntero("ID del pedido: ");
        order pedido = servicioPedidos.buscarPedido(idPedido);

        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        if (pedido.getDetalles().isEmpty()) {
            System.out.println("El pedido no tiene productos.");
            return;
        }

        System.out.println("\nDetalles:");
        //FOR EACH
        for (orderDetail detalle : pedido.getDetalles()) {
            product producto = servicioProductos.obtenerProducto(detalle.getIdProducto());
            String nombreProducto = producto != null ? producto.getNombreProducto() : "Producto eliminado";

            System.out.println("Producto: " + nombreProducto);
        }
    }

    private void calcularTotalPedido() {
        System.out.println("\n CALCULAR TOTAL");
        int idPedido = leerEntero("ID del pedido: ");

        if (!servicioPedidos.existePedido(idPedido)) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        System.out.println("Subtotal: $" + servicioPedidos.calcularSubtotal(idPedido));
        System.out.println("Impuestos: $" + servicioPedidos.calcularImpuestos(idPedido));
        System.out.println("Total: $" + servicioPedidos.calcularTotal(idPedido));
    }



// MENU PAGOS
    private void menuPagos() {
        int opcion;

        do {
            System.out.println("\n--- GESTION DE PAGOS ---");
            System.out.println("1. Procesar pago");
            System.out.println("2. Ver pagos por pedido");
            System.out.println("3. Historial de pagos");
            System.out.println("4. Volver");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    procesarPago();
                    break;
                case 2:
                    verPagosPorPedido();
                    break;
                case 3:
                    listarPagos();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 4);
    }

    private void procesarPago() {
        System.out.println("\n--- PROCESAR PAGO ---");
        int idPedido = leerEntero("ID del pedido: ");

        if (!servicioPedidos.puedeSerPagado(idPedido)) {
            System.out.println("El pedido no esta listo para pago.");
            return;
        }

        PaymentMethods metodo = seleccionarMetodoPago();
        if (metodo == null) {
            System.out.println("Metodo de pago invalido.");
            return;
        }

        payment pago = servicioPagos.procesarPago(idPedido, metodo);
        if (pago == null) {
            System.out.println("No se pudo procesar el pago.");
            return;
        }

        System.out.println("Pago procesado con exito.");
        System.out.println("ID del pago: " + pago.getId());
        System.out.println("ID del pedido: " + pago.getIdPedido());
        System.out.println("Monto: $" + pago.getMonto());
        System.out.println("Metodo: " + pago.getMetodoPago());
        System.out.println("Fecha: " + pago.getFecha());
    }

    private PaymentMethods seleccionarMetodoPago() {
        System.out.println("\nMetodos de pago:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta de credito");
        System.out.println("3. Tarjeta de debito");

        int opcion = leerEntero("Seleccione una opcion: ");

        switch (opcion) {
            case 1:
                return PaymentMethods.EFECTIVO;
            case 2:
                return PaymentMethods.TARJETA_CREDITO;
            case 3:
                return PaymentMethods.TARJETA_DEBITO;
            default:
                return null;
        }
    }

    private void verPagosPorPedido() {
        System.out.println("\n--- PAGOS POR PEDIDO ---");
        int idPedido = leerEntero("ID del pedido: ");
        List<payment> pagos = servicioPagos.buscarPagosPorPedido(idPedido);

        if (pagos.isEmpty()) {
            System.out.println("No hay pagos para ese pedido.");
            return;
        }

        for (payment pago : pagos) {
            imprimirPago(pago);
        }
    }

    private void listarPagos() {
        List<payment> pagos = servicioPagos.listarPagos();
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados.");
            return;
        }

        System.out.println("\n--- HISTORIAL DE PAGOS ---");
        for (payment pago : pagos) {
            imprimirPago(pago);
        }
    }

    private void imprimirPago(payment pago) {
        System.out.println(
                "Pago #" + pago.getId()
                        + " | Pedido: " + pago.getIdPedido()
                        + " | Monto: $" + pago.getMonto()
                        + " | Metodo: " + pago.getMetodoPago()
                        + " | Fecha: " + pago.getFecha()
        );
    }

    private void imprimirResumenPedido(order entidadPedido) {
        //Aqui ejecutamos el override de order que resume/muestra los pedidos
        System.out.println(entidadPedido.resumen());
    }


    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}
