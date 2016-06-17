package ca.pureplugins.mcbotapi.impl;

import java.util.Random;

import org.spacehq.mc.protocol.data.game.ClientRequest;
import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;
import org.spacehq.mc.protocol.data.game.entity.player.PlayerAction;
import org.spacehq.mc.protocol.data.game.entity.player.PlayerState;
import org.spacehq.mc.protocol.data.game.world.block.BlockFace;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerStatePacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerSwingArmPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;

import ca.pureplugins.mcbotapi.interfaces.AI;
import ca.pureplugins.mcbotapi.interfaces.Bot;

public class AIimpl implements AI
{
	private final Bot bot;

	public AIimpl(Bot bot)
	{
		this.bot = bot;
	}

	@Override
	public void sendMessage(String message)
	{
		bot.getSession().send(new ClientChatPacket(message));
	}

	@Override
	public void respawn()
	{
		bot.getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
	}

	@Override
	public void openInventory()
	{
		bot.getSession().send(new ClientRequestPacket(ClientRequest.OPEN_INVENTORY));
	}

	@Override
	public void jump()
	{
		new Thread(() ->
		{
			double y = bot.getLocation().getY();

			y += 0.15;

			bot.getSession().send(new ClientPlayerPositionPacket(false, bot.getLocation().getX(), y, bot.getLocation().getZ()));

			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}).start();
	}

	@Override
	public void switchToSlot(int slot)
	{
		bot.getSession().send(new ClientPlayerChangeHeldItemPacket(slot));
	}

	@Override
	public void panic()
	{
		new Thread(() ->
		{
			double x = bot.getLocation().getX();

			x += 0.15;

			bot.getSession().send(new ClientPlayerPositionPacket(false, x, bot.getLocation().getY(), bot.getLocation().getZ()));

			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			bot.getLocation().setX(x);
		}).start();
	}

	@Override
	public void move(Position position)
	{
		// TODO: this
	}

	@Override
	public void setSneaking(boolean sneak)
	{
		bot.getSession().send(new ClientPlayerStatePacket(bot.getUniqueId(), sneak ? PlayerState.START_SNEAKING : PlayerState.STOP_SNEAKING));
	}

	@Override
	public void setSprinting(boolean sprint)
	{
		bot.getSession().send(new ClientPlayerStatePacket(bot.getUniqueId(), sprint ? PlayerState.START_SPRINTING : PlayerState.STOP_SPRINTING));
	}

	@Override
	public void swingHand(Hand hand)
	{
		bot.getSession().send(new ClientPlayerSwingArmPacket(hand));
	}

	@Override
	public void useItemInHand(Hand hand)
	{
		bot.getSession().send(new ClientPlayerUseItemPacket(hand));
	}

	@Override
	public void turn(int degrees)
	{
		bot.getSession().send(new ClientPlayerRotationPacket(true, degrees - 60, 0));
	}

	@Override
	public void derp()
	{
		new Thread(() ->
		{
			boolean crouched = false;

			for (int i = 0; i < 1000; i++)
			{
				bot.getSession().send(new ClientPlayerRotationPacket(true, new Random().nextInt(120) - 60, new Random().nextInt(360) - 180));
				swingHand(Hand.MAIN_HAND);
				swingHand(Hand.OFF_HAND);
				setSneaking(crouched = !crouched);

				try
				{
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void dropItemInSlot(int slot)
	{
		switchToSlot(slot);
		bot.getSession().send(new ClientPlayerActionPacket(PlayerAction.DROP_ITEM_STACK, bot.getLocation().getPosition(), BlockFace.DOWN));
	}

	@Override
	public void dropInventory()
	{
		new Thread(() ->
		{
			for (int i = 0; i < 35; i++)
			{
				dropItemInSlot(i);
			}

			try
			{
				Thread.sleep(50L);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}).start();
	}
}