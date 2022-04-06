package me.mrnv.vip;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import me.mrnv.vip.PlayerLocation;

public class NetherRoof 
{
	private VIPPlugin plugin;
	public final ArrayList< PlayerLocation > playerInfo = new ArrayList( );
	
	public NetherRoof( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void processLeave( String player )
	{
		for( PlayerLocation playerLocation : playerInfo )
		{
			if( playerLocation.getPlayer( ).equalsIgnoreCase( player ) )
			{
				playerInfo.remove( playerLocation );
				break;
			}
		}
	}
	
	public void addTask( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				if( Bukkit.getOnlinePlayers( ).size( ) > 0 )
				{
					for( Player player : Bukkit.getOnlinePlayers( ) )
					{
						PlayerLocation playerLocation = getPlayerLocation( player );
						Location playerPosition = player.getLocation( );
						
						if( playerLocation != null )
						{
							if( player.getWorld( ).getEnvironment( ) == Environment.NETHER )
							{
								if( playerPosition.getY( ) > 127 )
								{
									if( playerLocation.getLocation( ).getY( ) > 127 )
										plugin.getUtils( ).teleport( player, plugin.getSafePositionHelper( ).getPosition( player ) );
									else
										plugin.getUtils( ).teleport( player, playerLocation.getLocation( ) );
								}
								else
									playerLocation.setLocation( playerPosition );
							}
							else
								playerLocation.setLocation( playerPosition );
						}
					}
				}
			}
		}, 20, 20 );
	}
	
	private PlayerLocation getPlayerLocation( Player player )
	{
		for( PlayerLocation playerLocation : playerInfo )
		{
			if( playerLocation.getPlayer( ).equalsIgnoreCase( player.getName( ) ) )
				return playerLocation;
		}
		
		PlayerLocation playerLocation = new PlayerLocation( player );
		playerInfo.add( playerLocation );
		return playerLocation;
	}
	
	public ArrayList< PlayerLocation > getPlayerInfo( )
	{
		return playerInfo;
	}
}
