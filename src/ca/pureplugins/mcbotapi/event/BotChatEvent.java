package ca.pureplugins.mcbotapi.event;

import org.json.JSONObject;

import ca.pureplugins.mcbotapi.event.base.Event;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import lombok.Getter;

@Getter
public class BotChatEvent extends Event
{
	private final Bot bot;
	private final JSONObject message;

	public BotChatEvent(Bot bot, JSONObject message)
	{
		this.bot = bot;
		this.message = message;
	}
}