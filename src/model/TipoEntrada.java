package model;

// BONUS que define los tipos de entrada disponibles por polimorfismo
public enum TipoEntrada {

    Adulto("Adulto", 5500),
    Estudiante("Estudiante", 3500),
    AdultoMayor("Adulto Mayor", 3000),
    Niño("Niño", 2500);

    // ATRIBUTOS
    private final String etiqueta;
    private final double precio;

    // CONSTRUCTOR
    TipoEntrada(String etiqueta, double precio) {
        this.etiqueta = etiqueta;
        this.precio = precio;
    }

    // GETTERS
    public String getEtiqueta() {
        return etiqueta;
    }

    public double getPrecio() {
        return precio;
    }

    // Metodo para mostrar todos los tipos disponibles para el usuario
    public static void mostrarOpciones() {
        System.out.println("  Tipos de entrada disponibles:");
        TipoEntrada[] tipos = values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("    %d) %-15s → $%.0f%n",
                    i + 1, tipos[i].getEtiqueta(), tipos[i].getPrecio());
        }
    }

    // Para obtener un tipo de entrada por su índice
    public static TipoEntrada desdePosicion(int posicion) {
        TipoEntrada[] tipos = values();
        if (posicion < 1 || posicion > tipos.length) {
            throw new IllegalArgumentException("Tipo de entrada no válido.");
        }
        return tipos[posicion - 1];
    }
}