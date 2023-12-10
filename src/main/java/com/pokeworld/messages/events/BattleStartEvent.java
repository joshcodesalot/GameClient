package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleStartEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		/* Dit heb ik veranderd van char naar bool! Want we kunnen nu bools lezen. */
		BattleManager.getInstance().startBattle(Request.readBool(), Request.readInt());
	}
}
