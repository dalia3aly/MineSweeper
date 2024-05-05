package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUI extends JPanel implements GameOverCallback, BoardObserver {
    private GameBoard gameBoard;
    private JPanel gameOverPanel;

    public GameUI() {
        this.gameBoard = new GameBoard(this);
        this.gameBoard.addObserver(this);
        setPreferredSize(
                new Dimension(gameBoard.getWidth() * Tile.TILE_WIDTH, gameBoard.getHeight() * Tile.TILE_HEIGHT));
        setupMouseListeners();
        setupGameOverPanel();
    }

    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameBoard.isGameOver()) {
                    return; // stop processing clicks if game is over
                }
                super.mouseClicked(e);
                int x = e.getX() / Tile.TILE_WIDTH;
                int y = e.getY() / Tile.TILE_HEIGHT;
                if (x >= 0 && x < gameBoard.getWidth() && y >= 0 && y < gameBoard.getHeight()) {
                    if (!gameBoard.isGameOver()) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            gameBoard.getTile(x, y).onRightClick();
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            gameBoard.getTile(x, y).onClick();
                        }
                        repaint();
                    }
                }
            }
        });
    }

    private void setupGameOverPanel() {
        gameOverPanel = new JPanel(new BorderLayout());
        gameOverPanel.setSize(this.getSize());
        gameOverPanel.setOpaque(false);

        // load game over image
        ImageIcon gameOverImage = ImageManager.getImageIcon("/resources/game_over.png");
        JLabel imageLabel = new JLabel(gameOverImage);

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 30));

        // Layout adjustments
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(gameOverLabel);
        labelPanel.add(imageLabel);
        gameOverPanel.add(labelPanel, BorderLayout.CENTER);

        JButton replayButton = new JButton("Play Again?");
        replayButton.addActionListener(e -> {
            gameBoard.resetBoard();
            gameOverPanel.setVisible(false);
        });
        gameOverPanel.add(replayButton, BorderLayout.SOUTH);

        this.add(gameOverPanel);
        gameOverPanel.setVisible(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameBoard == null) {
            System.out.println("Error: GameBoard not initialized.");
            return;
        }

        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                Tile tile = gameBoard.getTile(x, y);
                if (tile == null) {
                    System.out.println("Error: Tile at (" + x + ", " + y + ") is null.");
                    continue;
                }
                TileRenderer renderer = new TileRenderer(tile);
                renderer.draw(g);
            }
        }
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void requestUIUpdate() {
        repaint();
    }

    @Override
    public void update() {
        repaint();

        if (gameBoard.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                gameOverPanel.setVisible(true);
            });
        }
    }

    @Override
    public void onGameOver(boolean won) {
        SwingUtilities.invokeLater(() -> {
            String message = won ? "Congratulations! You won! Play again?" : "Game over! Do you want to play again?";
            int response = JOptionPane.showConfirmDialog(this, message, "Game Over", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                gameBoard.resetBoard();
            } else {
                System.exit(0);
            }
        });
    }

    @Override
    public void getGameOverCallback() {
        // TODO Auto-generated method stub

    }
}
