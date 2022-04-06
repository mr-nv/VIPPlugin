package me.mrnv.vip.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import me.mrnv.vip.VIPPlugin;

public class PlayerBucketEmpty implements Listener
{
	private static VIPPlugin plugin;
	
	public PlayerBucketEmpty( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerBucketEmpty( PlayerBucketEmptyEvent event )
	{
		plugin.getEndPortalManager( ).onPlayerBucketEmpty( event );
	}
}
