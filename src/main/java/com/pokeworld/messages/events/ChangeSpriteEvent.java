package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.entity.Player;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class ChangeSpriteEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int player = Request.readInt();
		int sprite = Request.readInt();

		Player p = GameClient.getInstance().getMapMatrix().getPlayer(player);
		if(p != null)
		{
			p.setSprite(sprite);
			p.loadSpriteImage();
		}
	}
}
