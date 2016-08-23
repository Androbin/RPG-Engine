package de.androbin.rpg;

import static de.androbin.math.util.floats.FloatMathUtil.*;
import de.androbin.game.*;
import java.awt.*;
import java.awt.event.*;

public class RPGScreen extends Screen
{
	protected World		world;
	private Entity		player;
	
	private Direction	requestedDir;
	
	private float		dx;
	private float		dy;
	
	protected float		scale;
	
	public RPGScreen( final Game game, final float scale )
	{
		super( game );
		this.scale = scale;
	}
	
	private void calcTranslation()
	{
		final float pw = scale * world.width;
		final float ph = scale * world.height;
		
		if ( pw > getWidth() )
		{
			dx = bound( getWidth() - pw, 0.5f * getWidth() - scale * player.getPX(), 0f );
		}
		else
		{
			dx = 0.5f * ( getWidth() - pw );
		}
		
		if ( ph > getHeight() )
		{
			dy = bound( getHeight() - ph, 0.5f * getHeight() - scale * player.getPY(), 0f );
		}
		else
		{
			dy = 0.5f * ( getHeight() - ph );
		}
	}
	
	public Entity getPlayer()
	{
		return player;
	}
	
	@ Override
	public KeyListener getKeyListener()
	{
		return new KeyAdapter()
		{
			@ Override
			public void keyPressed( final KeyEvent event )
			{
				final int keycode = event.getKeyCode();
				final Direction dir = Direction.byKeyCode( keycode );
				
				if ( dir != null )
				{
					requestDir( dir );
				}
			}
		};
	}
	
	public float getTranslationX()
	{
		return dx;
	}
	
	public float getTranslationY()
	{
		return dy;
	}
	
	@ Override
	public void render( final Graphics2D g )
	{
		g.setColor( Color.BLACK );
		g.fillRect( 0, 0, getWidth(), getHeight() );
		
		if ( world == null )
		{
			return;
		}
		
		g.translate( dx, dy );
		
		final int startY = Math.max( 0, (int) ( -dy / scale ) );
		final int endY = Math.min( (int) ( ( getHeight() - dy ) / scale ), world.height - 1 );
		
		final int startX = Math.max( 0, (int) ( -dx / scale ) );
		final int endX = Math.min( (int) ( ( getWidth() - dx ) / scale ), world.width - 1 );
		
		world.render( g, new Rectangle( startX, startY, endX - startX, endY - startY ), scale );
		
		g.translate( -dx, -dy );
	}
	
	public void requestDir( final Direction dir )
	{
		if ( player.getMoveDir() == null )
		{
			player.move( dir );
		}
		else
		{
			this.requestedDir = dir;
		}
	}
	
	public void setPlayer( final Entity player )
	{
		player.addMoveListener( this::onPlayerMoved );
		this.player = player;
	}
	
	protected void onPlayerMoved()
	{
		if ( requestedDir != null )
		{
			player.move( requestedDir );
			requestedDir = null;
		}
	}
	
	@ Override
	protected void onResized( final int width, final int height )
	{
	}
	
	@ Override
	protected void update( final float delta )
	{
		if ( world == null )
		{
			return;
		}
		
		for ( final Entity entity : world.getEntities() )
		{
			entity.update( delta );
		}
		
		calcTranslation();
	}
}