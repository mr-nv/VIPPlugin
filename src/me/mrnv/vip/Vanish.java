package me.mrnv.vip;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Vanish
{
	private VIPPlugin plugin;
	private ArrayList< VanishStatus > vanishData = new ArrayList( );
	
	public Vanish( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void toggle( PlayerCommandPreprocessEvent event )
	{
		try
		{
			VanishStatus status = getVanishStatus( event.getPlayer( ).getName( ) );
			if( status != null )
			{
				status.setVanished( !status.isVanished( ) );
				
				for( Player player : Bukkit.getOnlinePlayers( ) )
				{
					if( player.getName( ) != status.getPlayer( ) &&
						!player.isOp( ) )
						setVanish( event.getPlayer( ), player, status.isVanished( ) );
				}
				
				event.getPlayer( ).sendMessage( ChatColor.YELLOW +
						( status.isVanished( )
						? "Vanish enabled"
						: "Vanish disabled" ) );
			}
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	public void onPlayerJoin( PlayerJoinEvent event )
	{
		for( VanishStatus status : vanishData )
		{
			if( status.isVanished( ) )
			{
				Player player = Bukkit.getPlayerExact( status.getPlayer( ) );
				if( player != null )
				{
					if( event.getPlayer( ).getName( ).equals( status.getPlayer( ) ) )
					{
						for( Player onlineplayer : Bukkit.getOnlinePlayers( ) )
						{
							if( !onlineplayer.getName( ).equals( status.getPlayer( ) ) &&
								!onlineplayer.isOp( ) )
								onlineplayer.hidePlayer( player );
						}
					}
					else
					{
						if( !event.getPlayer( ).isOp( ) )
							event.getPlayer( ).hidePlayer( player );
					}
				}
			}
		}
	}
	
	private void setVanish( Player vanishedplayer, Player player, boolean state )
	{
		if( state )
			player.hidePlayer( vanishedplayer );
		else
			player.showPlayer( vanishedplayer );
	}
	
	private VanishStatus getVanishStatus( String player )
	{
		for( VanishStatus status : vanishData )
		{
			if( status.getPlayer( ).equals( player ) )
				return status;
		}
		
		VanishStatus status = new VanishStatus( player );
		vanishData.add( status );
		return status;
	}
}
