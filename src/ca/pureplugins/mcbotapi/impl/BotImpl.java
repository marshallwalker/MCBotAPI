package ca.pureplugins.mcbotapi.impl;

import java.util.Arrays;
import java.util.Random;

import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.mc.protocol.MinecraftConstants;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.message.Message;
import org.spacehq.mc.protocol.data.message.TranslationMessage;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import ca.pureplugins.mcbotapi.event.BotJoinEvent;
import ca.pureplugins.mcbotapi.event.BotQuitEvent;
import ca.pureplugins.mcbotapi.event.base.EventBus;
import ca.pureplugins.mcbotapi.interfaces.AI;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Location;
import ca.pureplugins.mcbotapi.model.Server;

public class BotImpl implements Bot
{
	private final Account account;
	private final Server server;

	private final EventBus eventBus = new EventBus();

	private final int id = new Random().nextInt(1000);

	private MinecraftProtocol protocol;
	private Client client;
	private Location location;

	public BotImpl(Account account, Server server)
	{
		this.account = account;
		this.server = server;
	}

	@Override
	public void login()
	{
		try
		{
			protocol = new MinecraftProtocol(account.getUsername(), account.getPassword(), false);
		}
		catch (RequestException e)
		{
			e.printStackTrace();
		}

		client = new Client(server.getHostname(), server.getPort(), protocol, new TcpSessionFactory(server.getProxy()));
		client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, account.getProxy());
		client.getSession().connect();

		client.getSession().addListener(new SessionAdapter()
		{
			@Override
			public void packetReceived(PacketReceivedEvent event)
			{
				System.out.println("Packet in: " + event.getPacket().toString());

				if (event.getPacket() instanceof ServerJoinGamePacket)
				{
					eventBus.post(new BotJoinEvent(BotImpl.this));
				}

				if (event.getPacket() instanceof ServerChatPacket)
				{
					Message message = event.<ServerChatPacket> getPacket().getMessage();
					System.out.println("Received Message: " + message.getFullText());

					if (message instanceof TranslationMessage)
					{
						System.out.println("Received Translation Components: " + Arrays.toString(((TranslationMessage) message).getTranslationParams()));
					}
				}
			}

			@Override
			public void disconnected(DisconnectedEvent event)
			{
				eventBus.post(new BotQuitEvent(BotImpl.this, Message.fromString(event.getReason()).getFullText()));
				System.err.println(event.getCause());
			}
		});
	}

	@Override
	public EventBus getEventBus()
	{
		return eventBus;
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public Session getSession()
	{
		return client.getSession();
	}

	@Override
	public Account getAccount()
	{
		return account;
	}

	@Override
	public AI getAI()
	{
		return new AIimpl(this);
	}

	@Override
	public Location getLocation()
	{
		return location;
	}

	@Override
	public Server getServer()
	{
		return server;
	}
}