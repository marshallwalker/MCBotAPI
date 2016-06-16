package ca.pureplugins.mcbotapi.impl;

import org.spacehq.mc.protocol.data.game.ClientRequest;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;
import org.spacehq.mc.protocol.data.game.entity.player.PlayerState;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerStatePacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerSwingArmPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;

import ca.pureplugins.mcbotapi.interfaces.AI;
import ca.pureplugins.mcbotapi.interfaces.Bot;
import ca.pureplugins.mcbotapi.model.Location;

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
		// TODO: this
	}

	@Override
	public void switchSlot(int slot)
	{
		bot.getSession().send(new ClientPlayerChangeHeldItemPacket(slot));
	}

	@Override
	public void panic()
	{
	}

	@Override
	public void move(Location location)
	{
		Location loc = bot.getLocation();
		loc.add(location);

		// Updates the position
		bot.getSession().send(new ClientPlayerPositionPacket(loc.isOnGround(), loc.getX(), loc.getY(), loc.getZ()));
	}

	@Override
	public void sneak(boolean sneak)
	{
		bot.getSession().send(new ClientPlayerStatePacket(bot.getId(), sneak ? PlayerState.START_SNEAKING : PlayerState.STOP_SNEAKING));
	}

	@Override
	public void sprint(boolean sprint)
	{
		bot.getSession().send(new ClientPlayerStatePacket(bot.getId(), sprint ? PlayerState.START_SPRINTING : PlayerState.STOP_SPRINTING));
	}

	@Override
	public void swingHand(Hand hand)
	{
		bot.getSession().send(new ClientPlayerSwingArmPacket(hand));
	}

	@Override
	public void useItem(Hand hand)
	{
		bot.getSession().send(new ClientPlayerUseItemPacket(hand));
	}
}