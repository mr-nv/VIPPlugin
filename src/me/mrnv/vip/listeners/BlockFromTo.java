package me.mrnv.vip.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromTo implements Listener
{
	@EventHandler
	public void onBlockFromTo( BlockFromToEvent event )
	{
		if( event.getBlock( ).getType( ) == Material.DRAGON_EGG )
		{
			if( event.getToBlock( ).getType( ) == Material.ENDER_PORTAL ||
				event.getToBlock( ).getType( ) == Material.ENDER_PORTAL_FRAME )
				event.setCancelled( true );
		}
	}
}
