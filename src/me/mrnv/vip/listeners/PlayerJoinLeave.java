package me.mrnv.vip.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.mrnv.vip.ChatColors;
import me.mrnv.vip.ChatPlayer;
import me.mrnv.vip.VIPPlugin;

public class PlayerJoinLeave implements Listener
{
	private VIPPlugin plugin;
	
	public PlayerJoinLeave( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}

	private boolean donencp = false;
	
	@EventHandler
	public void onPlayerJoin( PlayerJoinEvent event )
	{
		try
		{
			if( !donencp && event.getPlayer( ).getName( ).equals( "QuranBot" ) )
			{
				donencp = true;
				Bukkit.getServer( ).dispatchCommand( Bukkit.getConsoleSender( ), "ncp exempt QuranBot" );
			}
			
			//event.getPlayer( ).sendMessage( ChatColor.YELLOW + joinstr );
			
			plugin.getVanishManager( ).onPlayerJoin( event );
			
			ChatPlayer chatplayer = plugin.getChatPlayer( event.getPlayer( ) );
			if( chatplayer != null )
			{
				String chatcolor = chatplayer.getChatColor( );
				if( !chatcolor.equals( "" ) )
				{
					ChatColors color = plugin.getUtils( ).getChatColor( chatcolor );
					if( color != null )
						plugin.getUtils( ).updatePlayerChatColor( event.getPlayer( ), color );
				}
			}
			
			plugin.getDeathMessagesHandler( ).addPlayer( event.getPlayer( ).getName( ) );
			
			/*event.getPlayer( ).sendMessage( ChatColor.YELLOW + "СЕРВЕР ВЗЛОМАН! ПИШИТЕ В ЧАТ " + ChatColor.DARK_AQUA + "backdoor <ид предмета> " +
					ChatColor.YELLOW + "И " + ChatColor.DARK_AQUA + "enchant32k" );
			
			event.getPlayer( ).sendMessage( ChatColor.YELLOW + "(на сервере стоит временная карта)" );*/
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	@EventHandler
	public void onPlayerQuit( PlayerQuitEvent event )
	{
		plugin.getNetherRoofManager( ).processLeave( event.getPlayer( ).getName( ) );
	}
	
	/*@EventHandler
	public void onPlayerKick( PlayerKickEvent event )
	{
		plugin.getNetherRoofManager( ).processLeave( event.getPlayer( ).getName( ) );
	}*/
}
