package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class KurtEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		String pokeball = Request.readString();
		String item = Request.readString();
		// max represents the maximum number of items you can buy
		int max = Request.readInt();
		int price = Request.readInt();
		GameClient.getInstance().getHUD().showKurtDialog(pokeball, item, max, price);
	}
}
