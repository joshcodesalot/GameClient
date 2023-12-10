package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PokedexInitializeEvent implements MessageEvent
{

	@Override
	public void parse(Session session, ServerMessage Request, ClientMessage Message)
	{
		String[] details = Request.readString().split(",");
		GameClient.getInstance().getOurPlayer().initializePokedex(details);
	}

}
