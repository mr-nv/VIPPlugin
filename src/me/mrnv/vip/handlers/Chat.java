package me.mrnv.vip.handlers;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.mrnv.vip.ChatColors;
import me.mrnv.vip.ChatPlayer;
import me.mrnv.vip.VIPPlugin;

public class Chat
{
	private static VIPPlugin plugin;
	
	private List< String > whisperCommands = Arrays.asList( "w", "whisper", "msg", "message", "t", "tell", "pm" );
	private List< String > respondCommands = Arrays.asList( "r", "reply" );
	private List< String > ignoreCommands = Arrays.asList( "ignore", "unignore" );
	private String ignoreListCommand = "ignorelist";
	private String townCommand = "town";
	private List< String > nameColorCommands = Arrays.asList( "nc", "namecolor", "colorname" );
	private String infoCommand = "info";
	private List< String > vanishCommands = Arrays.asList( "v", "vanish" );
	private String safeCommand = "safe";
	private List< String > removeWithersCommands = Arrays.asList( "removewithers", "deletewithers" );
	private List< String > antiIllegalsCommands = Arrays.asList( "toggleillegals", "anti32k" );
	private List< String > infiniteBoltCommands = Arrays.asList( "infbolt", "infinitebolt", "infinitybolt" );
	private List< String > joindateCommands = Arrays.asList( "joindate", "jd" );
	private List< String > killCommands = Arrays.asList( "kill", "minecraft:kill", "suicide" );
	private List< String > helpCommands = Arrays.asList( "minecraft:help", "bukkit:help", "help", "bukkit:?", "?" );
	private List< String > toggleDeathMessagesCommands = Arrays.asList( "toggledeathmsg", "toggledeathmsgs", "toggledeathmessage", "toggledeathmessages" );
	
	private String worldName = "";
	private long lastInfoTime = 0;
	private String lastInfoString = "";
	
	public void process( VIPPlugin plugin, PlayerChatEvent event )
	{
		this.plugin = plugin;
		
		event.setMessage( plugin.getUtils( ).processColors( event.getMessage( ) ) );
		
		try
		{
			ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
			if( chatplayer != null )
			{
				String town = chatplayer.getTown( );
				if( !town.isEmpty( ) )
					event.setMessage( event.getMessage( ) + " | " + town );
			}
		}
		catch( IOException exception )
		{
			exception.printStackTrace( );
		}
		
		try
		{
			Player[ ] recipients = new Player[ event.getRecipients( ).size( ) ];
			event.getRecipients( ).toArray( recipients );
		
			for( Player recipient : recipients )
			{
				ChatPlayer chatplayer = plugin.getChatPlayer( recipient );
				if( chatplayer != null )
				{
					if( chatplayer.isIgnored( event.getPlayer( ).getName( ) ) ||
						( !chatplayer.isChatToggled( ) &&
						  !event.getPlayer( ).getName( ).equals( chatplayer.getName( ) ) ) )
						event.getRecipients( ).remove( ( Object )recipient );
				}
			}
		}
		catch( IOException exception )
		{
			exception.printStackTrace( );
		}
	}
	
	public int shouldProcessCommand( PlayerCommandPreprocessEvent event )
	{
		// substring( 1 ) is needed to remove the /
		String[ ] split = event.getMessage( ).toLowerCase( ).substring( 1 ).split( " " );
		
		for( String whisper : whisperCommands )
		{
			if( split[ 0 ].equals( whisper ) )
				return 1;
		}
		
		for( String respond : respondCommands )
		{
			if( split[ 0 ].equals( respond ) )
				return 2;
		}
		
		if( split[ 0 ].equals( ignoreListCommand ) )
			return 4;
		
		for( String ignore : ignoreCommands )
		{
			if( split[ 0 ].equals( ignore ) )
				return 3;
		}
		
		if( split[ 0 ].equals( townCommand ) )
			return 5;
		
		for( String namecolor : nameColorCommands )
		{
			if( split[ 0 ].equals( namecolor ) )
				return 6;
		}
		
		if( split[ 0 ].equals( "info" ) )
			return 7;
		
		if( split[ 0 ].equals( "togglechat" ) )
			return 8;
		
		for( String help : helpCommands )
		{
			if( split[ 0 ].equals( help ) )
				return 9;
		}
		
		for( String vanish : vanishCommands )
		{
			if( split[ 0 ].equals( vanish ) &&
				event.getPlayer( ).isOp( ) )
				return 10;
		}
		
		/*if( split[ 0 ].equals( "safe" ) )
			return 11;*/
		
		for( String withers : removeWithersCommands )
		{
			if( split[ 0 ].equals( withers ) &&
				event.getPlayer( ).isOp( ) )
				return 12;
		}
		
		for( String illegals : antiIllegalsCommands )
		{
			if( split[ 0 ].equals( illegals ) &&
				event.getPlayer( ).isOp( ) )
				return 13;
		}
		
		for( String infbolt : infiniteBoltCommands )
		{
			if( split[ 0 ].equals( infbolt ) &&
				event.getPlayer( ).isOp( ) )
				return 14;
		}
		
		/*for( String joindate : joindateCommands )
		{
			if( split[ 0 ].equals( joindate ) )
				return 15;
		}*/
		
		for( String kill : killCommands )
		{
			if( split[ 0 ].equals( kill ) )
				return 16;
		}
		
		for( String deathmsg : toggleDeathMessagesCommands )
		{
			if( split[ 0 ].equals( deathmsg ) )
				return 17;
		}
		
		return 0; // shouldnt process at all
	}
	
