package me.mrnv.vip.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class EntityPortal implements Listener
{
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onEntityPortal( EntityPortalEvent event )
	{
		// minecart dupe easy fix!!!!!
		//event.setCancelled( true );
	}
}
