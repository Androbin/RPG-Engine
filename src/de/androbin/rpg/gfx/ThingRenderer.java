package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import de.androbin.rpg.thing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class ThingRenderer implements Renderer {
  protected final Thing thing;
  
  public ThingRenderer( final Thing thing ) {
    this.thing = thing;
  }
  
  @ Override
  public Rectangle2D.Float getBounds( final Point2D.Float pos ) {
    final ThingData data = thing.data;
    final BufferedImage image = data.image;
    final Dimension size = data.size;
    
    final float res = image.getWidth() / size.width;
    final float y = pos.y + size.height - image.getHeight() / res;
    final float height = image.getHeight() / res;
    
    return new Rectangle2D.Float( pos.x, y, size.width, height );
  }
  
  @ Override
  public void render( final Graphics2D g, final Point2D.Float pos, final float scale ) {
    final Rectangle2D.Float bounds = getBounds( pos );
    
    bounds.x *= scale;
    bounds.y *= scale;
    bounds.width *= scale;
    bounds.height *= scale;
    
    drawImage( g, thing.data.image, bounds );
  }
}