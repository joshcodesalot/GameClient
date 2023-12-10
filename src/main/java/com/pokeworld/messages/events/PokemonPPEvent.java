package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class PokemonPPEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		int poke = Request.readInt();
		int move = Request.readInt();
		int curPP = Request.readInt();
		int maxPP = Request.readInt();
		GameClient.getInstance().getOurPlayer().getPokemon()[poke].setMoveCurPP(move, curPP);
		GameClient.getInstance().getOurPlayer().getPokemon()[poke].setMoveMaxPP(move, maxPP);
	}
}
