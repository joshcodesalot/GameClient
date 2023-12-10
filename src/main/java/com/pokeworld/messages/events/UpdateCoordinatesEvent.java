package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.entity.Player;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class UpdateCoordinatesEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		// get coordinates
		int x = Request.readInt();
		int y = Request.readInt();

		// get player
		Player p = GameClient.getInstance().getOurPlayer();

		// set coordinates clientside/serverside
		p.setX(x);
		p.setY(y);
		p.setServerX(p.getX());
		p.setServerY(p.getY());

		/* Reposition screen above player */
		GameClient.getInstance().getMapMatrix().getCurrentMap().setXOffset(400 - p.getX(), false);
		GameClient.getInstance().getMapMatrix().getCurrentMap().setYOffset(300 - p.getY(), false);
		GameClient.getInstance().getMapMatrix().recalibrate();
	}
}
