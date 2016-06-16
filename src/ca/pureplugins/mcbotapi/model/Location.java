package ca.pureplugins.mcbotapi.model;

import lombok.Data;

@Data
public class Location
{
	private double x, y, z;
	private float yaw, pitch;
	private boolean isOnGround;

	public Location(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = 0;
		this.pitch = 0;
	}

	public Location(double x, double y, double z, float yaw, float pitch)
	{
		this(x, y, z);
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public void add(Location location)
	{
		x += location.getX();
		y += location.getY();
		z += location.getZ();
		yaw += location.getYaw();
		pitch += location.getPitch();
	}
}