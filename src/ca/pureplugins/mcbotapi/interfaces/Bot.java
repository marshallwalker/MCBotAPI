package ca.pureplugins.mcbotapi.interfaces;

import org.spacehq.packetlib.Session;

import ca.pureplugins.mcbotapi.BotSession;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Location;

public abstract interface Bot
{
	int getUniqueId();

	BotSession getBotSession();

	Account getAccount();

	void login();

	Session getSession();

	void updatePosition();

	AI getAI();

	Location getLocation();

	boolean getIsOnGround();
}