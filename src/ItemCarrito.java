import java.util.ArrayList;
public class ItemCarrito {
    private Articulo articulo;
    private int catidad;

    public ItemCarrito(int cantidad, Articulo articulo) {
        this.articulo = articulo;
        this.catidad = cantidad;
    }
    public double getSubtotal() {
        return articulo.getPrecioLista() * this.catidad;
    }
    public Articulo getArticulo() {
        return articulo;
    }
    public int getCantidad() {
        return catidad;
    }
}
