```mermaid
classDiagram
    class Usuario {
        -int id
        -String nombre
        -String email
        +getId() int
    }

    class Articulo {
        -int id
        -String nombre
        -int stock
        +reducirStock(int cantidad) boolean
        +incrementarStock(int cantidad) void
    }

    class Carrito {
        -int id
        -boolean pagado
        +agregarArticulo(Articulo articulo, int cantidad) boolean
        +calcularTotal() double
        +pagar(MetodoPago metodo) boolean
    }

    class ItemCarrito {
        -Articulo articulo;
        -int catidad;
    }

    class Tienda {
        -List<Articulo> catalogo
        -List<Usuario> usuarios
        -List<Carrito> ventasRealizadas
        +procesarVenta(Carrito carrito, MetodoPago pago) boolean
        +obtenerClienteMasCompradorPorMonto() Usuario
        +obtenerClienteMasCompradorPorCantidad() Usuario
        +obtenerArticuloMasVendido() Articulo
    }

    Carrito --> Usuario : cliente
    Carrito *-- ItemCarrito : items
    ItemCarrito --> Articulo : articulo
    Tienda o-- Articulo : inventario
    Tienda o-- Usuario : clientes
