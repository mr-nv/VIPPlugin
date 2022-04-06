package me.mrnv.vip;

public class DeathMessage
{
	private String player;
	private String message;
	private long time;
	
	public DeathMessage( String player )
	{
		this.player = player;
		this.message = "";
		this.time = System.currentTimeMillis( );
	}
	
	public String getPlayer( )
	{
		return player;
	}
	
	public void setMessage( String message )
	{
		this.message = message;
	}
	
	public String getMessage( )
	{
		return message;
	}
	
	public long getTime( )
	{
		return time;
	}
}
