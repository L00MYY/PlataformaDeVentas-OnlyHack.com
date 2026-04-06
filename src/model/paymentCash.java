package model;

public class paymentCash extends payment {

    public paymentCash() {}

    public paymentCash(int id, int idPedido, double monto, String fecha) {
        super(id, idPedido, monto, fecha);
    }

    @Override
    public String getMetodoPago() {
        return "EFECTIVO";
    }
}
