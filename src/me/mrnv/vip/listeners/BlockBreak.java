package me.mrnv.vip.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener
{
	@EventHandler
	public void onBlockBreak( BlockBreakEvent event )
	{
		if( event.getBlock( ).getType( ) == Material.ENDER_PORTAL ||
			event.getBlock( ).getType( ) == Material.ENDER_PORTAL_FRAME )
			event.setCancelled( true );
	}
}
