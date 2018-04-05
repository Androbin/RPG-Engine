package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.sheet.*;

public class EntityRenderer<E extends Entity> {
  public final Rectangle2D.Float getBounds( final E entity ) {
    return getBounds( entity, entity.getFloatPos() );
  }
  
  public final Rectangle2D.Float getBounds( final E entity, final Point2D.Float pos ) {
    return getBounds( entity.data, Sheets.getImage( entity ), pos );
  }
  
  public Rectangle2D.Float getBounds( final EntityData data, final BufferedImage image,
      final Point2D.Float pos ) {
    final Dimension size = data.size;
    
    final float res = Globals.get().res;
    
    final float width = image.getWidth() / res;
    final float height = image.getHeight() / res;
    
    final float x = pos.x - data.sheetDX / res;
    final float y = pos.y + size.height - height - data.sheetDY / res;
    
    return new Rectangle2D.Float( x, y, width, height );
  }
  
  public final void render( final Graphics2D g, final E entity, final float scale ) {
    render( g, entity, entity.getFloatPos(), scale );
  }
  
  public void render( final Graphics2D g, final E entity,
      final Point2D.Float pos, final float scale ) {
    final BufferedImage image = Sheets.getImage( entity );
    final Rectangle2D.Float bounds = getBounds( entity.data, image, pos );
    
    bounds.x *= scale;
    bounds.y *= scale;
    bounds.width *= scale;
    bounds.height *= scale;
    
    drawImage( g, image, bounds );
  }
}