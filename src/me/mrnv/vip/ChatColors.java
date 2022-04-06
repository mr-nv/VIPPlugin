package me.mrnv.vip;

import org.bukkit.ChatColor;

public class ChatColors
{
	private String colorName;
	private ChatColor chatColor;
	
	public ChatColors( String colorName, ChatColor chatColor )
	{
		this.colorName = colorName;
		this.chatColor = chatColor;
	}
	
	public String getColorName( )
	{
		return this.colorName;
	}
	
	public ChatColor getColor( )
	{
		return this.chatColor;
	}
}