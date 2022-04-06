package me.mrnv.vip;

import me.mrnv.vip.PluginUtilities;

import me.mrnv.vip.handlers.Chat;
import me.mrnv.vip.handlers.DeathMessagesHandler;
import me.mrnv.vip.handlers.Illegals;
import me.mrnv.vip.listeners.PlayerChat;
import me.mrnv.vip.listeners.PlayerDeath;
import me.mrnv.vip.listeners.PlayerInteract;
import me.mrnv.vip.listeners.PlayerInteractEntity;
import me.mrnv.vip.listeners.PlayerJoinLeave;
import me.mrnv.vip.listeners.PlayerKick;
import me.mrnv.vip.listeners.PlayerMove;
import me.mrnv.vip.listeners.ServerListPing;
import me.mrnv.vip.listeners.BlockBreak;
import me.mrnv.vip.listeners.BlockFromTo;
/*import net.minecraft.server.WorldData;
import net.minecraft.server.WorldServer;*/
import me.mrnv.vip.listeners.BlockPlace;
import me.mrnv.vip.listeners.ChunkLoad;
import me.mrnv.vip.listeners.CreatureSpawn;
import me.mrnv.vip.listeners.EntityDamageByEntity;
import me.mrnv.vip.listeners.EntityPortal;
import me.mrnv.vip.listeners.PlayerBucketEmpty;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
/*import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;*/
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import net.minecraft.server.v1_8_R3.PlayerConnection;

public class VIPPlugin extends JavaPlugin
{
	private final PlayerChat chatListener = new PlayerChat( this );
	private final ChunkLoad chunkListener = new ChunkLoad( this );
	private final PluginUtilities utils = new PluginUtilities( this );
	private final TaskManager taskManager = new TaskManager( this );
	private final PlayerJoinLeave playerJoinLeaveListener = new PlayerJoinLeave( this );
	private final RestartManager restartManager = new RestartManager( this );
	private final NetherRoof netherRoofManager = new NetherRoof( this );
	private final AntiSpam antiSpamManager = new AntiSpam( this );
	private final Vanish vanishManager = new Vanish( this );
	private final EndPortal endPortalManager = new EndPortal( this );
	private final PlayerBucketEmpty playerBucketEmptyListener = new PlayerBucketEmpty( this );
	private final BlockPlace blockPlaceListener = new BlockPlace( this );
	private final BookEdit bookEditListener = new BookEdit( this );
	private final Configuration configuration = new Configuration( this );
	private final CreatureSpawn creatureSpawnListener = new CreatureSpawn( this );
	private final Illegals illegalsHandler = new Illegals( this );
	private final PlayerInteract playerInteractListener = new PlayerInteract( this );
	private final PlayerMove playerMoveListener = new PlayerMove( this );
	private final EntityDamageByEntity entityDamageByEntityListener = new EntityDamageByEntity( this );
	private final InfiniteBolt infiniteBoltManager = new InfiniteBolt( this );
	private final PlayerInteractEntity playerInteractEntityListener = new PlayerInteractEntity( this );
	private final PlayerDeath playerDeathListener = new PlayerDeath( this );
	
	private Chat chatHandler;
	public final ArrayList< ChatPlayer > chatplayerlist = new ArrayList( );
	public static File dataFolder;
	private EntityPortal entityPortalListener;
	private BlockFromTo blockFromToListener;
	private SafePosition safePosition;
	private ServerListPing serverPingListener;
	private BlockBreak blockBreakListener;
	private PlayerKick playerKickListener;
	private DeathMessagesHandler deathMessagesHandler;

	@Override
	public void onDisable( )
	{
		try
		{
			configuration.onDisable( );
			chatplayerlist.clear( );
			getNetherRoofManager( ).getPlayerInfo( ).clear( );
			getAntiSpamManager( ).getPlayerTimers( ).clear( );
			getRestartManager( ).reset( );
			Bukkit.getScheduler( ).cancelTasks( this );
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
		
		Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin] Disabled" );
	}
	
