package minesweeper;

public abstract class AbstractTile {
	protected boolean clicked;
    protected int posX;
    protected int posY;

    public AbstractTile(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.clicked = false;
    }

    public abstract void handleClick();
    /**
     * Returns whether the tile has been clicked.
     * @return true if the tile has been clicked, otherwise false.
     */

    // methods that don't vary between different types of tiles
    public boolean isClicked() {
        return clicked;
    }
    
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void resetTile() {
		clicked = false;
	}
	
	public void reveal() {
		clicked = true;
	}
}
