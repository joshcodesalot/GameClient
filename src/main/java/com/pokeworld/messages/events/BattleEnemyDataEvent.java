package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleEnemyDataEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		BattleManager.getInstance().setEnemyPoke(Request.readInt(), Request.readString(), Request.readInt(), Request.readInt(), Request.readInt(), Request.readInt(), Request.readInt(),
				Request.readBool());
	}
}
