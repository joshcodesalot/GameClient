package com.pokeworld.ui.frames.speechframes;

import com.pokeworld.GameClient;
import com.pokeworld.constants.ServerPacket;
import com.pokeworld.protocol.ClientMessage;

/**
 * NPC speech pop-up
 * 
 * @author Myth1c
 */
public class NPCSpeechFrame extends SpeechFrame
{
	/**
	 * Default Constructor
	 * 
	 * @param text
	 */
	public NPCSpeechFrame(String text)
	{
		super(text);
	}

	/**
	 * Modified constructor, sets time to auto-skip to the next line.
	 * 
	 * @param text
	 * @param seconds
	 */
	public NPCSpeechFrame(String text, int seconds)
	{
		super(text, seconds);
	}

	/**
	 * Sends a packet when finished displaying text
	 */
	@Override
	public void advancedPast(String advancedMe)
	{
		if(speechQueue.peek() == null)
		{
			triangle = null;
			GameClient.getInstance().getHUD().removeNPCSpeechFrame();
			ClientMessage message = new ClientMessage(ServerPacket.TALKING_FINISH);
			GameClient.getInstance().getSession().send(message);
		}
	}
}
