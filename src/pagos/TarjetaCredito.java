package pagos;

public class TarjetaCredito implements MetodoPago {
    private String numeroTarjeta;
    private String fechaVencimiento;

    public TarjetaCredito(String numeroTarjeta, String fechaVencimiento) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago de $" + monto + " vía Tarjeta de Crédito (" + numeroTarjeta + ")");
        return true;
    }
}
