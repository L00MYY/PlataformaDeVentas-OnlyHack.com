package model;

public class paymentCreditCard extends payment {

    public paymentCreditCard() {}

    public paymentCreditCard(int id, int idPedido, double monto, String fecha) {
        super(id, idPedido, monto, fecha);
    }

    @Override
    public String getMetodoPago() {
        return "TARJETA_CREDITO";
    }
}
