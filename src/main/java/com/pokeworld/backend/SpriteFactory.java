package com.pokeworld.backend;

import java.util.HashMap;

import com.pokeworld.backend.entity.Player.Direction;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Handles overworld sprites
 * 
 * @author shinobi
 */
public class SpriteFactory
{
	private HashMap<Integer, SpriteSheet> spriteSheets;

	/**
	 * Returns the requested sprite
	 * 
	 * @param dir
	 * @param isMoving
	 * @param isLeftFoot
	 * @param sprite
	 * @return
	 */
	public Image getSprite(Direction dir, boolean isMoving, boolean isLeftFoot, int sprite)
	{
		SpriteSheet sheet = spriteSheets.get(sprite);
		if(isMoving)
		{
			if(isLeftFoot)
			{
				switch(dir)
				{
					case Up:
						return sheet.getSprite(0, 0);
					case Down:
						return sheet.getSprite(0, 2);
					case Left:
						return sheet.getSprite(0, 3);
					case Right:
						return sheet.getSprite(0, 1);
				}
			}
			else
			{
				switch(dir)
				{
					case Up:
						return sheet.getSprite(2, 0);
					case Down:
						return sheet.getSprite(2, 2);
					case Left:
						return sheet.getSprite(2, 3);
					case Right:
						return sheet.getSprite(2, 1);
				}
			}
		}
		else
		{
			switch(dir)
			{
				case Up:
					return sheet.getSprite(1, 0);
				case Down:
					return sheet.getSprite(1, 2);
				case Left:
					return sheet.getSprite(1, 3);
				case Right:
					return sheet.getSprite(1, 1);
			}
		}
		return null;
	}

	/**
	 * Initialises the database of sprites
	 */
	public SpriteFactory()
	{

		spriteSheets = new HashMap<Integer, SpriteSheet>();
		try
		{
			String location;
			Image temp;
			Image[] imgArray = new Image[500];
			SpriteSheet ss = null;
			/* WARNING: Change 224 to the amount of sprites we have in client
			 * the load bar only works when we don't make a new SpriteSheet ie.
			 * ss = new SpriteSheet(temp, 41, 51); needs to be commented out in
			 * order for the load bar to work. */
			for(int i = -7; i < 474; i++)
			{
				try
				{
					location = "res/characters/" + String.valueOf(i) + ".png";
					temp = new Image(location);
					imgArray[i + 7] = temp;
					ss = new SpriteSheet(temp, 41, 51);

					spriteSheets.put(i, ss);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public SpriteFactory(Image[] imgArray)
	{
		spriteSheets = new HashMap<Integer, SpriteSheet>();

		for(int i = -7; i < 474; i++)
		{
			spriteSheets.put(i, new SpriteSheet(imgArray[i + 7], 41, 51));
			// System.out.println(i);
		}
	}
}
