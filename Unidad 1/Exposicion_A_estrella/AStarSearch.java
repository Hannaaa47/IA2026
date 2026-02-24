/*
	Algoritmo de búsqueda A*

1- Inicializar la lista abierta (open list).

2- Inicializar la lista cerrada (closed list).
Colocar el nodo inicial en la lista abierta (puedes dejar su valor f = 0).

3- Mientras la lista abierta no esté vacía:

	a) Encontrar el nodo con el menor valor f en la lista abierta, llamarlo “q”.

	b) Quitar q de la lista abierta.

	c) Generar los 8 sucesores de q y asignarles q como padre.

	d) Para cada sucesor:

		i) Si el sucesor es la meta, detener la búsqueda.

		ii) En caso contrario, calcular g y h del sucesor:
			- sucesor.g = q.g + distancia entre sucesor y q
			- sucesor.h = distancia desde el sucesor hasta la meta (esto puede hacerse de varias formas; se pueden usar tres heurísticas: Manhattan, Diagonal y Euclidiana)
			- sucesor.f = sucesor.g + sucesor.h

		iii) Si existe un nodo en la lista abierta con la misma posición y con menor f que el sucesor, ignorar este sucesor.

		iv) Si existe un nodo en la lista cerrada con la misma posición y con menor f que el sucesor, ignorar este sucesor; de lo contrario, agregar el nodo a la lista abierta.

	(fin del ciclo for)

	e) Agregar q a la lista cerrada.

(fin del ciclo while)

 */

package A_Estrella;

import java.util.*;

class AStar {

    static final int ROW = 9;
    static final int COL = 10;

    public static class Cell {
        int parentI, parentJ;
        double f, g, h;

        Cell() {
            f = g = h = Double.POSITIVE_INFINITY;
            parentI = parentJ = -1;
        }
    }

    public static class Node implements Comparable<Node> {
        int i, j;
        double f;

        Node(int i, int j, double f) {
            this.i = i;
            this.j = j;
            this.f = f;
        }

        public int compareTo(Node other) {
            return Double.compare(this.f, other.f);
        }
    }

    public static boolean isValid(int r, int c) {
        return r >= 0 && r < ROW && c >= 0 && c < COL;
    }

    public static boolean isUnBlocked(int[][] grid, int r, int c) {
        return grid[r][c] == 1;
    }

    public static boolean isDestination(int r, int c, int[] dest) {
        return r == dest[0] && c == dest[1];
    }

    public static double heuristic(int r, int c, int[] dest) {
        return Math.sqrt(Math.pow(r - dest[0], 2) + Math.pow(c - dest[1], 2));
    }

    public static void tracePath(Cell[][] details, int[] dest) {
        System.out.println("Camino encontrado:");

        int r = dest[0], c = dest[1];
        Stack<int[]> path = new Stack<>();

        while (!(details[r][c].parentI == r && details[r][c].parentJ == c)) {
            path.push(new int[]{r, c});
            int pr = details[r][c].parentI;
            int pc = details[r][c].parentJ;
            r = pr;
            c = pc;
        }

        path.push(new int[]{r, c});

        while (!path.isEmpty()) {
            int[] p = path.pop();
            System.out.print(" -> (" + p[0] + "," + p[1] + ")");
        }
        System.out.println();
    }

    public static void aStar(int[][] grid, int[] src, int[] dest) {

        if (!isValid(src[0], src[1]) || !isValid(dest[0], dest[1])) {
            System.out.println("Fuente o destino inválido");
            return;
        }

        if (!isUnBlocked(grid, src[0], src[1]) || !isUnBlocked(grid, dest[0], dest[1])) {
            System.out.println("Celda bloqueada");
            return;
        }

        boolean[][] closed = new boolean[ROW][COL];
        Cell[][] details = new Cell[ROW][COL];

        for (int i = 0; i < ROW; i++)
            for (int j = 0; j < COL; j++)
                details[i][j] = new Cell();

        int si = src[0], sj = src[1];

        details[si][sj].f = 0;
        details[si][sj].g = 0;
        details[si][sj].h = 0;
        details[si][sj].parentI = si;
        details[si][sj].parentJ = sj;

        PriorityQueue<Node> open = new PriorityQueue<>();
        open.add(new Node(si, sj, 0));

        int[][] moves = {
                {-1,0},{1,0},{0,1},{0,-1},
                {-1,1},{-1,-1},{1,1},{1,-1}
        };

        while (!open.isEmpty()) {

            Node current = open.poll();
            int i = current.i, j = current.j;

            closed[i][j] = true;

            for (int[] m : moves) {

                int ni = i + m[0];
                int nj = j + m[1];

                if (!isValid(ni, nj))
                    continue;

                if (isDestination(ni, nj, dest)) {
                    details[ni][nj].parentI = i;
                    details[ni][nj].parentJ = j;
                    tracePath(details, dest);
                    return;
                }

                if (!closed[ni][nj] && isUnBlocked(grid, ni, nj)) {

                    double cost = (Math.abs(m[0]) + Math.abs(m[1]) == 2) ? 1.414 : 1;

                    double gNew = details[i][j].g + cost;
                    double hNew = heuristic(ni, nj, dest);
                    double fNew = gNew + hNew;

                    if (details[ni][nj].f > fNew) {

                        details[ni][nj].f = fNew;
                        details[ni][nj].g = gNew;
                        details[ni][nj].h = hNew;
                        details[ni][nj].parentI = i;
                        details[ni][nj].parentJ = j;

                        open.add(new Node(ni, nj, fNew));
                    }
                }
            }
        }

        System.out.println("No se encontró camino");
    }

    public static void main(String[] args) {

        int[][] grid = {
                {1,0,1,1,1,1,0,1,1,1},
                {1,1,1,0,1,1,1,0,1,1},
                {1,1,1,0,1,1,0,1,0,1},
                {0,0,1,0,1,0,0,0,0,1},
                {1,1,1,0,1,1,1,0,1,0},
                {1,0,1,1,1,1,0,1,0,0},
                {1,0,0,0,0,1,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,1},
                {1,1,1,0,0,0,1,0,0,1}
        };

        int[] src = {8,0};
        int[] dest = {0,0};

        aStar(grid, src, dest);
    }
}