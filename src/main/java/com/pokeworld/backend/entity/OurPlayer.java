package com.pokeworld.backend.entity;

import java.util.ArrayList;
import com.pokeworld.GameClient;
import com.pokeworld.backend.entity.Enums.Poketype;
import de.matthiasmann.twl.Widget;

/**
 * Represents our player
 * 
 * @author shadowkanji
 */
public class OurPlayer extends Player
{
	private int[] m_badges;
	private String[] s_badges;
	private int m_breedingLvl = -1;
	private int m_coordinatingLvl = -1;
	private int m_fishingLvl = -1;
	private ArrayList<PlayerItem> m_items;
	private int m_money;
	private int[] m_pokedex = new int[494];
	private OurPokemon[] m_pokemon;
	private int m_trainerLvl = -1;
	private boolean isBoxing;

	/**
	 * Default constructor
	 */
	public OurPlayer()
	{
		m_pokemon = new OurPokemon[6];
		m_items = new ArrayList<PlayerItem>();
		m_badges = new int[42];
		m_money = 0;
		isBoxing = false;
		// Apply badges names
		s_badges = new String[32];
		s_badges = new String[]{ "Pewter City", "Cerulean City", "Vermilion City", "Celadon City", "Fuchsia City", "Saffron City", "Cinnabar Island", "Viridian City",
				"Violet City", "Azalea Town", "Goldenrod City", "Ecruteak City", "Cianwood City", "Olivine City", "Mahogany Town", "Blackthorn City",
				"Rustboro City", "Dewford Town", "Mauville City", "Lavaridge Town", "Petallburg City", "Fortree City", "Mossdeep City", "Sootopolis City",
				"Oreburgh City", "Eterna City", "Veilstone City", "Pastoria City", "Hearthome City", "Canalave City", "Snowpoint", "Sunnyshore City"
		};		
	}

	/**
	 * Constructor to be used if our player already exists
	 * 
	 * @param original
	 */
	public OurPlayer(OurPlayer original)
	{
		m_badges = original.getBadges();
		m_pokemon = original.getPokemon();
		m_items = original.getItems();
		m_sprite = original.getSprite();
		m_username = original.getUsername();
		m_isAnimating = original.isAnimating();
		m_trainerLvl = original.getTrainerLevel();
		m_breedingLvl = original.getBreedingLevel();
		m_fishingLvl = original.getFishingLevel();
		m_coordinatingLvl = original.getCoordinatingLevel();
		m_pokedex = original.getPokedex();
	}

	/*
	 * Return badge town
	 */
	public String returnBadgeCity(int iBadge)
	{
		// Return badge city
		return s_badges[iBadge];
	}
	
	/**
	 * Adds a badge to the player
	 * 
	 * @param index
	 */
	public void addBadge(int index)
	{
		m_badges[index] = 1;
	}

	/**
	 * Adds an item to this player's bag (automatically handles if its in the bag already)
	 * 
	 * @param number
	 * @param quantity
	 */
	public void addItem(int number, int quantity)
	{
		boolean exists = false;
		for(int i = 0; i < m_items.size(); i++)
			if(m_items.get(i) != null && m_items.get(i).getNumber() == number)
			{
				m_items.get(i).setQuantity(m_items.get(i).getQuantity() + quantity);
				exists = true;
				if(GameClient.getInstance().getHUD().getBag() != null)
					GameClient.getInstance().getHUD().getBag().addItem(number, false);
			}
		if(!exists)
		{
			m_items.add(new PlayerItem(number, quantity));
			if(GameClient.getInstance().getHUD().getBag() != null)
				GameClient.getInstance().getHUD().getBag().addItem(number, true);
		}
	}

	/**
	 * Returns the player's badges
	 */
	public int[] getBadges()
	{
		return m_badges;
	}

	/**
	 * Returns the player's breeding level
	 * 
	 * @return m_breedingLvl
	 */
	public int getBreedingLevel()
	{
		return m_breedingLvl;
	}

	/**
	 * Returns the player's coordinating level
	 * 
	 * @return m_coordinatingLvl
	 */
	public int getCoordinatingLevel()
	{
		return m_coordinatingLvl;
	}

	/**
	 * Returns the player's fishing level
	 * 
	 * @return m_fishingLvl
	 */
	public int getFishingLevel()
	{
		return m_fishingLvl;
	}

