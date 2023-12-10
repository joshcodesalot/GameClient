package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class AddFriendListEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		GameClient.getInstance().getHUD().getFriendsList().addFriend(Request.readString());
	}
}
