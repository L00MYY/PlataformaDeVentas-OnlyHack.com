package service;

import enums.OrderStatus;
import model.order;
import model.orderDetail;
import model.product;

import java.util.ArrayList;
import java.util.List;

public class orderService {
    private final List<order> pedidos = new ArrayList<>();
    private int siguienteIdPedido = 1;
    private int siguienteIdDetalle = 1;

    private final customerService servicioClientes;
    private final productService servicioProductos;

    public orderService(customerService servicioClientes, productService servicioProductos) {
        this.servicioClientes = servicioClientes;
        this.servicioProductos = servicioProductos;
    }

    public order crearPedido(int idCliente) {
        if (!servicioClientes.existeCliente(idCliente)) {
            return null;
        }

        order nuevoPedido = new order();
        nuevoPedido.setId(siguienteIdPedido++);
        nuevoPedido.setIdCliente(idCliente);
        nuevoPedido.setEstado(OrderStatus.PENDIENTE);
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }

    public boolean agregarProducto(int idPedido, int idProducto, int cantidad) {
        order pedidoActual = buscarPedido(idPedido);
        if (pedidoActual == null || pedidoActual.getEstado() != OrderStatus.PENDIENTE) {
            return false;
        }
        if (!servicioProductos.hayStock(idProducto, cantidad)) {
            return false;
        }

        product productoActual = servicioProductos.obtenerProducto(idProducto);
        if (productoActual == null) {
            return false;
        }

        orderDetail detalle = new orderDetail();
        detalle.setId(siguienteIdDetalle++);
        detalle.setIdPedido(idPedido);
        detalle.setIdProducto(idProducto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(productoActual.getPrecio());

        pedidoActual.getDetalles().add(detalle);
        return true;
    }

    public double calcularSubtotal(int idPedido) {
        order pedidoActual = buscarPedido(idPedido);
        if (pedidoActual == null) {
            return 0;
        }

        double subtotal = 0;
        for (orderDetail detalle : pedidoActual.getDetalles()) {
            subtotal += detalle.getCantidad() * detalle.getPrecioUnitario();
        }
        return subtotal;
    }

    public double calcularImpuestos(int idPedido) {
        return calcularSubtotal(idPedido) * 0.13;
    }

    public double calcularTotal(int idPedido) {
        return calcularSubtotal(idPedido) + calcularImpuestos(idPedido);
    }

    public boolean finalizarPedido(int idPedido) {
        order pedidoActual = buscarPedido(idPedido);
        if (pedidoActual == null || pedidoActual.getDetalles().isEmpty()) {
            return false;
        }

        for (orderDetail detalle : pedidoActual.getDetalles()) {
            servicioProductos.actualizarStock(detalle.getIdProducto(), detalle.getCantidad());
        }

        pedidoActual.setEstado(OrderStatus.PAGADO);
        return true;
    }

    public order buscarPedido(int idPedido) {
        for (order pedidoActual : pedidos) {
            if (pedidoActual.getId() == idPedido) {
                return pedidoActual;
            }
        }
        return null;
    }

    public List<order> listarPedidosPorCliente(int idCliente) {
        List<order> resultado = new ArrayList<>();
        for (order pedidoActual : pedidos) {
            if (pedidoActual.getIdCliente() == idCliente) {
                resultado.add(pedidoActual);
            }
        }
        return resultado;
    }

    public boolean puedeSerPagado(int idPedido) {
        order pedidoActual = buscarPedido(idPedido);
        return pedidoActual != null
                && !pedidoActual.getDetalles().isEmpty()
                && pedidoActual.getEstado() == OrderStatus.PENDIENTE;
    }

    public boolean existePedido(int idPedido) {
        return buscarPedido(idPedido) != null;
    }
}
