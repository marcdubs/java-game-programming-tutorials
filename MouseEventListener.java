import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener 
{
	private Game game;

	public MouseEventListener(Game game) 
	{
		this.game = game;
	}

	public void mouseClicked(MouseEvent event)
	{

	}

	public void mouseDragged(MouseEvent event)
	{

	}

	public void mouseEntered(MouseEvent event)
	{

	}

	public void mouseExited(MouseEvent event)
	{

	}

	public void mouseMoved(MouseEvent event)
	{

	}

	public void mousePressed(MouseEvent event)
	{
		if(event.getButton() == MouseEvent.BUTTON1)
			game.leftClick(event.getX(), event.getY());

		if(event.getButton() == MouseEvent.BUTTON3)
			game.rightClick(event.getX(), event.getY());
	}

	public void mouseReleased(MouseEvent event)
	{
		
	}
}