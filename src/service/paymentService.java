package service;

import enums.PaymentMethods;
import model.payment;
import model.paymentCash;
import model.paymentCreditCard;
import model.paymentDebitCard;

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

        payment pago;
        switch (metodo) {
            case EFECTIVO:
                pago = new paymentCash();
                break;
            case TARJETA_CREDITO:
                pago = new paymentCreditCard();
                break;
            case TARJETA_DEBITO:
                pago = new paymentDebitCard();
                break;
            default:
                return null;
        }

        pago.setId(siguienteId++);
        pago.setIdPedido(idPedido);
        pago.setMonto(total);
        pago.setFecha(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        pagos.add(pago);
        servicioPedidos.finalizarPedido(idPedido);

        return pago;
    }

    private boolean existePagoParaPedido(int idPedido) {
        return pagos.stream().anyMatch(pago -> pago.getIdPedido() == idPedido);
    }

    public List<payment> buscarPagosPorPedido(int idPedido) {
        // Esto solo deja pasar pagos con idPedido que sean iguales al valor del idPedido
        //Stream se usa para filtrar y transformar, en este caso se usa para filtrar el idPedido
        // en pago -> ... esto es una expresion lambda que es una funcion anonima para cada objeto pago
        // DESPUES De filtrarse el .collectors devuelve los objetos filtrados en una lista nuevamente
        return pagos.stream() .filter(pago -> pago.getIdPedido() == idPedido).collect(Collectors.toList());
    }

    public List<payment> listarPagos() {
        return new ArrayList<>(pagos);
    }

}
