package service;

import enums.PaymentMethods;
import model.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class paymentService {

    private List<payment> pagos;
    private orderService servicioPedidos;
    private static int siguienteId = 1;

    public paymentService(orderService servicioPedidos) {
        this.pagos = new ArrayList<>();
        this.servicioPedidos = servicioPedidos;
    }

    public payment procesarPago(int idPedido, PaymentMethods metodo) {
        if (!servicioPedidos.existePedido(idPedido)) {
            return null;
        }

        if (!servicioPedidos.puedeSerPagado(idPedido)) {
            return null;
        }

        if (existePagoParaPedido(idPedido)) {
            return null;
        }

        double total = servicioPedidos.calcularTotal(idPedido);

        payment pago = new payment();
        pago.setId(siguienteId++);
        pago.setIdPedido(idPedido);
        pago.setMonto(total);
        pago.setMetodoPago(metodo);
        pago.setFecha(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        pagos.add(pago);
        servicioPedidos.finalizarPedido(idPedido);

        return pago;
    }

    private boolean existePagoParaPedido(int idPedido) {
        return pagos.stream().anyMatch(pago -> pago.getIdPedido() == idPedido);
    }

    public List<payment> buscarPagosPorPedido(int idPedido) {
        return pagos.stream()
                .filter(pago -> pago.getIdPedido() == idPedido)
                .collect(Collectors.toList());
    }

    public List<payment> listarPagos() {
        return new ArrayList<>(pagos);
    }

    public List<payment> listarPagosPorFecha(String fecha) {
        return pagos.stream()
                .filter(pago -> pago.getFecha().startsWith(fecha))
                .collect(Collectors.toList());
    }
}
