package Puzzle8;

import java.util.*;

public class Solucion2 {
    String estadoActual;
    String estadoFinal = "12345678*";
    private List<String> camino = new ArrayList<>();
    
    private int[][] movimientos = {
        {1, 3}, {0, 2, 4}, {1, 5},
        {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8},
        {3, 7}, {4, 6, 8}, {5, 7}
    };
    
    public Solucion2(String e) {
        estadoActual = e;
    }
    
    public void solucionar() {
        if (bfs()) {
            System.out.println("¡Solución encontrada en " + (camino.size() - 1) + " pasos!");
            imprimirSolucion();
        } else {
            System.out.println("No se encontró solución.");
        }
    }
    
    private boolean bfs() {
        Queue<Nodo> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();
        
        Nodo raiz = new Nodo(estadoActual, 0, null);
        cola.add(raiz);
        visitados.add(estadoActual);
        
        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.poll();
            
            if (nodoActual.estado.equals(estadoFinal)) {
                reconstruirCaminoDesdeNodo(nodoActual);
                return true;
            }
            
            int posVacia = nodoActual.estado.indexOf('*');
            
            for (int destino : movimientos[posVacia]) {
                String nuevoEstado = intercambiar(nodoActual.estado, posVacia, destino);
                
                if (!visitados.contains(nuevoEstado)) {
                    visitados.add(nuevoEstado);
                    Nodo nodoHijo = new Nodo(nuevoEstado, nodoActual.nivel + 1, nodoActual);
                    cola.add(nodoHijo);
                }
            }
        }
        
        return false;
    }
    
    private void reconstruirCaminoDesdeNodo(Nodo nodoFinal) {
        camino.clear();
        
        Nodo actual = nodoFinal;
        Stack<String> pila = new Stack<>();
        
        while (actual != null) {
            pila.push(actual.estado);
            actual = actual.padre;
        }
        
        while (!pila.isEmpty()) {
            camino.add(pila.pop());
        }
    }
    
    private String intercambiar(String estado, int i, int j) {
        char[] chars = estado.toCharArray();
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
        return new String(chars);
    }
    
    private void imprimirSolucion() {
        System.out.println("Camino de solución (" + (camino.size() - 1) + " movimientos):");
        System.out.println("=" .repeat(40));
        
        for (int i = 0; i < camino.size(); i++) {
            System.out.println("Paso " + i + " (nivel " + i + "):");
            imprimirEstado(camino.get(i));
            
            System.out.println();
        }
    }
    
    private void imprimirEstado(String estado) {
        for (int i = 0; i < 9; i++) {
            System.out.print(estado.charAt(i) + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
    
}