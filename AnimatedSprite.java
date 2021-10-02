import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject
{

	private Sprite[] sprites;
	private int currentSprite = 0;
	private int speed;
	private int counter = 0;

	private int startSprite = 0;
	private int endSprite;

	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed) 
	{
		sprites = new Sprite[positions.length];
		this.speed = speed;
		this.endSprite = positions.length - 1;

		for(int i = 0; i < positions.length; i++)
			sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].w, positions[i].h);
	}

	public AnimatedSprite(SpriteSheet sheet, int speed) 
	{
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length - 1;
	}

	//@param speed represents how many frames pass until the sprite changes
	public AnimatedSprite(BufferedImage[] images, int speed)
	{
		sprites = new Sprite[images.length];
		this.speed = speed;
		this.startSprite = images.length - 1;

		for(int i = 0; i < images.length; i++)
			sprites[i] = new Sprite(images[i]);

	}

	//Render is dealt specifically with the Layer class.
	public void render(RenderHandler renderer, int xZoom, int yZoom) {}

	//Call whenever mouse is clicked on Canvas.
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

	//Call at 60 fps rate.
	public void update(Game game)
	{
		counter++;
		if(counter >= speed) 
		{
			counter = 0;
			incrementSprite();
		}
	}

	public void reset()
	{
		counter = 0;
		currentSprite = startSprite;
	}

	public void setAnimationRange(int startSprite, int endSprite)
	{
		this.startSprite = startSprite;
		this.endSprite = endSprite;
		reset();
	}

	public int getWidth()
	{
		return sprites[currentSprite].getWidth();
	}

	public int getHeight()
	{
		return sprites[currentSprite].getHeight();
	}

	public int[] getPixels()
	{
		return sprites[currentSprite].getPixels();
	}

	public void incrementSprite() 
	{
		currentSprite++;
		if(currentSprite >= endSprite)
			currentSprite = startSprite;
	}

	public int getLayer() {
		System.out.println("Called getLayer() of AnimatedSprite! This has no meaning here.");
		return -1;
	}

	public Rectangle getRectangle() {
		System.out.println("Called getRectangle() of AnimatedSprite! This has no meaning here.");
		return null;
	}

}