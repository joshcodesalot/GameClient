package com.pokeworld.messages.events;

import java.util.HashMap;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class ShopListEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		HashMap<Integer, Integer> stock = new HashMap<Integer, Integer>();
		String[] merchData = Request.readString().split(",");
		for(int i = 0; i < merchData.length; i++)
		{
			String[] tempStockData = merchData[i].split(":");
			stock.put(Integer.parseInt(tempStockData[0]), Integer.parseInt(tempStockData[1]));
		}
		GameClient.getInstance().getHUD().showShop(stock);
	}
}
