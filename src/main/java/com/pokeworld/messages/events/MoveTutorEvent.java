package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class MoveTutorEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		String s = Request.readString();
		String name = s.split("_")[0];
		if(name.equals("MoveRelearner"))
		{
			GameClient.getInstance().getHUD().showRelearnDialog(s.split("_")[1]);
		}
		else if(name.equals("MoveTutor"))
		{
			GameClient.getInstance().getHUD().showTutorDialog(s.split("_")[1]);
		}
	}
}
