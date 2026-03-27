package service;

import enums.OrderStatus;
import model.order;
import model.orderDetail;

import java.util.ArrayList;
import java.util.List;

public class orderService {
    private List<order> pedidos = new ArrayList<>();
    private int contadorPedidos = 1;
    private int contadorDetalles = 1;

    private customerService clienteServicio;
    private productService productoServicio;

    public orderService(customerService clienteServicio, productService productoServicio) {
        this.clienteServicio = clienteServicio;
        this.productoServicio = productoServicio;
    }

    public order crearPedido(int idCliente) {
        if (!clienteServicio.existeCliente(idCliente)) {
            return null;
        }

        order pedido = new order();
        pedido.setId(contadorPedidos++);
        pedido.setCustomerId(idCliente);
        pedido.setStatus(OrderStatus.PENDIENTE);

        pedidos.add(pedido);
        return pedido;
    }

    public boolean agregarProducto(int idPedido, int idProducto, int cantidad) {
        order pedido = buscarPedido(idPedido);


        if (pedido == null || pedido.getStatus() != OrderStatus.PENDIENTE) {
            return false;
        }

        if (!productoServicio.hayStock(idProducto, cantidad)) {
            return false;
        }


        model.product producto = productoServicio.obtenerProducto(idProducto);
        if (producto == null) return false;

        orderDetail detalle = new orderDetail();
        detalle.setId(contadorDetalles++);
        detalle.setOrderId(idPedido);
        detalle.setProductId(idProducto);
        detalle.setQuantity(cantidad);
        detalle.setUnitPrice(producto.getPrecio());

        pedido.getDetails().add(detalle);
        return true;
    }

    public boolean quitarProducto(int idPedido, int idDetalle) {
        order pedido = buscarPedido(idPedido);

        if (pedido == null || pedido.getStatus() != OrderStatus.PENDIENTE) {
            return false;
        }

        return pedido.getDetails().removeIf(d -> d.getId() == idDetalle);  // CORREGIDO: getId()
    }

    public double calcularSubtotal(int idPedido) {
        order pedido = buscarPedido(idPedido);
        if (pedido == null) return 0;

        double subtotal = 0;
        for (orderDetail d : pedido.getDetails()) {
            subtotal += d.getQuantity() * d.getUnitPrice();
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
        order pedido = buscarPedido(idPedido);
        if (pedido == null) return false;


        if (pedido.getDetails().isEmpty()) return false;


        for (orderDetail d : pedido.getDetails()) {
            productoServicio.actualizarStock(d.getProductId(), d.getQuantity());
        }


        pedido.setStatus(OrderStatus.PAGADO);
        return true;
    }

    public order buscarPedido(int idPedido) {
        for (order p : pedidos) {
            if (p.getId() == idPedido) {
                return p;
            }
        }
        return null;
    }


    public List<order> listarPedidosPorCliente(int idCliente) {
        List<order> lista = new ArrayList<>();
        for (order p : pedidos) {
            if (p.getCustomerId() == idCliente) {
                lista.add(p);
            }
        }
        return lista;
    }

    public boolean puedeSerPagado(int idPedido) {
        order pedido = buscarPedido(idPedido);
        return pedido != null &&
                !pedido.getDetails().isEmpty() &&
                pedido.getStatus() == OrderStatus.PENDIENTE; 
    }
}