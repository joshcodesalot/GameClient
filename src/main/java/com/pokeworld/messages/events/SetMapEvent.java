package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.time.WeatherService.Weather;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class SetMapEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		char direction = Request.readString().charAt(0);
		GameClient.getInstance().getMapMatrix().setNewMapPos(direction);
		GameClient.getInstance().setMap(Request.readInt(), Request.readInt());
		switch(Request.readInt())
		{
			case 0:
				GameClient.getInstance().getWeatherService().setWeather(Weather.NORMAL);
				break;
			case 1:
				GameClient.getInstance().getWeatherService().setWeather(Weather.RAIN);
				break;
			case 2:
				GameClient.getInstance().getWeatherService().setWeather(Weather.HAIL);
				break;
			case 3:
				GameClient.getInstance().getWeatherService().setWeather(Weather.SANDSTORM);
				break;
			case 4:
				GameClient.getInstance().getWeatherService().setWeather(Weather.FOG);
				break;
			default:
				GameClient.getInstance().getWeatherService().setWeather(Weather.NORMAL);
				break;
		}
	}
}
