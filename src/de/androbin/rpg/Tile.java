package de.androbin.rpg;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;

public class Tile
{
	public final int		x;
	public final int		y;
	
	public final TileData	data;
	
	private Entity			reservation;
	
	public Tile( final int x, final int y, final TileData data )
	{
		this.x = x;
		this.y = y;
		
		this.data = data;
	}
	
	public final Object interact( final Class< ? extends Entity> type, final Object ... args )
	{
		return reservation == null ? null : reservation.onInteract( type, args );
	}
	
	public void render( final Graphics2D g, final float scale )
	{
		final float px = x * scale;
		final float py = y * scale;
		
		drawImage( g, data.image, px, py, scale, scale );
	}
	
	public final boolean reserve( final Entity entity )
	{
		if ( reservation != null && reservation != entity && entity != null )
		{
			return false;
		}
		
		reservation = entity;
		return true;
	}
	
	public final Class< ? extends Entity> reservationType()
	{
		return reservation == null ? null : reservation.getClass();
	}
	
	public interface Factory
	{
		Tile build( final int x, final int y, final TileData data );
	}
}