import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Map
{
	private Sprite background;
	private Tiles tileSet;
	private File mapFile;

	private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
	private HashMap<Integer, String> comments = new HashMap<Integer, String>();


	public Map(Sprite background, Tiles tileSet, File mapFile)
	{
		this.background = background;
		this.tileSet = tileSet;
		this.mapFile = mapFile;
		try 
		{
			Scanner scanner = new Scanner(mapFile);
			int currentLine = 0;
			while(scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				if(!line.startsWith("//"))
				{

					String[] splitString = line.split(",");
					if(splitString.length >= 3)
					{
						MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]),
															   Integer.parseInt(splitString[1]),
															   Integer.parseInt(splitString[2]));
						mappedTiles.add(mappedTile);
					}
				}
				else
				{
					comments.put(currentLine, line);
				}
				currentLine++;
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}


	}

	public void saveMap()
	{
		try
		{
			int currentLine = 0;
			if(mapFile.exists()) 
				mapFile.delete();
			mapFile.createNewFile();

			PrintWriter printWriter = new PrintWriter(mapFile);

			for(int i = 0; i < mappedTiles.size(); i++) {
				if(comments.containsKey(currentLine))
					printWriter.println(comments.get(currentLine));

				MappedTile tile = mappedTiles.get(i);
				printWriter.println(tile.id + "," + tile.x + "," + tile.y);
				currentLine++;
			}

			printWriter.close();
		} 
		catch (java.io.IOException e)
		{
			e.printStackTrace();
		}
	}

	public void update(Game game)
	{

	}

	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{
		Rectangle camera = renderer.getCamera();
		renderer.renderSprite(background, 0, 0, camera.w, camera.h, 1, 1, true, camera.x, camera.y);
		for(int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++)
		{
			MappedTile mappedTile = mappedTiles.get(tileIndex);
			tileSet.renderTile(mappedTile.id, renderer, mappedTile.x, mappedTile.y, xZoom, yZoom);
		}	
	}

	public void setTile(int tileX, int tileY, int tileID)
	{
		mappedTiles.add(new MappedTile(tileID, tileX, tileY));
	}

	public void removeTile(int tileX, int tileY, int xZoom, int yZoom)
	{
		for(int i = mappedTiles.size() - 1; i >= 0; i--)
		{
			MappedTile mappedTile = mappedTiles.get(i);
			Sprite sprite = tileSet.getSprite(mappedTile.id);
			Rectangle mappedTileRect = new Rectangle(mappedTile.x, mappedTile.y, sprite.getWidth() * xZoom, sprite.getHeight() * yZoom);
			Rectangle mouse = new Rectangle(tileX, tileY, 1, 1);
			if(mouse.intersects(mappedTileRect)) {
				mappedTiles.remove(i);
				break;
			}
		}
	}

	public int getMaxX()
	{
		return background.getWidth();
	}

	//Tile ID in the tileSet and the position of the tile in the map
	class MappedTile
	{
		public int id, x, y;

		public MappedTile(int id, int x, int y)
		{
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
}