package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleLevelupEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		final String[] levelData = Request.readString().split(",");
		BattleManager.getInstance().getNarrator().informLevelUp(levelData[0], Integer.parseInt(levelData[1]));
	}
}
