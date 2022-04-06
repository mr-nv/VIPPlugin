package me.mrnv.vip.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.mrnv.vip.VIPPlugin;

public class PlayerInteract implements Listener
{
	private VIPPlugin plugin;
	
	public PlayerInteract( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract( PlayerInteractEvent event )
	{
		if( event.getAction( ) != Action.RIGHT_CLICK_BLOCK ) return;
	
		Block block = event.getClickedBlock( );
		if( block.getState( ) instanceof Chest )
		{
			Chest chest = ( Chest )block.getState( );
			if( chest != null )
			{
				Inventory contents = chest.getInventory( );
				if( contents != null )
				{
					if( plugin.getIllegalsHandler( ).handleIllegals( contents ) )
						chest.update( );
				}
			}
		}
		else if( block.getState( ) instanceof Dispenser )
		{
			Dispenser dispenser = ( Dispenser )block.getState( );
			if( dispenser != null )
			{
				Inventory contents = dispenser.getInventory( );
				if( contents != null )
				{
					if( plugin.getIllegalsHandler( ).handleIllegals( contents ) )
						dispenser.update( );
				}
			}
		}
		else if( block.getState( ) instanceof Furnace )
		{
			Furnace furnace = ( Furnace )block.getState( );
			if( furnace != null )
			{
				Inventory contents = furnace.getInventory( );
				if( contents != null )
				{
					if( plugin.getIllegalsHandler( ).handleIllegals( contents ) )
						furnace.update( );
				}
			}
		}
		/*else if( block.getState( ) instanceof StorageMinecart )
		{
			StorageMinecart minecart = ( StorageMinecart )block.getState( );
			if( minecart != null )
			{
				Inventory contents = minecart.getInventory( );
				if( contents != null )
					plugin.getIllegalsHandler( ).handleIllegals( contents );
			}
		}*/
	}
}
