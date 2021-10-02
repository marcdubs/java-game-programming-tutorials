import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject
{

	public AnimatedSprite(BufferedImage image) 
	{
		super(image);
	}

	//Call every time physically possible.
	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{

	}

	//Call at 60 fps rate.
	public void update(Game game)
	{

	}

}