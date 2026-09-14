import modelo.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarritoTest {

    @Test
    public void testAgregarCantidadNegativas() {

        Usuario cliente = new Usuario(1, "Facundo", "facu@email.com");
        Carrito carrito = new Carrito(1, cliente);
        Articulo articulo = new Articulo(1, "Mouse", "Gamer", 10, 15000.0);

        boolean pudoAgregar = carrito.agregarArticulo(articulo, -2);

        assertFalse(pudoAgregar, "El carrito no debería aceptar cantidades negativas");
        assertEquals(0, carrito.calcularTotal(), "El total debe ser 0 porque no se agregó nada");
    }

}