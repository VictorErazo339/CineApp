package model;

import java.util.ArrayList;
import java.util.List;

// Clase que representa una función de cine
public class Funcion {

    // ATRIBUTOS ENCAPSULADOS
    private static int contadorId = 1; // N° de funcion autoincremental

    private int id;
    private Pelicula pelicula;
    private String horario;
    private int capacidadSala;
    private List<Integer> asientosOcupados; // asientos ya vendidos
    private List<Entrada> entradasVendidas;

    // CONSTRUCTOR
    public Funcion(Pelicula pelicula, String horario, int capacidadSala) {
        this.id = contadorId++;
        setPelicula(pelicula);
        setHorario(horario);
        setCapacidadSala(capacidadSala);
        this.asientosOcupados = new ArrayList<>();
        this.entradasVendidas = new ArrayList<>();
    }

    // GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película no puede ser nula.");
        }
        this.pelicula = pelicula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        if (horario == null || horario.trim().isEmpty()) {
            throw new IllegalArgumentException("El horario no puede estar vacío.");
        }
        this.horario = horario.trim();
    }

    public int getCapacidadSala() {
        return capacidadSala;
    }

    public void setCapacidadSala(int capacidadSala) {
        if (capacidadSala <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0.");
        }
        this.capacidadSala = capacidadSala;
    }

    public List<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    // LÓGICA DE ASIENTOS
    // Calcula cuántos asientos quedan disponibles
    public int getAsientosDisponibles() {
        return capacidadSala - asientosOcupados.size();
    }

    // Verifica si hay al menos un asiento disponible
    public boolean hayDisponibilidad() {
        return getAsientosDisponibles() > 0;
    }

    // Verifica si un asiento específico ya está ocupado
    public boolean asientoOcupado(int numeroAsiento) {
        return asientosOcupados.contains(numeroAsiento);
    }

    // Registra una entrada vendida para esta función. Valida disponibilidad del asiento
    public Entrada venderEntrada(Cliente cliente, int numeroAsiento, TipoEntrada tipo) {
        if (!hayDisponibilidad()) {
            throw new IllegalStateException("No hay asientos disponibles para esta función.");
        }
        if (numeroAsiento < 1 || numeroAsiento > capacidadSala) {
            throw new IllegalArgumentException(
                    "El asiento debe estar entre 1 y " + capacidadSala + ".");
        }
        if (asientoOcupado(numeroAsiento)) {
            throw new IllegalStateException("El asiento " + numeroAsiento + " ya está ocupado.");
        }

        Entrada entrada = new Entrada(cliente, this, numeroAsiento, tipo);
        asientosOcupados.add(numeroAsiento);
        entradasVendidas.add(entrada);
        return entrada;
    }

    // VISUALIZACIÓN

    // Muestra el resumen de la función en cartelera
    public void mostrarEnCartelera() {
        System.out.printf("  [%d] %-30s | %s | Sala: %d asientos | Disponibles: %d%n",
                id,
                pelicula.getTitulo(),
                horario,
                capacidadSala,
                getAsientosDisponibles());
    }

    // Muestra el detalle completo de la función
    public void mostrarDetalle() {
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.printf ("  ║  Función #%-4d                       ║%n", id);
        System.out.println("  ╚══════════════════════════════════════╝");
        pelicula.mostrarInfo();
        System.out.println("     Horario  : " + horario);
        System.out.println("     Sala     : " + capacidadSala + " asientos");
        System.out.println("     Vendidas : " + asientosOcupados.size());
        System.out.println("     Libres   : " + getAsientosDisponibles());
        mostrarMapaAsientos();
    }

    // Muestra un mapa visual de asientos ocupados/libres
    private void mostrarMapaAsientos() {
        System.out.println("     Mapa de asientos (O libre | X ocupado):");
        System.out.print("     ");
        for (int i = 1; i <= capacidadSala; i++) {
            System.out.print(asientoOcupado(i) ? " [X]" : " [O]");
            if (i % 10 == 0 && i < capacidadSala) System.out.print("\n     ");
        }
        System.out.println(); // cierra la última fila
    }

    @Override
    public String toString() {
        return "Función #" + id + " - " + pelicula.getTitulo() + " - " + horario;
    }
}