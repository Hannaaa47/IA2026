package Puzzle8;

public class Main {
    public static void main(String[] args) {
        String estadoInicial = "87654321*";
        Solucion2 solucion = new Solucion2(estadoInicial);
        if (solucion.bfs()) {
            System.out.println("¡Solución encontrada!");
            solucion.imprimirSolucion();
        } else {
            System.out.println("No se encontró solución.");
        }
    }
}
