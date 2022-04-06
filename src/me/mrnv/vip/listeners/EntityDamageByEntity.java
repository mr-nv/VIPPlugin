package me.mrnv.vip.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.mrnv.vip.VIPPlugin;

public class EntityDamageByEntity implements Listener
{
	private VIPPlugin plugin;
	
	public EntityDamageByEntity( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamage( EntityDamageByEntityEvent event )
	{
		if( !( event.getDamager( ) instanceof Player ) ) return;
		
		Player attacker = ( Player )event.getDamager( );
		if( attacker == null ) return;
		
		ItemStack item = attacker.getItemInHand( );
		if( item != null )
		{
			if( plugin.getIllegalsHandler( ).checkItem( item ) )
				event.setCancelled( true );
		}
	}
}
