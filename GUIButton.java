public abstract class GUIButton implements GameObject
{
	protected Sprite sprite;
	protected Rectangle rect;
	protected boolean fixed;

	public GUIButton(Sprite sprite, Rectangle rect, Boolean fixed)
	{
		this.sprite = sprite;
		this.rect = rect;
		this.fixed = fixed;
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect)
	{
		renderer.renderSprite(sprite, rect.x + interfaceRect.x, rect.y + interfaceRect.y, xZoom, yZoom, fixed);
	}

	//Call at 60 fps rate.
	public void update(Game game) {}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
	{
		if(mouseRectangle.intersects(rect)) {
			activate();
			return true;
		}

		return false;
	}

	public abstract void activate();

	public int getLayer() {
		return Integer.MAX_VALUE;
	}

	public Rectangle getRectangle() {
		return rect;
	}

}