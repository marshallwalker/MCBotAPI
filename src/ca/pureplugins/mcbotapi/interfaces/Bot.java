package ca.pureplugins.mcbotapi.interfaces;

import org.spacehq.packetlib.Session;

import ca.pureplugins.mcbotapi.event.base.EventBus;
import ca.pureplugins.mcbotapi.model.Account;
import ca.pureplugins.mcbotapi.model.Location;
import ca.pureplugins.mcbotapi.model.Server;

public abstract interface Bot
{
	void login();

	EventBus getEventBus();

	int getId();

	Session getSession();

	Account getAccount();

	AI getAI();

	Location getLocation();

	Server getServer();
}