package minesweeper;

public interface GameOverCallback {
	
	public void onGameOver(boolean lost);
	public void requestUIUpdate();

	public void getGameOverCallback();
}
