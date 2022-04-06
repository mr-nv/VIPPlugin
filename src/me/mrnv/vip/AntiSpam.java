package me.mrnv.vip;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerChatEvent;

import org.apache.commons.lang.StringUtils;

import me.mrnv.vip.PlayerChatTimer;
import net.md_5.bungee.api.ChatColor;

public class AntiSpam
{
	private VIPPlugin plugin;
	//private PlayerChatTimer[ ] playerTimers = new PlayerChatTimer[ ]{ };
	//public final ArrayList< ChatPlayer > chatplayerlist = new ArrayList( );
	private final ArrayList< PlayerChatTimer > playerTimers = new ArrayList( );
	
	public AntiSpam( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public boolean processChat( String message, Player player )
	{
		try
		{
			PlayerChatTimer playertimer = getPlayerTimer( player.getName( ) );
			if( playertimer != null )
			{
				if( playertimer.getTimer( ) == 0 )
				{
					//playertimer.reset( );
					playertimer.setTimer( System.currentTimeMillis( ) );
				}
				
				playertimer.addMessage( message );
		
				boolean shouldkick = false;

				if( playertimer.getMessages( ) != null &&
					playertimer.getMessages( ).size( ) >= 3 )
				{
					if( playertimer.getTimer( ) + 5000 < System.currentTimeMillis( ) &&
						playertimer.getMessages( ).size( ) >= 4 )
						shouldkick = true;
					
					if( !shouldkick )
					{
						double diff1 = getStringSimilarity( playertimer.getMessages( ).get( 0 ), playertimer.getMessages( ).get( 1 ) );
						double diff2 = getStringSimilarity( playertimer.getMessages( ).get( 1 ), playertimer.getMessages( ).get( 2 ) );
					
						//plugin.getUtils().broadcast( "diff1 -> " + diff1 + " | diff2 -> " + diff2 );
						if( diff1 >= 0.6 && diff2 >= 0.6 )
							shouldkick = true;
					}
				}
				
				if( shouldkick )
				{
					player.kickPlayer( "Stop spamming" );
					playertimer.clearMessages( );
					return true;
				}
			}
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
		
		return false;
	}
	
	public void addTask( )
	{
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				try
				{
					for( PlayerChatTimer playertimer : playerTimers )
					{
						if( playertimer != null )
						{
							if( playertimer.getTimer( ) + 5000 < System.currentTimeMillis( ) )
								playertimer.reset( );
							
							/*boolean shouldkick = false;

							if( playertimer.getMessages( ) != null &&
								playertimer.getMessages( ).size( ) >= 3 )
							{
								if( playertimer.getTimer( ) + 6000 < System.currentTimeMillis( ) &&
									playertimer.getMessages( ).size( ) >= 4 )
									shouldkick = true;
								
								if( !shouldkick )
								{
									double diff1 = getStringSimilarity( playertimer.getMessages( ).get( 0 ), playertimer.getMessages( ).get( 1 ) );
									double diff2 = getStringSimilarity( playertimer.getMessages( ).get( 1 ), playertimer.getMessages( ).get( 2 ) );
								
									plugin.getUtils().broadcast( "diff1 -> " + diff1 + " | diff2 -> " + diff2 );
									if( diff1 >= 0.55 && diff2 >= 0.55 )
										shouldkick = true;
								}
							}
							
							if( shouldkick )
							{
								Player player = Bukkit.getPlayerExact( playertimer.getPlayer( ) );
								if( player != null )
								{
									playertimer.clearMessages( );
									player.kickPlayer( "Stop spamming" );
								}
							}*/
						}
					}
				}
				catch( Exception exception )
				{
					exception.printStackTrace( );
				}
			}
		}, 20, 5 );
	}
	
	private PlayerChatTimer getPlayerTimer( String player )
	{
		/*
		 * for( ChatPlayer chatplayer : chatplayerlist )
		{
			if( chatplayer.getName( ).equals( player.getName( ) ) )
				return chatplayer;
		}
		
		ChatPlayer chatplayer = new ChatPlayer( this, player );
		chatplayerlist.add( chatplayer );
		return chatplayer;
		 */
		for( PlayerChatTimer playertimer : playerTimers )
		{
			if( playertimer.getPlayer( ).equals( player ) )
				return playertimer;
		}
		
		PlayerChatTimer playertimer = new PlayerChatTimer( player );
		playerTimers.add( playertimer );
		return playertimer;
	}
	
	private static double getStringSimilarity( String s1, String s2 )
	{
		String longer = s1, shorter = s2;
		if( s1.length( ) < s2.length( ) )
		{
			// longer should always have greater length
			longer = s2;
			shorter = s1;
		}
			
		int longerLength = longer.length( );
		if( longerLength == 0 )
			return 1.0;
		
		return( longerLength - StringUtils.getLevenshteinDistance( longer, shorter ) ) / ( double )longerLength;
	}
	
	public ArrayList< PlayerChatTimer > getPlayerTimers( )
	{
		return playerTimers;
	}
}
