package me.mrnv.vip.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

import me.mrnv.vip.VIPPlugin;

public class PlayerInteractEntity implements Listener
{
	private VIPPlugin plugin;
	
	public PlayerInteractEntity( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteractEntity( PlayerInteractEntityEvent event )
	{
		Entity entity = event.getRightClicked( );
		if( entity != null )
		{
			if( entity.getType( ) == EntityType.MINECART ||
				entity.getType( ) == EntityType.MINECART_HOPPER )
			{
				try
				{
					StorageMinecart minecart = ( StorageMinecart )entity;
					if( minecart != null )
					{
						Inventory inventory = minecart.getInventory( );
						if( inventory != null )
							plugin.getIllegalsHandler( ).handleIllegals( inventory );
					}
				}
				catch( Exception exception )
				{
					
				}
			}
		}
	}
}
