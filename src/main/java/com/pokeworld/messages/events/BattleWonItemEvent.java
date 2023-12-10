package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.BattleManager;
import com.pokeworld.backend.ItemDatabase;
import com.pokeworld.backend.entity.Item;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class BattleWonItemEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int itemID = Request.readInt();
		Item item = ItemDatabase.getInstance().getItem(itemID);
		GameClient.getInstance().getOurPlayer().addItem(item.getId(), 1);
		BattleManager.getInstance().getNarrator().informItemDropped(item.getName());
	}
}
