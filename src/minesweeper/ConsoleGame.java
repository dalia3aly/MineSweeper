package minesweeper;

import java.util.Scanner;

public class ConsoleGame {
    private GameBoard gameBoard;
    private Scanner scanner;

    public ConsoleGame(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (!gameBoard.isGameOver()) {
            System.out.println(gameBoard);
            System.out.print("Enter row and column numbers: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            gameBoard.revealTile(row, col);
        }
        System.out.println("Game Over!");
        System.out.println(gameBoard);
    }
}
