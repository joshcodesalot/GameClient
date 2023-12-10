package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleVictoryEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int condition = Request.readInt();
		switch(condition)
		{
			case 0: /* You won! */
				BattleManager.getInstance().getNarrator().informVictory();
				BattleManager.getInstance().deleteInstance();
				break;
			case 1: /* You lost! */
				BattleManager.getInstance().getNarrator().informLoss();
				BattleManager.getInstance().deleteInstance();
				break;
			case 2: /* We caught the Pokemon! */
				BattleManager.getInstance().endBattle();
				BattleManager.getInstance().deleteInstance();
				break;
		}
	}
}
