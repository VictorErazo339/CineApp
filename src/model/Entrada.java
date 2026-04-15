package model;

// Clase que representa una entrada de cine vendida
public class Entrada {

    // ATRIBUTOS ENCAPSULADOS
    private static int contadorId = 1;

    private int id;
    private Cliente cliente;
    private Funcion funcion;
    private int numeroAsiento;
    private TipoEntrada tipo; // Para el BONUS: tipo que determina el precio
    private double precio;

    // CONSTRUCTOR
    public Entrada(Cliente cliente, Funcion funcion, int numeroAsiento, TipoEntrada tipo) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.funcion = funcion;
        this.numeroAsiento = numeroAsiento;
        this.tipo = tipo;
        this.precio = tipo.getPrecio(); // Para el BONUS: precio según tipo
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public TipoEntrada getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    // VISUALIZACIÓN

    // Muestra el detalle de la boleta
    public void mostrarResumen() {
        System.out.println("  ┌─────────────────────────────────────┐");
        System.out.printf ("  │  🎟  ENTRADA #%-43d│%n", id);
        System.out.println("  ├─────────────────────────────────────┤");
        System.out.printf ("  │  Cliente  : %-45s│%n", cliente.getNombre());
        System.out.printf ("  │  Película : %-45s│%n", funcion.getPelicula().getTitulo());
        System.out.printf ("  │  Horario  : %-45s│%n", funcion.getHorario());
        System.out.printf ("  │  Asiento  : %-45d│%n", numeroAsiento);
        System.out.printf ("  │  Tipo     : %-45s│%n", tipo.getEtiqueta());
        System.out.printf ("  │  Precio   : $%-44.0f│%n", precio);
        System.out.println("  └─────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return "Entrada #" + id + " | " + funcion.getPelicula().getTitulo()
                + " | Asiento " + numeroAsiento + " | " + tipo.getEtiqueta()
                + " | $" + (int) precio;
    }
}