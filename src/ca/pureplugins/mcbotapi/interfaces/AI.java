package ca.pureplugins.mcbotapi.interfaces;

import org.spacehq.mc.protocol.data.game.entity.player.Hand;

import ca.pureplugins.mcbotapi.model.Location;

public abstract interface AI
{
	void sendMessage(String message);

	void respawn();

	void openInventory();

	void jump();

	void switchSlot(int slot);

	void panic();

	void move(Location location);

	void sneak(boolean sneak);

	void sprint(boolean sprint);

	void swingHand(Hand hand);

	void useItem(Hand hand);
}