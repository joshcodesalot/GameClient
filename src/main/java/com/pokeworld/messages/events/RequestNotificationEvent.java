package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class RequestNotificationEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		switch(Request.readInt())
		{
			case 0:
				// The player logged out
				break;
			case 1:
				// Players must stand beside each other to battle
				GameClient.getInstance().getHUD().getChat().addSystemMessage("You must be standing next to and facing the person you want to battle.");
				break;
			case 2:
				// PvP is disabled on this map
				GameClient.getInstance().getHUD().getChat().addSystemMessage("You are not allowed to PvP in this map.");
				break;
			case 3:
				// You must be within 3 squares to force this player to battle
				GameClient.getInstance().getHUD().getChat().addSystemMessage("You must be within 3 squares of this player to battle.");
				break;
			case 4:
				GameClient.getInstance().getHUD().getChat().addSystemMessage("You need to have more than one pokemon and/or you must wait 1 minute before trading again.");
				break;
		}
	}
}
