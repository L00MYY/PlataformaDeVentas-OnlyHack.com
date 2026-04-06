package model;

public class paymentDebitCard extends payment {

    public paymentDebitCard() {}

    public paymentDebitCard(int id, int idPedido, double monto, String fecha) {
        super(id, idPedido, monto, fecha);
    }

    @Override
    public String getMetodoPago() {
        return "TARJETA_DEBITO";
    }
}
