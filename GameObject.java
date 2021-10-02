public interface GameObject 
{

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom);

	//Call at 60 fps rate.
	public void update(Game game);

	//Call whenever mouse is clicked on Canvas.
	//Return true to stop checking other clicks.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);

	public int getLayer();

	public Rectangle getRectangle();
}