package ca.pureplugins.mcbotapi.model;

import java.net.Proxy;

import lombok.Getter;

@Getter
public class Account
{
	private final String username;
	private final String password;
	private final Proxy proxy;

	public Account(String username, String password, Proxy proxy)
	{
		this.username = username;
		this.password = password;
		this.proxy = proxy;
	}

	public Account(String username, String password)
	{
		this(username, password, Proxy.NO_PROXY);
	}
}