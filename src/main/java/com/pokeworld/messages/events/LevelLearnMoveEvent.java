package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.MoveLearningManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class LevelLearnMoveEvent implements MessageEvent
{
	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int i = Request.readInt();
		MoveLearningManager.getInstance().queueMoveLearning(i, Request.readString());
	}
}
