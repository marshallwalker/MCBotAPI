package ca.pureplugins.mcbotapi;

import ca.pureplugins.mcbotapi.event.BotJoinEvent;
import ca.pureplugins.mcbotapi.impl.BotImpl;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Server;

public class MCBotAPI
{
	private static final String USERNAME = "black0psaddict@hotmail.com";
	private static final String PASSWORD = "tomboy16";

	public static void main(String[] args)
	{
		Bot bot = new BotImpl(new Account(USERNAME, PASSWORD), new Server("play.rebirthcraft.net", 25565));
		bot.login();

		bot.getEventBus().register(BotJoinEvent.class, event ->
		{
			System.out.println(event.getBot().getAccount().getUsername() + " has joined " + event.getBot().getServer().getHostname());
		});
	}
}