	/**
	 * Gets item quantity from bag.
	 * 
	 * @param number
	 */
	public int getItemQuantity(int number)
	{
		int quantity = 0;
		for(int i = 0; i < m_items.size(); i++)
			if(m_items.get(i) != null && m_items.get(i).getItem().getId() == number)
			{
				quantity = m_items.get(i).getQuantity();
				return quantity;
			}
			else
				quantity = 0;
		return quantity;
	}

	/**
	 * Returns our player's bag
	 * 
	 * @return
	 */
	public ArrayList<PlayerItem> getItems()
	{
		return m_items;
	}

	/**
	 * Returns the player's money
	 * 
	 * @return
	 */
	public int getMoney()
	{
		return m_money;
	}

	public int[] getPokedex()
	{
		return m_pokedex;
	}

	/**
	 * Returns our player's party
	 * 
	 * @return
	 */
	public OurPokemon[] getPokemon()
	{
		return m_pokemon;
	}

	/**
	 * Returns the player's trainer level
	 * 
	 * @return m_trainerLvl
	 */
	public int getTrainerLevel()
	{
		return m_trainerLvl;
	}

	@Override
	public int getType()
	{
		return 1;
	}

	/**
	 * Initializes the player's badges
	 * 
	 * @param str
	 */
	public void initBadges(String str)
	{
		m_badges = new int[str.length()];
		for(int i = 0; i < str.length(); i++)
			try
			{
				m_badges[i] = Integer.valueOf(String.valueOf(str.charAt(i)));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}

	public void initializePokedex(String[] details)
	{
		int id = 1;

		for(String s : details)
		{
			m_pokedex[id] = Integer.parseInt(s);
			id++;
		}

		GameClient.getInstance().getHUD().getPokedex().initGUI();
	}

	/**
	 * Removes an item from this player's bag
	 * 
	 * @param number
	 * @param quantity
	 */
	public void removeItem(int number, int quantity, Widget root)
	{
		for(int i = 0; i < m_items.size(); i++)
			if(m_items.get(i) != null && m_items.get(i).getNumber() == number)
			{
				if(m_items.get(i).getQuantity() - quantity > 0)
				{
					m_items.get(i).setQuantity(m_items.get(i).getQuantity() - quantity);
					if(GameClient.getInstance().getHUD().getBag() != null)
						GameClient.getInstance().getHUD().getBag().removeItem(number, false, root);
				}
				else
				{
					m_items.remove(i);
					if(GameClient.getInstance().getHUD().getBag() != null)
						GameClient.getInstance().getHUD().getBag().removeItem(number, true, root);
				}
				return;
			}
	}

	public void set(Player p)
	{
		m_x = p.getX();
		m_y = p.getY();
		m_svrX = p.getServerX();
		m_svrY = p.getServerY();
		m_sprite = p.getSprite();
		m_direction = p.getDirection();
		m_username = p.getUsername();
		m_id = p.getId();
		m_ours = p.isOurPlayer();
		m_adminLevel = p.getAdminLevel();
	}

	/**
	 * Sets the player's breeding level
	 * 
	 * @param i
	 */
	public void setBreedingLevel(int i)
	{
		m_breedingLvl = i;
	}

	/**
	 * Sets the player's coordinating level
	 * 
	 * @param i
	 */
	public void setCoordinatingLevel(int i)
	{
		m_coordinatingLvl = i;
	}

	/**
	 * Sets the player's fishing level
	 * 
	 * @param i
	 */
	public void setFishingLevel(int i)
	{
		m_fishingLvl = i;
	}

	/**
	 * Sets the players money
	 * 
	 * @param m
	 */
	public void setMoney(int m)
	{
		m_money = m;
	}

	/**
	 * Sets a pokemon in this player's party
	 * 
	 * @param i
	 * @param information
	 */
	public void setPokemon(int i, String[] info)
	{
		if(info == null)
			m_pokemon[i] = null;
		else
		{
			/* Set sprite, name, gender and hp */
			m_pokemon[i] = new OurPokemon();
			m_pokemon[i].setSpriteNumber(Integer.parseInt(info[0]));
			m_pokemon[i].setName(info[1]);
			m_pokemon[i].setCurHP(Integer.parseInt(info[2]));
			m_pokemon[i].setGender(Integer.parseInt(info[3]));
			m_pokemon[i].setShiny(info[4].equals("1"));
			m_pokemon[i].setMaxHP(Integer.parseInt(info[5]));
			/* Stats */
			m_pokemon[i].setAtk(Integer.parseInt(info[6]));
			m_pokemon[i].setDef(Integer.parseInt(info[7]));
			m_pokemon[i].setSpeed(Integer.parseInt(info[8]));
			m_pokemon[i].setSpatk(Integer.parseInt(info[9]));
			m_pokemon[i].setSpdef(Integer.parseInt(info[10]));
			m_pokemon[i].setType1(Poketype.valueOf(info[11]));
			if(info[12] != null && !info[12].equals(""))
				m_pokemon[i].setType2(Poketype.valueOf(info[12]));
			m_pokemon[i].setExp(Integer.parseInt(info[13].substring(0, info[13].indexOf('.'))));
			m_pokemon[i].setLevel(Integer.parseInt(info[14]));
			m_pokemon[i].setAbility(info[15]);
			m_pokemon[i].setNature(info[16]);

			/* Moves */
			String[] moves = new String[4];
			String[] movetypes = new String[4];
			for(int j = 0; j < moves.length; j++)
				if(j < info.length - 17 && info[j + 17] != null)
					moves[j] = info[j + 17];
				else
					moves[j] = "";
			if(info[17] == null || info[17].equals(""))
				moves[0] = "Tackle";
			m_pokemon[i].setMoves(moves);

			for(int j = 0; j < movetypes.length; j++)
				if(j < info.length - 21 && info[j + 21] != null)
					movetypes[j] = info[j + 21];
				else
					movetypes[j] = "";
			if(info[17] == null || info[17].equals(""))
			{
				movetypes[0] = "Normal";
				m_pokemon[i].setMoveMaxPP(0, 35);
				m_pokemon[i].setMoveCurPP(0, 35);
			}
			m_pokemon[i].setMoveTypes(movetypes);
			m_pokemon[i].setHoldItem(info[25]);
			m_pokemon[i].setExpLvl(Integer.parseInt(info[26]));
			m_pokemon[i].setExpLvlUp(Integer.parseInt(info[27]));
			m_pokemon[i].setOriginalTrainer(info[28]);

		}
	}

	/**
	 * Sets the player's trainer level
	 * 
	 * @param i
	 */
	public void setTrainerLevel(int i)
	{
		m_trainerLvl = i;
	}

	/**
	 * Swaps two pokemon
	 * 
	 * @param Poke1
	 * @param Poke2
	 */
	public void swapPokemon(int Poke1, int Poke2)
	{
		OurPokemon temp1 = m_pokemon[Poke1];
		m_pokemon[Poke1] = m_pokemon[Poke2];
		m_pokemon[Poke2] = temp1;
		GameClient.getInstance().getHUD().refreshParty();
	}

	public void updatePokedex(int id, int value)
	{
		m_pokedex[id] = value;
	}

	/**
	 * Updates a pokemon's stats
	 * 
	 * @param i
	 * @param info
	 */
	public void updatePokemon(int i, String[] info)
	{
		if(m_pokemon[i] != null)
		{
			m_pokemon[i].setCurHP(Integer.parseInt(info[0]));
			m_pokemon[i].setMaxHP(Integer.parseInt(info[1]));
			m_pokemon[i].setAtk(Integer.parseInt(info[2]));
			m_pokemon[i].setDef(Integer.parseInt(info[3]));
			m_pokemon[i].setSpeed(Integer.parseInt(info[4]));
			m_pokemon[i].setSpatk(Integer.parseInt(info[5]));
			m_pokemon[i].setSpdef(Integer.parseInt(info[6]));
		}
	}

	/**
	 * Returns the amount of Pokemon in this player's party
	 * 
	 * @return
	 */
	public int getPartyCount()
	{
		int r = 0;
		for(int i = 0; i < m_pokemon.length; i++)
			if(m_pokemon[i] != null)
				r++;
		return r;
	}

	public boolean isBoxing()
	{
		return isBoxing;
	}

	public void setBoxing(boolean isBoxing)
	{
		this.isBoxing = isBoxing;
	}
}
