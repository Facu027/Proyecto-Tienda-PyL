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
    }

    class Carrito {
        -int id
        -boolean pagado
        +agregarArticulo(Articulo articulo, int cantidad) boolean
        +calcularTotal() double
    }

    class Tienda {
        +procesarVenta(Carrito carrito, MetodoPago pago) boolean
    }

    Carrito --> Usuario : cliente
    Tienda o-- Articulo : inventario
    Tienda o-- Usuario : clientes
