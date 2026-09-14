import static org.junit.jupiter.api.Assertions.*;
import modelo.*;
import org.junit.jupiter.api.Test;
import pagos.*;
class TiendaTest {

    @Test
    public void testProcesarVentaExitosa() {

        Tienda tienda = new Tienda();
        Articulo articulo = new Articulo(1, "Mouse", "Gamer", 5, 15000.0);
        Usuario cliente = new Usuario(1, "Facundo", "facu@email.com");

        tienda.agregarArticulo(articulo);
        tienda.registrarUsuario(cliente);

        Carrito carrito = new Carrito(1, cliente);
        carrito.agregarArticulo(articulo, 2); // 2 Mouses = $30.000

        MetodoPago pago = new MercadoPago("facu@mp.com");

        boolean resultado = tienda.procesarVenta(carrito, pago);

        assertTrue(resultado, "La venta debería procesarse correctamente");
        assertEquals(3, articulo.getStock(), "El stock debería bajar de 5 a 3");
    }
    @Test
    public void testProcesarVentaConCarritoVacio() {

        Tienda tienda = new Tienda();
        Usuario cliente = new Usuario(2, "Carlos", "carlos@email.com");
        Carrito carritoVacio = new Carrito(2, cliente);
        MetodoPago pago = new MercadoPago("carlos@mp.com");

        boolean resultado = tienda.procesarVenta(carritoVacio, pago);

        assertFalse(resultado, "La tienda NO debería procesar una venta si el carrito está vacío");
    }
    @Test
    public void testIdentificacionDelClienteMasComprador() {
        // Arrange - Setup general
        Tienda tienda = new Tienda();
        Articulo notebook = new Articulo(1, "Notebook", "Gamer", 5, 500000.0);
        Articulo mouse = new Articulo(2, "Mouse", "Inalámbrico", 10, 15000.0);

        Usuario u1 = new Usuario(1, "Facundo Raspa", "facu@email.com");
        Usuario u2 = new Usuario(2, "Elizabeth Bellavilla", "eli@email.com");

        tienda.agregarArticulo(notebook);
        tienda.agregarArticulo(mouse);
        tienda.registrarUsuario(u1);
        tienda.registrarUsuario(u2);

        MetodoPago pago = new MercadoPago("pagos@mp.com");

        //Venta 1: Facundo compra 1 Notebook (Gasta mucho dinero de una vez: $500.000)
        Carrito c1 = new Carrito(1, u1);
        c1.agregarArticulo(notebook, 1);
        tienda.procesarVenta(c1, pago);

        // Venta 2 y 3: Elizabeth compra 2 Mouses en carritos distintos (Más cantidad de transacciones, pero gasta menos: $30.000)
        Carrito c2 = new Carrito(2, u2);
        c2.agregarArticulo(mouse, 1);
        tienda.procesarVenta(c2, pago);

        Carrito c3 = new Carrito(3, u2);
        c3.agregarArticulo(mouse, 1);
        tienda.procesarVenta(c3, pago);

        assertEquals(
                "Facundo Raspa",
                tienda.obtenerClienteMasCompradorPorMonto().getNombre(),
                "Facundo debería ser el ganador por monto ($500.000 vs $30.000)"
        );

        assertEquals(
                "Elizabeth Bellavilla",
                tienda.obtenerClienteMasCompradorPorCantidad().getNombre(),
                "Elizabeth debería ser la ganadora por cantidad de compras (2 carritos vs 1)"
        );

        assertEquals(
                "Mouse",
                tienda.obtenerArticuloMasVendido().getNombre(),
                "El Mouse debería ser el artículo más vendido (2 unidades vs 1 Notebook)"
        );
    }
}