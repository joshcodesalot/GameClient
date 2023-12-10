package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class TradeAddPokemonEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		final int index = Request.readInt();
		final String[] data = Request.readString().split(",");
		GameClient.getInstance().getGUI().invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				GameClient.getInstance().getHUD().getTradeDialog().addPoke(index, data);
			}
		});
	}
}
