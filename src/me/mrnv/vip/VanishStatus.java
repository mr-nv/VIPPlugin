package me.mrnv.vip;

public class VanishStatus
{
	private String player;
	private boolean vanished;
	
	public VanishStatus( String player )
	{
		this.player = player;
		this.vanished = false;
	}
	
	public String getPlayer( )
	{
		return player;
	}
	
	public boolean isVanished( )
	{
		return vanished;
	}
	
	public void setVanished( boolean vanished )
	{
		this.vanished = vanished;
	}
}
