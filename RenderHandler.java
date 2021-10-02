import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
{
	private BufferedImage view;
	private Rectangle camera;
	private int[] pixels;

	public RenderHandler(int width, int height) 
	{
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		camera = new Rectangle(0, 0, width, height);

		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

	}

	//Render our array of pixels to the screen
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

	//Render our image to our array of pixels.
	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed)
	{
		int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imagePixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
	}

	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
	}

	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int renderWidth, int renderHeight, int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), renderWidth, renderHeight, xPosition, yPosition, 
					xZoom, yZoom, fixed, xOffset, yOffset);
	}

	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom, boolean fixed)
	{
		int[] rectanglePixels = rectangle.getPixels();
		if(rectanglePixels != null)
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom, fixed);	
	}

	public void renderRectangle(Rectangle rectangle, Rectangle offset, int xZoom, int yZoom, boolean fixed)
	{
		int[] rectanglePixels = rectangle.getPixels();
		if(rectanglePixels != null)
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x + offset.x, rectangle.y + offset.y, xZoom, yZoom, fixed);	
	}

	public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) 
	{
		renderArray(renderPixels, renderWidth, renderHeight, renderWidth, renderHeight, xPosition, yPosition, 
					xZoom, yZoom, fixed, 0, 0);
		// for(int y = 0; y < renderHeight; y++)
		// 	for(int x = 0; x < renderWidth; x++)
		// 		for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
		// 			for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
		// 				setPixel(renderPixels[x + y * renderWidth], (x * xZoom) + xPosition + xZoomPosition, ((y * yZoom) + yPosition + yZoomPosition), fixed);
	}

	/*
		renderPixels = pixels to render
		imageWidth = width of entire image
		imageHeight = height of entire image
		renderWidth = width of image to render
		renderHeight = height of image to render
		xPosition = x position to render image
		yPosition = y position to render image
		xZoom = horizontal zoom
		yZoom = vertical zoom
		fixed = should offset by camera position
		xOffset = offset into the full image to render x
		yOffset = offset into the full image to render y
		
	*/
	public void renderArray(int[] renderPixels, int imageWidth, int imageHeight, int renderWidth, int renderHeight, int xPosition, int yPosition, 
							int xZoom, int yZoom, boolean fixed, int xOffset, int yOffset)
	{
		for(int y = yOffset; y < yOffset + renderHeight; y++)
			for(int x = xOffset; x < xOffset + renderWidth; x++)
				for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
					for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
						setPixel(renderPixels[x + y * imageWidth], ((x - xOffset) * xZoom) + xPosition + xZoomPosition, (((y - yOffset) * yZoom) + yPosition + yZoomPosition), fixed);
	}

	private void setPixel(int pixel, int x, int y, boolean fixed) 
	{
		int pixelIndex = 0;
		if(!fixed) 
		{
			if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h)
				pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
		}
		else
		{
			if(x >= 0 && y >= 0 && x <= camera.w && y <= camera.h)
				pixelIndex = x + y * view.getWidth();
		}

		if(pixels.length > pixelIndex && pixel != Game.alpha)
			pixels[pixelIndex] = pixel;
	}

	public Rectangle getCamera() 
	{
		return camera;
	}

	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}

}