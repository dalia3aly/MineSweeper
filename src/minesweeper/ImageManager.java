package minesweeper;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.net.URL;

// provides images to TileRenderer
public class ImageManager {
	 private static BufferedImage tileUnclicked;
	    private static BufferedImage tileBlank;
	    private static BufferedImage[] numberImages = new BufferedImage[6];
	    private static BufferedImage mineActive;
	    private static BufferedImage mineRevealed;
	    private static BufferedImage flagRight;
	    private static BufferedImage flagWrong;

	    static {
	    	 try {
	             tileUnclicked = loadImage("/minesweeper/resources/Tile_unclicked.png");
	             tileBlank = loadImage("/minesweeper/resources/Tile_blank.png");
	             mineActive = loadImage("/minesweeper/resources/Mine_active.png");
	             mineRevealed = loadImage("/minesweeper/resources/Mine_revealed.png");
	             flagRight = loadImage("/minesweeper/resources/Flag_right.png");
	             flagWrong = loadImage("/minesweeper/resources/Flag_wrong.png");
	             // for tiles with numbers 1-6
	             for (int i = 0; i < numberImages.length; i++) {
	                 numberImages[i] = loadImage("/minesweeper/resources/Tile_" + (i + 1) + ".png");
	             }
	         } catch (IOException e) {
	             e.printStackTrace();
	 // for exceptions or errors in loading resources, we can use a default image
	             tileUnclicked = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	         }
	    }

	    private static BufferedImage loadImage(String path) throws IOException {
	        URL url = ImageManager.class.getResource(path);
	        if (url == null) {
	            throw new IOException("Resource not found: " + path);
	        }
	        return ImageIO.read(url);
	    }

	    public static BufferedImage getTileUnclicked() {
	        return tileUnclicked;
	    }

	    public static BufferedImage getTileBlank() {
	        return tileBlank;
	    }

	    public static BufferedImage getNumberImage(int number) {
	        if (number > 0 && number <= 6) {
	            return numberImages[number - 1];
	        }
	        throw new IllegalArgumentException("Invalid number for getting an image: " + number);
	    }

	    public static BufferedImage getMineActive() {
	        return mineActive;
	    }

	    public static BufferedImage getMineRevealed() {
	        return mineRevealed;
	    }

	    public static BufferedImage getFlagRight() {
	        return flagRight;
	    }

	    public static BufferedImage getFlagWrong() {
	        return flagWrong;
	    }
	    
	    static {
	        try {
	            tileUnclicked = loadImage("/minesweeper/resources/Tile_unclicked.png");
	        } catch (IOException e) {
	            tileUnclicked = createFallbackImage();
	            logError("Failed to load 'Tile Unclicked' image.", e);
	        }
	        
			try {
				tileBlank = loadImage("/minesweeper/resources/Tile_blank.png");
			} catch (IOException e) {
				tileBlank = createFallbackImage();
				logError("Failed to load 'Tile Blank' image.", e);
			}
			
			try {
				mineActive = loadImage("/minesweeper/resources/Mine_active.png");
			} catch (IOException e) {
				mineActive = createFallbackImage();
				logError("Failed to load 'Mine Active' image.", e);
			}
			
			try {
				mineRevealed = loadImage("/minesweeper/resources/Mine_revealed.png");
			} catch (IOException e) {
				mineRevealed = createFallbackImage();
				logError("Failed to load 'Mine Revealed' image.", e);
			}
			
			try {
				flagRight = loadImage("/minesweeper/resources/Flag_right.png");
			} catch (IOException e) {
				flagRight = createFallbackImage();
				logError("Failed to load 'Flag Right' image.", e);
			}
			
			try {
				flagWrong = loadImage("/minesweeper/resources/Flag_wrong.png");
			} catch (IOException e) {
				flagWrong = createFallbackImage();
				logError("Failed to load 'Flag Wrong' image.", e);
			}
			
			for (int i = 0; i < numberImages.length; i++) {
				try {
					numberImages[i] = loadImage("/minesweeper/resources/Tile_" + (i + 1) + ".png");
				} catch (IOException e) {
					numberImages[i] = createFallbackImage();
					logError("Failed to load 'Tile " + (i + 1) + "' image.", e);
				}
			}
	        // Repeat for other images...
	        
	    }

	    private static BufferedImage createFallbackImage() {
	        BufferedImage fallback = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	        fallback.setRGB(0, 0, Color.GRAY.getRGB()); // Create a single gray pixel as a fallback
	        return fallback;
	    }
	    
	    public static ImageIcon getImageIcon(String path) {
	        URL url = ImageManager.class.getResource(path);
	        if (url != null) {
	            return new ImageIcon(url);
	        }
	        return null;
	    }

	    private static void logError(String message, Throwable t) {
	        // Log the error to a file or system log
	        System.err.println(message);
	        t.printStackTrace();
	    }

	}
