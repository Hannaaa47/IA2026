package Puzzle8;

import java.util.*;

public class Solucion2 {
    private String estadoActual;
    private String estadoFinal = "12345678*";
    private List<String> camino = new ArrayList<>();
    
    private long nodosExpandidos;
    private long tiempoInicio;
    
    private int[][] movimientos = {
        {1, 3}, {0, 2, 4}, {1, 5},
        {0, 4, 6}, {1, 3, 5, 7}, {2, 4, 8},
        {3, 7}, {4, 6, 8}, {5, 7}
    };
    
    public Solucion2(String e) {
        estadoActual = e;
    }
        
    public boolean bfs() {
    	nodosExpandidos = 0;
    	tiempoInicio = System.currentTimeMillis();
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
                    nodosExpandidos++;
                    cola.add(nodoHijo);
                }
            }
        }
        
        return false;
    }
    
    public boolean dfs() {
    	nodosExpandidos = 0;
    	tiempoInicio = System.currentTimeMillis();
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
                    nodosExpandidos++;
                    pila.push(nodoHijo);
                }
            }
        }
        
        return false;
    }

    public boolean ucs() {
    	nodosExpandidos = 0;
    	tiempoInicio = System.currentTimeMillis();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>((a, b) -> Integer.compare(a.costo, b.costo));
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
                    nodosExpandidos++;
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
    
    private void imprimirEstadisticas(String nombre, boolean exito) {
        long tiempo = System.currentTimeMillis() - tiempoInicio;

        System.out.println("===== " + nombre + " =====");
        System.out.println("¿Solución encontrada?: " + exito);
        System.out.println("Longitud solución: " + (exito ? camino.size()-1 : -1));
        System.out.println("Nodos expandidos: " + nodosExpandidos);
        System.out.println("Tiempo(ms): " + tiempo);
        System.out.println();
    }
    
    public void compararAlgoritmos() {        
        System.out.println("Tabla comparativa sobre los diversos algoritmos de búsqueda en Puzzle 8");
        System.out.println("================================================================");
        System.out.printf("%-10s %-10s %-12s %-15s %-10s%n",
                "Algoritmo", "Exito", "Movimientos", "Nodos", "Tiempo(ms)");
        System.out.println("================================================================");

        boolean exito;
        long tiempo;
        int movimientos;

        camino.clear();
        nodosExpandidos = 0;
        tiempoInicio = System.currentTimeMillis();

        exito = bfs();

        tiempo = System.currentTimeMillis() - tiempoInicio;
        movimientos = exito ? camino.size() - 1 : -1;

        System.out.printf("%-10s %-10s %-12d %-15d %-10d%n", "BFS", exito ? "SI" : "NO", movimientos, nodosExpandidos, tiempo);

        camino.clear();
        nodosExpandidos = 0;
        tiempoInicio = System.currentTimeMillis();

        exito = dfs();

        tiempo = System.currentTimeMillis() - tiempoInicio;
        movimientos = exito ? camino.size() - 1 : -1;

        System.out.printf("%-10s %-10s %-12d %-15d %-10d%n", "DFS", exito ? "SI" : "NO", movimientos, nodosExpandidos, tiempo);


        camino.clear();
        nodosExpandidos = 0;
        tiempoInicio = System.currentTimeMillis();

        exito = ucs();

        tiempo = System.currentTimeMillis() - tiempoInicio;
        movimientos = exito ? camino.size() - 1 : -1;

        System.out.printf("%-10s %-10s %-12d %-15d %-10d%n", "UCS", exito ? "SI" : "NO", movimientos, nodosExpandidos, tiempo);
        
        System.out.println("================================================================");
    }
}