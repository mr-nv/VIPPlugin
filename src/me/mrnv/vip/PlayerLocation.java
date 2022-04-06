package me.mrnv.vip;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerLocation
{
	private Location location;
	private String player;
	
	public PlayerLocation( Player player )
	{
		this.player = player.getName( );
		this.location = player.getLocation( );
	}
	
	public Location getLocation( )
	{
		return this.location;
	}
	
	public String getPlayer( )
	{
		return this.player;
	}
	
	public void setLocation( Location location )
	{
		this.location = location;
	}
}