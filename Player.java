public class Player implements GameObject
{
	private Rectangle playerRectangle;
	private int speed = 15;
	private int jumpSpeed = 40;

	//0 = Right, 1 = Left, 2 = Up, 3 = Down
	private int direction = 0;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;

	private Vector2 velocity = new Vector2();
	private float gravityAcceleration = 1.5f;
	private float acceleration = 10.0f;

	public Player(Sprite sprite, int xZoom, int yZoom)
	{
		this.sprite = sprite;

		if(sprite != null && sprite instanceof AnimatedSprite)
			animatedSprite = (AnimatedSprite) sprite;

		updateDirection();
		playerRectangle = new Rectangle(32, 16, 20*xZoom, 26*yZoom);
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
		int yMin = game.getRenderer().getCamera().h - playerRectangle.h;

		if(playerRectangle.y < yMin)
		{
			velocity.y += gravityAcceleration;
		} else {
			velocity.y = 0;
			playerRectangle.y = yMin;
			velocity.x = 0;
		}

		KeyBoardListener keyListener = game.getKeyListener();

		boolean didMove = false;
		int newDirection = direction;

		boolean onGround = playerRectangle.y == yMin;
		
		if(keyListener.left())
		{
			newDirection = 1;
			didMove = true;
			velocity.x = -acceleration;
		}
		if(keyListener.right())
		{
			newDirection = 0;
			didMove = true;
			velocity.x = acceleration;
		}
		if(keyListener.up()) 
		{
			if(onGround)
				velocity.y -= jumpSpeed;
		}
		if(keyListener.down()) 
		{
			//newDirection = 3;
			//didMove = true;
			//playerRectangle.y += speed;
		}

		if(newDirection != direction) 
		{
			direction = newDirection;
			updateDirection();
		}

		if(!didMove) {
			animatedSprite.reset();
		}

		updateCamera(game.getMap(), game.getRenderer().getCamera());

		if(didMove)
			animatedSprite.update(game);

		playerRectangle.x += velocity.x;
		playerRectangle.y += velocity.y;
	}

	public void updateCamera(Map map, Rectangle camera) {
		int x = playerRectangle.x - (camera.w / 2);
		if(x < 0)
			x = 0;

		if(x + camera.w > map.getMaxX())
			x = map.getMaxX() - camera.w;

		camera.x = x;
		// camera.y = playerRectangle.y - (camera.h / 2);
	}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
}