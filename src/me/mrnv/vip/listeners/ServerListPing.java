package me.mrnv.vip.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPing implements Listener
{
	private List< String > messages = new ArrayList< String >( );
	private Random random;
	
	public ServerListPing( )
	{
		random = new Random( );
		
		messages.add( "Political server with no rules" );
		messages.add( ChatColor.DARK_RED + "Nobody is safe" );
		messages.add( ChatColor.GREEN + "> Diamond Fish" );
		messages.add( ChatColor.GREEN + ">greentext" );
		messages.add( "kiev)" );
		messages.add( "we need MORE cp hentai maparts" );
		messages.add( "CKOPO - Minecart Dupe Fix" );
		messages.add( ChatColor.DARK_RED + "Vam zdes' ne rady" );
		messages.add( "sponsored by ISIS" );
		messages.add( "1.7.10 zavtra" );
		messages.add( ChatColor.GREEN + "vk.com/hist0rymc" );
		messages.add( "Kommunisty ushli..." );
		messages.add( "ADMIN YKPAL MOI BEDROCK" );
		messages.add( ChatColor.GREEN + "> admins with autism ONLY" );
		messages.add( "/safe /safe /safe /safe" );
		messages.add( "ya russkiy (zdal na 3)" );
		messages.add( ChatColor.DARK_RED + "/removewithers" );
		messages.add( "TeamSpeak > Discord" );
		messages.add( ChatColor.GOLD + "< Ebanniy baran odnokletochniy" );
		messages.add( "/coords chat" );
		messages.add( ChatColor.GREEN + "> Andrew Riber" );
		messages.add( "/kill cows - easy eda" );
		messages.add( "The Youngest Political Server in MC" );
		messages.add( "rockeZZergon is still angry" );
		messages.add( "i terrain exploited u irl see u soon" );
		messages.add( ChatColor.DARK_RED + "Can't reach server" );
		messages.add( ChatColor.GREEN + "> wipe in 7 days" );
		messages.add( ChatColor.GOLD + "Position in queue: " + ChatColor.BOLD + "0" );
		messages.add( "3ToT CePBeP ToJIbKo Dl9 PyccKuX" );
		messages.add( "RASSLABYXA.RU" );
		messages.add( "sponsored by RASSLABYXA.RU" );
		messages.add( ChatColor.GREEN + "> PyccKue EbyT Bcex" );
		messages.add( ChatColor.GREEN + ">/login 13579root" );
		messages.add( "govno huy zhopa" );
		messages.add( "Sharpness enchantment.level.32767" );
		messages.add( "sponsored by GDZPUTINA.RU" );
		messages.add( "Ezzz mad" );
		messages.add( ChatColor.GOLD + "/op Lemoliam" );
		//messages.add( ChatColor.GREEN + "> ADMINU 13 LET" );
		messages.add( "lavacast delaet pshhhhhh" );
		messages.add( "NO U CANT COPY 2B2T!!!!!!!!!!!" );
		messages.add( "hueta dlya debilov" );
		messages.add( "NAHUY elytra NAHUY totem of undying" );
		messages.add( ChatColor.DARK_RED + "vas zabanyat" );
		messages.add( ChatColor.DARK_RED + "vas kiknyt" );
		messages.add( ChatColor.RED + "foot fetish on top" );
		messages.add( "NO FUN ALLOWED" );
		messages.add( "Crossdressers only server" );
		messages.add( "/backdoormoneymoneymoneymoney" );
		messages.add( "AutoBedBomb " + ChatColor.GREEN + "enabled" );
		
		messages.add( ChatColor.DARK_RED.toString( ) + ChatColor.MAGIC + "everybody" +
				ChatColor.RESET + ChatColor.DARK_AQUA + " died" );
		
		// hehe
		for( int i = 0; i < 3; i++ )
		{
			int x = random.nextInt( 20000 );
			if( random.nextInt( 6 ) <= 3 )
				x = -x;
			
			int z = random.nextInt( 20000 );
			if( random.nextInt( 6 ) <= 3 )
				z = -z;
			
			String msg = String.format( "X: %d | Y: %d | Z: %d :^)", x, random.nextInt( 100 ), z );
			messages.add( msg );
		}
		
		messages.add( ChatColor.WHITE + "X: " +
				ChatColor.GREEN + "-699988 " +
				ChatColor.WHITE + "Y: " +
				ChatColor.GREEN + "15 " +
				ChatColor.WHITE + "Z: " +
				ChatColor.GREEN + "-44 | West" );
	}
	
	@EventHandler
	public void onPlayerListPingEvent( ServerListPingEvent event )
	{
		event.setMotd( ChatColor.YELLOW + getRandomMOTD( ) );
	}
	
	private String getRandomMOTD( )
	{
		//return "HistoryMC - TEMP MAP";
		return messages.get( random.nextInt( messages.size( ) ) );
	}
}
