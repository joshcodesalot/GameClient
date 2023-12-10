package com.pokeworld.messages;

import com.pokeworld.Session;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public interface MessageEvent
{
	public void parse(Session session, ServerMessage request, ClientMessage message);
}