package me.mrnv.vip.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.mrnv.vip.VIPPlugin;

public class Illegals
{
	private VIPPlugin plugin;
	private boolean toggled = true;
	private List< Material > blacklist = new ArrayList< Material >( );

	public Illegals( VIPPlugin plugin )
	{
		this.plugin = plugin;
		this.toggled = true;
		
		// blacklist
		blacklist.add( Material.BEDROCK );
		blacklist.add( Material.FIRE );
		blacklist.add( Material.PORTAL );
		blacklist.add( Material.MONSTER_EGG );
		blacklist.add( Material.MOB_SPAWNER );
		blacklist.add( Material.ENDER_PORTAL_FRAME );
		blacklist.add( Material.ENDER_PORTAL );
		blacklist.add( Material.WATER );
		blacklist.add( Material.LAVA );
		blacklist.add( Material.COMMAND );
		//blacklist.add( Material.BLOCK )
		//blacklist.add( Material.LOCK );
		blacklist.add( Material.STATIONARY_LAVA );
		blacklist.add( Material.STATIONARY_WATER );
		blacklist.add( Material.BARRIER );
	}
	
	public void loadConfig( )
	{
		try
		{
			this.toggled = plugin.getConfig( ).getBoolean( "config.anti-illegals" );
		}
		catch( Exception exception )
		{
			this.toggled = true;
			//exception.printStackTrace( );
		}
	}
	
	public boolean handleIllegals( Inventory inv )
	{
		if( !this.toggled ) return false;
		
		boolean ret = false;
		
		if( inv instanceof PlayerInventory )
		{
			PlayerInventory playerinv = ( PlayerInventory )inv;
			for( int i = 0; i < playerinv.getArmorContents( ).length; i++ )
			{
				ItemStack item = playerinv.getArmorContents( )[ i ];
				if( item == null ) continue;
				
				for( Enchantment ench : Enchantment.values( ) )
				{
					if( ench == null ) continue;
					
					if( item.containsEnchantment( ench ) )
					{
						if( item.getEnchantmentLevel( ench ) > ench.getMaxLevel( ) ||
							!ench.canEnchantItem( item ) )
						{
							item.removeEnchantment( ench );
							ret = true;
						}
					}
				}
			}
		}
		
		for( int i = 0; i < inv.getContents( ).length; i++ )
		{
			ItemStack item = inv.getItem( i );
			if( item == null ) continue;
			
			/*String s = checkSkull( item );
			Bukkit.broadcastMessage( s );*/
			//if( !s.equals( "-1" ) )
				//plugin.getUtils().broadcast( s );
			
			if( checkItem( item ) || checkSkull( item ) )
			{
				inv.removeItem( item );
				ret = true;
				continue;
			}
		}
		
		return ret;
	}
	
	public boolean isEnabled( )
	{
		return toggled;
	}
	
	public boolean toggle( )
	{
		try
		{
			toggled = !toggled;
			plugin.getConfig( ).set( "config.anti-illegals", toggled );
			return toggled;
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
		
		return toggled;
	}
	
	public boolean checkItem( ItemStack item )
	{
		if( !toggled ) return false;
		
		for( Material blacklistedMaterial : blacklist )
		{
			if( item.getType( ) == blacklistedMaterial )
				return true;
		}
		
		return ( ( item.getAmount( ) > item.getMaxStackSize( ) ) ||
				 checkItemEnchants( item ) );
	}
	
	public boolean checkSkull( ItemStack item )
	{
		if( item.getType( ) != Material.SKULL_ITEM ) return false;
		
		// https://bukkit.org/threads/set-skull-types.130864/
		// creeper is 4, player 3, zombie 2, wither 1 and skeleton 0
		return item.getDurability( ) == 3;
	}
	
	public boolean checkBlock( Block block )
	{
		if( !toggled ) return false;
		
		for( Material blacklistedMaterial : blacklist )
		{
			if( block.getType( ) == blacklistedMaterial )
				return true;
		}
		
		return false;
	}
	
	public boolean checkItemEnchants( ItemStack item )
	{
		if( !toggled ) return false;
		if( !( item.hasItemMeta( ) && item.getItemMeta( ).hasEnchants( ) ) ) return false;
		
		ItemMeta itemmeta = item.getItemMeta( );
		
		Map< Enchantment, Integer > enchantments =
					( itemmeta.getEnchants( ) != null )
					? itemmeta.getEnchants( )
					: item.getEnchantments( );

		if( enchantments != null )
		{
			for( Enchantment ench : Enchantment.values( ) )
			{
				if( ench == null ) continue;
				
				if( enchantments.containsKey( ench ) )
				{
					if( item.getEnchantmentLevel( ench ) > ench.getMaxLevel( ) ||
						!ench.canEnchantItem( item ) )
					{
						if( !ench.canEnchantItem( item ) )
							item.removeEnchantment( ench );
						else
							item.addEnchantment( ench, ench.getMaxLevel( ) );
					}
				}
			}
		}
		
		return false;
	}
}
