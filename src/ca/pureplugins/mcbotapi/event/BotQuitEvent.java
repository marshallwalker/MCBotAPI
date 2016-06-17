package ca.pureplugins.mcbotapi.event;

import org.spacehq.mc.protocol.data.message.Message;

import ca.pureplugins.mcbotapi.event.base.Event;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import lombok.Getter;

@Getter
public class BotQuitEvent extends Event
{
	private final Bot bot;
	private final Message reason;

	public BotQuitEvent(Bot bot, Message reason)
	{
		this.bot = bot;
		this.reason = reason;
	}
}