package de.androbin.rpg;

import java.awt.event.*;

public enum Direction
{
	UP( 0, -1 ),
	LEFT( -1, 0 ),
	DOWN( 0, 1 ),
	RIGHT( 1, 0 );
	
	public final int	dx;
	public final int	dy;
	
	private Direction( final int dx, final int dy )
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public static Direction bestFit( final float dx, final float dy )
	{
		final float ax = Math.abs( dx );
		final float ay = Math.abs( dy );
		
		if ( ax < 0.5f && ay < 0.5f )
		{
			return null;
		}
		
		if ( ax >= ay )
		{
			return dx >= 0f ? Direction.RIGHT : Direction.LEFT;
		}
		else
		{
			return dy >= 0f ? Direction.DOWN : Direction.UP;
		}
	}
	
	public static Direction byKeyCode( final int keycode )
	{
		switch ( keycode )
		{
			case KeyEvent.VK_UP :
			case KeyEvent.VK_W :
				return UP;
			
			case KeyEvent.VK_LEFT :
			case KeyEvent.VK_A :
				return LEFT;
			
			case KeyEvent.VK_DOWN :
			case KeyEvent.VK_S :
				return DOWN;
			
			case KeyEvent.VK_RIGHT :
			case KeyEvent.VK_D :
				return RIGHT;
		}
		
		return null;
	}
	
	public Direction opposite()
	{
		return values()[ ( ordinal() + 2 ) % 4 ];
	}
}