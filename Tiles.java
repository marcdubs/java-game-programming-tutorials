import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Tiles 
{
	private SpriteSheet spriteSheet;
	private ArrayList<Tile> tilesList = new ArrayList<Tile>();

	//This will only work assuming the sprites in the spriteSheet have been loaded.
	public Tiles(Game game, File tilesFile)
	{
		this.spriteSheet = spriteSheet;
		try 
		{
			Scanner scanner = new Scanner(tilesFile);
			while(scanner.hasNextLine()) 
			{
				String line = scanner.nextLine();
				if(!line.startsWith("//"))
				{
					String[] splitString = line.split("-");
					String tileName = splitString[0];
					Tile tile = new Tile(tileName, new Sprite(game.loadImage("Sprites/" + splitString[1])));
					if(splitString.length > 2)
						tile.type = splitString[2];
					
					tilesList.add(tile);
				}
			}
		} 
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom)
	{
		if(tileID >= 0 && tilesList.size() > tileID)
		{
			renderer.renderSprite(tilesList.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom, false);
		}
		else
		{
			System.out.println("TileID " + tileID + " is not within range " + tilesList.size() + ".");
		}
	}

	public int size()
	{
		return tilesList.size();
	}

	public Sprite[] getSprites()
	{
		Sprite[] sprites = new Sprite[size()];

		for(int i = 0; i < sprites.length; i++)
			sprites[i] = tilesList.get(i).sprite;

		return sprites;
	}

	public Sprite getSprite(int tileID)
	{
		if(tileID >= 0 && tileID < size())
			return tilesList.get(tileID).sprite;

		return null;
	}

	public String getType(int tileID)
	{
		if(tileID >= 0 && tileID < size())
			return tilesList.get(tileID).type;

		return null;
	}


	class Tile 
	{
		public String tileName;
		public Sprite sprite;
		public String type = "normal";

		public Tile(String tileName, Sprite sprite) 
		{
			this.tileName = tileName;
			this.sprite = sprite;
		}
	}
}