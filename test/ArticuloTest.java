import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.*;
class ArticuloTest {

    @Test
    void testReducirStockConCantidadValida() {

        Articulo articulo = new Articulo(1, "Auriculares", "Bluetooth", 10, 30000.0);

        boolean resultado = articulo.reducirStock(4);

        assertTrue(resultado, "Debería devolver true porque hay stock suficiente");
        assertEquals(6, articulo.getStock(), "El stock debería bajar de 10 a 6");
    }

    @Test
    void testReducirStockConCantidadInvalida() {

        Articulo articulo = new Articulo(2, "Monitor", "24 pulgadas", 5, 150000.0);

        boolean resultado = articulo.reducirStock(10); // Intentamos sacar 10, pero solo hay 5

        assertFalse(resultado, "Debería devolver false porque pide más de lo que hay en stock");
        assertEquals(5, articulo.getStock(), "El stock debe mantenerse intacto (5) si la operación falla");
    }

    @Test
    void testIncrementarStock() {

        Articulo articulo = new Articulo(3, "Mouse", "Gamer", 2, 15000.0);

        articulo.incrementarStock(5);

        assertEquals(7, articulo.getStock(), "El stock debería subir de 2 a 7");
    }

}