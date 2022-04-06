package me.mrnv.vip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RestartManager
{
	private VIPPlugin plugin;
	// 15 minutes, 10 minutes, 5 minutes, 2 minutes
	private boolean[ ] announced = new boolean[ ]{ false, false, false, false };
	private long time;
	private int lastSecond;
	
	public RestartManager( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void reset( )
	{
		time = System.currentTimeMillis( ) + 21600000; // 2 hours
		for( int i = 0; i <= 3; i++ )
			announced[ i ] = false;
	}
	
	public void addTask( )
	{
		reset( );
		
		Bukkit.getScheduler( ).scheduleSyncRepeatingTask( plugin, new Runnable( )
		{
			public void run( )
			{
				long difference = ( time - System.currentTimeMillis( ) );
				
				if( !announced[ 0 ] )
				{
					if( difference <= 900000 ) // 15 minutes
					{
						plugin.getUtils( ).broadcast( "������������ ������� ����� 15 �����" );
						announced[ 0 ] = true;
					}
				}
				else if( !announced[ 1 ] )
				{
					if( difference <= 600000 ) // 10 minutes
					{
						plugin.getUtils( ).broadcast( "������������ ������� ����� 10 �����" );
						announced[ 1 ] = true;
					}
				}
				else if( !announced[ 2 ] )
				{
					if( difference <= 300000 ) // 5 minutes
					{
						plugin.getUtils( ).broadcast( "������������ ������� ����� 5 �����" );
						announced[ 2 ] = true;
					}
				}
				else if( !announced[ 3 ] )
				{
					if( difference <= 120000 ) // 2 minutes
					{
						plugin.getUtils( ).broadcast( "������������ ������� ����� 2 ������" );
						announced[ 3 ] = true;
					}
				}
				else
				{
					if( difference < 1000 )
					{
						/*for( Player player : Bukkit.getOnlinePlayers( ) )
							player.kickPlayer( ChatColor.YELLOW + "Server is restarting" );
						
						Bukkit.getScheduler( ).cancelAllTasks( );
						Bukkit.getServer( ).shutdown( );
						
						return;*/
						
						for( int i = 0; i <= 1; i++)
							for( Player player : Bukkit.getOnlinePlayers( ) )
								player.kickPlayer( ChatColor.YELLOW + "Server is restarting" );
						
						Bukkit.getServer( ).dispatchCommand( Bukkit.getConsoleSender( ), "stop" );
						reset( );
						return;
					}
					
					if( difference < 16000 )
					{
						int seconds = Math.round( ( difference / 1000 ) );
						if( seconds != lastSecond )
						{
							switch( seconds )
							{
							case 4:
							case 3:
							case 2:
								plugin.getUtils( ).broadcast( "������������ ������� ����� " +
										seconds + " �������" );
								break;
							case 1:
								plugin.getUtils( ).broadcast( "������������ ������� ����� 1 �������" );
								break;
							default:
								plugin.getUtils( ).broadcast( "������������ ������� ����� " +
										seconds + " ������" );
								break;
							}

							lastSecond = seconds;
						}
					}
				}
			}
		}, 20, 5 );
	}
}
