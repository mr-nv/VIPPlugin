package me.mrnv.vip.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.mrnv.vip.VIPPlugin;

public class CreatureSpawn implements Listener
{
	private VIPPlugin plugin;
	private int maxWitherCount = 0;
	
	public CreatureSpawn( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void loadConfig( )
	{
		try
		{
			this.maxWitherCount = plugin.getConfig( ).getInt( "config.chunk-wither-limit" );
		}
		catch( Exception exception )
		{
			this.maxWitherCount = 4;
			plugin.getConfig( ).set( "config.chunk-wither-limit", 4 );
			exception.printStackTrace( );
		}
	}
	
	@EventHandler
	public void onCreatureSpawn( CreatureSpawnEvent event )
	{
		if( event.getEntityType( ) != EntityType.WITHER ) return;
		
		LivingEntity entity = event.getEntity( );
		Location location = entity.getLocation( );
		Chunk chunk = location.getChunk( );
		
		int withers = 1;
		for( Entity chunkentity : chunk.getEntities( ) )
		{
			if( chunkentity.getType( ) == EntityType.WITHER )
			{
				withers++;
				
				if( withers >= maxWitherCount )
				{
					event.setCancelled( true );
					break;
				}
			}
		}
	}
}
