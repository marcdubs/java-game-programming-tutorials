public class SDKButton extends GUIButton
{
	private Game game;
	private int tileID;
	private boolean isGreen = false;

	public SDKButton(Game game, int tileID, Sprite tileSprite, Rectangle rect) 
	{
		super(tileSprite, rect, true);
		this.game = game;
		this.tileID = tileID;
		rect.generateGraphics(0xFFDB3D);
	}

	@Override
	public void update(Game game) 
	{
		if(tileID == game.getSelectedTile())
		{
			if(!isGreen) 
			{
				rect.generateGraphics(0x67FF3D);
				isGreen = true;
			}
		}
		else
		{
			if(isGreen)
			{
				rect.generateGraphics(0xFFDB3D);
				isGreen = false;
			}
		}
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect)
	{
		renderer.renderRectangle(rect, interfaceRect, 1, 1, fixed);
		renderer.renderSprite(sprite, 
							  rect.x + interfaceRect.x + (xZoom - (xZoom - 1))*rect.w/2/xZoom, 
							  rect.y + interfaceRect.y + (yZoom - (yZoom - 1))*rect.h/2/yZoom, 
							  xZoom - 1, 
							  yZoom - 1, 
							  fixed);
	}

	public void activate()
	{
		game.changeTile(tileID);
	}

}