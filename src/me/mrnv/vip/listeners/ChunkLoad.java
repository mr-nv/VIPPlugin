package me.mrnv.vip.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.mrnv.vip.VIPPlugin;

import org.bukkit.World.Environment;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoad implements Listener
{
	public static VIPPlugin plugin;
	
    public ChunkLoad( VIPPlugin instance )
    {
        plugin = instance;
    }
    
	public void onChunkLoad( ChunkLoadEvent event )
	{
		if( event.getWorld( ).getEnvironment( ) != Environment.NORMAL ) return;
	}
}
