import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Map
{
	private Tiles tileSet;
	private int fillTileID = -1;

	private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();
	private HashMap<Integer, String> comments = new HashMap<Integer, String>();

	private File mapFile;

	public Map(File mapFile, Tiles tileSet)
	{
		this.mapFile = mapFile;
		this.tileSet = tileSet;
		try 
		{
			Scanner scanner = new Scanner(mapFile);
			int currentLine = 0;
			while(scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				if(!line.startsWith("//"))
				{
					if(line.contains(":")) 
					{
						String[] splitString = line.split(":");
						if(splitString[0].equalsIgnoreCase("Fill"))
						{
							fillTileID = Integer.parseInt(splitString[1]);
							continue;
						}
					}


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

	public void setTile(int tileX, int tileY, int tileID)
	{
		boolean foundTile = false;

		for(int i = 0; i < mappedTiles.size(); i++)
		{
			MappedTile mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY) {
				mappedTile.id = tileID;
				foundTile = true;
				break;
			}
		}

		if(!foundTile)
			mappedTiles.add(new MappedTile(tileID, tileX, tileY));
	}

	public void removeTile(int tileX, int tileY)
	{
		for(int i = 0; i < mappedTiles.size(); i++)
		{
			MappedTile mappedTile = mappedTiles.get(i);
			if(mappedTile.x == tileX && mappedTile.y == tileY) {
				mappedTiles.remove(i);
			}
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

			if(fillTileID >= 0) {
				if(comments.containsKey(currentLine)) 
				{
					printWriter.println(comments.get(currentLine));
					currentLine++;
				}
				printWriter.println("Fill:" + fillTileID);
			}

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

	public void render(RenderHandler renderer, int xZoom, int yZoom)
	{
		int tileWidth = 16 * xZoom;
		int tileHeight = 16 * yZoom;

		if(fillTileID >= 0)
		{
			Rectangle camera = renderer.getCamera();

			for(int y = camera.y - tileHeight - (camera.y % tileHeight); y < camera.y + camera.h; y+= tileHeight)
			{
				for(int x = camera.x - tileWidth - (camera.x % tileWidth); x < camera.x + camera.w; x+= tileWidth)
				{
					tileSet.renderTile(fillTileID, renderer, x, y, xZoom, yZoom);
				}
			}
		}

		for(int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++)
		{
			MappedTile mappedTile = mappedTiles.get(tileIndex);
			tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
		}
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