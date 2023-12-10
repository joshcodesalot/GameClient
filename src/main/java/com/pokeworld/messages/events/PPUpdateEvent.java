package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PPUpdateEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		for(int i = 0; i < 4; i++)
		{

			BattleManager.getInstance().getCurPoke().setMoveCurPP(i, Request.readInt());
			if(BattleManager.getInstance().getCurPoke().getMoveMaxPP()[i] != 0)
				BattleManager.getInstance().getBattleWindow().getPPLabel(i)
						.setText(BattleManager.getInstance().getCurPoke().getMoveCurPP()[i] + "/" + BattleManager.getInstance().getCurPoke().getMoveMaxPP()[i]);
		}
	}
}
