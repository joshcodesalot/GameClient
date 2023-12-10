package com.pokeworld.messages.events;

import java.util.ArrayList;
import java.util.List;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.Translator;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class RegisterNotificationEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, final ServerMessage Request, ClientMessage Message)
	{
		GameClient.getInstance().getGUI().invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				List<String> translated = new ArrayList<String>();
				translated = Translator.translate("_LOGIN");

				switch(Request.readInt())
				{
					case 1:
						// Account server offline
						GameClient.getInstance().showMessageDialog(translated.get(24));
						break;
					case 2:
						GameClient.getInstance().showMessageDialog(translated.get(25));
						break;
					case 3:
						GameClient.getInstance().showMessageDialog(translated.get(26));
						break;
					case 4:
						GameClient.getInstance().showMessageDialog(translated.get(27));
						break;
					case 5:
						GameClient.getInstance().showMessageDialog(translated.get(41));
						break;
					case 6:
						GameClient.getInstance().showMessageDialog("Email too long!");
						break;
					case 7:
						GameClient.getInstance().showMessageDialog("Username cannot contain: " + Request.readString());

				}
				GameClient.getInstance().getLoginScreen().getRegistration().enableRegistration();
			}
		});
	}
}
