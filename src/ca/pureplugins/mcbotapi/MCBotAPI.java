package ca.pureplugins.mcbotapi;

import java.util.Random;

import org.spacehq.mc.protocol.data.game.entity.player.Hand;

import ca.pureplugins.mcbotapi.event.BotChatEvent;
import ca.pureplugins.mcbotapi.event.BotJoinEvent;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Server;

public class MCBotAPI
{
	public static void main(String[] args)
	{
		BotSession session = new BotSession(new Server("localhost", 25566));

		session.addAccount(new Account("thomas.ziel1@web.de", "03111995"));

		session.start();

		session.getEventBus().register(BotJoinEvent.class, event ->
		{
			System.out.println(event.getBot().getAccount().getUsername() + " has joined " + session.getServer().getHostname());
		});

		session.getEventBus().register(BotChatEvent.class, event ->
		{
			String message = event.getMessage().getFullText();

			if (message.contains(".swing"))
			{
				session.getBots().forEach(bot -> bot.getAI().swingHand(Hand.MAIN_HAND));
			}

			if (message.contains(".jump"))
			{
				session.getBots().forEach(bot -> bot.getAI().jump());
			}

			if (message.contains(".derp"))
			{
				session.getBots().forEach(bot -> bot.getAI().derp());
			}

			if (message.contains(".turn"))
			{
				event.getBot().getAI().turn(new Random().nextInt(120));
			}

			if (message.contains(".panic"))
			{
				event.getBot().getAI().panic();
			}
		});
	}
}