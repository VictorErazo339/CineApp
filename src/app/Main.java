package app;

import service.CineService;

// Clase principal de CineApp
public class Main {

    // Crea el servicio e iniciar el sistema
    public static void main(String[] args) {
        CineService sistema = new CineService();
        sistema.iniciar();
    }
}