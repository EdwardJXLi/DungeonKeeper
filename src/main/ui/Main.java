package ui;

/*
 * Main class. Chooses to either launch in graphics or terminal mode.
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Game...");
//        new TerminalGame(32, 24);
        new GraphicalGame(32, 24);
    }
}
