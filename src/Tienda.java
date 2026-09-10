import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tienda {
    private List<Articulo> catalogo;
    private List<Usuario> usuarios;
    private List<Carrito> ventasRealizadas;

    public Tienda() {
        this.catalogo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.ventasRealizadas = new ArrayList<>();
    }

    public void agregarArticulo(Articulo articulo) {
        catalogo.add(articulo);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean procesarVenta(Carrito carrito, MetodoPago metodo) {
        if (carrito.pagar(metodo)) {
            ventasRealizadas.add(carrito);
            return true;
        }
        return false;
    }

    /*
     Justificación del uso de Map:
     Se optó por utilizar la estructura Map<Usuario, Double> (y Map<Usuario, Integer>) por las siguientes razones:

        Agrupación de datos dispersos:
        Un mismo usuario puede tener múltiples compras registradas en distintos carritos a lo largo del tiempo.
        El Map permite unificar y acumular el historial de cada cliente asociando un objeto único (Usuario) a un valor totalizador.

        Eficiencia algorítmica:
        Se evita el uso de ciclos for anidados (que serían necesarios si se usara una List para buscar si el cliente ya existe).

        Acceso directo mediante clave:
        Métodos como get() o getOrDefault() permiten acceder, sumar y actualizar los valores
        (gastos o cantidad de compras) de manera inmediata e intuitiva en una sola línea de código,
        mejorando la legibilidad y el rendimiento del sistema.

    */

    // Cliente que más dinero gastó en total (mayor volumen de dinero)
    public Usuario obtenerClienteMasCompradorPorMonto() {
        return obtenerClaveConMaximoValor(agruparGastoPorCliente());
    }

    // Cliente que más transacciones/carritos realizó (mayor frecuencia de compra)
    public Usuario obtenerClienteMasCompradorPorCantidad() {
        return obtenerClaveConMaximoValor(agruparCantidadComprasPorCliente());
    }

    // Retorna el artículo con más unidades vendidas en total
    public Articulo obtenerArticuloMasVendido() {
        return obtenerClaveConMaximoValor(agruparCantidadPorArticulo());
    }

    // Agrupa el dinero gastado por cada cliente
    private Map<Usuario, Double> agruparGastoPorCliente() {
        Map<Usuario, Double> gastoPorCliente = new HashMap<>();
        for (Carrito venta : ventasRealizadas) {
            Usuario cliente = venta.getCliente();
            double totalVenta = venta.calcularTotal();
            gastoPorCliente.put(cliente, gastoPorCliente.getOrDefault(cliente, 0.0) + totalVenta);
        }
        return gastoPorCliente;
    }

    // Agrupa la cantidad de compras (carritos) por cliente
    private Map<Usuario, Integer> agruparCantidadComprasPorCliente() {
        Map<Usuario, Integer> comprasPorCliente = new HashMap<>();
        for (Carrito venta : ventasRealizadas) {
            Usuario cliente = venta.getCliente();
            comprasPorCliente.put(cliente, comprasPorCliente.getOrDefault(cliente, 0) + 1);
        }
        return comprasPorCliente;
    }

    // Agrupa y suma las unidades vendidas de cada artículo
    private Map<Articulo, Integer> agruparCantidadPorArticulo() {
        Map<Articulo, Integer> cantidadPorArticulo = new HashMap<>();

        for (Carrito venta : ventasRealizadas) {
            for (ItemCarrito item : venta.getItems()) {
                Articulo articulo = item.getArticulo();
                int cantidad = item.getCantidad();
                cantidadPorArticulo.put(articulo, cantidadPorArticulo.getOrDefault(articulo, 0) + cantidad);
            }
        }

        return cantidadPorArticulo;
    }

    // Recibe cualquier Mapa y devuelve la clave asociada al valor más grande
    private <Key, Value extends Comparable<Value>> Key obtenerClaveConMaximoValor(Map<Key, Value> mapa) {
        if (mapa == null || mapa.isEmpty()) return null;

        Key claveTop = null;
        Value maxValor = null;

        for (Map.Entry<Key, Value> registro : mapa.entrySet()) {
            if (maxValor == null || registro.getValue().compareTo(maxValor) > 0) {
                maxValor = registro.getValue();
                claveTop = registro.getKey();
            }
        }

        return claveTop;
    }

}
/* Retorna el cliente que más plata gastó en el total de todas sus compras
    public Usuario obtenerClienteMasComprador() {
        if (ventasRealizadas.isEmpty()) return null;

        //Como el usuario puede haber realizado 3 o 4 compras distintas guardadas en carritos diferentes se uso un Map para evitar varios ciclos for y reemplazarlos por metodos del Map com get() o getOrDefault()
        Map<Usuario, Double> gastoPorCliente = new HashMap<>();
        for (Carrito venta : ventasRealizadas) {
            Usuario cliente = venta.getCliente();
            double totalVenta = venta.calcularTotal();
            gastoPorCliente.put(cliente, gastoPorCliente.getOrDefault(cliente, 0.0) + totalVenta);//Si es la primera vez que aparece el cliente, parte de 0.0 y le suma el total de ese carrito
        }                                                                                                   //Si el cliente ya existía en el mapa, toma lo que llevaba gastado en compras anteriores y le suma el monto de la nueva venta

        Usuario clienteTop = null;
        double maxGasto = -1;
        for (Map.Entry<Usuario, Double> clienteGasto : gastoPorCliente.entrySet()) {
            if (clienteGasto.getValue() > maxGasto) {
                clienteTop = clienteGasto.getKey(); //Clave, objeto Usuario
                maxGasto = clienteGasto.getValue(); //Valor, el gasto
            }
        }
        return clienteTop;
    }*/