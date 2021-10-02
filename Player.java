public class Player implements GameObject
{
	Rectangle playerRectangle;
	int speed = 10;

	public Player()
	{
		playerRectangle = new Rectangle(32, 16, 16, 32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{
		renderer.renderRectangle(playerRectangle, xZoom, yZoom);
	}

	//Call at 60 fps rate.
	public void update(Game game)
	{
		KeyBoardListener keyListener = game.getKeyListener();

		if(keyListener.up())
			playerRectangle.y -= speed;
		if(keyListener.down())
			playerRectangle.y += speed;
		if(keyListener.left())
			playerRectangle.x -= speed;
		if(keyListener.right())
			playerRectangle.x += speed;

		updateCamera(game.getRenderer().getCamera());
	}

	public void updateCamera(Rectangle camera) {
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h / 2);
	}

}