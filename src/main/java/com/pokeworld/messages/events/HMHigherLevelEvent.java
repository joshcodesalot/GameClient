package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class HMHigherLevelEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{

		int level = Request.readInt();

		GameClient.getInstance().showMessageDialog("You are not strong enough to do this.\n" + "Your trainer level must be " + level + " to do this.");
	}
}
