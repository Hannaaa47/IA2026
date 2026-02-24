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
        
    public boolean bfs() {
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
    
    public boolean dfs() {
        Stack<Nodo> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();
        
        Nodo raiz = new Nodo(estadoActual, 0, null);
        pila.push(raiz);
        visitados.add(estadoActual);
        
        while (!pila.isEmpty()) {
            Nodo nodoActual = pila.pop();
            
            if (nodoActual.estado.equals(estadoFinal)) {
                reconstruirCaminoDesdeNodo(nodoActual);
                return true;
            }
            
            int posVacia = nodoActual.estado.indexOf('*');
            
            for (int i = movimientos[posVacia].length - 1; i >= 0; i--) {
                int destino = movimientos[posVacia][i];
                String nuevoEstado = intercambiar(nodoActual.estado, posVacia, destino);
                
                if (!visitados.contains(nuevoEstado)) {
                    visitados.add(nuevoEstado);
                    Nodo nodoHijo = new Nodo(nuevoEstado, nodoActual.nivel + 1, nodoActual);
                    pila.push(nodoHijo);
                }
            }
        }
        
        return false;
    }

    public boolean ucs() {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>((a, b) -> 
            Integer.compare(a.costo, b.costo));
        Map<String, Integer> visitados = new HashMap<>();
        
        Nodo raiz = new Nodo(estadoActual, 0, null);
        raiz.costo = 0;
        colaPrioridad.add(raiz);
        visitados.put(estadoActual, 0);
        
        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();
            
            if (nodoActual.estado.equals(estadoFinal)) {
                reconstruirCaminoDesdeNodo(nodoActual);
                return true;
            }
            
            int posVacia = nodoActual.estado.indexOf('*');
            
            for (int destino : movimientos[posVacia]) {
                String nuevoEstado = intercambiar(nodoActual.estado, posVacia, destino);
                int nuevoCosto = nodoActual.costo + 1; 
                
                if (!visitados.containsKey(nuevoEstado) || nuevoCosto < visitados.get(nuevoEstado)) {
                    visitados.put(nuevoEstado, nuevoCosto);
                    Nodo nodoHijo = new Nodo(nuevoEstado, nodoActual.nivel + 1, nodoActual);
                    nodoHijo.costo = nuevoCosto;
                    colaPrioridad.add(nodoHijo);
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
    
    public void imprimirSolucion() {
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