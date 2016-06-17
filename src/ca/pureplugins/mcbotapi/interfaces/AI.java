package ca.pureplugins.mcbotapi.interfaces;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;

public abstract interface AI
{
	void sendMessage(String message);

	void respawn();

	void openInventory();

	void jump();

	void switchToSlot(int slot);

	void panic();

	void move(Position position);

	void setSneaking(boolean sneak);

	void setSprinting(boolean sprint);

	void swingHand(Hand hand);

	void useItemInHand(Hand hand);

	void turn(int degrees);

	void derp();

	void dropItemInSlot(int slot);

	void dropInventory();
}