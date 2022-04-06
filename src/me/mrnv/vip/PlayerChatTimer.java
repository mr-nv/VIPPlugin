package me.mrnv.vip;

import java.util.ArrayList;
import java.util.List;

public class PlayerChatTimer
{
	private String player;
	private long timer;
	private List< String > messages;
	
	public PlayerChatTimer( String player )
	{
		this.player = player;
		this.timer = System.currentTimeMillis( );
		this.messages = new ArrayList( );
	}
	
	public String getPlayer( )
	{
		return player;
	}
	
	public long getTimer( )
	{
		return timer;
	}
	
	public List< String > getMessages( )
	{
		return messages;
	}
	
	public void setTimer( long timer )
	{
		this.timer = timer;
	}
	
	public void addMessage( String message )
	{
		if( messages.size( ) > 5 )
			messages.remove( 0 );
		
		messages.add( message );
	}
	
	public void clearMessages( )
	{
		messages.clear( );
	}
	
	public void reset( )
	{
		setTimer( 0 );
		/*if( getMessages( ) != null && getMessages( ).size( ) > 0 )
			clearMessages( );*/
	}
}
