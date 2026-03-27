package service;

import enums.PaymentMethods;
import model.payment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class paymentService {

    private List<payment> payments;
    private orderService orderService;
    private static int nextId = 1;

    public paymentService(orderService orderService) {
        this.payments = new ArrayList<>();
        this.orderService = orderService;
    }

    public payment processPayment(int orderId, PaymentMethods method) {
        if (!orderService.orderExists(orderId)) {
            return null;
        }

        if (!orderService.canBePaid(orderId)) {
            return null;
        }

        if (hasPaymentForOrder(orderId)) {
            return null;
        }

        double total = orderService.calculateOrderTotal(orderId);

        payment payment = new payment();
        payment.setId(nextId++);
        payment.setOrderId(orderId);
        payment.setAmount(total);
        payment.setPaymentMethod(method);
        payment.setDate(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        payments.add(payment);

        orderService.finalizeOrder(orderId);

        return payment;
    }

    private boolean hasPaymentForOrder(int orderId) {
        return payments.stream().anyMatch(p -> p.getOrderId() == orderId);
    }

    public List<payment> findPaymentsByOrder(int orderId) {
        return payments.stream()
                .filter(p -> p.getOrderId() == orderId)
                .collect(Collectors.toList());
    }

    public List<payment> listAllPayments() {
        return new ArrayList<>(payments);
    }

    public List<payment> listPaymentsByDate(String date) {
        return payments.stream()
                .filter(p -> p.getDate().startsWith(date))
                .collect(Collectors.toList());
    }
}