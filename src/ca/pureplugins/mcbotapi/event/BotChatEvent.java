package ca.pureplugins.mcbotapi.event;

import org.spacehq.mc.protocol.data.message.Message;

import ca.pureplugins.mcbotapi.event.base.Event;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import lombok.Getter;

@Getter
public class BotChatEvent extends Event
{
	private final Bot bot;
	private final Message message;

	public BotChatEvent(Bot bot, Message message)
	{
		this.bot = bot;
		this.message = message;
	}
}