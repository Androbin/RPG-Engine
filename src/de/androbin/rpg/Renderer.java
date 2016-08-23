package de.androbin.rpg;

import de.androbin.gfx.*;
import java.awt.*;
import java.awt.geom.*;

public abstract class Renderer implements Renderable
{
	public final Rectangle2D.Float bounds;
	
	public Renderer()
	{
		this( new Rectangle2D.Float() );
	}
	
	public Renderer( final Rectangle2D.Float bounds )
	{
		this.bounds = bounds;
	}
	
	public static abstract class Decorator extends Renderer
	{
		private final Renderer renderer;
		
		public Decorator( final Renderer renderer )
		{
			super( renderer.bounds );
			this.renderer = renderer;
		}
		
		@ Override
		public final void render( final Graphics2D g )
		{
			renderer.render( g );
			decorate( g );
		}
		
		public abstract void decorate( final Graphics2D g );
	}
}