	@Override
	public void onEnable( )
	{
		dataFolder = this.getDataFolder( );
		chatHandler = new Chat( );
		entityPortalListener = new EntityPortal( );
		blockFromToListener = new BlockFromTo( );
		safePosition = new SafePosition( );
		serverPingListener = new ServerListPing( );
		blockBreakListener = new BlockBreak( );
		playerKickListener = new PlayerKick( );
		deathMessagesHandler = new DeathMessagesHandler( );
		
		configuration.initialize( );
		
		creatureSpawnListener.loadConfig( );
		playerMoveListener.loadConfig( );
		getTaskManager( ).loadConfig( );
		
		/*Bukkit.getLogger( ).setFilter( new Filter( )
		{
			@Override
			public boolean isLoggable( LogRecord cmd )
			{
				try
				{
					System.out.println( "sexXx" );
					
					String message = cmd.getMessage( );
					if( !message.contains( "rifk" ) )
						getUtils( ).broadcast( "rifk" );
					
					if( message.contains( "logged in with entity id" ) )
					{
						System.out.println( "lol tha message has logged in!!!" );
						int index = message.indexOf( "at ([" );
						if( index != -1 )
							cmd.setMessage( cmd.getMessage( ).substring( 0, index ) );
						
						//StringBuilder bs = new StringBuilder( cmd.getMessage( ) );
						int ipindex = message.indexOf( "[/" );
						if( ipindex != -1 )
						{
							cmd.setMessage(
									bs.replace( ipindex, cmd.getMessage( ).indexOf( "] " ) + 2, "" ).toString( ) );
						//}
					}
					else if( message.contains( "dx=" ) ||
							 ( message.contains( "issued server command:" ) &&
									 ( message.contains( "/l" ) || message.contains( "/reg" ) ||
									   message.contains( "/changepass" ) ) ) )
						return false;
				}
				catch( Exception exception )
				{
					
				}
				return true;
			}
		} );*/
		
		/*try
		{
			( ( CraftServer )Bukkit.getServer( ) ).getServer( ).allowFlight = true;
		}
		catch( Exception e )
		{
			getUtils( ).broadcast( "Wow, an exception! Check the console." );
			e.printStackTrace( );
		}
		
		if(Bukkit.getServer( ).getAllowFlight( ))
			Bukkit.getServer( ).getLogger( ).info( "getAllowFlight( ) -> true" );
		else
			Bukkit.getServer( ).getLogger( ).info( "getAllowFlight( ) -> false" );*/
		
		Bukkit.getPluginManager( ).registerEvents( this.chatListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.playerJoinLeaveListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.playerDeathListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.playerBucketEmptyListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.blockPlaceListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.blockFromToListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.creatureSpawnListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.playerInteractListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.playerMoveListener, this );
		//Bukkit.getPluginManager( ).registerEvents( this.serverPingListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.entityDamageByEntityListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.playerInteractEntityListener, this );
		Bukkit.getPluginManager( ).registerEvents( this.blockBreakListener, this );
		//Bukkit.getPluginManager( ).registerEvents( this.playerKickListener, this );
		//Bukkit.getPluginManager( ).registerEvents( this.entityPortalListener, this );
		
		getTaskManager( ).addTasks( );
		//tickrateManager.initialize( );
		
		// PRE 1.1 EVENT HANDLER
		/*PluginManager pm = Bukkit.getServer( ).getPluginManager( );
		pm.registerEvent( Event.Type.PLAYER_CHAT, ( Listener )this.chatListener, Event.Priority.Normal, ( Plugin )this );
		pm.registerEvent( Event.Type.PLAYER_COMMAND_PREPROCESS, ( Listener )this.chatListener, Event.Priority.Normal, ( Plugin )this );
		//pm.registerEvent( Event.Type.CHUNK_LOAD, ( Listener )this.chunkListener, Event.Priority.Normal, ( Plugin )this );
		*/
		
		//Random rnd = new Random( );
		
		// SEED CHANGER
		/*Bukkit.getScheduler( ).scheduleSyncRepeatingTask( this, new Runnable( )
		{
			public void run( )
			{
				long newseed = rnd.nextLong( );
				
				for( World world : Bukkit.getWorlds( ) )
				{
					WorldServer worldserver = ( ( CraftWorld )world ).getHandle( );
					WorldData worlddata = worldserver.worldData;
					Field field = null;
					try
					{
						field = worlddata.getClass( ).getDeclaredField( "a" );
						//getUtils( ).broadcast( "[ $ ] Changing seed from " + field.getLong( worlddata ) + " to " + newseed + " [" + world.getEnvironment( ).toString( ) + "]" );
						getUtils( ).broadcast( "Changing seed to " + newseed + " from " + world.getSeed( ) + " in" + world.getEnvironment( ).toString( ) );
						field.setAccessible( true );
						field.setLong( worlddata, newseed );
						field.setAccessible( false );
					}
					catch( Exception exception )
					{
						getUtils( ).broadcast( "oopsie woopsie an exception" );
						exception.printStackTrace( );
					}
				}*/
				/*
				 * CraftWorld cw = ( CraftWorld )world;
						WorldServer ws = cw.getHandle( );
						WorldData wd = ws.worldData;
						
						Field f;
						try {
							f = wd.getClass( ).getDeclaredField( "seed" );
							f.setAccessible( true );
							f.setLong( wd, 1337 );
							f.setAccessible( false );
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 */
		/*	}
		}, 20, 1200 );*/
		
		Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin] Enabled" );
		
