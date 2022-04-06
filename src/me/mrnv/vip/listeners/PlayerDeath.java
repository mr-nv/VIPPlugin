package me.mrnv.vip.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.mrnv.vip.VIPPlugin;

public class PlayerDeath implements Listener
{
	private VIPPlugin plugin;
	
	public PlayerDeath( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath( PlayerDeathEvent event )
	{
		if( event.getEntity( ) instanceof Player )
		{
			/*for( ChatColor cl : ChatColor.values( ) )
				event.getEntity( ).sendMessage( cl + cl.name( ) );*/
			
			String deathmsg = ChatColor.DARK_RED + event.getDeathMessage( );

			Player player = event.getEntity( );
			if( player == null ) return;
			if( player.getName( ).equals( "QuranBot" ) )
			{
				event.setDeathMessage( null );
				return;
			}
			
			Player killer = player.getKiller( );
			if( killer != null )
			{
				ItemStack item = killer.getItemInHand( );
				if( item != null && item.hasItemMeta( ) )
				{
					try
					{
						String itemname = item.getItemMeta( ).getDisplayName( );
						if( itemname != null && !itemname.equals( "" ) )
						{
							deathmsg = deathmsg.replaceAll( itemname,
									ChatColor.GOLD + itemname + ChatColor.DARK_RED );
						}
					}
					catch( Exception e )
					{
						e.printStackTrace( );
					}
				}
				
				if( deathmsg.lastIndexOf( " " + killer.getName( ) ) > -1 )
				{
					if( deathmsg.endsWith( " " + killer.getName( ) ) )
						deathmsg = deathmsg.replaceAll( " " + killer.getName( ),
								ChatColor.DARK_AQUA + " " + killer.getName( ) );
				}
				
				deathmsg = deathmsg.replaceAll( ChatColor.DARK_RED + killer.getName( ),
						ChatColor.DARK_AQUA + killer.getName( ) + ChatColor.DARK_RED );
				
				deathmsg = deathmsg.replaceAll( killer.getName( ) + " ",
						ChatColor.DARK_AQUA + killer.getName( ) + " " + ChatColor.DARK_RED );
			}
			
			deathmsg = deathmsg.replace( player.getName( ) + " ",
					ChatColor.DARK_AQUA + player.getName( ) + " " + ChatColor.DARK_RED );
			
			deathmsg = deathmsg.replace( ChatColor.DARK_RED + player.getName( ),
					ChatColor.DARK_AQUA + player.getName( ) + ChatColor.DARK_RED );
			
			event.setDeathMessage( null );
			plugin.getDeathMessagesHandler( ).send( player.getName( ), deathmsg );
		}
	}
}
