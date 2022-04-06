package me.mrnv.vip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration
{
	private VIPPlugin plugin;
	private File fileConfig;
	private FileConfiguration config;
	
	public Configuration( VIPPlugin plugin )
	{
		this.plugin = plugin;
	}
	
	public void initialize( )
	{
		fileConfig = new File( plugin.dataFolder, "config.yml" );
		
		try
		{
			if( !fileConfig.exists( ) )
			{
				fileConfig.getParentFile( ).mkdirs( );
				saveDefaults( );
			}
			
			config = new YamlConfiguration( );
			config.load( fileConfig );
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	public void onDisable( )
	{
		try
		{
			config.save( fileConfig );
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
	
	public FileConfiguration getConfig( )
	{
		return config;
	}
	
	private void saveDefaults( )
	{
		if( fileConfig == null ) return;
		
		try
		{
			FileWriter writer = new FileWriter( fileConfig );
			writer.write( "config:\n" );
			writer.write( "  chunk-wither-limit: 4\n" );
			writer.write( "  spawn-radius: 250\n" );
			writer.write( "  max-withers-in-spawn-radius: 40\n" );
			writer.write( "  world-border-minimum-x: -1000000\n" );
			writer.write( "  world-border-minimum-z: -1000000\n" );
			writer.write( "  world-border-maximum-x: 1000000\n" );
			writer.write( "  world-border-maximum-z: 1000000\n" );
			writer.write( "  anti-illegals: true\n" );
			writer.close( );
		}
		catch( Exception exception )
		{
			exception.printStackTrace( );
		}
	}
}
