package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.entity.OurPlayer;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BadgeChangeEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int i = Request.readInt();
		OurPlayer player = GameClient.getInstance().getOurPlayer();

		// init badges
		if(i == 0)
		{
			player.initBadges(Request.readString());
			GameClient.getInstance().setOurPlayer(player);
		}
		else
			player.addBadge(Request.readInt());
	}
}
