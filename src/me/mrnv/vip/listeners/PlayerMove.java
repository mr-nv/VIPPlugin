package me.mrnv.vip.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.mrnv.vip.VIPPlugin;

public class PlayerMove implements Listener
{
	private VIPPlugin plugin;
	private int minX = 0;
	private int minZ = 0;
	private int maxX = 0;
	private int maxZ = 0;
	
	public PlayerMove( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void loadConfig( )
	{
		try
		{
			minX = plugin.getConfig( ).getInt( "config.world-border-minimum-x" );
			minZ = plugin.getConfig( ).getInt( "config.world-border-minimum-z" );
			maxX = plugin.getConfig( ).getInt( "config.world-border-maximum-x" );
			maxZ = plugin.getConfig( ).getInt( "config.world-border-maximum-z" );
		}
		catch( Exception exception )
		{
			minX = -1000000;
			minZ = -1000000;
			maxX = 1000000;
			maxZ = 1000000;
			
			if( plugin.getConfig( ) != null )
			{
				plugin.getConfig( ).set( "config.world-border-minimum-x", minX );
				plugin.getConfig( ).set( "config.world-border-minimum-z", minZ );
				plugin.getConfig( ).set( "config.world-border-maximum-x", maxX );
				plugin.getConfig( ).set( "config.world-border-maximum-z", maxZ );
			}
			
			exception.printStackTrace( );
		}
	}
	
	@EventHandler
	public void onPlayerMove( PlayerMoveEvent event )
	{
		Location to = event.getTo( );
		if( to.getX( ) < minX || to.getX( ) > maxX ||
			to.getZ( ) < minZ || to.getZ( ) > maxZ )
		{
			Location newLocation = new Location( event.getPlayer( ).getWorld( ),
					to.getX( ), to.getY( ), to.getZ( ) );
			
			newLocation.setPitch( to.getPitch( ) );
			newLocation.setYaw( to.getYaw( ) );
			
			if( to.getX( ) < minX )
				newLocation.setX( minX );
			
			if( to.getX( ) > maxX )
				newLocation.setX( maxX );
			
			if( to.getZ( ) < minZ )
				newLocation.setZ( minZ );
			
			if( to.getZ( ) > maxZ )
				newLocation.setZ( maxZ );
			
			event.setTo( newLocation );
		}
	}
}
