package ca.pureplugins.mcbotapi.event;

import ca.pureplugins.mcbotapi.event.base.Event;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import lombok.Getter;

@Getter
public class BotQuitEvent extends Event
{
	private final Bot bot;
	private final String reason;

	public BotQuitEvent(Bot bot, String reason)
	{
		this.bot = bot;
		this.reason = reason;
	}
}