package service;

import model.*;
import util.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Servicio principal del sistema CineApp.
public class CineService {

    // COLECCIONES PRINCIPALES
    private List<Cliente>  clientes  = new ArrayList<>();
    private List<Funcion>  funciones = new ArrayList<>();

    // BONUS: Map para búsqueda rápida de cliente por RUT
    private Map<String, Cliente> clientesPorRut = new HashMap<>();

    // PUNTO DE PARTIDA
    // Inicia el sistema y muestra el menú principal en bucle.
    public void iniciar() {
        cargarDatosDemo(); // Carga los datos DEMO para pruebas
        mostrarBienvenida();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = Input.leerEntero("  Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> verCartelera();
                case 3 -> comprarEntrada();
                case 4 -> verEntradasCliente();
                case 5 -> verDetallesFuncion();   // info completa + mapa asientos
                case 6 -> mostrarReporteVentas(); // BONUS: reporte de ventas
                case 0 -> System.out.println("\n  👋 ¡Hasta pronto! Gracias por usar el CineApp de Homies.");
                default -> System.out.println("  ⚠️  Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);

        Input.cerrar();
    }

    // MENÚ PRINCIPAL
    private void mostrarBienvenida() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║      BIENVENIDO AL CINEAPP DE HOMIES     ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("  ┌──────────────────────────────────────────┐");
        System.out.println("  │           MENÚ PRINCIPAL                 │");
        System.out.println("  ├──────────────────────────────────────────┤");
        System.out.println("  │  1) Crear cliente                        │");
        System.out.println("  │  2) Ver cartelera                        │");
        System.out.println("  │  3) Comprar entrada                      │");
        System.out.println("  │  4) Ver entradas del cliente             │");
        System.out.println("  │  5) Ver detalle de función               │");
        System.out.println("  │  6) Reporte de ventas  (BONUS)           │");
        System.out.println("  │  0) Salir                                │");
        System.out.println("  └──────────────────────────────────────────┘");
    }

    //  OPCIÓN 1 — CREAR CLIENTE
    private void crearCliente() {
        System.out.println("\n  ── Crear Cliente ──────────────────────────");

        String nombre = Input.leerTexto("  Nombre : ");
        String rut    = Input.leerTexto("  RUT    : ");

        // Validar que el RUT no esté duplicado (usando el Map para el BONUS)
        if (clientesPorRut.containsKey(rut.toUpperCase())) {
            System.out.println("  ❌ Ya existe un cliente con el RUT: " + rut);
            return;
        }

        try {
            Cliente nuevo = new Cliente(nombre, rut.toUpperCase());
            clientes.add(nuevo);
            clientesPorRut.put(rut.toUpperCase(), nuevo); // BONUS: registro en Map
            System.out.println("  ✔ Cliente creado: " + nuevo.presentarse());
        } catch (IllegalArgumentException e) {
            System.out.println("  ❌ Error: " + e.getMessage());
        }
    }

    //  OPCIÓN 2 — VER CARTELERA
    private void verCartelera() {
        System.out.println("\n  ── Cartelera ──────────────────────────────");

        if (funciones.isEmpty()) {
            System.out.println("  ⚠️  No hay funciones disponibles.");
            return;
        }

        System.out.printf("  %-6s %-30s %-10s %-10s %-10s%n",
                "#", "Película", "Horario", "Asientos", "Disponibles");
        System.out.println("  " + "─".repeat(68));

        for (Funcion f : funciones) {
            f.mostrarEnCartelera();
        }
    }

    // OPCIÓN 3 — COMPRAR ENTRADA
    private void comprarEntrada() {
        System.out.println("\n  ── Comprar Entrada ────────────────────────");

        // Verificar que hay clientes y funciones
        if (clientes.isEmpty()) {
            System.out.println("  ⚠️  No hay clientes registrados. Cree uno primero (opción 1).");
            return;
        }
        if (funciones.isEmpty()) {
            System.out.println("  ⚠️  No hay funciones disponibles.");
            return;
        }

        // Seleccionar cliente
        Cliente cliente = seleccionarCliente();
        if (cliente == null) return;

        // Mostrar cartelera y seleccionar función
        verCartelera();
        Funcion funcion = seleccionarFuncion();
        if (funcion == null) return;

        // Verificar disponibilidad
        if (!funcion.hayDisponibilidad()) {
            System.out.println("  ❌ No hay asientos disponibles en esta función.");
            return;
        }

        // Mostrar mapa de asientos y pedir número
        funcion.mostrarDetalle();
        int asiento = Input.leerEnteroRango(
                "  Número de asiento (1-" + funcion.getCapacidadSala() + "): ",
                1, funcion.getCapacidadSala());

        if (funcion.asientoOcupado(asiento)) {
            System.out.println("  ❌ El asiento " + asiento + " ya está ocupado. Elija otro.");
            return;
        }

        // BONUS: seleccionar tipo de entrada
        TipoEntrada.mostrarOpciones();
        int opTipo = Input.leerEnteroRango("  Tipo de entrada: ", 1, TipoEntrada.values().length);
        TipoEntrada tipo = TipoEntrada.desdePosicion(opTipo);

        // Realizar la venta
        try {
            Entrada entrada = funcion.venderEntrada(cliente, asiento, tipo);
            cliente.agregarEntrada(entrada);

            System.out.println("\n  ✔ ¡Compra exitosa!");
            entrada.mostrarResumen();

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("  ❌ Error al comprar: " + e.getMessage());
        }
    }

    // OPCIÓN 4 — VER ENTRADAS DEL CLIENTE
    private void verEntradasCliente() {
        System.out.println("\n  ── Entradas del Cliente ───────────────────");

        if (clientes.isEmpty()) {
            System.out.println("  ⚠️  No hay clientes registrados.");
            return;
        }

        Cliente cliente = seleccionarCliente();
        if (cliente == null) return;

        // POLIMORFISMO: presentarse() es el método sobrescrito en Cliente
        System.out.println("\n  " + cliente.presentarse());
        System.out.println();
        cliente.mostrarEntradas();
    }

    // OPCIÓN 5 — VER DETALLE DE FUNCIÓN
    private void verDetallesFuncion() {
        System.out.println("\n  ── Detalle de Función ─────────────────────");

        if (funciones.isEmpty()) {
            System.out.println("  ⚠️  No hay funciones registradas.");
            return;
        }

        verCartelera();
        Funcion funcion = seleccionarFuncion();
        if (funcion == null) return;

        System.out.println();
        funcion.mostrarDetalle();
    }

    // OPCIÓN 6 — BONUS: REPORTE DE VENTAS
    private void mostrarReporteVentas() {
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║             REPORTE DE VENTAS            ║");
        System.out.println("  ╚══════════════════════════════════════════╝");

        int totalEntradas = 0;
        double totalRecaudado = 0;

        // Conteo por tipo de entrada
        Map<String, Integer> conteoPorTipo = new HashMap<>();
        Map<String, Double>  ingresoPorTipo = new HashMap<>();

        for (TipoEntrada t : TipoEntrada.values()) {
            conteoPorTipo.put(t.getEtiqueta(), 0);
            ingresoPorTipo.put(t.getEtiqueta(), 0.0);
        }

        // Recorrer todas las funciones y sus entradas
        for (Funcion f : funciones) {
            int vendidas = f.getEntradasVendidas().size();
            double recaudado = f.getEntradasVendidas().stream()
                    .mapToDouble(Entrada::getPrecio).sum();

            System.out.printf("  🎬 %-28s | %s | %2d entradas | $%.0f%n",
                    f.getPelicula().getTitulo(),
                    f.getHorario(),
                    vendidas,
                    recaudado);

            totalEntradas += vendidas;
            totalRecaudado += recaudado;

            for (Entrada e : f.getEntradasVendidas()) {
                String etiq = e.getTipo().getEtiqueta();
                conteoPorTipo.put(etiq, conteoPorTipo.get(etiq) + 1);
                ingresoPorTipo.put(etiq, ingresoPorTipo.get(etiq) + e.getPrecio());
            }
        }

        System.out.println("  " + "─".repeat(60));
        System.out.println("  Desglose por tipo:");
        for (TipoEntrada t : TipoEntrada.values()) {
            int cant = conteoPorTipo.get(t.getEtiqueta());
            double ing  = ingresoPorTipo.get(t.getEtiqueta());
            if (cant > 0) {
                System.out.printf("    %-15s: %2d entradas → $%.0f%n",
                        t.getEtiqueta(), cant, ing);
            }
        }

        System.out.println("  " + "─".repeat(60));
        System.out.printf("  Total clientes registrados : %d%n", clientes.size());
        System.out.printf("  Total entradas vendidas    : %d%n", totalEntradas);
        System.out.printf("  Total recaudado            : $%.0f%n", totalRecaudado);
    }

    // HELPERS DE SELECCIÓN
    // Lista los clientes y permite seleccionar uno
    private Cliente seleccionarCliente() {
        if (clientes.isEmpty()) {
            System.out.println("  ⚠️  No hay clientes registrados.");
            return null;
        }
        System.out.println("  Clientes registrados:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("    %d) %s%n", i + 1, clientes.get(i));
        }
        int idx = Input.leerEnteroRango("  Seleccione cliente: ", 1, clientes.size());
        return clientes.get(idx - 1);
    }

    // Lista las funciones y permite seleccionar una
    private Funcion seleccionarFuncion() {
        final int id = Input.leerEntero("  Ingrese N° de función: ");
        return funciones.stream()
                .filter(f -> f.getId() == id)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("  ❌ No existe una función con ese numero: " + id);
                    return null;
                });
    }

    //  Datos tipo DEMO (para probar)
    private void cargarDatosDemo() {
        // Películas
        Pelicula p1 = new Pelicula("Super Mario Galaxy: La Pelicula",99, "Infantil");
        Pelicula p2 = new Pelicula("Michael", 127,  "Musical");
        Pelicula p3 = new Pelicula("El Diablo Viste a la Moda 2", 114, "Comedia");

        // Funciones
        funciones.add(new Funcion(p1, "14:30", 20));
        funciones.add(new Funcion(p1, "18:00", 20));
        funciones.add(new Funcion(p2, "16:00", 20));
        funciones.add(new Funcion(p3, "20:30", 20));

        System.out.println("\n  Cartelera disponible de hoy:");
        System.out.println("     • 3 películas | 4 funciones ");
    }
}