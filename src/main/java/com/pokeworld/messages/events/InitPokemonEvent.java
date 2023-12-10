package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class InitPokemonEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int i = Request.readInt();
		String[] details = Request.readString().split(",");
		GameClient.getInstance().getOurPlayer().setPokemon(i, details);
		if(GameClient.getInstance().getOurPlayer().isBoxing())
			GameClient.getInstance().getHUD().getBoxDialog().getTeamPanel().reloadPokemon();
	}
}
