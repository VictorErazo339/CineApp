package model;


// Clase base que representa una persona genérica
// Sirve como superclase para Cliente y cualquier otro rol futuro
public class Persona {

    // Atributo encapsulado privado.
    private String nombre;

    //Constructor
    public Persona(String nombre) {
        this.nombre = nombre;
    }

    //Getter
    public String getNombre() {
        return nombre;
    }

    //Setter para evitar campo vacio
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    //Metodo de presentacion de persona
    public String presentarse() {
        return "Persona: " + nombre;
    }

    //Las subclases pueden sobreescribir (polimorfismo)
    @Override
    public String toString() {
        return nombre;
    }
}