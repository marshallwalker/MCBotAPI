package ca.pureplugins.mcbotapi;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import ca.pureplugins.mcbotapi.event.base.EventBus;
import ca.pureplugins.mcbotapi.impl.BotImpl;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Server;
import lombok.Data;

@Data
public class BotSession
{
	private final EventBus eventBus = new EventBus();

	private final List<Account> accounts = new ArrayList<>();
	private final List<Proxy> proxies = new ArrayList<>();
	private final List<Bot> bots = new ArrayList<>();

	private final Server server;

	public BotSession(Server server)
	{
		this.server = server;
	}

	public BotSession addAccount(Account account)
	{
		accounts.add(account);
		return this;
	}

	public void start()
	{
		Bot bot = new BotImpl(this, accounts.get(0));

		bots.add(bot);

		bot.login();
	}

	public void broadcastMessage(String message)
	{
		bots.forEach(bot -> bot.getAI().sendMessage(message));
	}
}