package util;

import java.util.Scanner;

// Utilidad centralizada para leer entradas del usuario de forma segura.
public class Input {

    // Preparando Scanner
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor privado sin cuerpo (no se instancia)
    private Input() {}

    // MÉTODOS
    // Para evitar campo vacio en variables String
    public static String leerTexto(String prompt) {
        String valor;
        do {
            System.out.print(prompt);
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("     Este campo no puede estar vacío.");
            }
        } while (valor.isEmpty());
        return valor;
    }

    // Para evitar campo vacio en variables int
    public static int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("      Ingrese un número entero válido.");
            }
        }
    }

    // Para limitar la variable int dentro de un rango inclusivo.
    public static int leerEnteroRango(String prompt, int min, int max) {
        while (true) {
            int valor = leerEntero(prompt);
            if (valor >= min && valor <= max) return valor;
            System.out.printf("      Ingrese un número entre %d y %d.%n", min, max);
        }
    }

    // Para evitar campo vacio en variables double
    public static double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("       Ingrese un número válido (ej: 12.5).");
            }
        }
    }

    // Para confirmar y retornar true para sí, false para no.
    public static boolean leerSiNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s") || r.equals("si") || r.equals("sí")) return true;
            if (r.equals("n") || r.equals("no")) return false;
            System.out.println("       Ingrese 's' para Sí o 'n' para No.");
        }
    }

    // Cierra el Scanner al salir del sistema.
    public static void cerrar() {
        scanner.close();
    }
}