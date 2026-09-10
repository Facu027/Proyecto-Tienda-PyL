public class MercadoPago implements MetodoPago{
    private String emailCuenta;

    public MercadoPago(String emailCuenta) {
        this.emailCuenta = emailCuenta;
    }

    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago de $" + monto + " vía Mercado Pago (" + emailCuenta + ")");
        return true;
    }
}
