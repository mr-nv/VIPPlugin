package me.mrnv.vip;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SafePosition
{
	public Location getPosition( Player player )
	{
		Location playerPosition = player.getLocation( );
		
		final int radius = 64;
		
		int xrand = 0;
		int zrand = 0;
		int y = 0;
		int tries = 0;
		
		do
		{
			tries++;
			xrand = ( int )playerPosition.getX( ) +
					radius + ( int )( Math.random( ) * ( radius - -radius ) + 0.5 );
			
			zrand = ( int )playerPosition.getZ( ) +
					radius + ( int )( Math.random( ) * ( radius - -radius ) + 0.5 );
			
			y = getSafeY( new Location( player.getWorld( ), xrand, 0, zrand ) );
		}
		while( y == -1 && tries < 100 );
		
		return ( new Location( player.getWorld( ), xrand, y, zrand ) );
	}
	
	private int getSafeY( Location location )
	{
		location.getChunk( ).load( true );
		
		int x = ( int )location.getX( );
		int y = 65;
		int z = ( int )location.getZ( );
		int blockid = 0;
		World world = location.getWorld( );
		
		for (int blockYid = world.getBlockTypeIdAt(x, y, z), blockY2id = world.getBlockTypeIdAt(x, y + 1, z); y < 256 && (blockYid != 0 || blockY2id != 0); ++y, blockYid = blockY2id, blockY2id = world.getBlockTypeIdAt(x, y + 1, z)) {}
		
		if( y == 256 )
			return -1;
        
        if (blockid == 8) {
        	return -1;
        }
        if (blockid == 9) {
        	return -1;
        }
        if (blockid == 10) {
        	return -1;
        }
        if (blockid == 11) {
        	return -1;
        }
        if (blockid == 51) {
        	return -1;
        }
        if (blockid == 18) {
        	return -1;
        }
        if( blockid == Material.LAVA.getId( ) ||
        	blockid == Material.STATIONARY_LAVA.getId( ) ||
        	blockid == Material.WATER.getId( ) ||
        	blockid == Material.STATIONARY_WATER.getId( ) )
        	return -1;
        blockid = world.getBlockTypeIdAt(x, y + 1, z);
        if (blockid == 81) {
            return -1;
        }
        if (blockid == 8) {
        	return -1;
        }
        if (blockid == 9) {
        	return -1;
        }
        if (blockid == 10) {
        	return -1;
        }
        if (blockid == 11) {
        	return -1;
        }
        if (blockid == 51) {
        	return -1;
        }
        if (blockid == 18) {
        	return -1;
        }
        if( blockid == Material.LAVA.getId( ) ||
            blockid == Material.STATIONARY_LAVA.getId( ) ||
            blockid == Material.WATER.getId( ) ||
            blockid == Material.STATIONARY_WATER.getId( ) )
            return -1;
        
        return y;
	}
}
