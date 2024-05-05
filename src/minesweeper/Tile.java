package minesweeper;


public class Tile extends AbstractTile implements Clickable {
	public static final int TILE_WIDTH = 50;
	public static final int TILE_HEIGHT = 50;

	private GameBoard gameBoard;
	private boolean bomb;
	private boolean flagged;
	private boolean revealed;
	private int adjacentBombs;
	
	public Tile(int x, int y, boolean bomb, GameBoard gameBoard) { 
	    super(x, y);
		this.bomb = bomb;
		this.gameBoard = gameBoard;
		this.flagged = false;
		this.revealed = false;
		this.adjacentBombs = 0;
	}
		
		// standard getters and setters for the properties
		
		public boolean hasBomb() {
            return bomb;
        }
		
		public boolean isFlagged() {
            return flagged;
        }
		
		public void setFlagged(boolean flagged) {
            this.flagged = flagged;
        }
		
		public boolean isRevealed() {
            return revealed;
        }
		
		public void setRevealed(boolean revealed) {
            this.revealed = revealed;
        }
		
		public int getAdjacentBombs() {
            return adjacentBombs;
        }
		
		public void setAdjacentBombs(int adjacentBombs) {
            this.adjacentBombs = adjacentBombs;

		}
		
		// implementation of the Clickable interface
		
		 @Override
		 public void onClick() {
		    if (!clicked && !flagged) {
		        clicked = true;
		        revealed = true;
		        handleClick();
		    }

		 }
		
		@Override
		public void onRightClick() {
			if (!revealed) {
				flagged = !flagged;
			}
		}
		
		// implementation of the AbstractTile method
		
		@Override
		public void handleClick() {
			if (bomb) {
				System.out.println("Game Over!");
				gameBoard.setGameOver(true);
				gameBoard.revealAllMines();
				if (gameBoard.getGameOverCallback() != null) {
					gameBoard.getGameOverCallback().onGameOver(false);
				}
			}
		}
		
		// other methods that don't vary between different types of tiles
		
		public void incrementAdjacentBombs() {
			adjacentBombs++;
		}
		
		public void decrementAdjacentBombs() {
			adjacentBombs--;
		}
		
		public void resetTile() {
			flagged = false;
			revealed = false;
			clicked = false;
			adjacentBombs = 0;
		}
		
		public void setBomb(boolean bomb) {
			this.bomb = bomb;
		}

}
