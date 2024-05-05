package minesweeper;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GameUI gameUI = new GameUI();
        new GameWindow(gameUI);

        Thread consoleThread = new Thread(() -> {
            ConsoleGame consoleGame = new ConsoleGame(gameBoard);
            consoleGame.start();
        });
        // start console game in parallel
        consoleThread.start();
    }
}