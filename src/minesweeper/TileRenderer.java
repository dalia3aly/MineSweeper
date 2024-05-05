package minesweeper;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TileRenderer {
    private Tile tile;

    public TileRenderer(Tile tile) {
        this.tile = tile;
    }

    public void draw(Graphics g) {
        BufferedImage image = getImage();
        if (image != null) {
            
            int x = tile.getPosX() * Tile.TILE_WIDTH;
            int y = tile.getPosY() * Tile.TILE_HEIGHT;
            g.drawImage(image, x, y, null);
        }
    }

    private BufferedImage getImage() {
        if (tile.isRevealed()) {
            if (tile.hasBomb()) {
                return ImageManager.getMineActive();
            } else if (tile.getAdjacentBombs() > 0){
                return ImageManager.getNumberImage(tile.getAdjacentBombs());
            }
        } else if (tile.isFlagged()) {
            return ImageManager.getFlagRight();
		} else if (tile.isClicked()) {
			return ImageManager.getTileBlank();
		} else if (tile.isClicked() && tile.hasBomb()) {
			// the bomb image for clicked bombs
			return ImageManager.getMineRevealed();
		} else if (tile.isClicked() && tile.isFlagged() && !tile.hasBomb()) {
            // the wrong flag image when the tile is flagged incorrectly
            return ImageManager.getFlagWrong();
        } else {
            return ImageManager.getTileUnclicked();
        }
		return null;
    }
}
