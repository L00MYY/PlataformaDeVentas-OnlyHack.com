package service;

import enums.OrderStatus;
import model.order;
import model.orderDetail;
import model.product;

import java.util.ArrayList;
import java.util.List;

public class orderService {
    private final List<order> orders = new ArrayList<>();
    private int nextOrderId = 1;
    private int nextDetailId = 1;

    private final customerService customerService;
    private final productService productService;

    public orderService(customerService customerService, productService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    /**
     * Crea un pedido solo si el cliente existe.
     */
    public order createOrder(int customerId) {
        if (!customerService.customerExists(customerId)) {
            return null;
        }

        order newOrder = new order();
        newOrder.setId(nextOrderId++);
        newOrder.setCustomerId(customerId);
        newOrder.setStatus(OrderStatus.PENDIENTE);
        orders.add(newOrder);
        return newOrder;
    }

    /**
     * Agrega un producto a un pedido pendiente.
     */
    public boolean addProductToOrder(int orderId, int productId, int quantity) {
        order currentOrder = findOrderById(orderId);
        if (currentOrder == null || currentOrder.getStatus() != OrderStatus.PENDIENTE) {
            return false;
        }
        if (!productService.hasStock(productId, quantity)) {
            return false;
        }

        product currentProduct = productService.getProductById(productId);
        if (currentProduct == null) {
            return false;
        }

        orderDetail detail = new orderDetail();
        detail.setId(nextDetailId++);
        detail.setOrderId(orderId);
        detail.setProductId(productId);
        detail.setQuantity(quantity);
        detail.setUnitPrice(currentProduct.getPrecio());

        currentOrder.getDetails().add(detail);
        return true;
    }

    /**
     * Quita un detalle del pedido.
     */
    public boolean removeProductFromOrder(int orderId, int detailId) {
        order currentOrder = findOrderById(orderId);
        if (currentOrder == null || currentOrder.getStatus() != OrderStatus.PENDIENTE) {
            return false;
        }
        return currentOrder.getDetails().removeIf(d -> d.getId() == detailId);
    }

    public double calculateSubtotal(int orderId) {
        order currentOrder = findOrderById(orderId);
        if (currentOrder == null) {
            return 0;
        }

        double subtotal = 0;
        for (orderDetail detail : currentOrder.getDetails()) {
            subtotal += detail.getQuantity() * detail.getUnitPrice();
        }
        return subtotal;
    }

    public double calculateTaxes(int orderId) {
        return calculateSubtotal(orderId) * 0.13;
    }

    public double calculateOrderTotal(int orderId) {
        return calculateSubtotal(orderId) + calculateTaxes(orderId);
    }

    /**
     * Finaliza pedido y descuenta stock.
     */
    public boolean finalizeOrder(int orderId) {
        order currentOrder = findOrderById(orderId);
        if (currentOrder == null || currentOrder.getDetails().isEmpty()) {
            return false;
        }

        for (orderDetail detail : currentOrder.getDetails()) {
            productService.updateStock(detail.getProductId(), detail.getQuantity());
        }

        currentOrder.setStatus(OrderStatus.PAGADO);
        return true;
    }

    public order findOrderById(int orderId) {
        for (order currentOrder : orders) {
            if (currentOrder.getId() == orderId) {
                return currentOrder;
            }
        }
        return null;
    }

    public List<order> listOrdersByCustomer(int customerId) {
        List<order> result = new ArrayList<>();
        for (order currentOrder : orders) {
            if (currentOrder.getCustomerId() == customerId) {
                result.add(currentOrder);
            }
        }
        return result;
    }

    public boolean canBePaid(int orderId) {
        order currentOrder = findOrderById(orderId);
        return currentOrder != null
                && !currentOrder.getDetails().isEmpty()
                && currentOrder.getStatus() == OrderStatus.PENDIENTE;
    }

    public boolean orderExists(int orderId) {
        return findOrderById(orderId) != null;
    }

    // Metodos de compatibilidad con nombres previos
    public order crearPedido(int idCliente) {
        return createOrder(idCliente);
    }

    public boolean agregarProducto(int idPedido, int idProducto, int cantidad) {
        return addProductToOrder(idPedido, idProducto, cantidad);
    }

    public boolean quitarProducto(int idPedido, int idDetalle) {
        return removeProductFromOrder(idPedido, idDetalle);
    }

    public double calcularSubtotal(int idPedido) {
        return calculateSubtotal(idPedido);
    }

    public double calcularImpuestos(int idPedido) {
        return calculateTaxes(idPedido);
    }

    public double calcularTotal(int idPedido) {
        return calculateOrderTotal(idPedido);
    }

    public boolean finalizarPedido(int idPedido) {
        return finalizeOrder(idPedido);
    }

    public order buscarPedido(int idPedido) {
        return findOrderById(idPedido);
    }

    public List<order> listarPedidosPorCliente(int idCliente) {
        return listOrdersByCustomer(idCliente);
    }

    public boolean puedeSerPagado(int idPedido) {
        return canBePaid(idPedido);
    }
}