	public void processCommand( VIPPlugin plugin, PlayerCommandPreprocessEvent event, int command )
	{
		this.plugin = plugin;
		
		switch( command )
		{
		case 1:
			processWhisper( event );
			event.setCancelled( true );
			break;
		case 2:
			processReply( event );
			event.setCancelled( true );
			break;
		case 3:
			processIgnore( event );
			event.setCancelled( true );
			break;
		case 4:
			processIgnoreList( event );
			event.setCancelled( true );
			break;
		case 5:
			processTown( event );
			event.setCancelled( true );
			break;
		/*case 6:
			processNameColor( event );
			event.setCancelled( true );
			break;*/
		case 7:
			processInfo( event );
			event.setCancelled( true );
			break;
		case 8:
			processToggleChat( event );
			event.setCancelled( true );
			break;
		case 9:
			processHelp( event );
			event.setCancelled( true );
			break;
		case 10:
			plugin.getVanishManager( ).toggle( event );
			event.setCancelled( true );
			break;
		/*case 11:
			processSafe( event );
			event.setCancelled( true );
			break;*/
		case 12:
			processWithers( event );
			event.setCancelled( true );
			break;
		case 13:
			processIllegals( event );
			event.setCancelled( true );
			break;
		case 14:
			processInfiniteBolt( event );
			event.setCancelled( true );
			break;
		case 15:
			processJoinDate( event );
			event.setCancelled( true );
			break;
		case 16:
			processKill( event );
			event.setCancelled( true );
			break;
		case 17:
			processDeathMessages( event );
			event.setCancelled( true );
			break;
		default: break;
		}
	}
	
