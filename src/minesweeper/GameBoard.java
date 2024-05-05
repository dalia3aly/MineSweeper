package minesweeper;

import java.util.ArrayList;
import java.util.List;



public class GameBoard{
    public static final int TILE_WIDTH = 50;
    public static final int TILE_HEIGHT = 50;
    public static final int TILE_GAP = 1;
    public static final int BOARD_COLS = 15;
    public static final int BOARD_ROWS = 15;
    public static final int MINES = 20;

    // Gameboard dimensions
    public static final int BOARD_WIDTH = TILE_WIDTH * BOARD_COLS + TILE_GAP * (BOARD_COLS + 1);
    public static final int BOARD_HEIGHT = TILE_HEIGHT * BOARD_ROWS + TILE_GAP * (BOARD_ROWS + 1);

    // tiles array
    private Tile[][] tiles = new Tile[BOARD_ROWS][BOARD_COLS];
    private boolean gameOver;

    private List<BoardObserver> observers = new ArrayList<>();

    public GameBoard() {
        resetBoard();
    }

    public void addObserver(BoardObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BoardObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (BoardObserver observer : observers) {
            observer.update();
        }
    }

    public void resetBoard() {
        gameOver = false;
        initializeTiles();
        placeMines();
    }

    protected void initializeTiles() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                tiles[i][j] = new Tile(i, j, false, this);
            }
        }
    }

    protected void placeMines() {
        int minesPlaced = 0;
        while (minesPlaced < MINES) {
            int row = (int) (Math.random() * BOARD_ROWS);
            int col = (int) (Math.random() * BOARD_COLS);
            if (!tiles[row][col].hasBomb()) {
                tiles[row][col].setBomb(true);
                incrementAdjacentMines(row, col);
                minesPlaced++;
            }
        }
    }

    protected void incrementAdjacentMines(int row, int col) {
        for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, BOARD_ROWS - 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, BOARD_COLS - 1); j++) {
                if (i != row || j != col) {
                    tiles[i][j].incrementAdjacentBombs();
                }
            }
        }
    }

    protected void revealAllMines() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (tiles[i][j].hasBomb()) {
                    tiles[i][j].setRevealed(true);
                }
            }
        }
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < BOARD_ROWS && col >= 0 && col < BOARD_COLS) {
            return tiles[row][col];
        }
        throw new IllegalArgumentException("Invalid row and column: " + row + " " + col);
    }

    public int getWidth() {
        return BOARD_COLS;
    }

    public int getHeight() {
        return BOARD_ROWS;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private GameOverCallback gameOverCallback;

    public GameBoard(GameOverCallback callback) {
        this.gameOverCallback = callback;
        resetBoard();
    }

    public GameOverCallback getGameOverCallback() {
        return gameOverCallback;
    }

    public void revealTile(int row, int col) {
        if (isGameOver() || tiles[row][col].isRevealed() || tiles[row][col].isFlagged()) {
            return;
        }

        Tile tile = tiles[row][col];
        tile.setRevealed(true);
        if (tile.hasBomb()) {
            handleGameOver();
        } else {
            revealAdjacentTiles(row, col);
        }

        checkWinCondition();
    }

    // handle GAME OVER condition and make sure ALL mines are revealed before the user prompt panel is shown
    private void handleGameOver() {
    gameOver = true;
    revealAllMines();
    notifyObservers(); // This should trigger the UI to start redrawing mines
    if (gameOverCallback != null) {
        gameOverCallback.onGameOver(true);
        gameOverCallback.requestUIUpdate();
    }
}

    // recursive reveal of adjacent tiles
    private void revealAdjacentTiles(int row, int col) {
        if (tiles[row][col].getAdjacentBombs() == 0) {
            for (int i = Math.max(0, row - 1); i <= Math.min(row + 1, BOARD_ROWS - 1); i++) {
                for (int j = Math.max(0, col - 1); j <= Math.min(col + 1, BOARD_COLS - 1); j++) {
                    if (i != row || j != col) {
                        revealTile(i, j);
                    }
                }
            }
        }
    }

    private void checkWinCondition() {
        boolean allTilesRevealed = true;
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                if (!tiles[i][j].isRevealed() && !tiles[i][j].hasBomb()) {
                    allTilesRevealed = false;
                    break;
                }
            }
            if (!allTilesRevealed) break;
        }

        if (allTilesRevealed) {
            gameOver = true;
            if (gameOverCallback != null) {
                gameOverCallback.onGameOver(false);
                gameOverCallback.requestUIUpdate();
            }
            notifyObservers();
        }
    }
}
