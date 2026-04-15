package model;

// Clase que representa una película disponible en el cine
public class Pelicula {

    // ATRIBUTOS ENCAPSULADOS
    private String titulo;
    private int duracionMinutos;
    private String genero;

    // CONSTRUCTOR
    public Pelicula(String titulo, int duracionMinutos, String genero) {
        setTitulo(titulo);
        setDuracionMinutos(duracionMinutos);
        setGenero(genero);
    }

    // GETTERS Y SETTERS DE TITULO
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo.trim();
    }

    // GETTERS Y SETTERS DE DURACIÓN
    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        if (duracionMinutos <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 minutos.");
        }
        this.duracionMinutos = duracionMinutos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío.");
        }
        this.genero = genero.trim();
    }

    // Metodo para mostrar la información completa de la película
    public void mostrarInfo() {
        System.out.println("  🎬 " + titulo);
        System.out.println("     Género   : " + genero);
        System.out.printf ("     Duración : %d min (%dh %02dmin)%n",
                duracionMinutos,
                duracionMinutos / 60,
                duracionMinutos % 60);
    }

    @Override
    public String toString() {
        return titulo + " (" + duracionMinutos + " min)";
    }
}