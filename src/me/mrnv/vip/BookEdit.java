package me.mrnv.vip;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.nio.charset.Charset;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.StreamSerializer;

public class BookEdit implements Listener
{
	private VIPPlugin plugin;
	private boolean initialized = false;
	
	public BookEdit( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void init( )
	{
		if( initialized ) return;
		
		initialized = true;
		
		final StreamSerializer serializer = new StreamSerializer( );
		
		ProtocolLibrary.getProtocolManager( ).addPacketListener(
		new PacketAdapter( plugin, ConnectionSide.CLIENT_SIDE, Packets.Server.CUSTOM_PAYLOAD )
		{
			@Override
			public void onPacketReceiving( PacketEvent event )
			{
				try
				{
					String tag = event.getPacket( ).getStrings( ).read( 0 );
					byte[ ] data = event.getPacket( ).getByteArrays( ).read( 0 );
					
					if( tag.equals( "MC|BEdit" ) ||
						tag.equals( "MC|BSign" ) )
					{
						ItemStack book = serializer.deserializeItemStack( getInput( data ) );
						
						if( !Charset.forName( "ISO-8859-1" ).newEncoder( ).canEncode( book.toString( ) ) )
							event.setCancelled( true );
					}
				}
				catch( Exception e )
				{
					e.printStackTrace( );
				}
			}
		} );
	}
	
	public boolean isInitialized( )
	{
		return initialized;
	}
	
	private DataInputStream getInput( byte[ ] data )
	{
        return new DataInputStream( new ByteArrayInputStream( data ) );
    }
}
