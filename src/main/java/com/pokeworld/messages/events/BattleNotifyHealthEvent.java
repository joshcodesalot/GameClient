package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleNotifyHealthEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		if(Request.readInt() == 0)
			// Our pokemon's health
			BattleManager.getInstance().getNarrator().informHealthChanged(Request.readString().split(","), 0);
		else
			// Enemy pokemon's health
			BattleManager.getInstance().getNarrator().informHealthChanged(Request.readString().split(","), 1);
	}
}