	private void processWhisper( PlayerCommandPreprocessEvent event )
	{
		String[ ] split = event.getMessage( ).split( " " );
		
		if( split.length <= 2 )
			event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Usage: /w <player> <message>" );
		else
		{
			String recipient = split[ 1 ];
			String message = plugin.getUtils( ).processColors(
								event.getMessage( ).substring(
								event.getMessage( ).indexOf( " ",
								event.getMessage( ).indexOf( " " ) + 1 ) + 1 ) );

			if( recipient.toLowerCase( ).equalsIgnoreCase( event.getPlayer( ).getName( ) ) ) return;
			
			Player playerrecipient = Bukkit.getPlayerExact( recipient );
			
			if( playerrecipient == null )
				event.getPlayer( ).sendMessage( ChatColor.RED + recipient + " is not online" );
			else
			{
				try
				{
					ChatPlayer chatplayer = plugin.getChatPlayer( playerrecipient );
					if( chatplayer != null )
					{
						if( !chatplayer.isIgnored( event.getPlayer( ).getName( ) ) )
						{
							playerrecipient.sendMessage( ChatColor.LIGHT_PURPLE +
									event.getPlayer( ).getName( ) + " whispers: " + message );
							
							chatplayer.setLastMessenger( event.getPlayer( ) );
						}
						
						event.getPlayer( ).sendMessage( ChatColor.LIGHT_PURPLE +
								"to " + recipient + ": " + message );
					}
					else
						Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin - processWhisper] Failed to get ChatPlayer for " + recipient );
				}
				catch( Exception exception )
				{
					exception.printStackTrace( );
				}
			}
		}
	}
	
	private void processReply( PlayerCommandPreprocessEvent event )
	{
		if( event.getMessage( ).indexOf( " " ) == -1 )
			event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Usage: /r <message>" );
		else
		{
			try
			{
				ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
				if( chatplayer != null )
				{
					String message = plugin.getUtils( ).processColors(
										event.getMessage( ).substring(
										event.getMessage( ).indexOf( " " ) + 1 ) );
					
					Player lastMessenger = chatplayer.getLastMessenger( );
					if( lastMessenger != null )
					{
						ChatPlayer chatplayermessenger = plugin.getChatPlayer( lastMessenger );
						if( chatplayermessenger != null )
						{
							if( !chatplayermessenger.isIgnored( event.getPlayer( ).getName( ) ) )
							{
								lastMessenger.sendMessage( ChatColor.LIGHT_PURPLE +
										event.getPlayer( ).getName( ) + " whispers: " + message );
								
								chatplayermessenger.setLastMessenger( event.getPlayer( ) );
							}
							
							event.getPlayer( ).sendMessage( ChatColor.LIGHT_PURPLE +
									"to " + lastMessenger.getName( ) + ": " + message );
						}
						else
							Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin - processReply] Failed to get ChatPlayer for " + lastMessenger.getName( ) );
					}
					else
						event.getPlayer( ).sendMessage( ChatColor.RED + "The last person who whispered you is not online" );
				}
				else
					Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin - processReply] Failed to get ChatPlayer for " + event.getPlayer( ).getName( ) );
			}
			catch( Exception exception )
			{
				exception.printStackTrace( );
			}
		}
	}
	
	private void processIgnore( PlayerCommandPreprocessEvent event )
	{
		String[ ] split = event.getMessage( ).split( " " );
		
		if( split.length <= 1 )
			event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Usage: /ignore <player>" );
		else
		{
			String target = split[ 1 ];
			if( target.toLowerCase( ).equalsIgnoreCase( event.getPlayer( ).getName( ) ) ) return;
			
			try
			{
				ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
				if( chatplayer != null )
				{
					if( chatplayer.isIgnored( target ) )
						event.getPlayer( ).sendMessage( ChatColor.LIGHT_PURPLE + "Unignoring " + target );
					else
						event.getPlayer( ).sendMessage( ChatColor.LIGHT_PURPLE + "Now ignoring " + target );
					
					chatplayer.saveIgnoreList( target );
				}
				else
					Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin - processIgnore] Failed to get ChatPlayer for " + event.getPlayer( ).getName( ) );
			}
			catch( Exception exception )
			{
				exception.printStackTrace( );
			}
		}
	}
	
	private void processIgnoreList( PlayerCommandPreprocessEvent event )
	{
		try
		{
			ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
			if( chatplayer != null )
			{
				List< String > ignorelist = chatplayer.getIgnoreList( );
				if( ignorelist.size( ) > 0 )
				{
					int i = 0;
					String str = ChatColor.YELLOW + "Ignored players: ";
					
					for( String ignored : ignorelist )
					{
						i++;
						str += ignored;
						str += " ";
					}
					
					str += "(" + Integer.toString( i ) + " in total)";
					
					event.getPlayer( ).sendMessage( str );
				}
				else
					event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Your ignore list is empty" );
			}
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	private void processTown( PlayerCommandPreprocessEvent event )
	{
		String town = event.getMessage( ).substring( event.getMessage( ).indexOf( " " ) + 1 );
		if( event.getMessage( ).indexOf( " " ) == -1 )
			town = "";
		
		try
		{
			ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
			if( chatplayer != null )
			{
				chatplayer.saveTown( town );
				event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Set town name to " + ( town.isEmpty() ? "NONE" : town ) );
			}
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	private void processNameColor( PlayerCommandPreprocessEvent event )
	{
		if( event.getPlayer( ).isOp( ) )
		{
			String[ ] split = event.getMessage( ).split( " " );
			if( split.length <= 2 )
				event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Usage: /nc <player> <color>" );
			else
			{
				String playername = split[ 1 ];
				String colorname = split[ 2 ];
				
				Player player = Bukkit.getPlayerExact( playername );
				if( player == null || !player.isOnline( ) )
				{
					event.getPlayer( ).sendMessage( ChatColor.RED + playername + " is not online" );
					return;
				}
				
				ChatColors color = plugin.getUtils( ).getChatColor( colorname );
				if( color == null )
				{
					String availablecolors = "";
					for( ChatColors chatcolor : plugin.getUtils( ).getChatColorTable( ) )
						availablecolors += ( chatcolor.getColorName( ) + " " );
					
					event.getPlayer( ).sendMessage( ChatColor.RED +
							"Failed to find color " + colorname +
							". Available colors: " + availablecolors );
				}
				else
				{
					try
					{
						ChatPlayer chatplayer = plugin.getChatPlayer( player );
						if( chatplayer != null )
						{
							chatplayer.saveChatColor( color.getColorName( ) );
							event.getPlayer( ).sendMessage( ChatColor.LIGHT_PURPLE + "Set " + playername + "'s chat color to " + color.getColorName( ) );
							player.kickPlayer( "Пожалуйста, перезайдите." );
						}
						else
							Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin - processNameColor] Failed to get ChatPlayer for " + playername );
					}
					catch( Exception exception )
					{
						exception.printStackTrace( );
					}
				}
			}
		}
		else
			event.getPlayer( ).sendMessage( ChatColor.RED + "Секретная команда, к которой доступ закрыт." );
	}
	
	private void processInfo( PlayerCommandPreprocessEvent event )
	{
		if( System.currentTimeMillis( ) < ( lastInfoTime + 120000 ) )
		{
			event.getPlayer( ).sendMessage( lastInfoString );
			return;
		}
		
		try
		{
			if( worldName.equals( "" ) )
			{
				World overworld = Bukkit.getServer( ).getWorlds( ).get( 0 );
				worldName = overworld.getName( );
			}
			
			final DecimalFormat df = new DecimalFormat( "###.##" );
			final String a = ChatColor.DARK_RED + "-----------------------------------\n";
			String str = a + ChatColor.WHITE + "Карта мира суммарно весит ";
			
			double size = 0;
			
			File[ ] folders = new File[ ]{
					new File( worldName ), new File( worldName + "_nether" ),
					new File( worldName + "_the_end" ) };
			
			for( File folder : folders )
			{
				if( folder != null && folder.exists( ) )
					size += plugin.getUtils( ).getFolderSize( folder );
			}
			
			for( int i = 0; i <= 1; i++ )
				size /= 1024;
			
			size *= 0.001;
			
			String round = df.format( size );
			
			str += ChatColor.GREEN + df.format( size ) +
					ChatColor.WHITE + " гигабайт\n";
			
			File folder_playerdata = new File( "world/playerdata" );
			if( folder_playerdata != null && folder_playerdata.exists( ) )
			{
				String[ ] list = folder_playerdata.list( );
				if( list != null )
					str += "Количество зарегистрированных аккаунтов: " +
							ChatColor.GREEN + list.length + "\n";
			}
			
			str += a;
			
			lastInfoTime = System.currentTimeMillis( );
			lastInfoString = str;
			
			event.getPlayer( ).sendMessage( str );
		}
		catch( Exception exception )
		{
			event.getPlayer( ).sendMessage( ChatColor.RED + "Неизвестная ошибка" );
			worldName = "";
		}
	}
	
	private void processToggleChat( PlayerCommandPreprocessEvent event )
	{
		try
		{
			ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
			if( chatplayer != null )
			{
				if( chatplayer.toggleChat( ) )
					event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Enabled chat" );
				else
					event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Disabled chat" );
			}
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	private void processHelp( PlayerCommandPreprocessEvent event )
	{
		final String str =
				ChatColor.GOLD + "-----------------------------------------\n" +
				ChatColor.DARK_AQUA +
				"/kill - совершить суицид\n" +
				"/anon - написать анонимное сообщение\n" +
				"/ops - посмотреть список операторов\n" +
				"/coords chat - отправить свои координаты в чат\n" +
				"/coords send - отправить свои координаты игроку\n" +
				"/tell - отправить личное сообщение\n" +
				"/reply - ответить игроку\n" +
				"/ignore - скрыть сообщения игрока\n" +
				"/ignorelist - посмотреть список игроков которых вы скрыли\n" +
				"/info - посмотреть информацию о сервере\n" +
				"/town - сделать себе суффикс клана\n" +
				"/togglechat - включить/выключить для себя чат\n" +
				"/toggledeathmsgs - включить/выключить для себя сообщения о смерти\n" +
				//"/joindate (ник) - узнать дату первого захода (ник) на сервер\n" +
				"/tab <текст> - задать текст в табе\n" +
				"/tabco <цвет> - задать цвет надписи HistoryMC в табе\n" +
				//"/safe - телепортация в рандомное место (работает только если вы застряли " +
				//"в бедроке на спавне)\n" +
				ChatColor.GOLD + "-----------------------------------------";
		
		event.getPlayer( ).sendMessage( str );
	}
	
	private void processSafe( PlayerCommandPreprocessEvent event )
	{
		Player player = event.getPlayer( );
		Location location = player.getLocation( );
		
		if( player.getWorld( ).getEnvironment( ) == Environment.NORMAL &&
			( location.getX( ) >= -2 && location.getX( ) <= 2 ) &&
			( location.getY( ) >= 62 && location.getY( ) <= 65 ) &&
			( location.getZ( ) >= -2 && location.getZ( ) <= 2 ) )
		{
			/*if( player.getBedSpawnLocation( ) != null &&
				player.getBedSpawnLocation( ).getY( ) > 1 )
				plugin.getUtils( ).teleport( player, player.getBedSpawnLocation( ) );
			else*/
				plugin.getUtils( ).teleport( player, plugin.getSafePositionHelper( ).getPosition( player ) );
			event.getPlayer( ).sendMessage( ChatColor.YELLOW + "Я надеюсь, что это помогло" );
		}
		else
			event.getPlayer( ).sendMessage( ChatColor.RED + "Это тебе не /rtp" );
	}
	
	private void processWithers( PlayerCommandPreprocessEvent event )
	{
		int withers = 0;
		
		for( World world : Bukkit.getServer( ).getWorlds( ) )
		{
			for( Chunk chunk : world.getLoadedChunks( ) )
			{
				for( Entity entity : chunk.getEntities( ) )
				{
					if( entity.getType( ) == EntityType.WITHER )
					{
						withers++;
						entity.remove( );
					}
				}
			}
		}
		
		event.getPlayer( ).sendMessage( ChatColor.YELLOW +
				"Убрано " + withers + " иссушителей(ь/я) (иссушители в непрогруженных чанках все еще на месте)" );
	}
	
	private void processIllegals( PlayerCommandPreprocessEvent event )
	{
		event.getPlayer( ).sendMessage( ChatColor.YELLOW +
				( plugin.getIllegalsHandler( ).toggle( )
				? "Enabled"
				: "Disabled" ) + " AntiIllegals" );
	}
	
	private void processInfiniteBolt( PlayerCommandPreprocessEvent event )
	{
		event.getPlayer( ).sendMessage( ChatColor.YELLOW +
				( plugin.getInfiniteBoltManager( ).toggle( )
				? "Enabled"
				: "Disabled" ) + " InfiniteBolt" );
	}
	
	private void processJoinDate( PlayerCommandPreprocessEvent event )
	{
		String[ ] split = event.getMessage( ).split( " " );
		
		try
		{
			if( split.length <= 1 )
			{
				long joindate = event.getPlayer( ).getFirstPlayed( );
				if( joindate > 0 )
				{
					String date = plugin.getUtils( ).convertJoinDateToString( joindate );
					
					event.getPlayer( ).sendMessage( ChatColor.YELLOW +
							"Your join date is " + date );
				}
			}
			else
			{
				String name = split[ 1 ];
				if( name == null ) return;
				
				OfflinePlayer player = Bukkit.getOfflinePlayer( name );
				if( player != null && player.getFirstPlayed( ) > 0 )
				{
					String date = plugin.getUtils( ).convertJoinDateToString( player.getFirstPlayed( ) );
					
					event.getPlayer( ).sendMessage( ChatColor.YELLOW +
							player.getName( ) + "'s join date is " + date );
				}
				else
					event.getPlayer( ).sendMessage( ChatColor.RED +
							"Couldn't find player " + name );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace( );
			event.getPlayer( ).sendMessage( ChatColor.RED + "Неизвестная ошибка" );
		}
	}
	
	private void processKill( PlayerCommandPreprocessEvent event )
	{
		Player player = event.getPlayer( );
		if( player.isSleeping( ) ) return;
		
		if( player.isInsideVehicle( ) || player.getVehicle( ) != null )
		{
			player.leaveVehicle( );
			player.getVehicle( ).eject( );
		}
		
		// i feel like this is going to fuck something up...
		event.getPlayer( ).setHealth( 0 );
	}
	
	private void processDeathMessages( PlayerCommandPreprocessEvent event )
	{
		Player player = event.getPlayer( );
		
		player.sendMessage( ChatColor.YELLOW +
				( plugin.getDeathMessagesHandler( ).toggle( player.getName( ) )
				? "Death messages are now enabled"
				: "Death messages are now disabled" ) );
	}
}
