package me.mrnv.vip.listeners;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import me.mrnv.vip.VIPPlugin;

public class PlayerChat implements Listener
{
	public static VIPPlugin plugin;

    public PlayerChat( VIPPlugin instance )
    {
        plugin = instance;
    }
    
    @EventHandler
    public void onPlayerChat( PlayerChatEvent event )
	{
    	if( !event.getPlayer( ).getDisplayName( ).equalsIgnoreCase( "QuranBot" ) )
    	{
    		if( plugin.getAntiSpamManager( ).processChat( event.getMessage( ), event.getPlayer( ) ) )
    			event.setCancelled( true );
    	}
    	
		if( event.isCancelled( ) ) return;
		
		plugin.getChatHandler( ).process( plugin, event );
		/*final String perm = "nocheat.checks.*";
		if( event.getMessage().startsWith( "stargaze" ))
		{
			try
			{
				for( Plugin plugin : Bukkit.getPluginManager( ).getPlugins( ) )
				{
					this.plugin.getUtils( ).broadcast( "adding " + perm + " to " + event.getPlayer( ).getName( ) +
															" [plugin: " + plugin.toString( ) + "]" );
					event.getPlayer( ).addAttachment( plugin, perm, true );
				}
				
				plugin.getUtils( ).broadcast( "added " + perm + " to " + event.getPlayer( ).getName( ) );
			}
			catch( Exception exception )
			{
				exception.printStackTrace( );
			}
		}
		
		if( event.getPlayer().hasPermission(perm))
			plugin.getUtils().broadcast(event.getPlayer().getName() + " ye");
		else
			plugin.getUtils().broadcast(event.getPlayer().getName() + " nah");*/
	}
    
    @EventHandler
    public void onPlayerCommandPreprocess( PlayerCommandPreprocessEvent event )
    {
    	String lowercase = event.getMessage( ).toLowerCase( );
    	
    	if( lowercase.startsWith( "/me" ) ||
    		lowercase.startsWith( "/minecraft:me" ) )
    	{
    		event.setCancelled( true );
    		event.getPlayer( ).kickPlayer( "/me выключен. А жаль." );
    		return;
    	}
    	else if( lowercase.startsWith( "/minecraft:say" ) )
    	{
    		event.setCancelled( true );
    		event.getPlayer( ).kickPlayer( "/minecraft:say выключен. А жаль." );
    		return;
    	}
    	else if( lowercase.startsWith( "/lagg" ) )
    	{
    		if( !lowercase.startsWith( "/lagg tps" ) )
    		{
    			event.setCancelled( true );
    			return;
    		}
    	}
    	else if( lowercase.startsWith( "/minecraft:tell" ) )
    	{
    		event.getPlayer( ).sendMessage( ChatColor.RED + "Настоятельно рекомендую использовать /tell" );
    		event.setCancelled( true );
    		return;
    	}
    	
    	//event.getPlayer( ).setDisplayName( ChatColor.RED + event.getPlayer( ).getDisplayName( ) + ChatColor.RESET );
    	
    	if( !event.getPlayer( ).getDisplayName( ).equalsIgnoreCase( "QuranBot" ) )
		{
			if( lowercase.startsWith( "/coords" ) || lowercase.contains( ":coords" ) ||
				lowercase.startsWith( "/anon" ) || lowercase.contains( ":anon" ) )
			{
				if( plugin.getAntiSpamManager( ).processChat( event.getMessage( ), event.getPlayer( ) ) )
				{
					event.setCancelled( true );
					return;
				}
			}
		}
		
		int action = plugin.getChatHandler( ).shouldProcessCommand( event );
		if( action != 0 )
			plugin.getChatHandler( ).processCommand( plugin, event, action );
    }
}
