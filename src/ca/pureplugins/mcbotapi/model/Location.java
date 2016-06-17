package ca.pureplugins.mcbotapi.model;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;

import lombok.Data;

@Data
public class Location
{
	private double x, y, z;
	private float yaw, pitch;

	public Location(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Position getPosition()
	{
		return new Position((int) x, (int) y, (int) z);
	}
}