package modelo;

public class Articulo {
    private int id;
    private String nombre;
    private String descripcion;
    private int stock;
    private double precioLista;

    public Articulo(int id, String nombre, String descripcion, int stock, double precioLista) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precioLista = precioLista;
    }

    public boolean reducirStock(int cantidad) {
        if (cantidad <= this.stock) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }
    public void incrementarStock(int cantidad) {
        this.stock += cantidad;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getStock() {
        return stock;
    }
    public double getPrecioLista() {
        return precioLista;
    }

}
