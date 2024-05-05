package minesweeper;

import javax.swing.JFrame;

// Main will create instances of GameBoard and GameUI, and place them within this GameWindow
public class GameWindow extends JFrame {
	public GameWindow(GameUI gameUI) {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gameUI);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
