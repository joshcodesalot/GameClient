package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PokemonLevelChangeEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		String[] levelData = Request.readString().split(",");

		GameClient.getInstance().getOurPlayer().getPokemon()[Integer.parseInt(levelData[0])].setLevel(Integer.parseInt(levelData[1]));
		GameClient.getInstance().getOurPlayer().getPokemon()[Integer.parseInt(levelData[0])].setExpLvlUp(Integer.parseInt(levelData[2]));
		GameClient.getInstance().getOurPlayer().getPokemon()[Integer.parseInt(levelData[0])].setExpLvl((Integer.parseInt(levelData[3])));
		GameClient.getInstance().getHUD().update(false);
	}
}
