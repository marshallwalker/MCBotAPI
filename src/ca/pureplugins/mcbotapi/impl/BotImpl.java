package ca.pureplugins.mcbotapi.impl;

import java.net.Proxy;
import java.util.Random;

import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.mc.protocol.MinecraftConstants;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.message.Message;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import ca.pureplugins.mcbotapi.BotSession;
import ca.pureplugins.mcbotapi.event.BotChatEvent;
import ca.pureplugins.mcbotapi.event.BotJoinEvent;
import ca.pureplugins.mcbotapi.event.BotQuitEvent;
import ca.pureplugins.mcbotapi.interfaces.AI;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Location;

public class BotImpl implements Bot
{
	private final int id = new Random().nextInt(1000);
	private final BotSession botSession;
	private final Account account;

	private MinecraftProtocol protocol;
	private Client client;
	private Location location;

	private boolean isOnGround = true;

	public BotImpl(BotSession botSession, Account account)
	{
		this.botSession = botSession;
		this.account = account;
	}

	@Override
	public int getUniqueId()
	{
		return id;
	}

	@Override
	public Account getAccount()
	{
		return account;
	}

	@Override
	public BotSession getBotSession()
	{
		return botSession;
	}

	@Override
	public Session getSession()
	{
		return client.getSession();
	}

	@Override
	public Location getLocation()
	{
		return location;
	}

	@Override
	public AI getAI()
	{
		return new AIimpl(this);
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

		client = new Client(botSession.getServer().getHostname(), botSession.getServer().getPort(), protocol, new TcpSessionFactory(Proxy.NO_PROXY));
		client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, Proxy.NO_PROXY);
		client.getSession().connect();

		client.getSession().addListener(new SessionAdapter()
		{
			@Override
			public void packetReceived(PacketReceivedEvent event)
			{
				if (event.getPacket() instanceof ServerPlayerPositionRotationPacket)
					client.getSession().send(new ClientTeleportConfirmPacket(event.<ServerPlayerPositionRotationPacket> getPacket().getTeleportId()));

				if (event.getPacket() instanceof ServerConfirmTransactionPacket)
				{
					ServerConfirmTransactionPacket p = event.getPacket();
					client.getSession().send(new ClientConfirmTransactionPacket(p.getWindowId(), p.getActionId(), p.getAccepted()));
				}

				if (event.getPacket() instanceof ServerJoinGamePacket)
					botSession.getEventBus().post(new BotJoinEvent(BotImpl.this));

				if (event.getPacket() instanceof ServerChatPacket)
					botSession.getEventBus().post(new BotChatEvent(BotImpl.this, event.<ServerChatPacket> getPacket().getMessage()));

				if (event.getPacket() instanceof ServerSpawnPositionPacket)
				{
					Position pos = event.<ServerSpawnPositionPacket> getPacket().getPosition();
					location = new Location(pos.getX(), pos.getY(), pos.getZ());
					updatePosition();
				}
			}

			@Override
			public void disconnected(DisconnectedEvent event)
			{
				botSession.getEventBus().post(new BotQuitEvent(BotImpl.this, Message.fromString(event.getReason())));
				event.getCause().printStackTrace();
			}
		});
	}

	@Override
	public void updatePosition()
	{
		getSession().send(new ClientPlayerPositionPacket(isOnGround, location.getX(), location.getY(), location.getZ()));
	}

	@Override
	public boolean getIsOnGround()
	{
		return isOnGround;
	}
}