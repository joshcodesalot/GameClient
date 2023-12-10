package com.pokeworld.backend;

import java.util.Iterator;

import com.pokeworld.GameClient;
import com.pokeworld.backend.entity.HMObject;
import com.pokeworld.backend.entity.Player;
import com.pokeworld.backend.entity.Player.Direction;
import com.pokeworld.constants.UserClass;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Represents a map to be rendered on screen
 * 
 * @author shadowkanji
 * @author ZombieBear
 */
public class ClientMap extends TiledMap
{
	private static Image m_grassOverlay;
	// private static Image m_SinnohGrassOverlay;
	private static Image m_SinnohOverlay;
	public int m_x;
	public int m_y;
	private boolean m_isCurrent = false;
	private ClientMapMatrix m_mapMatrix;
	private int m_mapX;
	private int m_mapY;
	private String m_name;
	private int m_walkableLayer;
	private int m_xOffset;
	// map offset modifiers
	private int m_xOffsetModifier;
	private int m_yOffset;
	private int m_yOffsetModifier;

	/**
	 * Default constructor
	 * 
	 * @param f
	 * @param tileSetsLocation
	 * @throws SlickException
	 */
	public ClientMap(String tileSetsLocation) throws SlickException
	{
		super(tileSetsLocation);

		try
		{
			m_grassOverlay = new Image("res/ui/grass_overlay.png", false);
			m_SinnohOverlay = new Image("res/ui/sinnoh_grass.png", false);
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
		m_xOffsetModifier = Integer.parseInt(getMapProperty("xOffsetModifier", "0").trim());
		m_yOffsetModifier = Integer.parseInt(getMapProperty("yOffsetModifier", "0").trim());
		m_xOffset = m_xOffsetModifier;
		m_yOffset = m_yOffsetModifier;
	}

	public int getMapX()
	{
		return m_mapX;
	}

	public int getMapY()
	{
		return m_mapY;
	}

	/**
	 * Returns the map's name
	 * 
	 * @return the map's name
	 */
	public String getName()
	{
		return m_name;
	}

	/**
	 * Returns the index of the walkable layer
	 * 
	 * @return
	 */
	public int getWalkableLayer()
	{
		return m_walkableLayer;
	}

	/**
	 * Returns the X offset of this map
	 * 
	 * @return
	 */
	public int getXOffset()
	{
		return m_xOffset;
	}

	/**
	 * Returns the x offset modifier
	 * 
	 * @return
	 */
	public int getXOffsetModifier()
	{
		return m_xOffsetModifier;
	}

	/**
	 * Returns the Y offset of this map
	 * 
	 * @return
	 */
	public int getYOffset()
	{
		return m_yOffset;
	}

	/**
	 * Returns the y offset modifier
	 * 
	 * @return
	 */
	public int getYOffsetModifier()
	{
		return m_yOffsetModifier;
	}

	/**
	 * Returns true if the player is colliding with an object
	 * 
	 * @param p
	 * @param d
	 * @return
	 */
	public boolean isColliding(Player p, Direction d)
	{
		int newX = 0, newY = 0;
		switch(d)
		{
			case Up:
				newX = p.getServerX();
				newY = p.getServerY() - 32;
				break;
			case Down:
				newX = p.getServerX();
				newY = p.getServerY() + 32;
				break;
			case Left:
				newX = p.getServerX() - 32;
				newY = p.getServerY();
				break;
			case Right:
				newX = p.getServerX() + 32;
				newY = p.getServerY();
				break;
		}
		if(isNewMap(newX, newY))
			return false;
		for(HMObject m_hmObj : m_mapMatrix.getHMObjects())
			if(m_hmObj.getX() == newX && m_hmObj.getY() == newY)
				return true;

		int collisionLayer = 0;
		int waterLayer = 0;
		int ledgeLayer = 0;
		for(int i = 0; i < getLayerCount(); i++)
			// Test for collisions
			if(getLayer(i).name.equals("Collisions") && collisionLayer == 0)
				collisionLayer = getLayer(i).getTileID(newX / 32, (newY + 8) / 32);
			else if(getLayer(i).name.equals("Water") && waterLayer == 0 && GameClient.getInstance().getOurPlayer().getTrainerLevel() < 25)
				waterLayer = getLayer(i).getTileID(newX / 32, (newY + 8) / 32);
			else
			{ // Test for ledges
				if(p.getDirection() != Direction.Left && getLayer("LedgesLeft") != null)
					ledgeLayer = getLayer("LedgesLeft").getTileID(newX / 32, (newY + 8) / 32);
				if(p.getDirection() != Direction.Right && getLayer("LedgesRight") != null && ledgeLayer == 0)
					ledgeLayer = getLayer("LedgesRight").getTileID(newX / 32, (newY + 8) / 32);
				if(p.getDirection() != Direction.Down && getLayer("LedgesDown") != null && ledgeLayer == 0)
					ledgeLayer = getLayer("LedgesDown").getTileID(newX / 32, (newY + 8) / 32);
			}
		// Maybe not the right place, but it works :)
		if(p.getDirection() == Direction.Down && getLayer("LedgesDown") != null && getLayer("LedgesDown").getTileID(newX / 32, (newY + 8) / 32) > 0)
		{
			GameClient.getInstance().move(Direction.Down);
			p.moveDown();
		}
		if(p.getDirection() == Direction.Right && getLayer("LedgesRight") != null && getLayer("LedgesRight").getTileID(newX / 32, (newY + 8) / 32) > 0)
		{
			GameClient.getInstance().move(Direction.Right);
			p.moveRight();
		}
		if(p.getDirection() == Direction.Left && getLayer("LedgesLeft") != null && getLayer("LedgesLeft").getTileID(newX / 32, (newY + 8) / 32) > 0)
		{
			GameClient.getInstance().move(Direction.Left);
			p.moveLeft();
		}
		if(ledgeLayer + collisionLayer + waterLayer != 0)
			return true;
		/* Check NPCs */
		for(int i = 0; i < m_mapMatrix.getPlayers().size(); i++)
		{
			Player tmp = m_mapMatrix.getPlayers().get(i);
			if(tmp.getUsername().equalsIgnoreCase("!NPC!") && tmp.getX() == newX && tmp.getY() == newY)
				return true;
		}
		return false;
	}

	/**
	 * Returns true if this is 1,1 in the map matrix
	 * 
	 * @return
	 */
	public boolean isCurrent()
	{
		return m_isCurrent;
	}

	/**
	 * Returns true if the grass overlay should be drawn
	 * 
	 * @param p
	 * @return
	 */
	public boolean isOnGrass(Player p)
	{
		int newX = 0, newY = 0;
		newX = p.getServerX();
		newY = p.getServerY();

		try
		{
			if(getTileProperty(getLayer("Walkable").getTileID(newX / 32, (newY + 8) / 32), "Grass", "").equalsIgnoreCase("true"))
				return true;

			for(int i = 0; i < getLayerCount(); i++)
				if(getTileProperty(getLayer(i).getTileID(newX / 32, (newY + 8) / 32), "Grass", "").equalsIgnoreCase("true"))
					return true;
		}
		catch(Exception e)
		{
		}
		return false;
	}

	/**
	 * Returns true if this map is/should be rendering on screen
	 * 
	 * @return
	 */
	public boolean isRendering()
	{
		int drawWidth = getXOffset() + getWidth() * 32;
		int drawHeight = getYOffset() + getHeight() * 32;

		if(!(drawWidth < -32 && getXOffset() < -32 || drawWidth > 832 && getXOffset() > 832) && !(drawHeight < -32 && getYOffset() < -32 || drawHeight > 632 && getYOffset() > 632))
			return true;
		return false;
	}

	/**
	 * Reinitializes a map after shifting in the map matrix
	 */
	public void reinitialize()
	{
		m_xOffsetModifier = Integer.parseInt(getMapProperty("xOffsetModifier", "0").trim());
		m_yOffsetModifier = Integer.parseInt(getMapProperty("yOffsetModifier", "0").trim());
		m_xOffset = m_xOffsetModifier;
		m_yOffset = m_yOffsetModifier;
	}

	@Override
	public void render(int x, int y, int sx, int sy, int width, int height, boolean lineByLine)
	{
		for(int ty = 0; ty < height; ty++)
			for(int i = 0; i < layers.size(); i++)
			{
				Layer layer = (Layer) layers.get(i);
				if(!m_isCurrent)
					layer.render(x, y, sx, sy, width, ty, lineByLine, tileWidth, tileHeight);
				else if(!layer.name.equalsIgnoreCase("WalkBehind"))
					layer.render(x, y, sx, sy, width, ty, lineByLine, tileWidth, tileHeight);
			}
	}

	/**
	 * Renders the player, water reflections, grass overlays, and the WalkBehind layer.
	 * 
	 * @param g
	 */
	public void renderTop(Graphics g)
	{
		synchronized(m_mapMatrix.getPlayers())
		{
			Player player;
			Iterator<Player> it = m_mapMatrix.getPlayers().iterator();
			while(it.hasNext())
			{
				player = it.next();
				ClientMap m_curMap = m_mapMatrix.getCurrentMap();
				int m_xOffset = m_curMap.getXOffset();
				int m_yOffset = m_curMap.getYOffset();
				if(player != null && player.getSprite() != 0 && player.getCurrentImage() != null)
				{
					// Draw the player
					player.getCurrentImage().draw(m_xOffset + player.getX() - 4, m_yOffset + player.getY());
					if(m_curMap.shouldReflect(player))
					{
						// If there's a reflection, flip the player's image, set
						// his alpha so its more translucent, and then draw it.
						Image m_reflection = player.getCurrentImage().getFlippedCopy(false, true);
						m_reflection.setAlpha((float) 0.05);
						if(player.getSprite() != -1)
							m_reflection.draw(m_xOffset + player.getX() - 4, m_yOffset + player.getY() + 32);
						else
							m_reflection.draw(m_xOffset + player.getX() - 4, m_yOffset + player.getY() + 8);
					}
					if(m_curMap.wasOnGrass(player) && m_curMap.isOnGrass(player))
					{
						// sinnoh maps use different grass
						if((m_x > 32 && m_y < -37))
						{
							switch(player.getDirection())
							{
								case Up:
									m_SinnohOverlay.draw(m_xOffset + player.getServerX(), m_yOffset + player.getServerY() + 32 + 8);
									break;
								case Left:
									m_SinnohOverlay.copy().draw(m_xOffset + player.getServerX() + 32, m_yOffset + player.getServerY() + 8);
									break;
								case Right:
									m_SinnohOverlay.copy().draw(m_xOffset + player.getServerX() - 32, m_yOffset + player.getServerY() + 8);
									break;
								case Down:
									break;
							}
						}
						else
						{
							switch(player.getDirection())
							{
								case Up:
									m_grassOverlay.draw(m_xOffset + player.getServerX(), m_yOffset + player.getServerY() + 32 + 8);
									break;
								case Left:
									m_grassOverlay.copy().draw(m_xOffset + player.getServerX() + 32, m_yOffset + player.getServerY() + 8);
									break;
								case Right:
									m_grassOverlay.copy().draw(m_xOffset + player.getServerX() - 32, m_yOffset + player.getServerY() + 8);
									break;
								case Down:
									break;
							}
						}
					}
					if(m_curMap.isOnGrass(player) && player.getY() <= player.getServerY())
					{
						if((m_x > 32 && m_y < -37))
						{
							m_SinnohOverlay.draw(m_xOffset + player.getServerX(), m_yOffset + player.getServerY() + 9);
						}
						else
						{
							m_grassOverlay.draw(m_xOffset + player.getServerX(), m_yOffset + player.getServerY() + 9);
						}
					}
					// Draw the walk behind layer
					g.scale(2, 2);
					try
					{
						for(int i = 0; i < getLayer("WalkBehind").height; i++)
							getLayer("WalkBehind")
									.render(getXOffset() / 2, getYOffset() / 2, 0, 0, (int) (GameClient.getInstance().getGUIPane().getWidth() - getXOffset()) / 32 + 32, i, false, 16, 16);
					}
					catch(Exception e)
					{
					}
					g.resetTransform();
					// Draw player names
					if(!player.getUsername().equalsIgnoreCase("!NPC!"))
					{
						switch(player.getAdminLevel())
						{
							case UserClass.ADMIN:
								Color previous = g.getColor();
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 40);
								g.setColor(new Color(255,255, 0));
								g.drawString("<Admin>", m_xOffset + player.getX() - g.getFont().getWidth("<Admin>") / 2 + 16, m_yOffset + player.getY() - 22);
								g.setColor(previous);
								break;
							case UserClass.DEVELOPER:
								previous = g.getColor();
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 40);
								g.setColor(new Color(96, 168, 168));
								g.drawString("<Dev>", m_xOffset + player.getX() - g.getFont().getWidth("<Dev>") / 2 + 16, m_yOffset + player.getY() - 22);
								g.setColor(previous);
								break;
							case UserClass.SUPER_MOD:
								previous = g.getColor();
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 40);
								g.setColor(new Color(120, 0, 192));
								g.drawString("<SuperMod>", m_xOffset + player.getX() - g.getFont().getWidth("<SuperMod>") / 2 + 16, m_yOffset + player.getY() - 22);
								g.setColor(previous);
								break;
							case UserClass.MODERATOR:
								previous = g.getColor();
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 40);
								g.setColor(new Color(120, 0, 192));
								g.drawString("<Mod>", m_xOffset + player.getX() - g.getFont().getWidth("<Mod>") / 2 + 16, m_yOffset + player.getY() - 22);
								g.setColor(previous);
								break;
							case UserClass.VIP:
								previous = g.getColor();
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 40);
								g.setColor(new Color(255, 153, 51));
								g.drawString("<VIP>", m_xOffset + player.getX() - g.getFont().getWidth("<VIP>") / 2 + 16, m_yOffset + player.getY() - 22);
								g.setColor(previous);
								break;
							case UserClass.DEFAULT:
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 36);
								break;
							default:
								g.drawString(player.getUsername(), m_xOffset + player.getX() - g.getFont().getWidth(player.getUsername()) / 2 + 16, m_yOffset + player.getY() - 36);
								break;
						}
					}
				}
			}
		}
	}

	/**
	 * Set to true if this is at 1,1 in the map matrix
	 * 
	 * @param b
	 */
	public void setCurrent(boolean b)
	{
		m_isCurrent = b;
	}

	/**
	 * Sets the map matrix
	 * 
	 * @param m
	 */
	public void setMapMatrix(ClientMapMatrix m)
	{
		m_mapMatrix = m;
	}

	/**
	 * Sets the map x (in map matrix)
	 * 
	 * @param x
	 */
	public void setMapX(int x)
	{
		m_mapX = x;
	}

	/**
	 * Sets the map y (in map matrix)
	 * 
	 * @param y
	 */
	public void setMapY(int y)
	{
		m_mapY = y;
	}

	/**
	 * Sets the map's name
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		m_name = name;
	}

	/**
	 * Sets the x offset and recalibrates surrounding maps if calibrate is set to true
	 * 
	 * @param offset
	 * @param calibrate
	 */
	public void setXOffset(int offset, boolean calibrate)
	{
		m_xOffset = offset;

		if(calibrate)
		{
			// 0, 1 -- Left
			ClientMap map = m_mapMatrix.getMap(m_mapX - 1, m_mapY);
			if(map != null)
				map.setXOffset(offset - map.getWidth() * 32 - m_xOffsetModifier + map.getXOffsetModifier(), false);
			// 2, 1 -- Right
			map = m_mapMatrix.getMap(m_mapX + 1, m_mapY);
			if(map != null)
				map.setXOffset(offset + getWidth() * 32 - getXOffsetModifier() + map.getXOffsetModifier(), false);
			// 1, 0 -- Up
			map = m_mapMatrix.getMap(m_mapX, m_mapY - 1);
			if(map != null)
				map.setXOffset(offset - getXOffsetModifier() + map.getXOffsetModifier(), false);
			// 1, 2 -- Down
			map = m_mapMatrix.getMap(m_mapX, m_mapY + 1);
			if(map != null)
				map.setXOffset(offset - getXOffsetModifier() + map.getXOffsetModifier(), false);
			// 0, 0 -- Upper Left
			map = m_mapMatrix.getMap(m_mapX - 1, m_mapY - 1);
			if(map != null)
				if(m_mapMatrix.getMap(0, 1) != null)
					map.setXOffset(m_mapMatrix.getMap(0, 1).m_xOffset - m_mapMatrix.getMap(0, 1).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else if(m_mapMatrix.getMap(1, 0) != null)
					// exists
					map.setXOffset(m_mapMatrix.getMap(1, 0).m_xOffset - map.getWidth() * 32 - m_mapMatrix.getMap(1, 0).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else
					map.setXOffset(offset - map.getWidth() * 32 - getXOffsetModifier() + map.getXOffsetModifier(), false);
			// 2, 0 -- Upper Right
			map = m_mapMatrix.getMap(m_mapX + 1, m_mapY - 1);
			if(map != null)
				if(m_mapMatrix.getMap(2, 1) != null)
					map.setXOffset(m_mapMatrix.getMap(2, 1).m_xOffset - m_mapMatrix.getMap(2, 1).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else if(m_mapMatrix.getMap(1, 0) != null)
					// exists
					map.setXOffset(m_mapMatrix.getMap(1, 0).m_xOffset + m_mapMatrix.getMap(1, 0).getWidth() * 32 - m_mapMatrix.getMap(1, 0).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else
					map.setXOffset(offset - map.getWidth() * 32 - getXOffsetModifier() + map.getXOffsetModifier(), false);
			// 2, 2 -- Lower Right
			map = m_mapMatrix.getMap(m_mapX + 1, m_mapY + 1);
			if(map != null)
				if(m_mapMatrix.getMap(2, 1) != null)
					map.setXOffset(m_mapMatrix.getMap(2, 1).m_xOffset - m_mapMatrix.getMap(2, 1).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else if(m_mapMatrix.getMap(1, 2) != null)
					// exists
					map.setXOffset(m_mapMatrix.getMap(1, 2).m_xOffset + m_mapMatrix.getMap(1, 2).getWidth() * 32 - m_mapMatrix.getMap(1, 2).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else
					map.setXOffset(offset + getWidth() * 32 - getXOffsetModifier() + map.getXOffsetModifier(), false);
			// 0, 2 -- Lower Left
			map = m_mapMatrix.getMap(m_mapX - 1, m_mapY + 1);
			if(map != null)
				if(m_mapMatrix.getMap(0, 1) != null)
					map.setXOffset(m_mapMatrix.getMap(0, 1).m_xOffset - m_mapMatrix.getMap(0, 1).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else if(m_mapMatrix.getMap(1, 2) != null)
					// exists
					map.setXOffset(m_mapMatrix.getMap(1, 2).m_xOffset - map.getWidth() * 32 - m_mapMatrix.getMap(1, 2).m_xOffsetModifier + map.getXOffsetModifier(), false);
				else
					map.setXOffset(offset + getWidth() * 32 - getXOffsetModifier() + map.getXOffsetModifier(), false);
		}
	}

	/**
	 * Sets the y offset and recalibrates surrounding maps if calibrate is true
	 * 
	 * @param offset
	 * @param calibrate
	 */
	public void setYOffset(int offset, boolean calibrate)
	{
		m_yOffset = offset;

		if(calibrate)
		{
			// 0, 1 -- Left
			ClientMap map = m_mapMatrix.getMap(m_mapX - 1, m_mapY);
			if(map != null)
				map.setYOffset(offset - getYOffsetModifier() + map.getYOffsetModifier(), false);
			// 2, 1 -- Right
			map = m_mapMatrix.getMap(m_mapX + 1, m_mapY);
			if(map != null)
				map.setYOffset(offset - getYOffsetModifier() + map.getYOffsetModifier(), false);
			// 1, 0 -- Top
			map = m_mapMatrix.getMap(m_mapX, m_mapY - 1);
			if(map != null)
				map.setYOffset(offset - map.getHeight() * 32, false);
			// 1, 2 -- Bottom
			map = m_mapMatrix.getMap(m_mapX, m_mapY + 1);
			if(map != null)
				map.setYOffset(offset + getHeight() * 32, false);
			// 2, 0 -- Upper Right
			map = m_mapMatrix.getMap(m_mapX + 1, m_mapY - 1);
			if(map != null)
				if(m_mapMatrix.getMap(2, 1) != null)
					map.setYOffset(m_mapMatrix.getMap(2, 1).m_yOffset - map.getHeight() * 32, false);
				else if(m_mapMatrix.getMap(1, 0) != null)
					// exists
					map.setYOffset(m_mapMatrix.getMap(1, 0).m_yOffset - m_mapMatrix.getMap(1, 0).m_yOffsetModifier + map.getYOffsetModifier(), false);
				else
					map.setYOffset(offset - map.getHeight() * 32, false);
			// 0, 0 -- Upper Left
			map = m_mapMatrix.getMap(m_mapX - 1, m_mapY - 1);
			if(map != null)
				if(m_mapMatrix.getMap(0, 1) != null)
					map.setYOffset(m_mapMatrix.getMap(0, 1).m_yOffset - map.getHeight() * 32, false);
				else if(m_mapMatrix.getMap(1, 0) != null)
					// exists
					map.setYOffset(m_mapMatrix.getMap(1, 0).m_yOffset - m_mapMatrix.getMap(1, 0).m_yOffsetModifier + map.getYOffsetModifier(), false);
				else
					map.setYOffset(offset - map.getHeight() * 32, false);
			// 2, 2 -- Lower Right
			map = m_mapMatrix.getMap(m_mapX + 1, m_mapY + 1);
			if(map != null)
				if(m_mapMatrix.getMap(1, 2) != null)
					map.setYOffset(m_mapMatrix.getMap(1, 2).m_yOffset - m_mapMatrix.getMap(1, 2).m_yOffsetModifier + map.getYOffsetModifier(), false);
				else if(m_mapMatrix.getMap(2, 1) != null)
					// exists
					map.setYOffset(m_mapMatrix.getMap(2, 1).m_yOffset + m_mapMatrix.getMap(2, 1).getHeight() * 32, false);
				else
				{ // Try in previous way
					map.setYOffset(offset + getHeight() * 32, false);
				}
			// 0, 2 -- Lower Left
			map = m_mapMatrix.getMap(m_mapX - 1, m_mapY + 1);
			if(map != null)
				if(m_mapMatrix.getMap(0, 1) != null)
					map.setYOffset(m_mapMatrix.getMap(0, 1).m_yOffset + m_mapMatrix.getMap(0, 1).getHeight() * 32, false);
				else if(m_mapMatrix.getMap(1, 2) != null)
					// exists
					map.setYOffset(m_mapMatrix.getMap(1, 2).m_yOffset - m_mapMatrix.getMap(1, 2).m_yOffsetModifier + map.getYOffsetModifier(), false);
				else
					map.setYOffset(offset + getHeight() * 32, false);
		}
	}

	/**
	 * Returns true if a reflection should be drawn
	 * 
	 * @param p
	 * @return
	 */
	public boolean shouldReflect(Player p)
	{
		int newX = 0, newY = 0;
		newX = p.getServerX();
		newY = p.getServerY() + 32;

		try
		{
			if(getTileProperty(getLayer("Water").getTileID(newX / 32, (newY + 8) / 32), "Reflection", "").equalsIgnoreCase("true"))
				return true;

			if(getTileProperty(getLayer("Walkable").getTileID(newX / 32, (newY + 8) / 32), "Reflection", "").equalsIgnoreCase("true"))
				return true;

			for(int i = 0; i < getLayerCount(); i++)
				if(getTileProperty(getLayer(i).getTileID(newX / 32, (newY + 8) / 32), "Reflection", "").equalsIgnoreCase("true"))
					return true;
		}
		catch(Exception e)
		{
		}
		return false;
	}

	/**
	 * Returns true if a the previous grass overlay should be drawn
	 * 
	 * @param p
	 * @return
	 */
	public boolean wasOnGrass(Player p)
	{
		int newX = 0, newY = 0;

		newX = p.getServerX();
		newY = p.getServerY();

		switch(p.getDirection())
		{
			case Up:
				newY += 32;
				break;
			case Down:
				newY -= 32;
				break;
			case Left:
				newX += 32;
				break;
			case Right:
				newX -= 32;
				break;
		}

		try
		{
			if(getTileProperty(getLayer("Walkable").getTileID(newX / 32, (newY + 8) / 32), "Grass", "").equalsIgnoreCase("true"))
				return true;

			for(int i = 0; i < getLayerCount(); i++)
				if(getTileProperty(getLayer(i).getTileID(newX / 32, (newY + 8) / 32), "Grass", "").equalsIgnoreCase("true"))
					return true;
		}
		catch(Exception e)
		{
		}
		return false;
	}

	/**
	 * Returns a layer by its index
	 * 
	 * @param layer
	 * @return
	 */
	private Layer getLayer(int layer)
	{
		return (Layer) layers.get(layer);
	}

	/**
	 * Returns a layer by its name
	 * 
	 * @param layer
	 * @return
	 */
	private Layer getLayer(String layer)
	{
		int idx = getLayerIndex(layer);
		return idx < 0 ? null : getLayer(idx);
	}

	/**
	 * Returns true if x or y if off the map
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isNewMap(int x, int y)
	{
		return x < 0 || x >= getWidth() * 32 || y < 0 || y + 8 >= getHeight() * 32;
	}
}
