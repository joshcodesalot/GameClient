package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.entity.Player;
import com.pokeworld.backend.entity.Player.Direction;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class FaceLeftEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		Player p = GameClient.getInstance().getMapMatrix().getPlayer(Request.readInt());
		if(p != null)
		{
			p.setDirection(Direction.Left);
			p.loadSpriteImage();
		}
	}
}
