package me.mrnv.vip.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.mrnv.vip.VIPPlugin;

public class BlockPlace implements Listener
{
	private static VIPPlugin plugin;
	
	public BlockPlace( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockPlace( BlockPlaceEvent event )
	{
		plugin.getEndPortalManager( ).onBlockPlace( event );
		
		if( !event.isCancelled( ) )
		{
			if( plugin.getIllegalsHandler( ).checkBlock( event.getBlock( ) ) )
			{
				try
				{
					ItemStack playeritem = event.getPlayer( ).getItemInHand( );
					if( playeritem != null )
					{
						if( playeritem.getType( ).equals( Material.FLINT_AND_STEEL ) ||
							playeritem.getType( ).equals( Material.FIREBALL ) )
							return;
					}
					
					event.setCancelled( true );
				}
				catch( Exception e )
				{
					e.printStackTrace( );
					event.setCancelled( true );
				}
			}
		}
	}
}
