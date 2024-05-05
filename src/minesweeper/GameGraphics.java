package minesweeper;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;


public class GameGraphics {

	public static BufferedImage resize(BufferedImage img, int width, int height) {
		Image tempImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(tempImage, 0, 0, null);
		g2d.dispose();
		return resizedImage;
	}

	public static BufferedImage getBombImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public static BufferedImage getNumberImage(int adjacentBombs) {
		// TODO Auto-generated method stub
		return null;
	}

	public static BufferedImage getFlaggedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public static BufferedImage getHiddenImage() {
		// TODO Auto-generated method stub
		return null;
	}

}
