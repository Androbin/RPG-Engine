package de.androbin.rpg.obj;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import de.androbin.rpg.gfx.*;

public class GameObject implements Sprite
{
	public final GameObjectData	data;
								
	public final Point			pos;
								
	public GameObject( final Point pos )
	{
		this( null, pos );
	}
	
	public GameObject( final GameObjectData data, final Point pos )
	{
		this.data = data;
		this.pos = pos;
	}
	
	@ Override
	public Rectangle getBounds()
	{
		return new Rectangle( pos, data.size );
	}
	
	@ Override
	public Rectangle2D.Float getViewBounds()
	{
		final int res = data.image.getWidth() / data.size.width;
		
		final float x = pos.x;
		final float y = ( pos.y + data.size.height - data.image.getHeight() / res );
		
		final float w = data.size.width;
		final float h = (float) data.image.getHeight() / res;
		
		return new Rectangle2D.Float( x, y, w, h );
	}
	
	public void render( final Graphics2D g, final float scale )
	{
		final Rectangle2D.Float bounds = getViewBounds();
		
		final float px = bounds.x * scale;
		final float py = bounds.y * scale;
		
		final float pw = bounds.width * scale;
		final float ph = bounds.height * scale;
		
		drawImage( g, data.image, px, py, pw, ph );
	}
	
	public interface Builder
	{
		GameObject build( final GameObjectData data, final Point pos );
	}
}