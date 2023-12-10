package com.pokeworld.messages.events;

import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PasswordChangeEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		// 0 is failure // 1 is success;
		/* if (Request.readInt() == 1) { GameClient.getInstance().getUserManager().login(m_game.getPacketGenerator().getLastUsername(), m_game.getPacketGenerator().getLastPassword()); } else { GameClient.getInstance().getUserManager().login(m_game.getPacketGenerator().getLastUsername(), m_game.getPacketGenerator().getLastPassword()); } */
	}
}
