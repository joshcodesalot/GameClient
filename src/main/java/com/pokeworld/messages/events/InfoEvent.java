package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class InfoEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		String name = Request.readString();
		switch(name)
		{
			case "MoveRelearner":
				GameClient.getInstance().getHUD().getRelearnDialog().initUse(Request.readString());
				break;
			default:
				break;
		}
	}
}
