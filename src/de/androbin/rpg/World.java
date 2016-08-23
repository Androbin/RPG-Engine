package de.androbin.rpg;

import java.awt.*;
import java.util.*;
import java.util.List;

public class World
{
	public final int				width;
	public final int				height;
	
	protected final Tile[]			tiles;
	protected final List<Entity>	entities;
	
	public World( final int width, final int height )
	{
		this.width = width;
		this.height = height;
		
		this.tiles = new Tile[ width * height ];
		this.entities = new ArrayList<>();
	}
	
	public final void addEntity( final Entity entity )
	{
		getTile( entity.getX(), entity.getY() ).reserve( entity );
		entities.add( entity );
	}
	
	public final boolean checkBounds( final int x, final int y )
	{
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public final List<Entity> getEntities()
	{
		return Collections.unmodifiableList( entities );
	}
	
	public Tile getTile( final int x, final int y )
	{
		return checkBounds( x, y ) ? tiles[ y * width + x ] : null;
	}
	
	public final void removeEntity( final Entity entity )
	{
		entities.remove( entity );
		getTile( entity.getX(), entity.getY() ).reserve( null );
	}
	
	public final void render( final Graphics2D g, final Rectangle view, final float scale )
	{
		for ( int y = view.y; y <= view.y + view.height; y++ )
		{
			for ( int x = view.x; x <= view.x + view.width; x++ )
			{
				final Tile tile = getTile( x, y );
				
				if ( tile != null )
				{
					tile.render( g, scale );
				}
			}
		}
		
		for ( final Entity entity : getEntities() )
		{
			entity.render( g, scale );
		}
	}
	
	public final boolean setTile( final int x, final int y, final Tile tile )
	{
		if ( !checkBounds( x, y ) )
		{
			return false;
		}
		
		tiles[ y * width + x ] = tile;
		return true;
	}
}