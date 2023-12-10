package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.entity.Player;
import com.pokeworld.backend.entity.Player.Direction;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PlayerMoveEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int player = Request.readInt();
		int dir = Request.readInt();
		Player p = GameClient.getInstance().getMapMatrix().getPlayer(player);
		if(p == null)
			return;

		switch(dir)
		{
			case 0:
				p.queueMovement(Direction.Down);
				break;
			case 1:
				p.queueMovement(Direction.Up);
				break;
			case 2:
				p.queueMovement(Direction.Left);
				break;
			case 3:
				p.queueMovement(Direction.Right);
				break;
		}
	}
}
