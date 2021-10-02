public class Player implements GameObject
{
	private Rectangle playerRectangle;
	private int speed = 10;

	//0 = Right, 1 = Left, 2 = Up, 3 = Down
	private int direction = 0;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;

	public Player(Sprite sprite)
	{
		this.sprite = sprite;

		if(sprite != null && sprite instanceof AnimatedSprite)
			animatedSprite = (AnimatedSprite) sprite;

		updateDirection();
		playerRectangle = new Rectangle(32, 16, 16, 32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}

	private void updateDirection()
	{
		if(animatedSprite != null)
		{
			animatedSprite.setAnimationRange(direction * 8, (direction * 8) + 7);
		}
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{
		if(animatedSprite != null)
			renderer.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		else if(sprite != null)
			renderer.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		else
			renderer.renderRectangle(playerRectangle, xZoom, yZoom, false);
	}

	//Call at 60 fps rate.
	public void update(Game game)
	{
		KeyBoardListener keyListener = game.getKeyListener();

		boolean didMove = false;
		int newDirection = direction;

		if(keyListener.left())
		{
			newDirection = 1;
			didMove = true;
			playerRectangle.x -= speed;
		}
		if(keyListener.right())
		{
			newDirection = 0;
			didMove = true;
			playerRectangle.x += speed;
		}
		if(keyListener.up()) 
		{
			playerRectangle.y -= speed;
			didMove = true;
			newDirection = 2;	
		}
		if(keyListener.down()) 
		{
			newDirection = 3;
			didMove = true;
			playerRectangle.y += speed;
		}

		if(newDirection != direction) 
		{
			direction = newDirection;
			updateDirection();
		}

		if(!didMove) {
			animatedSprite.reset();
		}

		updateCamera(game.getRenderer().getCamera());

		if(didMove)
			animatedSprite.update(game);
	}

	public void updateCamera(Rectangle camera) {
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h / 2);
	}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
}