public class Test {
    public static void main(String[] args) {
        // 1. Instanciar la Tienda
        Tienda tienda = new Tienda();

        // 2. Registrar Artículos en el catálogo
        Articulo a1 = new Articulo(1, "Notebook Gamer", "Intel i7, 16GB RAM", 3, 500000.0);
        Articulo a2 = new Articulo(2, "Auriculares Bluetooth", "Cancelación de ruido", 10, 30000.0);
        Articulo a3 = new Articulo(3, "Mouse Inalámbrico", "Sensor óptico 1600 DPI", 5, 15000.0);

        tienda.agregarArticulo(a1);
        tienda.agregarArticulo(a2);
        tienda.agregarArticulo(a3);

        // 3. Registrar Usuarios
        Usuario u1 = new Usuario(101, "Facundo Raspa", "facundo@email.com");
        Usuario u2 = new Usuario(102, "Elizabeth Bellavilla", "elizabeth@email.com");
        Usuario u3 = new Usuario(103, "Carlos Perez", "carlos@email.com");

        tienda.registrarUsuario(u1);
        tienda.registrarUsuario(u2);
        tienda.registrarUsuario(u3);

        System.out.println("--- PRUEBA 1: Compra exitosa (Facundo: 1 Carrito - $530.000) ---");
        Carrito c1 = new Carrito(1, u1);
        c1.agregarArticulo(a1, 1); // 1 Notebook ($500.000)
        c1.agregarArticulo(a3, 2); // 2 Mouses ($30.000)

        MetodoPago pagoMP = new MercadoPago("facundo@mp.com");
        boolean venta1Exitosa = tienda.procesarVenta(c1, pagoMP);

        System.out.println("Venta 1 procesada correctamente: " + venta1Exitosa);
        System.out.println("Stock restante Notebook (Esperado: 2): " + a1.getStock());
        System.out.println("Stock restante Mouse (Esperado: 3): " + a3.getStock());
        System.out.println();

        System.out.println("--- PRUEBA 2: Control de Stock al agregar al carrito ---");
        Carrito c2 = new Carrito(2, u2);
        boolean pudoAgregarExceso = c2.agregarArticulo(a1, 5); // Intenta agregar 5 Notebooks pero solo quedan 2
        System.out.println("¿Permitió agregar 5 Notebooks superando el stock? (Esperado: false): " + pudoAgregarExceso);
        System.out.println();

        System.out.println("--- PRUEBA 3: Compras de Elizabeth (2 Carritos distintos - $210.000 total) ---");
        c2.agregarArticulo(a2, 6); // 6 Auriculares ($180.000)
        MetodoPago pagoTarjeta = new TarjetaCredito("4509-1234-5678-9012", "12/28");
        boolean venta2Exitosa = tienda.procesarVenta(c2, pagoTarjeta);

        Carrito c3 = new Carrito(3, u2);
        c3.agregarArticulo(a3, 2); // 2 Mouses ($30.000)
        boolean venta3Exitosa = tienda.procesarVenta(c3, pagoMP);

        System.out.println("Venta 2 procesada correctamente: " + venta2Exitosa);
        System.out.println("Venta 3 procesada correctamente: " + venta3Exitosa);
        System.out.println("Stock restante Auriculares (Esperado: 4): " + a2.getStock());
        System.out.println("Stock restante Mouse (Esperado: 1): " + a3.getStock());
        System.out.println();

        System.out.println("--- PRUEBA 4: Verificación de Reportes de Negocio ---");
        Usuario clienteMasMonto = tienda.obtenerClienteMasCompradorPorMonto();
        System.out.println("Cliente con mayor gasto (Esperado: Facundo Raspa): " +
                (clienteMasMonto != null ? clienteMasMonto.getNombre() : "Sin datos"));

        Usuario clienteMasCantidad = tienda.obtenerClienteMasCompradorPorCantidad();
        System.out.println("Cliente con más compras/carritos (Esperado: Elizabeth Bellavilla): " +
                (clienteMasCantidad != null ? clienteMasCantidad.getNombre() : "Sin datos"));

        Articulo articuloTop = tienda.obtenerArticuloMasVendido();
        System.out.println("Artículo más vendido (Esperado: Auriculares Bluetooth): " +
                (articuloTop != null ? articuloTop.getNombre() : "Sin datos"));
        System.out.println();

        System.out.println("--- PRUEBA 5: Caso Borde - Carrito Vacío ---");
        Carrito carritoVacio = new Carrito(4, u3);
        boolean ventaVaciaExitosa = tienda.procesarVenta(carritoVacio, pagoMP);
        System.out.println("¿Procesó una venta con carrito vacío? (Esperado: false): " + ventaVaciaExitosa);
        System.out.println();

        System.out.println("--- PRUEBA 6: Agotamiento total de stock y reintento con 0 disponible ---");
        Carrito c4 = new Carrito(5, u3);
        c4.agregarArticulo(a3, 1); // Quedaba exactamente 1 Mouse en stock
        boolean ventaUltimoMouse = tienda.procesarVenta(c4, pagoTarjeta);
        System.out.println("Venta del último Mouse en stock (Esperado: true): " + ventaUltimoMouse);
        System.out.println("Stock actual del Mouse (Esperado: 0): " + a3.getStock());

        Carrito c5 = new Carrito(6, u1);
        boolean pudoAgregarSinStock = c5.agregarArticulo(a3, 1); // Intenta agregar cuando el stock es 0
        System.out.println("¿Permitió agregar un producto con 0 stock? (Esperado: false): " + pudoAgregarSinStock);
        System.out.println();

        System.out.println("--- PRUEBA 7: Validaciones de Cantidades Inválidas (Negativas o Cero) ---");
        Carrito c6 = new Carrito(7, u1);
        boolean pudoAgregarNegativo = c6.agregarArticulo(a2, -3);
        boolean pudoAgregarCero = c6.agregarArticulo(a2, 0);
        System.out.println("¿Permitió agregar cantidad negativa? (Esperado: false): " + pudoAgregarNegativo);
        System.out.println("¿Permitió agregar cantidad cero? (Esperado: false): " + pudoAgregarCero);
        System.out.println();

        System.out.println("--- PRUEBA 8: Resiliencia ante Tienda Sin Ventas (Manejo de Nulos) ---");
        Tienda tiendaNueva = new Tienda();
        System.out.println("Cliente por monto en tienda vacía (Esperado: null): " + tiendaNueva.obtenerClienteMasCompradorPorMonto());
        System.out.println("Cliente por cantidad en tienda vacía (Esperado: null): " + tiendaNueva.obtenerClienteMasCompradorPorCantidad());
        System.out.println("Artículo más vendido en tienda vacía (Esperado: null): " + tiendaNueva.obtenerArticuloMasVendido());
    }
}
