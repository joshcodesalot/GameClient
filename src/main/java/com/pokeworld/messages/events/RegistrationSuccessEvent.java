package com.pokeworld.messages.events;

import java.util.ArrayList;
import java.util.List;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.Translator;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class RegistrationSuccessEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		GameClient.getInstance().getGUI().invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				List<String> translated = new ArrayList<String>();
				translated = Translator.translate("_LOGIN");

				GameClient.getInstance().showMessageDialog(translated.get(23));
				GameClient.getInstance().getLoginScreen().showLogin();
				GameClient.getInstance().getLoginScreen().getRegistration().clear();
			}
		});
	}
}
