package ca.pureplugins.mcbotapi.model;

import java.net.Proxy;

import lombok.Data;

@Data
public class Server
{
	private final String hostname;
	private final int port;
	private final Proxy proxy;

	public Server(String hostname, int port, Proxy proxy)
	{
		this.hostname = hostname;
		this.port = port;
		this.proxy = proxy;
	}

	public Server(String hostname, int port)
	{
		this(hostname, port, Proxy.NO_PROXY);
	}
}