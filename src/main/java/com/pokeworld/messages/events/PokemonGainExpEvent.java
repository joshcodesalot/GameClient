package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PokemonGainExpEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int p1 = Request.readInt();
		int exp = GameClient.getInstance().getOurPlayer().getPokemon()[p1].getExp() + Request.readInt();

		GameClient.getInstance().getOurPlayer().getPokemon()[p1].setExp(exp);
		GameClient.getInstance().getHUD().update(false);
	}
}
