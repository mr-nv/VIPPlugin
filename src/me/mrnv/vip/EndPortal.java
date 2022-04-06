package me.mrnv.vip;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import me.mrnv.vip.listeners.PlayerBucketEmpty;

public class EndPortal
{
	private VIPPlugin plugin;
	
	public EndPortal( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void onPlayerBucketEmpty( PlayerBucketEmptyEvent event )
	{
		Block relative = event.getBlockClicked( ).getRelative( event.getBlockFace( ) );
		
		if( relative.getType( ) == Material.ENDER_PORTAL )
		{
			Bukkit.getServer( ).getLogger( ).info( event.getPlayer( ).getName( ) +
													" tried to break an end portal block" );
			event.setCancelled( true );
			event.getPlayer( ).updateInventory( );
		}
	}
	
	public void onBlockPlace( BlockPlaceEvent event )
	{
		Block block = event.getBlock( );
		if( block.getType( ) == Material.DISPENSER )
		{
			if( event.getBlockAgainst( ).getType( ) == Material.ENDER_PORTAL )
			{
				Bukkit.getServer( ).getLogger( ).info( event.getPlayer( ).getName( ) +
						" tried to break an end portal block" );
				event.setCancelled( true );
				event.getPlayer( ).updateInventory( );
			}
			else
			{
				int radius = 1;
				loop:
				for( int x = -1; x <= 1; x++ )
				{
					for( int y = -1; y <= 1; y++ )
					{
						for( int z = -1; z <= 1; z++ )
						{
							Block relative = block.getRelative( x, y, z );
							if( relative.getType( ) == Material.ENDER_PORTAL )
							{
								Bukkit.getServer( ).getLogger( ).info( event.getPlayer( ).getName( ) +
										" tried to break an end portal block" );
								event.setCancelled( true );
								event.getPlayer( ).updateInventory( );
								break loop;
							}
						}
					}
				}
			}
		}
	}
	
	/*public void addTask( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				World end = Bukkit.getServer( ).getWorlds( ).get( 2 );
				//end.get
			}
		}, 200, 200 );
	}*/
}
