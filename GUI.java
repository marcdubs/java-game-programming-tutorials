public class GUI implements GameObject
{
	private Sprite backgroundSprite;
	private GUIButton[] buttons;
	private Rectangle rect = new Rectangle();
	private boolean fixed;

	public GUI(Sprite backgroundSprite, GUIButton[] buttons, int x, int y, boolean fixed)
	{
		this.backgroundSprite = backgroundSprite;
		this.buttons = buttons;
		this.fixed = fixed;

		rect.x = x;
		rect.y = y;

		if(backgroundSprite != null) 
		{
			rect.w = backgroundSprite.getWidth();
			rect.h = backgroundSprite.getHeight();
		}
	}

	public GUI(GUIButton[] buttons, int x, int y, boolean fixed)
	{
		this(null, buttons, x, y, fixed);
	}


	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{
		if(backgroundSprite != null)
			renderer.renderSprite(backgroundSprite, rect.x, rect.y, xZoom, yZoom, fixed);

		if(buttons != null)
			for(int i = 0; i < buttons.length; i++)
				buttons[i].render(renderer, xZoom, yZoom, rect);
	}

	//Call at 60 fps rate.
	public void update(Game game)
	{
		if(buttons != null)
			for(int i = 0; i < buttons.length; i++)
				buttons[i].update(game);
	}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
	{
		boolean stopChecking = false;

		if(!fixed) 
			mouseRectangle = new Rectangle(mouseRectangle.x + camera.x, mouseRectangle.y + camera.y, 1, 1);
		else
			mouseRectangle = new Rectangle(mouseRectangle.x, mouseRectangle.y, 1, 1);
		
		if(rect.w == 0 || rect.h == 0 || mouseRectangle.intersects(rect)) 
		{
			mouseRectangle.x -= rect.x;
			mouseRectangle.y -= rect.y;
			for(int i = 0; i < buttons.length; i++) 
			{
				boolean result = buttons[i].handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
				if(stopChecking == false)
					stopChecking = result;
			}
		}

		return stopChecking;
	}

	public int getLayer() {
		return Integer.MAX_VALUE;
	}

	public Rectangle getRectangle() {
		return rect;
	}


}