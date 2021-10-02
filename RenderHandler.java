import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
{
	private BufferedImage view;
	private int[] pixels;

	public RenderHandler(int width, int height) 
	{
		//Create a BufferedImage that will represent our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		//Create an array for pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	public void render(Graphics graphics)
	{
		for(int index = 0; index < pixels.length; index++) {
			pixels[index] = (int)(Math.random() * 0xFFFFFF);
		}

		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

}