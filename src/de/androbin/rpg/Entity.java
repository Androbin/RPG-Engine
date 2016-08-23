package de.androbin.rpg;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Entity implements Interaction
{
	private final World					world;
	
	private int							x;
	private int							y;
	
	protected Direction					viewDir;
	
	protected Renderer					renderer;
	
	private final List<MoveListener>	moveListener;
	
	private Direction					moveDir;
	private float						moveProgress;
	
	public Entity( final World world, final int x, final int y )
	{
		this.world = world;
		
		this.x = x;
		this.y = y;
		
		this.viewDir = Direction.DOWN;
		
		this.moveListener = new ArrayList<>();
	}
	
	public final void addMoveListener( final MoveListener listener )
	{
		moveListener.add( listener );
	}
	
	protected final boolean canMove()
	{
		return canMove( viewDir );
	}
	
	protected boolean canMove( final Direction dir )
	{
		final Tile tile = nextTile( dir );
		return tile != null && tile.data.passable && tile.reservationType() == null;
	}
	
	protected final Object interact( final Object ... args )
	{
		final Tile tile = nextTile();
		return tile == null ? null : tile.interact( getClass(), args );
	}
	
	private final void doMove()
	{
		getTile().reserve( null );
		
		x += moveDir.dx;
		y += moveDir.dy;
		
		moveDir = null;
		moveListener.forEach( MoveListener::onMoved );
	}
	
	public final float getPX()
	{
		return moveDir == null ? x : x + moveDir.dx * moveProgress;
	}
	
	public final float getPY()
	{
		return moveDir == null ? y : y + moveDir.dy * moveProgress;
	}
	
	public Direction getMoveDir()
	{
		return moveDir;
	}
	
	public float getMoveProgress()
	{
		return moveProgress;
	}
	
	private final Tile getTile()
	{
		return world.getTile( x, y );
	}
	
	public Direction getViewDir()
	{
		return viewDir;
	}
	
	public final int getX()
	{
		return x;
	}
	
	public final int getY()
	{
		return y;
	}
	
	protected boolean move( final Direction dir )
	{
		viewDir = dir;
		
		if ( !canMove() )
		{
			return false;
		}
		
		final Tile tile = nextTile();
		
		if ( !tile.reserve( this ) )
		{
			return false;
		}
		
		moveDir = dir;
		return true;
	}
	
	public abstract float moveSpeed();
	
	private final Tile nextTile()
	{
		return nextTile( viewDir );
	}
	
	private final Tile nextTile( final Direction dir )
	{
		final int tx = x + dir.dx;
		final int ty = y + dir.dy;
		
		return world.getTile( tx, ty );
	}
	
	public final void render( final Graphics2D g, final float scale )
	{
		if ( renderer == null )
		{
			return;
		}
		
		final float px = getPX() * scale;
		final float py = getPY() * scale;
		
		g.translate( px, py );
		renderer.bounds.width = scale;
		renderer.bounds.height = scale;
		renderer.render( g );
		g.translate( -px, -py );
	}
	
	protected final Class< ? extends Entity> reservationType()
	{
		final Tile tile = nextTile();
		return tile == null ? null : tile.reservationType();
	}
	
	protected final void turn( final Direction dir )
	{
		viewDir = dir;
	}
	
	protected void update( final float delta )
	{
		if ( moveDir != null )
		{
			moveProgress += delta * moveSpeed();
			
			while ( moveProgress >= 1f && moveDir != null )
			{
				doMove();
				moveProgress--;
			}
			
			if ( moveDir == null )
			{
				moveProgress = 0f;
			}
		}
	}
}