package me.mrnv.vip;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.common.collect.Iterables;

public class InfiniteBolt
{
	private VIPPlugin plugin;
	
	private Random random = new Random( );
	private boolean toggled = false;
	
	public InfiniteBolt( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void addTask( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				if( !toggled ) return;
				
				try
				{
					Collection< ? extends Player>  players = Bukkit.getOnlinePlayers( );
					if( players.size( ) > 0 )
					{
						Player player = Iterables.get( players, random.nextInt( players.size( ) ) );//players.//[ random.nextInt( players.length ) ];
						if( player != null )
						{
							World world = player.getWorld( );
							if( world != null )
							{
								Location playerLocation = player.getLocation( );
								Location location = new Location(
										world,
										playerLocation.getX( ) + random.nextInt( 100 ),
										playerLocation.getY( ),
										playerLocation.getZ( ) + random.nextInt( 100 ) );
								
								Chunk chunk = location.getChunk( );
								if( chunk != null )
								{
									if( !chunk.isLoaded( ) )
										chunk.load( true );
								}
								
								world.strikeLightning( location );	
							}
						}
					}
				}
				catch( Exception exception )
				{
					
				}
			}
		}, 1, 1 );
	}
	
	public boolean toggle( )
	{
		toggled = !toggled;
		return toggled;
	}
}
