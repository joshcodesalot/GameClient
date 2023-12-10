package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class LoginSuccessEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		final int id = Request.readInt();
		final String time = Request.readString();
		GameClient.getInstance().getGUI().invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				GameClient.getInstance().getGUIPane().hideLoginScreen();
			}
		});
		GameClient.getInstance().setPlayerId(id);
		GameClient.getInstance().getHUD().showChat();
		GameClient.getInstance().getTimeService().setTime(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(2)));

	}
}
