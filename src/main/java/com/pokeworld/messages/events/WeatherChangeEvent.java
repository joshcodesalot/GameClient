package com.pokeworld.messages.events;

import com.pokeworld.GameClient;
import com.pokeworld.Session;
import com.pokeworld.backend.time.WeatherService.Weather;
import com.pokeworld.messages.MessageEvent;
import com.pokeworld.protocol.ClientMessage;
import com.pokeworld.protocol.ServerMessage;

public class WeatherChangeEvent implements MessageEvent
{

	@Override
	public void parse(Session Session, ServerMessage Request, ClientMessage Message)
	{
		switch(Request.readInt())
		{
			case 1:
				GameClient.getInstance().getWeatherService().setWeather(Weather.NORMAL);
				break;
			case 2:
				GameClient.getInstance().getWeatherService().setWeather(Weather.RAIN);
				break;
			case 3:
				GameClient.getInstance().getWeatherService().setWeather(Weather.HAIL);
				break;
			case 4:
				GameClient.getInstance().getWeatherService().setWeather(Weather.SANDSTORM);
				break;

		}
	}
}
