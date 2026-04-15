package model;

import java.util.ArrayList;
import java.util.List;

// Clase que representa un 'Cliente' del cine que HEREDA de 'Persona'
public class Cliente extends Persona {

    // ATRIBUTOS PROPIOS
    private String rut;
    private List<Entrada> entradas; // Se crea la colección de entradas compradas

    // CONSTRUCTOR
    public Cliente(String nombre, String rut) {
        super(nombre); // llama al constructor de Persona (HERENCIA)
        setRut(rut);
        this.entradas = new ArrayList<>();
    }

    // GETTERS Y SETTERS
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede estar vacío.");
        }
        this.rut = rut.trim();
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    // MÉTODOS
    // Para agregar una entrada comprada a la lista del cliente
    public void agregarEntrada(Entrada entrada) {
        entradas.add(entrada);
    }

    // Para sobrescribir el método de 'Persona' con info adicional
    @Override
    public String presentarse() {
        return "Cliente: " + getNombre() + " | RUT: " + rut
                + " | Entradas compradas: " + entradas.size();
    }

    // Para mostrar todas las entradas compradas por este cliente.
    public void mostrarEntradas() {
        if (entradas.isEmpty()) {
            System.out.println("  ⚠️  " + getNombre() + " no tiene entradas compradas.");
            return;
        }
        System.out.println("  Entradas de " + getNombre() + ":");
        for (Entrada e : entradas) {
            e.mostrarResumen();
        }
    }

    @Override
    public String toString() {
        return getNombre() + " [" + rut + "]";
    }
}