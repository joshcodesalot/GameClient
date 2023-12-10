package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleRunEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		// hier in server bool van maken ;) Die kunnen we nu tenslotte lezen! :)
		boolean canRun = Request.readBool();
		BattleManager.getInstance().getNarrator().informRun(canRun);
	}
}
