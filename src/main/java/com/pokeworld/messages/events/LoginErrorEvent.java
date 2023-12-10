package com.pokeworld.messages.events;

import java.util.ArrayList;
import java.util.List;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.Translator;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class LoginErrorEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		List<String> translated = new ArrayList<String>();
		translated = Translator.translate("_LOGIN");

		GameClient.getInstance().showMessageDialog(translated.get(21));
		GameClient.getInstance().getGUIPane().hideLoadingScreen();
		GameClient.getInstance().getLoginScreen().enableLogin();
	}
}
