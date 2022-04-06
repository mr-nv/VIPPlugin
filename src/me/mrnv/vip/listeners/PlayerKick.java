package me.mrnv.vip.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKick implements Listener
{
	private final static String reason = ChatColor.GOLD + "You have lost connection to the server";
	
	@EventHandler( priority = EventPriority.LOWEST )
	public void onPlayerKick( PlayerKickEvent event )
	{
		Bukkit.getLogger( ).info( "Kicking " + event.getPlayer( ).getName( ) + " for " + event.getReason( ) );
		event.setReason( reason );
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onPlayerLogin( PlayerLoginEvent event )
	{
		if( event.getResult( ) == Result.ALLOWED ) return;
		
		Bukkit.getLogger( ).info( "Kicking " + event.getPlayer( ).getName( ) + " for " + event.getKickMessage( ) );
		event.disallow( event.getResult( ), reason );
	}
}
