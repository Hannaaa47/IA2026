package Puzzle24;

public class Main {

    public static void main(String[] args) {

        String goal = "123456789ABCDEFGHIJKLMNO*";
        String initial = "123456789A*BCDEFGHIJKLMNO";

        Puzzle24 puzzle = new Puzzle24(initial, goal);

        puzzle.compare(); 
    }
}
