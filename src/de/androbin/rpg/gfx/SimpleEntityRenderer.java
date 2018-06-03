package de.androbin.rpg.gfx;

import static de.androbin.gfx.util.GraphicsUtil.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import de.androbin.rpg.*;
import de.androbin.rpg.entity.*;
import de.androbin.rpg.gfx.sheet.*;

public class SimpleEntityRenderer<E extends Entity> implements EntityRenderer<E> {
  @ Override
  public Rectangle2D.Float getBounds( final EntityData data, final Point2D.Float pos,
      final Dimension rawSize ) {
    final Dimension size = data.size;
    
    final float res = Globals.get().res;
    
    final float width = rawSize.width / res;
    final float height = rawSize.height / res;
    
    final float x = pos.x - data.sheetDX / res;
    final float y = pos.y + size.height - height - data.sheetDY / res;
    
    return new Rectangle2D.Float( x, y, width, height );
  }
  
  @ Override
  public void render( final Graphics2D g, final E entity, final Point2D.Float pos,
      final Rectangle2D.Float view, final float scale ) {
    final EntityData data = entity.getData();
    final Dimension rawSize = data.sheet.rawSize;
    
    final BufferedImage image = Sheets.getImage( entity, scale );
    final Rectangle2D.Float bounds = getBounds( data, pos, rawSize );
    
    bounds.x *= scale;
    bounds.y *= scale;
    bounds.width *= scale;
    bounds.height *= scale;
    
    drawImage( g, image, bounds );
  }
}