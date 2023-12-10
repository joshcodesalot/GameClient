package com.pokeworld.backend;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.pokeworld.GameClient;
import com.pokeworld.backend.entity.Item;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * The item database
 * 
 * @author shadowkanji
 * @author Nushio
 */
@Root
public class ItemDatabase
{
	private static ItemDatabase m_instance;

	@ElementMap
	private HashMap<Integer, Item> m_items;

	/**
	 * Returns the instance of item database
	 * 
	 * @return
	 */
	public static List<Item> getCategoryItems(String category)
	{
		List<Item> itemList = new ArrayList<Item>();
		for(int i = 0; i <= m_instance.m_items.size(); i++)
			try
			{
				Item item = m_instance.m_items.get(i);
				if(item.getCategory().equals(category))
					itemList.add(item);
			}
			catch(Exception e)
			{
			}
		return itemList;
	}

	/**
	 * Returns the instance of item database
	 * 
	 * @return
	 */
	public static ItemDatabase getInstance()
	{
		if(m_instance == null)
			m_instance = new ItemDatabase();
		return m_instance;
	}

	/**
	 * Adds an item to the database
	 * 
	 * @param id
	 * @param item
	 */
	public void addItem(int id, Item item)
	{
		if(m_items == null)
			m_items = new HashMap<Integer, Item>();
		m_items.put(id, item);
	}

	/**
	 * Returns an item based on its id
	 * 
	 * @param id
	 * @return
	 */
	public Item getItem(int id)
	{
		return m_items.get(id);
	}

	/**
	 * Returns an item based on its name
	 * 
	 * @param name
	 * @return
	 */
	public Item getItem(String name)
	{
		Iterator<Item> it = m_items.values().iterator();
		Item i;
		while(it.hasNext())
		{
			i = it.next();
			if(i.getName().equalsIgnoreCase(name))
				return i;
		}
		return null;
	}

	/**
	 * Reloads the database
	 */
	public void reinitialise()
	{
		Serializer serializer = new Persister();
		try
		{
			String respath = System.getProperty("res.path");
			if(respath == null)
				respath = "";
			InputStream source = FileLoader.loadFile(respath + "res/items/items.xml");
			m_instance = serializer.read(ItemDatabase.class, source);
			if(GameClient.DEBUG){ GameClient.getInstance().log("INFO: Items database loaded."); }
		}
		catch(Exception e)
		{
			GameClient.getInstance().log("ERROR: Item database could not be loaded.");
		}
	}
}
