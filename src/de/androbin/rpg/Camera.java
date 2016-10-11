package de.androbin.rpg;

import static de.androbin.math.util.floats.FloatMathUtil.*;
import java.awt.geom.*;
import java.util.function.*;

public final class Camera
{
	private Supplier<Point2D.Float>	focus;
	private Supplier<Point2D.Float>	nextFocus;
									
	private float					moveProgress;
	private float					moveSpeed;
									
	public Camera()
	{
	}
	
	public Camera( final Supplier<Point2D.Float> supplier )
	{
		this.focus = supplier;
	}
	
	public Camera( final Point2D.Float point )
	{
		this( focus( point ) );
	}
	
	public Camera( final Entity entity )
	{
		this( focus( entity ) );
	}
	
	private float breakpoint()
	{
		final Point2D.Float current = getCurrentFocus();
		final Point2D.Float next = getNextFocus();
		
		final float dx = next.x - current.x;
		final float dy = next.y - current.y;
		
		return dx / ( dx + dy );
	}
	
	protected float calcTranslationX( final float width, final float worldWidth, final float scale )
	{
		float dx = 0f;
		
		if ( worldWidth > width )
		{
			final Point2D.Float currentFocus = getCurrentFocus();
			final float ix = nextFocus == null ? currentFocus.x
			        : moveProgress < breakpoint() ? interpolate( currentFocus.x, moveProgress * 1 / breakpoint(), getNextFocus().x ) : getNextFocus().x;
			dx = bound( width - worldWidth, 0.5f * width - scale * ix, 0f );
		}
		else
		{
			dx = 0.5f * ( width - worldWidth );
		}
		
		return dx;
	}
	
	protected float calcTranslationY( final float height, final float worldHeight, final float scale )
	{
		float dy = 0f;
		
		if ( worldHeight > height )
		{
			final Point2D.Float currentFocus = getCurrentFocus();
			final float iy = nextFocus == null ? currentFocus.y
			        : moveProgress >= breakpoint() ? interpolate( currentFocus.y, ( moveProgress - breakpoint() ) * 1 / ( 1 - breakpoint() ), getNextFocus().y ) : currentFocus.y;
			dy = bound( height - worldHeight, 0.5f * height - scale * iy, 0f );
		}
		else
		{
			dy = 0.5f * ( height - worldHeight );
		}
		
		return dy;
	}
	
	public static Supplier<Point2D.Float> focus( final Entity entity )
	{
		return () -> new Point2D.Float( entity.getPX(), entity.getPY() );
	}
	
	public static Supplier<Point2D.Float> focus( final Point2D.Float point )
	{
		return () -> point;
	}
	
	public Point2D.Float getCurrentFocus()
	{
		return focus.get();
	}
	
	public Point2D.Float getNextFocus()
	{
		return nextFocus.get();
	}
	
	public void moveFocus( final Supplier<Point2D.Float> nextFocus, final float moveSpeed )
	{
		this.moveSpeed = moveSpeed;
		this.moveProgress = 0f;
		
		final Point2D.Float current = focus.get();
		this.focus = () -> current;
		
		this.nextFocus = nextFocus;
	}
	
	public void setFocus( final Supplier<Point2D.Float> focus )
	{
		this.moveProgress = 0f;
		this.focus = focus;
	}
	
	public void update( final float delta )
	{
		if ( nextFocus != null )
		{
			moveProgress += delta * moveSpeed;
			
			if ( moveProgress >= 1f )
			{
				moveProgress = 0f;
				
				focus = nextFocus;
				nextFocus = null;
			}
		}
	}
}