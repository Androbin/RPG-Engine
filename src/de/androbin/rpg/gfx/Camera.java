package de.androbin.rpg.gfx;

import static de.androbin.math.util.floats.FloatMathUtil.*;
import java.awt.geom.*;
import java.util.function.*;
import de.androbin.rpg.*;

public final class Camera
{
	private Supplier<Point2D.Float>	currentFocus;
	private Supplier<Point2D.Float>	nextFocus;
									
	private float					moveProgress;
	private float					moveSpeed;
									
	public Camera()
	{
	}
	
	public Camera( final Point2D.Float point )
	{
		this( focus( point ) );
	}
	
	public Camera( final Entity entity )
	{
		this( focus( entity ) );
	}
	
	public Camera( final Supplier<Point2D.Float> supplier )
	{
		this.currentFocus = supplier;
	}
	
	private float breakpoint()
	{
		final Point2D.Float current = getCurrentFocus();
		final Point2D.Float next = getNextFocus();
		
		final float dx = next.x - current.x;
		final float dy = next.y - current.y;
		
		return dx / ( dx + dy );
	}
	
	public float calcTranslationX( final float width, final float worldWidth, final float scale )
	{
		if ( worldWidth <= width )
		{
			return 0.5f * ( width - worldWidth );
		}
		
		if ( currentFocus == null )
		{
			return 0f;
		}
		
		final Point2D.Float focus = this.currentFocus.get();
		final float ix = nextFocus == null ? focus.x
		        : moveProgress < breakpoint() ? inter( focus.x, moveProgress * 1 / breakpoint(), getNextFocus().x ) : getNextFocus().x;
		return bound( width - worldWidth, 0.5f * width - scale * ix, 0f );
	}
	
	public float calcTranslationY( final float height, final float worldHeight, final float scale )
	{
		if ( worldHeight <= height )
		{
			return 0.5f * ( height - worldHeight );
		}
		
		if ( currentFocus == null )
		{
			return 0f;
		}
		
		final Point2D.Float currentFocus = getCurrentFocus();
		final float iy = nextFocus == null ? currentFocus.y
		        : moveProgress >= breakpoint() ? inter( currentFocus.y, ( moveProgress - breakpoint() ) * 1 / ( 1 - breakpoint() ), getNextFocus().y ) : currentFocus.y;
		return bound( height - worldHeight, 0.5f * height - scale * iy, 0f );
	}
	
	public static Supplier<Point2D.Float> focus( final Entity entity )
	{
		return () -> new Point2D.Float( entity.getFloatPos().x, entity.getFloatPos().y );
	}
	
	public static Supplier<Point2D.Float> focus( final Point2D.Float point )
	{
		return () -> point;
	}
	
	public Point2D.Float getCurrentFocus()
	{
		return currentFocus.get();
	}
	
	public Point2D.Float getNextFocus()
	{
		return nextFocus.get();
	}
	
	public void moveFocus( final Supplier<Point2D.Float> nextFocus, final float moveSpeed )
	{
		this.moveSpeed = moveSpeed;
		this.moveProgress = 0f;
		
		final Point2D.Float current = currentFocus.get();
		this.currentFocus = () -> current;
		
		this.nextFocus = nextFocus;
	}
	
	public void setFocus( final Supplier<Point2D.Float> focus )
	{
		this.moveProgress = 0f;
		this.currentFocus = focus;
	}
	
	public void update( final float delta )
	{
		if ( nextFocus != null )
		{
			moveProgress += delta * moveSpeed;
			
			if ( moveProgress >= 1f )
			{
				moveProgress = 0f;
				
				currentFocus = nextFocus;
				nextFocus = null;
			}
		}
	}
}