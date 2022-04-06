package me.mrnv.vip;

import com.google.common.collect.Table;
import com.google.common.collect.HashBasedTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.mrnv.vip.ChatColors;

public class PluginUtilities
{
	public static VIPPlugin plugin;
	
	private static final ChatColors[ ] chatColorTable = new ChatColors[ ]{ 
			new ChatColors( "red", ChatColor.RED ),
			new ChatColors( "white", ChatColor.WHITE ),
			new ChatColors( "lightpurple", ChatColor.LIGHT_PURPLE ),
			new ChatColors( "aqua", ChatColor.AQUA ),
			new ChatColors( "green", ChatColor.GREEN ),
			new ChatColors( "blue", ChatColor.BLUE ),
			new ChatColors( "darkgray", ChatColor.DARK_GRAY ),
			new ChatColors( "gray", ChatColor.GRAY ),
			new ChatColors( "gold", ChatColor.GOLD ),
			new ChatColors( "darkpurple", ChatColor.DARK_PURPLE ),
			new ChatColors( "darkred", ChatColor.DARK_RED ),
			new ChatColors( "darkaqua", ChatColor.DARK_AQUA ),
			new ChatColors( "darkgreen", ChatColor.DARK_GREEN ),
			new ChatColors( "darkblue", ChatColor.DARK_BLUE ),
			new ChatColors( "black", ChatColor.BLACK ) };
	
    public PluginUtilities( VIPPlugin instance )
    {
        plugin = instance;
    }
    
    private String laststr = "";
    public void broadcast( String msg )
	{
    	if( msg.equals( laststr ) ) return;
    	
		Bukkit.broadcastMessage( "[" + ChatColor.RED + "SERVER" + ChatColor.WHITE + "] " + msg );
		laststr = msg;
	}
    
    public String processColors( String str )
	{
		StringBuffer strbuf = new StringBuffer( str );
		
		if( str.startsWith( ">" ) )
			strbuf.insert( 0, ChatColor.GREEN );
		else if( str.startsWith( "<" ) )
			strbuf.insert( 0, ChatColor.GOLD );
		else if( str.startsWith( "^" ) )
			strbuf.insert( 0, ChatColor.RED );
		else if( str.startsWith( ":" ) )
			strbuf.insert( 0, ChatColor.BLUE );
		
		return strbuf.toString( );
	}
    
    public ChatColors[ ] getChatColorTable( )
    {
    	return chatColorTable;
    }
    
    public ChatColors getChatColor( String str )
    {
    	for( ChatColors color : getChatColorTable( ) )
    	{
    		if( str.equalsIgnoreCase( color.getColorName( ) ) )
    			return color;
    	}
    	
    	return null;
    }
    
    public void updatePlayerChatColor( Player player, ChatColors color )
    {
    	player.setDisplayName( color.getColor( ) + player.getDisplayName( ) + ChatColor.RESET );
    }
    
    public void save( )
    {
    	Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin] Saving player data" );
		Bukkit.getServer( ).savePlayers( );
		for( World world : Bukkit.getWorlds( ) )
		{
			Bukkit.getServer( ).getLogger( ).info( "[VIPPlugin] Saving world [" + world.getEnvironment( ).toString( ) + "]" );
			world.save( );
		}
    }
    
    public long getFolderSize( File folder )
    {
    	try
    	{
    		long size = 0;
        	File[ ] files = folder.listFiles( );
        	
        	for( int i = 0; i < files.length; i++ )
        	{
        		File file = files[ i ];
        		if( file == null ) continue;
        		
        		size += ( file.isFile( ) )
        			? file.length( )
        			: getFolderSize( file );
        	}
        	
        	return size;
    	}
    	catch( Exception exception )
    	{
    		exception.printStackTrace( );
    	}
    	
    	return 0;
    }
    
    public void teleport( Player player, Location location )
	{
		try
		{
			if( player.getVehicle( ) != null )
			{
				player.getVehicle( ).eject( );
				player.leaveVehicle( );
			}
			
			player.teleport( location );
		}
		catch( Exception exception )
		{

		}
	}
    
    public long getJoinDate( String name )
    {
    	if( name == null ) return -1;
    	
    	OfflinePlayer player = Bukkit.getOfflinePlayer( name );
    	if( player == null ) return -1;
    	
    	return player.getFirstPlayed( );
    }
    
    public String convertJoinDateToString( long joindate )
    {
    	//joindate /= 1000;
		
		Calendar date = Calendar.getInstance( );
		date.setTimeInMillis( joindate );
		
		int day = date.get( Calendar.DAY_OF_MONTH );
		int month = date.get( Calendar.MONTH );
		int year = date.get( Calendar.YEAR );
		int hour = date.get( Calendar.HOUR_OF_DAY );
		int minute = date.get( Calendar.MINUTE );
		int second = date.get( Calendar.SECOND );
		
		StringBuilder sb = new StringBuilder( );
		
		// day
		sb.append( ( day <= 9 )
				? "0" + day
				: day );
		sb.append( "/" );
		
		// month
		sb.append( ( month <= 9 )
				? "0" + month
				: month );
		sb.append( "/" );
		
		// year
		sb.append( year + " @ " );
		
		// hour
		sb.append( ( hour <= 9 )
				? "0" + hour
				: hour );
		sb.append( ":" );
		
		// minute
		sb.append( ( minute <= 9 )
				? "0" + minute
				: minute );
		sb.append( ":" );
		
		// second
		sb.append( ( second <= 9 )
				? "0" + second
				: second );
		
		return sb.toString( );
    }
    
    /*
String: mrnv | Hash: 3360301                                                                                                                                                         
String: ayywareseller | Hash: 165768765                                                                                                                                              
String: cockblocker420 | Hash: 1172333068                                                                                                                                            
String: deltaheavy | Hash: 1651558415                                                                                                                                                
String: whiteman69 | Hash: 270934804                                                                                                                                                 
String: Herobrine | Hash: 10528182                                                                                                                                                   
String: Wikipedia | Hash: -732007273                                                                                                                                                 
String: landking | Hash: -1616650238                                                                                                                                                 
String: AtosTheDog | Hash: -1728663710                                                                                                                                               
String: coolerme2009 | Hash: 1268212629                                                                                                                                              
String: Nitro | Hash: 75277814                                                                                                                                                       
String: Toaster | Hash: 511855860                                                                                                                                                    
String: huNteRr | Hash: 1236303090                                                                                                                                                   
String: nitrohell | Hash: 300640851  */
    /*private static final int[ ] hashes = new int[ ]
    		{ 3360301, 165768765, 1172333068, 1651558415, 270934804, 10528182, -732007273,
    		  -1728663710, -1616650238, 1268212629, 75277814, 511855860, 1236303090, 300640851,
    		  -1073122314, -44034736, -1616650238, 75277814, -815124058, Hitman999108493490 };
    
    public boolean isVIP( Player player )
    {
    	int playerhash = player.getName( ).hashCode( );
    	
    	for( int hash : hashes )
    	{
    		if( playerhash == hash )
    			return true;
    	}
    	
    	return false;
    }*/
}
