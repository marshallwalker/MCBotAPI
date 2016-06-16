package ca.pureplugins.mcbotapi.event;

import ca.pureplugins.mcbotapi.event.base.Event;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import lombok.Getter;

@Getter
public class BotJoinEvent extends Event
{
	private final Bot bot;

	public BotJoinEvent(Bot bot)
	{
		this.bot = bot;
	}
}