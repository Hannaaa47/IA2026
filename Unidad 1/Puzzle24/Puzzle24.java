package Puzzle24;
import java.util.*;

public class Puzzle24 {

    private final int N = 5;

    private String goal;
    private String initial;

    private long nodesExpanded;
    private long startTime;
    private int solutionLength;

    public Puzzle24(String initial, String goal) {
        this.initial = initial;
        this.goal = goal;
    }

    public int solve(String heuristicName) {

        nodesExpanded = 0;
        startTime = System.currentTimeMillis();

        int threshold = heuristic(initial, heuristicName);

        while (true) {
            int temp = search(initial, 0, threshold, heuristicName, new HashSet<>());

            if (temp == -1) {
                printStats(solutionLength);
                return solutionLength;
            }

            if (temp == Integer.MAX_VALUE)
                return -1;

            threshold = temp;
        }
    }

    private int search(String node, int g, int threshold, String heuristicName, Set<String> visited) {

        nodesExpanded++;

        int f = g + heuristic(node, heuristicName);

        if (f > threshold) return f;
        if (node.equals(goal)) {
            solutionLength = g;   
            return -1;
        }

        visited.add(node);

        int min = Integer.MAX_VALUE;

        for (String next : neighbors(node)) {

            if (visited.contains(next)) continue;

            int temp = search(next, g + 1, threshold, heuristicName, visited);

            if (temp == -1) return -1;

            min = Math.min(min, temp);
        }

        visited.remove(node);

        return min;
    }

    private int heuristic(String state, String name) {

        if (name.equalsIgnoreCase("manhattan"))
            return manhattan(state);

        if (name.equalsIgnoreCase("linear"))
            return linear(state);

        throw new IllegalArgumentException("Heurística inválida");
    }

    private int manhattan(String s) {

        int dist = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            if (ch == '*') continue;

            int val = value(ch) - 1;

            int goalR = val / N;
            int goalC = val % N;

            int r = i / N;
            int c = i % N;

            dist += Math.abs(goalR - r) + Math.abs(goalC - c);
        }

        return dist;
    }

    private int linear(String s) {

        int h = manhattan(s);
        int conflicts = 0;

        for (int row = 0; row < N; row++) {
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {

                    char a = s.charAt(row * N + i);
                    char b = s.charAt(row * N + j);

                    if (a == '*' || b == '*') continue;

                    int va = value(a) - 1;
                    int vb = value(b) - 1;

                    if (va / N == row && vb / N == row && (va % N) > (vb % N))
                        conflicts++;
                }
            }
        }

        for (int col = 0; col < N; col++) {
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {

                    char a = s.charAt(i * N + col);
                    char b = s.charAt(j * N + col);

                    if (a == '*' || b == '*') continue;

                    int va = value(a) - 1;
                    int vb = value(b) - 1;

                    if (va % N == col && vb % N == col && (va / N) > (vb / N))
                        conflicts++;
                }
            }
        }

        return h + 2 * conflicts;
    }


    private List<String> neighbors(String s) {

        List<String> list = new ArrayList<>();

        int pos = s.indexOf('*');

        int r = pos / N;
        int c = pos % N;

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        for (int[] d : dirs) {

            int nr = r + d[0];
            int nc = c + d[1];

            if (nr >= 0 && nr < N && nc >= 0 && nc < N) {

                int np = nr * N + nc;

                char[] arr = s.toCharArray();

                char tmp = arr[pos];
                arr[pos] = arr[np];
                arr[np] = tmp;

                list.add(new String(arr));
            }
        }

        return list;
    }

    private int value(char c) {

        if (c == '*') return 0;
        if (Character.isDigit(c)) return c - '0';
        return 10 + (c - 'A');
    }

    private void printStats(int cost) {

        long time = System.currentTimeMillis() - startTime;

        System.out.println("Longitud de la solución (movimientos): " + cost);
        System.out.println("Nodos expandidos: " + nodesExpanded);
        System.out.println("Tiempo(ms): " + time);
        System.out.println("-------------------------");
    }

    public void compare() {

        System.out.println("=== Manhattan ===");
        solve("manhattan");

        System.out.println("=== Linear Conflict ===");
        solve("linear");
    }
}