import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private int id;
    private Usuario cliente;
    private List<ItemCarrito> items;
    private  boolean pagado;

    public Carrito(int id, Usuario cliente) {
        this.id = id;
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.pagado = false;
    }

    public boolean agregarArticulo(Articulo articulo, int cantidad) {
        if (articulo != null && cantidad > 0) {

            int cantidadCarrito = 0;
            for (int i = 0; i < items.size(); i++) {
                ItemCarrito item = items.get(i);
                if (item.getArticulo().getId() == articulo.getId()) {
                    cantidadCarrito += item.getCantidad();
                }
            }
            if (articulo.getStock() >= (cantidadCarrito + cantidad)) {
                items.add(new ItemCarrito(cantidad, articulo));
                return true;
            }
        }

        return false;
    }

    public double calcularTotal() {
        if (items == null || items.isEmpty()) return 0.0;

        double total = 0.0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getSubtotal();
        }
        return total;
    }

    public boolean pagar(MetodoPago metodo) {
        if (pagado || items.isEmpty()) return false;

        double total = calcularTotal();

        if (metodo.procesarPago(total)) {
            for (int i = 0; i < items.size(); i++) {
                ItemCarrito item = items.get(i);
                item.getArticulo().reducirStock(item.getCantidad());
            }
            this.pagado = true;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }
    public Usuario getCliente() {
        return cliente;
    }
    public List<ItemCarrito> getItems() {
        return items;
    }
    public boolean getPagado() {
        return pagado;
    }
}