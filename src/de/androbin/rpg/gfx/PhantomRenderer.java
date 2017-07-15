package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import de.androbin.rpg.phantom.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class PhantomRenderer extends Renderer {
  protected final Phantom phantom;
  
  public PhantomRenderer( final Phantom phantom ) {
    this.phantom = phantom;
  }
  
  @ Override
  public Rectangle2D.Float getBounds() {
    final Point pos = phantom.pos;
    
    final PhantomData data = phantom.data;
    final BufferedImage image = data.image;
    final Dimension size = data.size;
    
    final float res = image.getWidth() / size.width;
    final float y = pos.y + size.height - image.getHeight() / res;
    final float height = image.getHeight() / res;
    
    return new Rectangle2D.Float( pos.x, y, size.width, height );
  }
  
  @ Override
  public void render( final Graphics2D g ) {
    final Rectangle2D.Float bounds = getBounds();
    
    bounds.x *= scale;
    bounds.y *= scale;
    bounds.width *= scale;
    bounds.height *= scale;
    
    drawImage( g, phantom.data.image, bounds );
  }
}