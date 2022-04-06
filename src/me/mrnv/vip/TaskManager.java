package me.mrnv.vip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.google.common.collect.Iterables;

public class TaskManager
{
	private VIPPlugin plugin;
	private int spawnRadius;
	private int maxWithersInSpawnRadius;
	private long lastSave;
	
	public TaskManager( VIPPlugin plugin )
	{
		this.plugin = plugin;
		this.lastSave = System.currentTimeMillis( );
	}
	
	public void loadConfig( )
	{
		try
		{
			this.spawnRadius = plugin.getConfig( ).getInt( "config.spawn-radius" );
			this.maxWithersInSpawnRadius = plugin.getConfig( ).getInt( "config.max-withers-in-spawn-radius" );
		}
		catch( Exception exception )
		{
			this.spawnRadius = 250;
			this.maxWithersInSpawnRadius = 40;
			plugin.getConfig( ).set( "config.spawn-radius", 250 );
			plugin.getConfig( ).set( "config.max-withers-in-spawn-radius", 40 );
			exception.printStackTrace( );
		}
	}
	
	public void addTasks( )
	{
		this.addTaskBroadcast( );
		this.addTaskAutoSave( );
		this.addTaskFairPlay( );
		this.addTaskFairPlayEnderChest( );
		this.addTaskFairPlayItemFrames( );
		//this.addTaskProtocolLib( );
		//this.addTaskWithers( );
		//plugin.getNetherRoofManager( ).addTask( );
		plugin.getAntiSpamManager( ).addTask( );
		plugin.getRestartManager( ).addTask( );
		plugin.getInfiniteBoltManager( ).addTask( );
	}
	
	private void addTaskBroadcast( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				plugin.getUtils( ).broadcast( "Discord: https://discord.gg/SZeHK9G" );
				plugin.getUtils( ).broadcast( "VK: https://vk.com/historymc" );
				plugin.getUtils( ).broadcast( "Donate: https://qiwi.me/historymc" );
			}
		}, 20, 11400 );
	}
	
	private void addTaskAutoSave( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				if( Bukkit.getServer( ).getOnlinePlayers( ).size( ) > 0 )
				{
					if( System.currentTimeMillis( ) > ( lastSave + 300000 ) )//3600000 ) )
					{
						plugin.getUtils( ).save( );
						lastSave = System.currentTimeMillis( );
					}
				}
			}
		}, 20, 600 );
	}
	
	private void addTaskFairPlay( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			@SuppressWarnings( "deprecation" )
			public void run( )
			{
				for( Player player : Bukkit.getOnlinePlayers( ) )
				{
					if( player.getGameMode( ) != GameMode.CREATIVE )
					{
						Inventory inv = player.getInventory( );
						if( inv != null )
						{
							if( plugin.getIllegalsHandler( ).handleIllegals( inv ) )
								player.updateInventory( );
						}
					}
				}
			}
		}, 20, 60 );
	}
	
	private void addTaskFairPlayEnderChest( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			@SuppressWarnings( "deprecation" )
			public void run( )
			{
				for( Player player : Bukkit.getOnlinePlayers( ) )
				{
					if( player.getGameMode( ) != GameMode.CREATIVE )
					{
						Inventory inv = player.getEnderChest( );
						if( inv != null )
						{
							if( plugin.getIllegalsHandler( ).handleIllegals( inv ) )
								player.updateInventory( );
						}
					}
				}
			}
		}, 20, 600 );
	}
	
	private void addTaskFairPlayItemFrames( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			@SuppressWarnings( "deprecation" )
			public void run( )
			{
				for( World world : Bukkit.getWorlds( ) )
				{
					for( Entity entity : world.getEntities( ) )
					{
						if( entity == null ) continue;
						
						if( entity instanceof ItemFrame )
						{
							ItemFrame itemframe = ( ItemFrame )entity;
							if( itemframe.getItem( ) != null )
							{
								if( plugin.getIllegalsHandler( ).checkItem( itemframe.getItem( ) ) )
									itemframe.setItem( null );
							}
						}
						else if( entity instanceof Horse )
						{
							Horse horse = ( Horse )entity;
							if( horse != null && horse.getInventory( ) != null )
								plugin.getIllegalsHandler( ).handleIllegals( horse.getInventory( ) );
						}
					}
				}
			}
		}, 20, 600 );
	}
	
	private void addTaskProtocolLib( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				if( !plugin.getBookEditListener( ).isInitialized( ) )
				{
					PluginManager pluginmanager = Bukkit.getPluginManager( );
					Plugin protocollib = pluginmanager.getPlugin( "ProtocolLib" );
					if( protocollib != null )
					{
						if( pluginmanager.isPluginEnabled( protocollib ) )
							plugin.getBookEditListener( ).init( );
					}
				}
			}
		}, 100, 100 );
	}
	
	private void addTaskWithers( )
	{
		final Comparator< Entity > COMPARATOR = new Comparator< Entity >( )
		{
			@Override
			public int compare( Entity e1, Entity e2 )
			{
				return Integer.compare( e1.getEntityId( ), e2.getEntityId( ) );
			}
		};
		
		Bukkit.getLogger().info(Integer.toString(this.spawnRadius));
		
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				for( World world : Bukkit.getServer( ).getWorlds( ) )
				{
					if( world == null ) continue;
					
					//Entity[ ] entities = world.getEntities()
					//List< Chunk > chunks = new ArrayList< Chunk >( );
					//List< Entity > withers = new ArrayList< Entity >( );
					
					/*for( int x = -spawnRadius; x <= spawnRadius; x += 16 )
					{
						for( int z = -spawnRadius; z <= spawnRadius; z += 16 )
						{
							Chunk chunk = world.getChunkAt( x, z );
							if( !chunk.isLoaded( ) )
								chunk.load( );
							
							chunks.add( chunk );
						}
					}*/
					
					
					/*for( Chunk chunk : chunks )
					{
						for( Entity entity : chunk.getEntities( ) )
						{
							if( entity.getType( ) == EntityType.WITHER )
								withers.add( entity );
						}
					}
					
					Bukkit.getLogger().info("loser");
					
					//Collections.sort( withers, COMPARATOR );
					
					while( withers.size( ) > maxWithersInSpawnRadius )
					{
						Entity wither = Iterables.getLast( withers );
						wither.remove( );
						withers.remove( wither );
					}
					*/
				}
			}
		}, 200, 200 );
	}
}
