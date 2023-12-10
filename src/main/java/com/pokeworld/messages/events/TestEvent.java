package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class TestEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		// String AuthString = Request.readString();
		// String[] Bits = AuthString.split(";");
		// String Username = Bits[0];

		System.out.println("RECEIVED HEADER: " + Request.readBool());
	}

}