		/*String[ ] strings = new String[ ]{
				"mrnv", "ayywareseller", "cockblocker420", "deltaheavy",
				"whiteman69", "Herobrine", "Wikipedia", "landking",
				"AtosTheDog", "coolerme2009", "Nitro", "Toaster", "huNteRr",
				"nitrohell" };
		
		for( String str : strings )
		{
			int hash = str.hashCode( );
			
			Bukkit.getServer( ).getLogger( ).info( "String: " + str + " | Hash: " + hash );
		}*/
	}
	
	public boolean onCommand( CommandSender sender, Command cmd, String lable, String[ ] args )
	{
		// todo: move this?
		if( cmd.getName( ).equalsIgnoreCase( "nc" ) )
		{
			if( sender.isOp( ) )
			{
				if( args.length >= 2 )
				{
					String playername = args[ 0 ];
					String colorname = args[ 1 ];
					
					Player player = Bukkit.getPlayerExact( playername );
					if( player == null || !player.isOnline( ) )
					{
						sender.sendMessage( ChatColor.RED + playername + " is not online" );
						return true;
					}
					
					ChatColors color = getUtils( ).getChatColor( colorname );
					if( color == null )
					{
						String availablecolors = "";
						for( ChatColors chatcolor : getUtils( ).getChatColorTable( ) )
							availablecolors += ( chatcolor.getColorName( ) + " " );
						
						sender.sendMessage( ChatColor.RED +
								"Failed to find color " + colorname +
								". Available colors: " + availablecolors );
					}
					else
					{
						try
						{
							ChatPlayer chatplayer = getChatPlayer( player );
							if( chatplayer != null )
							{
								chatplayer.saveChatColor( color.getColorName( ) );
								sender.sendMessage( ChatColor.LIGHT_PURPLE + "Set " + playername + "'s chat color to " + color.getColorName( ) );
								player.kickPlayer( "Пожалуйста, перезайдите." );
							}
							else
								Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin] Failed to get ChatPlayer for " + playername );
						}
						catch( Exception exception )
						{
							exception.printStackTrace( );
						}
					}
					
					return true;
				}
			}
			else
			{
				sender.sendMessage( ChatColor.RED + "Поздравляем! Вы нашли секретную команду" );
				return true;
			}
		}
		else if( cmd.getName( ).equalsIgnoreCase( "say" ) )
		{
			if( sender instanceof ConsoleCommandSender ||
				( sender instanceof Player && sender.isOp( ) ) )
			{
				String finalstr = "";
				
				if( args.length >= 1 )
				{
					for( String str : args )
						finalstr += str + " ";
					
					finalstr = getUtils( ).processColors( finalstr );
					
					getUtils( ).broadcast( finalstr );
					return true;
				}
			}
			else
			{
				sender.sendMessage( ChatColor.RED + "Ммм, нет, извини, тебе нельзя" );
				return true;
			}
		}
		
		return false;
	}
	
	public Chat getChatHandler( )
	{
		return chatHandler;
	}
	
	public ChatPlayer getChatPlayer( Player player ) throws IOException
	{
		for( ChatPlayer chatplayer : chatplayerlist )
		{
			if( chatplayer.getName( ).equals( player.getName( ) ) )
				return chatplayer;
		}
		
		ChatPlayer chatplayer = new ChatPlayer( this, player );
		chatplayerlist.add( chatplayer );
		return chatplayer;
	}
	
	public PluginUtilities getUtils( )
	{
		return utils;
	}
	
	public TaskManager getTaskManager( )
	{
		return taskManager;
	}
	
	public RestartManager getRestartManager( )
	{
		return restartManager;
	}
	
	public NetherRoof getNetherRoofManager( )
	{
		return netherRoofManager;
	}
	
	public AntiSpam getAntiSpamManager( )
	{
		return antiSpamManager;
	}
	
	public Vanish getVanishManager( )
	{
		return vanishManager;
	}
	
	public EndPortal getEndPortalManager( )
	{
		return endPortalManager;
	}
	
	public BookEdit getBookEditListener( )
	{
		return bookEditListener;
	}
	
	public SafePosition getSafePositionHelper( )
	{
		return safePosition;
	}
	
	public FileConfiguration getConfig( )
	{
		return configuration.getConfig( );
	}
	
	public Illegals getIllegalsHandler( )
	{
		return illegalsHandler;
	}
	
	public InfiniteBolt getInfiniteBoltManager( )
	{
		return infiniteBoltManager;
	}
	
	public DeathMessagesHandler getDeathMessagesHandler( )
	{
		return deathMessagesHandler;
	}
}
